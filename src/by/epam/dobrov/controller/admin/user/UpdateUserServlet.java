package by.epam.dobrov.controller.admin.user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epam.dobrov.controller.BaseServlet;
import by.epam.dobrov.service.UsersServices;

/**
 * Servlet implementation class UpdateUserServlet
 */
@WebServlet("/admin/update_user")
public class UpdateUserServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public UpdateUserServlet() {
		super();

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		UsersServices usersServices = new UsersServices(entityManager, request, response);
		usersServices.updateUser();
	}

}
