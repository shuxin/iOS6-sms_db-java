/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siata.sms.ios6.jpa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.siata.sms.ios6.Attachment;
import com.siata.sms.ios6.Message;
import com.siata.sms.ios6.jpa.exceptions.NonexistentEntityException;
import com.siata.sms.ios6.jpa.exceptions.PreexistingEntityException;

/**
 * 
 * @author m.siatkowski
 */
public class AttachmentJpaController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AttachmentJpaController(EntityManagerFactory emf) {
		this.emf = emf;
	}

	private EntityManagerFactory emf = null;

	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public void create(Attachment attachment)
			throws PreexistingEntityException, Exception {
		if (attachment.getMessageList() == null) {
			attachment.setMessageList(new ArrayList<Message>());
		}
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			List<Message> attachedMessageList = new ArrayList<Message>();
			for (Message messageListMessageToAttach : attachment
					.getMessageList()) {
				messageListMessageToAttach = em.getReference(
						messageListMessageToAttach.getClass(),
						messageListMessageToAttach.getRowid());
				attachedMessageList.add(messageListMessageToAttach);
			}
			attachment.setMessageList(attachedMessageList);
			em.persist(attachment);
			for (Message messageListMessage : attachment.getMessageList()) {
				messageListMessage.getAttachmentList().add(attachment);
				messageListMessage = em.merge(messageListMessage);
			}
			em.getTransaction().commit();
		} catch (Exception ex) {
			if (findAttachment(attachment.getRowid()) != null) {
				throw new PreexistingEntityException("Attachment " + attachment
						+ " already exists.", ex);
			}
			throw ex;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void edit(Attachment attachment) throws NonexistentEntityException,
			Exception {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			Attachment persistentAttachment = em.find(Attachment.class,
					attachment.getRowid());
			List<Message> messageListOld = persistentAttachment
					.getMessageList();
			List<Message> messageListNew = attachment.getMessageList();
			List<Message> attachedMessageListNew = new ArrayList<Message>();
			for (Message messageListNewMessageToAttach : messageListNew) {
				messageListNewMessageToAttach = em.getReference(
						messageListNewMessageToAttach.getClass(),
						messageListNewMessageToAttach.getRowid());
				attachedMessageListNew.add(messageListNewMessageToAttach);
			}
			messageListNew = attachedMessageListNew;
			attachment.setMessageList(messageListNew);
			attachment = em.merge(attachment);
			for (Message messageListOldMessage : messageListOld) {
				if (!messageListNew.contains(messageListOldMessage)) {
					messageListOldMessage.getAttachmentList()
							.remove(attachment);
					messageListOldMessage = em.merge(messageListOldMessage);
				}
			}
			for (Message messageListNewMessage : messageListNew) {
				if (!messageListOld.contains(messageListNewMessage)) {
					messageListNewMessage.getAttachmentList().add(attachment);
					messageListNewMessage = em.merge(messageListNewMessage);
				}
			}
			em.getTransaction().commit();
		} catch (Exception ex) {
			String msg = ex.getLocalizedMessage();
			if (msg == null || msg.length() == 0) {
				Integer id = attachment.getRowid();
				if (findAttachment(id) == null) {
					throw new NonexistentEntityException(
							"The attachment with id " + id
									+ " no longer exists.");
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
			Attachment attachment;
			try {
				attachment = em.getReference(Attachment.class, id);
				attachment.getRowid();
			} catch (EntityNotFoundException enfe) {
				throw new NonexistentEntityException("The attachment with id "
						+ id + " no longer exists.", enfe);
			}
			List<Message> messageList = attachment.getMessageList();
			for (Message messageListMessage : messageList) {
				messageListMessage.getAttachmentList().remove(attachment);
				messageListMessage = em.merge(messageListMessage);
			}
			em.remove(attachment);
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public List<Attachment> findAttachmentEntities() {
		return findAttachmentEntities(true, -1, -1);
	}

	public List<Attachment> findAttachmentEntities(int maxResults,
			int firstResult) {
		return findAttachmentEntities(false, maxResults, firstResult);
	}

	private List<Attachment> findAttachmentEntities(boolean all,
			int maxResults, int firstResult) {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			cq.select(cq.from(Attachment.class));
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

	public Attachment findAttachment(Integer id) {
		EntityManager em = getEntityManager();
		try {
			return em.find(Attachment.class, id);
		} finally {
			em.close();
		}
	}

	public int getAttachmentCount() {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			Root<Attachment> rt = cq.from(Attachment.class);
			cq.select(em.getCriteriaBuilder().count(rt));
			Query q = em.createQuery(cq);
			return ((Long) q.getSingleResult()).intValue();
		} finally {
			em.close();
		}
	}

}
