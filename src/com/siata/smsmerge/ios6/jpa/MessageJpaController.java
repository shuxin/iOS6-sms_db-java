/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siata.smsmerge.ios6.jpa;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.siata.smsmerge.ios6.ChatMessageJoin;
import com.siata.smsmerge.ios6.Attachment;
import com.siata.smsmerge.ios6.Message;
import com.siata.smsmerge.ios6.jpa.exceptions.NonexistentEntityException;
import com.siata.smsmerge.ios6.jpa.exceptions.PreexistingEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author m.siatkowski
 */
public class MessageJpaController implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MessageJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Message message) throws PreexistingEntityException, Exception {
        if (message.getAttachmentList() == null) {
            message.setAttachmentList(new ArrayList<Attachment>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ChatMessageJoin chatMessageJoin = message.getChatMessageJoin();
            if (chatMessageJoin != null) {
                chatMessageJoin = em.getReference(chatMessageJoin.getClass(), chatMessageJoin.getMessageId());
                message.setChatMessageJoin(chatMessageJoin);
            }
            List<Attachment> attachedAttachmentList = new ArrayList<Attachment>();
            for (Attachment attachmentListAttachmentToAttach : message.getAttachmentList()) {
                attachmentListAttachmentToAttach = em.getReference(attachmentListAttachmentToAttach.getClass(), attachmentListAttachmentToAttach.getRowid());
                attachedAttachmentList.add(attachmentListAttachmentToAttach);
            }
            message.setAttachmentList(attachedAttachmentList);
            em.persist(message);
            if (chatMessageJoin != null) {
                Message oldMessageOfChatMessageJoin = chatMessageJoin.getMessage();
                if (oldMessageOfChatMessageJoin != null) {
                    oldMessageOfChatMessageJoin.setChatMessageJoin(null);
                    oldMessageOfChatMessageJoin = em.merge(oldMessageOfChatMessageJoin);
                }
                chatMessageJoin.setMessage(message);
                chatMessageJoin = em.merge(chatMessageJoin);
            }
            for (Attachment attachmentListAttachment : message.getAttachmentList()) {
                attachmentListAttachment.getMessageList().add(message);
                attachmentListAttachment = em.merge(attachmentListAttachment);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMessage(message.getRowid()) != null) {
                throw new PreexistingEntityException("Message " + message + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Message message) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Message persistentMessage = em.find(Message.class, message.getRowid());
            ChatMessageJoin chatMessageJoinOld = persistentMessage.getChatMessageJoin();
            ChatMessageJoin chatMessageJoinNew = message.getChatMessageJoin();
            List<Attachment> attachmentListOld = persistentMessage.getAttachmentList();
            List<Attachment> attachmentListNew = message.getAttachmentList();
            if (chatMessageJoinNew != null) {
                chatMessageJoinNew = em.getReference(chatMessageJoinNew.getClass(), chatMessageJoinNew.getMessageId());
                message.setChatMessageJoin(chatMessageJoinNew);
            }
            List<Attachment> attachedAttachmentListNew = new ArrayList<Attachment>();
            for (Attachment attachmentListNewAttachmentToAttach : attachmentListNew) {
                attachmentListNewAttachmentToAttach = em.getReference(attachmentListNewAttachmentToAttach.getClass(), attachmentListNewAttachmentToAttach.getRowid());
                attachedAttachmentListNew.add(attachmentListNewAttachmentToAttach);
            }
            attachmentListNew = attachedAttachmentListNew;
            message.setAttachmentList(attachmentListNew);
            message = em.merge(message);
            if (chatMessageJoinOld != null && !chatMessageJoinOld.equals(chatMessageJoinNew)) {
                chatMessageJoinOld.setMessage(null);
                chatMessageJoinOld = em.merge(chatMessageJoinOld);
            }
            if (chatMessageJoinNew != null && !chatMessageJoinNew.equals(chatMessageJoinOld)) {
                Message oldMessageOfChatMessageJoin = chatMessageJoinNew.getMessage();
                if (oldMessageOfChatMessageJoin != null) {
                    oldMessageOfChatMessageJoin.setChatMessageJoin(null);
                    oldMessageOfChatMessageJoin = em.merge(oldMessageOfChatMessageJoin);
                }
                chatMessageJoinNew.setMessage(message);
                chatMessageJoinNew = em.merge(chatMessageJoinNew);
            }
            for (Attachment attachmentListOldAttachment : attachmentListOld) {
                if (!attachmentListNew.contains(attachmentListOldAttachment)) {
                    attachmentListOldAttachment.getMessageList().remove(message);
                    attachmentListOldAttachment = em.merge(attachmentListOldAttachment);
                }
            }
            for (Attachment attachmentListNewAttachment : attachmentListNew) {
                if (!attachmentListOld.contains(attachmentListNewAttachment)) {
                    attachmentListNewAttachment.getMessageList().add(message);
                    attachmentListNewAttachment = em.merge(attachmentListNewAttachment);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = message.getRowid();
                if (findMessage(id) == null) {
                    throw new NonexistentEntityException("The message with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Message message;
            try {
                message = em.getReference(Message.class, id);
                message.getRowid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The message with id " + id + " no longer exists.", enfe);
            }
            ChatMessageJoin chatMessageJoin = message.getChatMessageJoin();
            if (chatMessageJoin != null) {
                chatMessageJoin.setMessage(null);
                chatMessageJoin = em.merge(chatMessageJoin);
            }
            List<Attachment> attachmentList = message.getAttachmentList();
            for (Attachment attachmentListAttachment : attachmentList) {
                attachmentListAttachment.getMessageList().remove(message);
                attachmentListAttachment = em.merge(attachmentListAttachment);
            }
            em.remove(message);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Message> findMessageEntities() {
        return findMessageEntities(true, -1, -1);
    }

    public List<Message> findMessageEntities(int maxResults, int firstResult) {
        return findMessageEntities(false, maxResults, firstResult);
    }

    private List<Message> findMessageEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Message.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Message findMessage(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Message.class, id);
        } finally {
            em.close();
        }
    }

    public int getMessageCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Message> rt = cq.from(Message.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
