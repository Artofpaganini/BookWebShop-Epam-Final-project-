package by.epam.dobrov.controller.admin.category;

import by.epam.dobrov.controller.BaseServlet;
import by.epam.dobrov.service.CategoryServices;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CreateCategoryServlet
 */
@WebServlet("/admin/create_category")
public class CreateCategoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public CreateCategoryServlet() {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		CategoryServices categoryServices = new CategoryServices(entityManager,request,response);
		
		categoryServices.createCategory();
	}

}