package by.epam.dobrov.controller;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class BaseServlet extends HttpServlet { // мы объ€вл€ем тут Ёнтити менеджер фактори и энтити менеджер,
														// от этого сервлета наследуютс€ все остальные
	private static final long serialVersionUID = 1L;

	protected EntityManagerFactory entityManagerFactory;
	protected EntityManager entityManager;

	@Override
	public void init() throws ServletException { // вызываетс€ когда сервлет класс впервые создан тут мы инициализируем
													// сущности Ёнтити менеджер фактори и энтити менеджер
		entityManagerFactory = Persistence.createEntityManagerFactory("book_shop"); // что такое
																					// Persistence.createEntityManagerFactory
		entityManager = entityManagerFactory.createEntityManager();

	}

	@Override
	public void destroy() { // этот метод вызываетс€ когда мы сервлет уничтожен, мы освободаем ресурсы
							// Ёнтити менеджер фактори и энтити менеджер
		entityManager.close();
		entityManagerFactory.close();
	}

}
