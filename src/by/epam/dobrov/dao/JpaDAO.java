package by.epam.dobrov.dao;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class JpaDAO<E> {// Целью JpaDAO является предоставление общих операций,
						// которые совместно используются его подклассами

	protected EntityManager entityManager;// класс jpadao качестве оболочки вокруг EntityManager

	public JpaDAO(EntityManager entityManager) {
		super();
		this.entityManager = entityManager;
	}

	public E create(E entity) {// метод добавления сущности энтити , в БД используя ЕМ и потом возврает энтити
		// объект
		entityManager.getTransaction().begin();

		entityManager.persist(entity); // persist-использоваться только для новых сущностей.Добавляет сущность в бД ,
										// если ее там нет
		entityManager.flush();// заставит данные сохраняться в базе данных немедленно
		entityManager.refresh(entity);// когда из Бд вернется сущность t , объект обновится

		entityManager.getTransaction().commit();
		return entity;
	}

	public E update(E entity) {

		entityManager.getTransaction().begin();
		entity = entityManager.merge(entity); // используется когда нужно обновить информацию о сущности которая уже
												// есть в БД, подумать как заблокировать пользователя
		entityManager.getTransaction().commit();
		return entity;
	}

	public E find(Class<E> type, Object id) {

		E entity = entityManager.find(type, id);// Поиск по первичному ключу. Поиск сущности указанного класса и
												// первичного ключа. Если экземпляр сущности содержится в контексте
												// постоянства, он возвращается оттуда.
		if (entity != null) {
			entityManager.refresh(entity);// Обновляет состояние экземпляра из базы данных, перезаписав изменения,
											// которые были внесенны в объект.
		}
		return entity;
	}

	public E block(E entity) {

		entityManager.getTransaction().begin();
		entity = entityManager.merge(entity);
		entityManager.getTransaction().commit();
		return entity;
	}

	public void delete(Class<E> type, Object id) {
		/*
		 * Принцип работы сначала он вызвал инструкцию SELECT SQL, чтобы получить
		 * ссылку(get reference), вызвал оператор SELECT эту ссылку , а затем вызвал
		 * оператор DELETE для удаления ссылки и затем,мы вызываем метод get UserDAO, он
		 * снова выполнил инструкцию SELECT SQL и получил нул в (тесте)
		 */

		entityManager.getTransaction().begin();

		Object reference = entityManager.getReference(type, id);
		entityManager.remove(reference);

		entityManager.getTransaction().commit();
	}

	public List<E> findByNamedQuery(String queryName) {

		Query query = entityManager.createNamedQuery(queryName);

		return query.getResultList();

	}

	public List<E> findByNamedQuery(String queryName, String parameterName, Object parameterValue) {

		Query query = entityManager.createNamedQuery(queryName);

		query.setParameter(parameterName, parameterValue);

		return query.getResultList();
	}

	public List<E> findByNamedQuery(String queryName, Map<String, Object> parameters) {

		Query query = entityManager.createNamedQuery(queryName);
//entrySet() возвращает набор ключ-значений

		Set<Entry<String, Object>> setParameters = parameters.entrySet(); // этот сет получает все ЭНтри( в них хранятся
																			// пары ключ-значение)
		for (Entry<String, Object> entry : setParameters) {
			query.setParameter(entry.getKey(), entry.getValue());
		}

		return query.getResultList();
	}

	public long findCountByNamedQuery(String queryName) {

		Query query = entityManager.createNamedQuery(queryName);

		return (long) query.getSingleResult();

	}

}
