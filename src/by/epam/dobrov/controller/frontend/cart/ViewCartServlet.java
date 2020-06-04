package by.epam.dobrov.controller.frontend.cart;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epam.dobrov.dao.impl.BookDAOImpl;
import by.epam.dobrov.entity.Book;

@WebServlet("/view_cart")
public class ViewCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ViewCartServlet() {
		super();

	}

	/*
	 * � ������� ����� ��������, �� ����� ��������� �� �������� � �������� ����� ��
	 * �������� ���� ������� �� ��� �� ��������� ������ ���� � ������, ���� ����
	 * =null �� ������� ����� �������� ���� � ������ �� � ������
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		/*
		 * ����� ���������� ��������� ����� � �������, ������ ���� ����������� � ������
		 */
		Object cartObject = request.getSession().getAttribute("cart");
		
		if (cartObject == null) {

			Cart cart = new Cart();

			request.getSession().setAttribute("cart", cart);
		
		}
		
		String cartPage = "frontend/cart.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(cartPage);
		requestDispatcher.forward(request, response);
	}

}
