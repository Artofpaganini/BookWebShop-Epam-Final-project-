package by.epam.dobrov.controller.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epam.dobrov.service.UsersServices;

/**
 * 9. ������� ��������-�������. ������������� ������������ ������� ��������
 * �������. ������ ������ � ���������� ����� �� ������. ������������� �����
 * ������� �������������� � ������� ������.
 * 
 * @author Viktor
 *
 */
@WebServlet("/admin/login")
public class AdminLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		UsersServices usersServices = new UsersServices(request, response);
		usersServices.login();

	}
}
