package by.epam.dobrov.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class BaseDAOTest {

	protected static EntityManagerFactory entityManagerFactory;
	protected static EntityManager entityManager;
	
	
	public static void setUpClass() {

		entityManagerFactory = Persistence.createEntityManagerFactory("book_shop");
		entityManager = entityManagerFactory.createEntityManager();

	}
	
	public static void tearDownClass() {

		entityManager.close();
		entityManagerFactory.close();

	}
}
