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

import com.siata.sms.ios6.Chat;
import com.siata.sms.ios6.ChatMessageJoin;
import com.siata.sms.ios6.Handle;
import com.siata.sms.ios6.jpa.exceptions.NonexistentEntityException;
import com.siata.sms.ios6.jpa.exceptions.PreexistingEntityException;

/**
 * 
 * @author m.siatkowski
 */
public class ChatJpaController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ChatJpaController(EntityManagerFactory emf) {
		this.emf = emf;
	}

	private EntityManagerFactory emf = null;

	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public void create(Chat chat) throws PreexistingEntityException, Exception {
		if (chat.getHandleList() == null) {
			chat.setHandleList(new ArrayList<Handle>());
		}
		if (chat.getChatMessageJoinList() == null) {
			chat.setChatMessageJoinList(new ArrayList<ChatMessageJoin>());
		}
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			List<Handle> attachedHandleList = new ArrayList<Handle>();
			for (Handle handleListHandleToAttach : chat.getHandleList()) {
				handleListHandleToAttach = em.getReference(
						handleListHandleToAttach.getClass(),
						handleListHandleToAttach.getRowid());
				attachedHandleList.add(handleListHandleToAttach);
			}
			chat.setHandleList(attachedHandleList);
			List<ChatMessageJoin> attachedChatMessageJoinList = new ArrayList<ChatMessageJoin>();
			for (ChatMessageJoin chatMessageJoinListChatMessageJoinToAttach : chat
					.getChatMessageJoinList()) {
				chatMessageJoinListChatMessageJoinToAttach = em.getReference(
						chatMessageJoinListChatMessageJoinToAttach.getClass(),
						chatMessageJoinListChatMessageJoinToAttach
								.getMessageId());
				attachedChatMessageJoinList
						.add(chatMessageJoinListChatMessageJoinToAttach);
			}
			chat.setChatMessageJoinList(attachedChatMessageJoinList);
			em.persist(chat);
			for (Handle handleListHandle : chat.getHandleList()) {
				handleListHandle.getChatList().add(chat);
				handleListHandle = em.merge(handleListHandle);
			}
			for (ChatMessageJoin chatMessageJoinListChatMessageJoin : chat
					.getChatMessageJoinList()) {
				Chat oldChatIdOfChatMessageJoinListChatMessageJoin = chatMessageJoinListChatMessageJoin
						.getChatId();
				chatMessageJoinListChatMessageJoin.setChatId(chat);
				chatMessageJoinListChatMessageJoin = em
						.merge(chatMessageJoinListChatMessageJoin);
				if (oldChatIdOfChatMessageJoinListChatMessageJoin != null) {
					oldChatIdOfChatMessageJoinListChatMessageJoin
							.getChatMessageJoinList().remove(
									chatMessageJoinListChatMessageJoin);
					oldChatIdOfChatMessageJoinListChatMessageJoin = em
							.merge(oldChatIdOfChatMessageJoinListChatMessageJoin);
				}
			}
			em.getTransaction().commit();
		} catch (Exception ex) {
			if (findChat(chat.getRowid()) != null) {
				throw new PreexistingEntityException("Chat " + chat
						+ " already exists.", ex);
			}
			throw ex;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void edit(Chat chat) throws NonexistentEntityException, Exception {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			Chat persistentChat = em.find(Chat.class, chat.getRowid());
			List<Handle> handleListOld = persistentChat.getHandleList();
			List<Handle> handleListNew = chat.getHandleList();
			List<ChatMessageJoin> chatMessageJoinListOld = persistentChat
					.getChatMessageJoinList();
			List<ChatMessageJoin> chatMessageJoinListNew = chat
					.getChatMessageJoinList();
			List<Handle> attachedHandleListNew = new ArrayList<Handle>();
			for (Handle handleListNewHandleToAttach : handleListNew) {
				handleListNewHandleToAttach = em.getReference(
						handleListNewHandleToAttach.getClass(),
						handleListNewHandleToAttach.getRowid());
				attachedHandleListNew.add(handleListNewHandleToAttach);
			}
			handleListNew = attachedHandleListNew;
			chat.setHandleList(handleListNew);
			List<ChatMessageJoin> attachedChatMessageJoinListNew = new ArrayList<ChatMessageJoin>();
			for (ChatMessageJoin chatMessageJoinListNewChatMessageJoinToAttach : chatMessageJoinListNew) {
				chatMessageJoinListNewChatMessageJoinToAttach = em
						.getReference(
								chatMessageJoinListNewChatMessageJoinToAttach
										.getClass(),
								chatMessageJoinListNewChatMessageJoinToAttach
										.getMessageId());
				attachedChatMessageJoinListNew
						.add(chatMessageJoinListNewChatMessageJoinToAttach);
			}
			chatMessageJoinListNew = attachedChatMessageJoinListNew;
			chat.setChatMessageJoinList(chatMessageJoinListNew);
			chat = em.merge(chat);
			for (Handle handleListOldHandle : handleListOld) {
				if (!handleListNew.contains(handleListOldHandle)) {
					handleListOldHandle.getChatList().remove(chat);
					handleListOldHandle = em.merge(handleListOldHandle);
				}
			}
			for (Handle handleListNewHandle : handleListNew) {
				if (!handleListOld.contains(handleListNewHandle)) {
					handleListNewHandle.getChatList().add(chat);
					handleListNewHandle = em.merge(handleListNewHandle);
				}
			}
			for (ChatMessageJoin chatMessageJoinListOldChatMessageJoin : chatMessageJoinListOld) {
				if (!chatMessageJoinListNew
						.contains(chatMessageJoinListOldChatMessageJoin)) {
					chatMessageJoinListOldChatMessageJoin.setChatId(null);
					chatMessageJoinListOldChatMessageJoin = em
							.merge(chatMessageJoinListOldChatMessageJoin);
				}
			}
			for (ChatMessageJoin chatMessageJoinListNewChatMessageJoin : chatMessageJoinListNew) {
				if (!chatMessageJoinListOld
						.contains(chatMessageJoinListNewChatMessageJoin)) {
					Chat oldChatIdOfChatMessageJoinListNewChatMessageJoin = chatMessageJoinListNewChatMessageJoin
							.getChatId();
					chatMessageJoinListNewChatMessageJoin.setChatId(chat);
					chatMessageJoinListNewChatMessageJoin = em
							.merge(chatMessageJoinListNewChatMessageJoin);
					if (oldChatIdOfChatMessageJoinListNewChatMessageJoin != null
							&& !oldChatIdOfChatMessageJoinListNewChatMessageJoin
									.equals(chat)) {
						oldChatIdOfChatMessageJoinListNewChatMessageJoin
								.getChatMessageJoinList().remove(
										chatMessageJoinListNewChatMessageJoin);
						oldChatIdOfChatMessageJoinListNewChatMessageJoin = em
								.merge(oldChatIdOfChatMessageJoinListNewChatMessageJoin);
					}
				}
			}
			em.getTransaction().commit();
		} catch (Exception ex) {
			String msg = ex.getLocalizedMessage();
			if (msg == null || msg.length() == 0) {
				Integer id = chat.getRowid();
				if (findChat(id) == null) {
					throw new NonexistentEntityException("The chat with id "
							+ id + " no longer exists.");
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
			Chat chat;
			try {
				chat = em.getReference(Chat.class, id);
				chat.getRowid();
			} catch (EntityNotFoundException enfe) {
				throw new NonexistentEntityException("The chat with id " + id
						+ " no longer exists.", enfe);
			}
			List<Handle> handleList = chat.getHandleList();
			for (Handle handleListHandle : handleList) {
				handleListHandle.getChatList().remove(chat);
				handleListHandle = em.merge(handleListHandle);
			}
			List<ChatMessageJoin> chatMessageJoinList = chat
					.getChatMessageJoinList();
			for (ChatMessageJoin chatMessageJoinListChatMessageJoin : chatMessageJoinList) {
				chatMessageJoinListChatMessageJoin.setChatId(null);
				chatMessageJoinListChatMessageJoin = em
						.merge(chatMessageJoinListChatMessageJoin);
			}
			em.remove(chat);
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public List<Chat> findChatEntities() {
		return findChatEntities(true, -1, -1);
	}

	public List<Chat> findChatEntities(int maxResults, int firstResult) {
		return findChatEntities(false, maxResults, firstResult);
	}

	private List<Chat> findChatEntities(boolean all, int maxResults,
			int firstResult) {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			cq.select(cq.from(Chat.class));
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

	public Chat findChat(Integer id) {
		EntityManager em = getEntityManager();
		try {
			return em.find(Chat.class, id);
		} finally {
			em.close();
		}
	}

	public int getChatCount() {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			Root<Chat> rt = cq.from(Chat.class);
			cq.select(em.getCriteriaBuilder().count(rt));
			Query q = em.createQuery(cq);
			return ((Long) q.getSingleResult()).intValue();
		} finally {
			em.close();
		}
	}

}
