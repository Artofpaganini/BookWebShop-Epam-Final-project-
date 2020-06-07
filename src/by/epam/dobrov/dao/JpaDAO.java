package by.epam.dobrov.dao;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class JpaDAO<E> {// Целью JpaDAO является предоставление общих операций,
						// которые совместно используются его подклассами
	private static EntityManagerFactory entityManagerFactory;

	/*
	 * entityManagerFactory статический поэтому он создасться 1 раз и для всех
	 */
	static {
		entityManagerFactory = Persistence.createEntityManagerFactory("book_shop");
	}

	public JpaDAO() {

	}

	/*
	 * В каждом методе мы создаем entityManager и потом в конце метода его закрываем
	 */

	public E create(E entity) {// метод добавления сущности энтити , в БД используя ЕМ и потом возврает энтити
		// объект
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		entityManager.persist(entity); // persist-использоваться только для новых сущностей.Добавляет сущность в бД ,
										// если ее там нет
		entityManager.flush();// заставит данные сохраняться в базе данных немедленно
		entityManager.refresh(entity);// когда из Бд вернется сущность t , объект обновится

		entityManager.getTransaction().commit();
		entityManager.close();
		return entity;
	}

	public E update(E entity) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		entity = entityManager.merge(entity); // используется когда нужно обновить информацию о сущности которая уже
												// есть в БД, подумать как заблокировать пользователя
		entityManager.getTransaction().commit();

		entityManager.close();
		return entity;
	}

	public E find(Class<E> type, Object id) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		E entity = entityManager.find(type, id);// Поиск по первичному ключу. Поиск сущности указанного класса и
												// первичного ключа. Если экземпляр сущности содержится в контексте
												// постоянства, он возвращается оттуда.
		if (entity != null) {
			entityManager.refresh(entity);// Обновляет состояние экземпляра из базы данных, перезаписав изменения,
											// которые были внесенны в объект.
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
		/*
		 * Принцип работы сначала он вызвал инструкцию SELECT SQL, чтобы получить
		 * ссылку(get reference), вызвал оператор SELECT эту ссылку , а затем вызвал
		 * оператор DELETE для удаления ссылки и затем,мы вызываем метод get UserDAO, он
		 * снова выполнил инструкцию SELECT SQL и получил нул в (тесте)
		 */
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

		/*
		 * это сделано для того т.к. мы ошидаем ресалт после закрытия entitymanager а
		 * если его закрыть то резалт мы уже не получить , поэтому заранее присваиваем
		 * его мнимому списку
		 */
		List<E> resultList = query.getResultList();
		entityManager.close();
		return resultList;

	}

	public List<E> findByNamedQuery(String queryName, String parameterName, Object parameterValue) { // возвращает
																										// список
																										// объектов
																										// которые
																										// необходимо
																										// проверить
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
//entrySet() возвращает набор ключ-значений

		Set<Entry<String, Object>> setParameters = parameters.entrySet(); // этот сет получает все ЭНтри( в них хранятся
																			// пары ключ-значение)
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
	
	public List<E> findByNamedQuery(String queryName,int firstResult, int maxResult) {
		/*
		 * метод реализует именной запрос  на 4 значения , это для вывода  бест селлинг букс
		 */
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		Query query = entityManager.createNamedQuery(queryName);

		query.setFirstResult(firstResult);
		query.setMaxResults(maxResult);
		List<E> resultList = query.getResultList();
		
		entityManager.close();
		return resultList;
	}

}
