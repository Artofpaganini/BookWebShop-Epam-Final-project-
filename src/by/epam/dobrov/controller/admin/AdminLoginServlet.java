package by.epam.dobrov.controller.admin;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epam.dobrov.controller.BaseServlet;
import by.epam.dobrov.service.UsersServices;

@WebServlet("/admin/login")
public class AdminLoginServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// в этом методе мы извлекаем значения параметров электронной почты и пароля

		UsersServices usersServices = new UsersServices(entityManager, request, response);
		usersServices.login();

	}
}
