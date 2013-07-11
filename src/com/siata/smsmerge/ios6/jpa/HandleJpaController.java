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
import com.siata.smsmerge.ios6.Handle;
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
public class HandleJpaController implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HandleJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Handle handle) throws PreexistingEntityException, Exception {
        if (handle.getChatList() == null) {
            handle.setChatList(new ArrayList<Chat>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Chat> attachedChatList = new ArrayList<Chat>();
            for (Chat chatListChatToAttach : handle.getChatList()) {
                chatListChatToAttach = em.getReference(chatListChatToAttach.getClass(), chatListChatToAttach.getRowid());
                attachedChatList.add(chatListChatToAttach);
            }
            handle.setChatList(attachedChatList);
            em.persist(handle);
            for (Chat chatListChat : handle.getChatList()) {
                chatListChat.getHandleList().add(handle);
                chatListChat = em.merge(chatListChat);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findHandle(handle.getRowid()) != null) {
                throw new PreexistingEntityException("Handle " + handle + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Handle handle) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Handle persistentHandle = em.find(Handle.class, handle.getRowid());
            List<Chat> chatListOld = persistentHandle.getChatList();
            List<Chat> chatListNew = handle.getChatList();
            List<Chat> attachedChatListNew = new ArrayList<Chat>();
            for (Chat chatListNewChatToAttach : chatListNew) {
                chatListNewChatToAttach = em.getReference(chatListNewChatToAttach.getClass(), chatListNewChatToAttach.getRowid());
                attachedChatListNew.add(chatListNewChatToAttach);
            }
            chatListNew = attachedChatListNew;
            handle.setChatList(chatListNew);
            handle = em.merge(handle);
            for (Chat chatListOldChat : chatListOld) {
                if (!chatListNew.contains(chatListOldChat)) {
                    chatListOldChat.getHandleList().remove(handle);
                    chatListOldChat = em.merge(chatListOldChat);
                }
            }
            for (Chat chatListNewChat : chatListNew) {
                if (!chatListOld.contains(chatListNewChat)) {
                    chatListNewChat.getHandleList().add(handle);
                    chatListNewChat = em.merge(chatListNewChat);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = handle.getRowid();
                if (findHandle(id) == null) {
                    throw new NonexistentEntityException("The handle with id " + id + " no longer exists.");
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
            Handle handle;
            try {
                handle = em.getReference(Handle.class, id);
                handle.getRowid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The handle with id " + id + " no longer exists.", enfe);
            }
            List<Chat> chatList = handle.getChatList();
            for (Chat chatListChat : chatList) {
                chatListChat.getHandleList().remove(handle);
                chatListChat = em.merge(chatListChat);
            }
            em.remove(handle);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Handle> findHandleEntities() {
        return findHandleEntities(true, -1, -1);
    }

    public List<Handle> findHandleEntities(int maxResults, int firstResult) {
        return findHandleEntities(false, maxResults, firstResult);
    }

    private List<Handle> findHandleEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Handle.class));
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

    public Handle findHandle(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Handle.class, id);
        } finally {
            em.close();
        }
    }

    public int getHandleCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Handle> rt = cq.from(Handle.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
