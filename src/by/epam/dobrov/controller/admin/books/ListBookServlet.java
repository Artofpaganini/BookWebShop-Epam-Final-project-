package by.epam.dobrov.controller.admin.books;

import by.epam.dobrov.service.BookServices;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 9. Система Интернет-магазин. Администратор осуществляет ведение каталога
 * Товаров. Клиент делает и оплачивает Заказ на Товары. Администратор может
 * занести неплательщиков в “черный список”.
 * 
 * @author Viktor
 *
 */
@WebServlet("/admin/list_books")
public class ListBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ListBookServlet() {

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		BookServices bookServices = new BookServices(request, response);

		bookServices.listBooks();

	}

}
