package by.epam.dobrov.dao;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 * 9. Система Интернет-магазин. Администратор осуществляет ведение каталога
 * Товаров. Клиент делает и оплачивает Заказ на Товары. Администратор может
 * занести неплательщиков в “черный список”.
 * 
 * @author Viktor
 *
 */
public class JpaDAO<E> {

	private static EntityManagerFactory entityManagerFactory;

	static {
		entityManagerFactory = Persistence.createEntityManagerFactory("book_shop");
	}

	public JpaDAO() {

	}
	public E create(E entity) {
		
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		entityManager.persist(entity); 
		
		entityManager.flush();
		
		entityManager.refresh(entity);

		entityManager.getTransaction().commit();
		entityManager.close();
		return entity;
	}

	public E update(E entity) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		entity = entityManager.merge(entity); 
		
		entityManager.getTransaction().commit();

		entityManager.close();
		return entity;
	}

	public E find(Class<E> type, Object id) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		E entity = entityManager.find(type, id);
		
		if (entity != null) {
			entityManager.refresh(entity);
		}

		entityManager.close();
		return entity;
	}

	public E block(E entity) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		entity = entityManager.merge(entity);
		
		entityManager.getTransaction().commit();
		
		entityManager.close();
		return entity;
	}

	public void delete(Class<E> type, Object id) {
	
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		Object reference = entityManager.getReference(type, id);
		entityManager.remove(reference);

		entityManager.getTransaction().commit();

		entityManager.close();
	}

	public List<E> findByNamedQuery(String queryName) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		Query query = entityManager.createNamedQuery(queryName);

		List<E> resultList = query.getResultList();
		
		entityManager.close();
		return resultList;

	}

	public List<E> findByNamedQuery(String queryName, String parameterName, Object parameterValue) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		Query query = entityManager.createNamedQuery(queryName);

		query.setParameter(parameterName, parameterValue);

		List<E> resultList = query.getResultList();
		
		entityManager.close();
		return resultList;
	}

	public List<E> findByNamedQuery(String queryName, Map<String, Object> parameters) {
		EntityManager entityManager = entityManagerFactory.createEntityManager(); 
		
		Query query = entityManager.createNamedQuery(queryName); 
		
		Set<Entry<String, Object>> setParameters = parameters.entrySet(); 
		
		for (Entry<String, Object> entry : setParameters) {
			query.setParameter(entry.getKey(), entry.getValue());
		}

		List<E> resultList = query.getResultList();
		
		entityManager.close();
		return resultList;
	}

	public long findCountByNamedQuery(String queryName) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		Query query = entityManager.createNamedQuery(queryName);

		long resultCount = (long) query.getSingleResult();
		
		entityManager.close();
		return resultCount;

	}

	public List<E> findByNamedQuery(String queryName, int firstResult, int maxResult) {
	
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		Query query = entityManager.createNamedQuery(queryName);

		query.setFirstResult(firstResult);
		query.setMaxResults(maxResult);
		List<E> resultList = query.getResultList();

		entityManager.close();
		return resultList;
	}

}
