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
import com.siata.smsmerge.ios6.Chat;
import com.siata.smsmerge.ios6.ChatMessageJoin;
import com.siata.smsmerge.ios6.Message;
import com.siata.smsmerge.ios6.jpa.exceptions.NonexistentEntityException;
import com.siata.smsmerge.ios6.jpa.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author m.siatkowski
 */
public class ChatMessageJoinJpaController implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ChatMessageJoinJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ChatMessageJoin chatMessageJoin) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Chat chatId = chatMessageJoin.getChatId();
            if (chatId != null) {
                chatId = em.getReference(chatId.getClass(), chatId.getRowid());
                chatMessageJoin.setChatId(chatId);
            }
            Message message = chatMessageJoin.getMessage();
            if (message != null) {
                message = em.getReference(message.getClass(), message.getRowid());
                chatMessageJoin.setMessage(message);
            }
            em.persist(chatMessageJoin);
            if (chatId != null) {
                chatId.getChatMessageJoinList().add(chatMessageJoin);
                chatId = em.merge(chatId);
            }
            if (message != null) {
                ChatMessageJoin oldChatMessageJoinOfMessage = message.getChatMessageJoin();
                if (oldChatMessageJoinOfMessage != null) {
                    oldChatMessageJoinOfMessage.setMessage(null);
                    oldChatMessageJoinOfMessage = em.merge(oldChatMessageJoinOfMessage);
                }
                message.setChatMessageJoin(chatMessageJoin);
                message = em.merge(message);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findChatMessageJoin(chatMessageJoin.getMessageId()) != null) {
                throw new PreexistingEntityException("ChatMessageJoin " + chatMessageJoin + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ChatMessageJoin chatMessageJoin) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ChatMessageJoin persistentChatMessageJoin = em.find(ChatMessageJoin.class, chatMessageJoin.getMessageId());
            Chat chatIdOld = persistentChatMessageJoin.getChatId();
            Chat chatIdNew = chatMessageJoin.getChatId();
            Message messageOld = persistentChatMessageJoin.getMessage();
            Message messageNew = chatMessageJoin.getMessage();
            if (chatIdNew != null) {
                chatIdNew = em.getReference(chatIdNew.getClass(), chatIdNew.getRowid());
                chatMessageJoin.setChatId(chatIdNew);
            }
            if (messageNew != null) {
                messageNew = em.getReference(messageNew.getClass(), messageNew.getRowid());
                chatMessageJoin.setMessage(messageNew);
            }
            chatMessageJoin = em.merge(chatMessageJoin);
            if (chatIdOld != null && !chatIdOld.equals(chatIdNew)) {
                chatIdOld.getChatMessageJoinList().remove(chatMessageJoin);
                chatIdOld = em.merge(chatIdOld);
            }
            if (chatIdNew != null && !chatIdNew.equals(chatIdOld)) {
                chatIdNew.getChatMessageJoinList().add(chatMessageJoin);
                chatIdNew = em.merge(chatIdNew);
            }
            if (messageOld != null && !messageOld.equals(messageNew)) {
                messageOld.setChatMessageJoin(null);
                messageOld = em.merge(messageOld);
            }
            if (messageNew != null && !messageNew.equals(messageOld)) {
                ChatMessageJoin oldChatMessageJoinOfMessage = messageNew.getChatMessageJoin();
                if (oldChatMessageJoinOfMessage != null) {
                    oldChatMessageJoinOfMessage.setMessage(null);
                    oldChatMessageJoinOfMessage = em.merge(oldChatMessageJoinOfMessage);
                }
                messageNew.setChatMessageJoin(chatMessageJoin);
                messageNew = em.merge(messageNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = chatMessageJoin.getMessageId();
                if (findChatMessageJoin(id) == null) {
                    throw new NonexistentEntityException("The chatMessageJoin with id " + id + " no longer exists.");
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
            ChatMessageJoin chatMessageJoin;
            try {
                chatMessageJoin = em.getReference(ChatMessageJoin.class, id);
                chatMessageJoin.getMessageId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The chatMessageJoin with id " + id + " no longer exists.", enfe);
            }
            Chat chatId = chatMessageJoin.getChatId();
            if (chatId != null) {
                chatId.getChatMessageJoinList().remove(chatMessageJoin);
                chatId = em.merge(chatId);
            }
            Message message = chatMessageJoin.getMessage();
            if (message != null) {
                message.setChatMessageJoin(null);
                message = em.merge(message);
            }
            em.remove(chatMessageJoin);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ChatMessageJoin> findChatMessageJoinEntities() {
        return findChatMessageJoinEntities(true, -1, -1);
    }

    public List<ChatMessageJoin> findChatMessageJoinEntities(int maxResults, int firstResult) {
        return findChatMessageJoinEntities(false, maxResults, firstResult);
    }

    private List<ChatMessageJoin> findChatMessageJoinEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ChatMessageJoin.class));
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

    public ChatMessageJoin findChatMessageJoin(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ChatMessageJoin.class, id);
        } finally {
            em.close();
        }
    }

    public int getChatMessageJoinCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ChatMessageJoin> rt = cq.from(ChatMessageJoin.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
