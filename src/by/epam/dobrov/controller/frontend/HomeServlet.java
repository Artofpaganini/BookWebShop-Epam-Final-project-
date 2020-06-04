package by.epam.dobrov.controller.frontend;

import java.io.IOException;

import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epam.dobrov.dao.impl.CategoryDAOImpl;
import by.epam.dobrov.entity.Category;

@WebServlet("") // оставляем пустым,чтобы указать что сервлет будет обрабатывать запросы юзеров
				// на дом стр
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*
	 * этот сервлет вызывается когда мы получаем доступ к странице фронтэнд
	 */
	public HomeServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String homepage = "frontend/index.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(homepage);
		dispatcher.forward(request, response);

	}

}
