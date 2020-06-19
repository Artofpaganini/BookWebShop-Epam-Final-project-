package by.epam.dobrov.controller.admin;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epam.dobrov.dao.impl.BookDAOImpl;
import by.epam.dobrov.dao.impl.CategoryDAOImpl;
import by.epam.dobrov.dao.impl.CustomerDAOImpl;
import by.epam.dobrov.dao.impl.OrderDAOImpl;
import by.epam.dobrov.dao.impl.UserDAOImpl;

/**
 * 9. Система Интернет-магазин. Администратор осуществляет ведение каталога
 * Товаров. Клиент делает и оплачивает Заказ на Товары. Администратор может
 * занести неплательщиков в “черный список”.
 * 
 * @author Viktor
 *
 */
@WebServlet("/admin/")
public class AdminHomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AdminHomeServlet() {
		super();

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		doGet(req, resp);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		UserDAOImpl userDAOImpl = new UserDAOImpl();
		BookDAOImpl bookDAOImpl = new BookDAOImpl();
		CustomerDAOImpl customerDAOImpl = new CustomerDAOImpl();
		OrderDAOImpl orderDAOImpl = new OrderDAOImpl();
		CategoryDAOImpl categoryDAOImpl = new CategoryDAOImpl();

		long totalUsers = userDAOImpl.count();
		long totalBooks = bookDAOImpl.count();
		long totalCustomers = customerDAOImpl.count();
		long totalOrders = orderDAOImpl.count();
		long totalCategories = categoryDAOImpl.count();

		request.setAttribute("totalUsers", totalUsers);
		request.setAttribute("totalBooks", totalBooks);
		request.setAttribute("totalCategories", totalCategories);
		request.setAttribute("totalCustomers", totalCustomers);
		request.setAttribute("totalOrders", totalOrders);

		String adminHomepage = "index.jsp";

		RequestDispatcher dispatcher = request.getRequestDispatcher(adminHomepage);

		dispatcher.forward(request, response);
	}

}
