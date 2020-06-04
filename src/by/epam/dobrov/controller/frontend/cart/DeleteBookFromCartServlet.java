package by.epam.dobrov.controller.frontend.cart;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epam.dobrov.dao.impl.BookDAOImpl;
import by.epam.dobrov.entity.Book;

@WebServlet("/delete_from_cart")
public class DeleteBookFromCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DeleteBookFromCartServlet() {
		super();
	}

	/*
	 * сервлет активирует кнопку  удалить  книгу из корзины
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Integer bookId = Integer.parseInt(request.getParameter("book_id"));

		Object cartObject = request.getSession().getAttribute("cart");

		Cart cart = (Cart) cartObject;

		BookDAOImpl bookDAOImpl = new BookDAOImpl();

		cart.removeItem(new Book(bookId));

		String cartPage = request.getContextPath().concat("/view_cart");

		response.sendRedirect(cartPage);
	}

}
