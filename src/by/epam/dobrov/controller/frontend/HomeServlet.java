package by.epam.dobrov.controller.frontend;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epam.dobrov.controller.BaseServlet;
import by.epam.dobrov.dao.impl.CategoryDAOImpl;
import by.epam.dobrov.entity.Category;

@WebServlet("") // ��������� ������,����� ������� ��� ������� ����� ������������ ������� ������
				// �� ��� ���
public class HomeServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public HomeServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		/*
		 * ��� �� ������� ��� ������ � ������ ������ ��������� ��� ���� ������� �����
		 * ������������ �� ������� ��������, �.�. ���� ���� ��������� ������� ��������,
		 * �� �� �������� ������ �� ��� ������
		 */

		CategoryDAOImpl categoryDAOImpl = new CategoryDAOImpl(entityManager);
		List<Category> listCategory = categoryDAOImpl.listAll();
		request.setAttribute("listCategory", listCategory);

		String homepage = "frontend/index.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(homepage);
		dispatcher.forward(request, response);

	}

}
