package by.epam.dobrov.dao;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class JpaDAO<E> {// ����� JpaDAO �������� �������������� ����� ��������,
						// ������� ��������� ������������ ��� �����������

	protected EntityManager entityManager;// ����� jpadao �������� �������� ������ EntityManager

	public JpaDAO(EntityManager entityManager) {
		super();
		this.entityManager = entityManager;
	}

	public E create(E entity) {// ����� ���������� �������� ������ , � �� ��������� �� � ����� �������� ������
		// ������
		entityManager.getTransaction().begin();

		entityManager.persist(entity); // persist-�������������� ������ ��� ����� ���������.��������� �������� � �� ,
										// ���� �� ��� ���
		entityManager.flush();// �������� ������ ����������� � ���� ������ ����������
		entityManager.refresh(entity);// ����� �� �� �������� �������� t , ������ ���������

		entityManager.getTransaction().commit();
		return entity;
	}

	public E update(E entity) {

		entityManager.getTransaction().begin();
		entity = entityManager.merge(entity); // ������������ ����� ����� �������� ���������� � �������� ������� ���
												// ���� � ��, �������� ��� ������������� ������������
		entityManager.getTransaction().commit();
		return entity;
	}

	public E find(Class<E> type, Object id) {

		E entity = entityManager.find(type, id);// ����� �� ���������� �����. ����� �������� ���������� ������ �
												// ���������� �����. ���� ��������� �������� ���������� � ���������
												// �����������, �� ������������ ������.
		if (entity != null) {
			entityManager.refresh(entity);// ��������� ��������� ���������� �� ���� ������, ����������� ���������,
											// ������� ���� �������� � ������.
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
		 * ������� ������ ������� �� ������ ���������� SELECT SQL, ����� ��������
		 * ������(get reference), ������ �������� SELECT ��� ������ , � ����� ������
		 * �������� DELETE ��� �������� ������ � �����,�� �������� ����� get UserDAO, ��
		 * ����� �������� ���������� SELECT SQL � ������� ��� � (�����)
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
//entrySet() ���������� ����� ����-��������

		Set<Entry<String, Object>> setParameters = parameters.entrySet(); // ���� ��� �������� ��� �����( � ��� ��������
																			// ���� ����-��������)
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
