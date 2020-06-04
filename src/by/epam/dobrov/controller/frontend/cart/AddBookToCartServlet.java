package by.epam.dobrov.controller.frontend.cart;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epam.dobrov.dao.impl.BookDAOImpl;
import by.epam.dobrov.entity.Book;

@WebServlet("/add_to_cart")
public class AddBookToCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AddBookToCartServlet() {
		super();

	}
	/*
	 * сервлет реализует кнопку добавить в корзину
	 * 
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Integer bookId = Integer.parseInt(request.getParameter("book_id"));

		Object cartObject = request.getSession().getAttribute("cart");

		Cart cart = null;
		
		if (cartObject != null && cartObject instanceof Cart) {
			cart = (Cart) cartObject;
			
		} else {
			cart = new Cart();
			request.getSession().setAttribute("cart", cart);
		}
		
		BookDAOImpl bookDAOImpl = new BookDAOImpl();
	
		Book book = bookDAOImpl.get(bookId);
		
		cart.addItem(book);
		
		String cartPage = request.getContextPath().concat("/view_cart");
		
		response.sendRedirect(cartPage);
	}

}
