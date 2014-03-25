package org.w3c.wai.accessdb.eao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.hibernate.Session;
import org.w3c.wai.accessdb.om.base.BaseEntity;
import org.w3c.wai.accessdb.utils.ASBPersistenceException;

/**
 * @author evangelos.vlachogiannis@fit.fraunhofer.de
 * @since 14.03.12
 */

public abstract class BaseEAO<E> {

	static final EntityManagerFactory EMF = Persistence
			.createEntityManagerFactory("accessdb");

	public static EntityManager createEntityManager() {
		return EMF.createEntityManager();
	}

	public static void closeEntityManager(EntityManager em) {
		if (em != null && em.isOpen()) {
			em.close();
		}
	}

	public static EntityManagerFactory getEntityManagerFactory() {
		return EMF;
	}

	public E persist(E entity) throws ASBPersistenceException {
		EntityManager em = createEntityManager();
		em.getTransaction().begin();
		Class<? extends BaseEntity> entityClass = (Class<? extends BaseEntity>) entity
				.getClass();
		org.hibernate.Session session =  (Session) em.getDelegate();
		
		try {

			if (entity == null) {
				return null;
			}
			long id = (Long) entityClass.getMethod("getId").invoke(entity);
			if ( id <=0)
			{
				em.persist(entity);
				em.flush();

			}
				
			else
			{
				//E e = this.findById(id);
				//session.update(entity);
				em.merge(entity);
			}
				
			em.getTransaction().commit();
		} catch (Throwable t) {
			t.printStackTrace();
			em.getTransaction().rollback();
			throw new ASBPersistenceException(t);
		} finally {
			// closeEntityManager(em);
		}
		return entity;
	}

	public List<E> persist(List<E> entities) throws ASBPersistenceException {
		EntityManager em = createEntityManager();
		em.getTransaction().begin();
		List<E> pentities = new ArrayList<E>();
		for (E entity : entities) {
			if (entity == null) {
				return null;
			}
			pentities.add(this.persist(entity));
		}
		return pentities;
	}

	public void delete(E entity) throws ASBPersistenceException {
		EntityManager em = createEntityManager();
		try {
			em.getTransaction().begin();
			em.remove(em.merge(entity));
			em.getTransaction().commit();
		} catch (Throwable t) {
			t.printStackTrace();
			em.getTransaction().rollback();
			throw new ASBPersistenceException(t);

		} finally {
			closeEntityManager(em);
		}
	}

	public List<E> findAll() {
		EntityManager em = createEntityManager();
		String queryString = "SELECT e FROM " + this.getEntityClass().getName()
				+ " e";
		Query q = em.createQuery(queryString);
		List<E> l = q.getResultList();
		// closeEntityManager(em);
		return l;
	}

	public void remove(E entity) {
		EntityManager em = createEntityManager();
		entity = em.merge(entity);
		em.remove(entity);
		closeEntityManager(em);
	}

	public E findById(long id) {
		EntityManager em = createEntityManager();
		E entity = (E) em.find(this.getEntityClass(), id);
		// closeEntityManager(em);
		return entity;
	}

	public E findByEquals(E e) {
		List<E> all = this.findAll();
		for (E e2 : all) {
			if (e.equals(e2)) {
				return e2;
			}
		}
		return null;
	}

	public void removeAll() {
		EntityManager em = createEntityManager();
		List<E> entities = this.findAll();
		for (E entity : entities) {
			em.remove(entity);
		}
		closeEntityManager(em);
	}
	public void deleteAll() throws ASBPersistenceException {
        EntityManager em = createEntityManager();
        List<E> entities = this.findAll();
        for (E entity : entities) {
            this.delete(entity);
        }
        closeEntityManager(em);
    }

	public List<E> findByNamedQuery(final String name, Object... params) {
		EntityManager em = createEntityManager();
		javax.persistence.Query query = em.createNamedQuery(name);

		for (int i = 0; i < params.length; i++) {
			query.setParameter(i + 1, params[i]);
		}

		final List<E> result = (List<E>) query.getResultList();
		// closeEntityManager(em);
		return result;
	}

	public List doNamedQuery(final String name, Object... params) {
		EntityManager em = createEntityManager();
		javax.persistence.Query query = em.createNamedQuery(name);

		for (int i = 0; i < params.length; i++) {
			query.setParameter(i + 1, params[i]);
		}

		final List result = query.getResultList();
		// closeEntityManager(em);
		return result;
	}

	public List doSimpleQuery(final String q) {
		EntityManager em = createEntityManager();
		javax.persistence.Query query = em.createQuery(q);
		final List result = query.getResultList();
		// closeEntityManager(em);
		return result;
	}

	public List doSimpleSelectOnlyQuery(final String q) {
		if (q.toLowerCase().contains("insert")
				|| q.toLowerCase().contains("update")
				|| q.toLowerCase().contains("delete")
				|| q.toLowerCase().contains("grant")
				|| q.toLowerCase().contains("create")
				|| q.toLowerCase().contains("drop")
				|| q.toLowerCase().contains("truncate")
				|| q.toLowerCase().contains("alter")
				|| q.toLowerCase().contains("revoke")
				)
			return null;
		EntityManager em = createEntityManager();
		javax.persistence.Query query = em.createQuery(q);
		final List result = query.getResultList();
		// closeEntityManager(em);
		return result;
	}
	public List doSimpleSelectOnlyQuery(final String q, final int first, final int limit) { 
		if (q.toLowerCase().contains("insert")
				|| q.toLowerCase().contains("update")
				|| q.toLowerCase().contains("delete")
				|| q.toLowerCase().contains("grant")
				|| q.toLowerCase().contains("create")
				|| q.toLowerCase().contains("drop")
				|| q.toLowerCase().contains("truncate")
				|| q.toLowerCase().contains("alter")
				|| q.toLowerCase().contains("revoke")
				)
			return null;
		EntityManager em = createEntityManager();
		javax.persistence.Query query = em.createQuery(q).setFirstResult(first).setMaxResults(limit);
		//.setFirstResult( elementsPerBlock * ( (page-1) +1 ) ) .setMaxResults( elementsPerBlock );
		final List result = query.getResultList();
		// closeEntityManager(em);
		return result;
	}

	public List<E> findByNamedQueryAndNamedParams(final String name,
			final Map<String, ? extends Object> params) {
		EntityManager em = createEntityManager();
		javax.persistence.Query query = em.createNamedQuery(name);

		for (final Map.Entry<String, ? extends Object> param : params
				.entrySet()) {
			query.setParameter(param.getKey(), param.getValue());
		}

		final List<E> result = (List<E>) query.getResultList();
		closeEntityManager(em);
		return result;
	}

	public abstract <T> Class<T> getEntityClass();

}
