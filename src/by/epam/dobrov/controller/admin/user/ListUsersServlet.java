package by.epam.dobrov.controller.admin.user;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epam.dobrov.controller.BaseServlet;
import by.epam.dobrov.entity.Users;
import by.epam.dobrov.service.UsersServices;

@WebServlet("/admin/list_users") // ����� ���� URL �� �������� ������ � ����� ��������, ������ ���� � ����� �����
									// � �������� � ���� request
public class ListUsersServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public ListUsersServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) // ��� ��� ��������� ������ �
																					// �������,��� ��������� � ���������
			throws ServletException, IOException {

		UsersServices usersServices = new UsersServices(entityManager, request, response);
		usersServices.listUser(); // ���� ������ ��� ��������� � listUser

	} // ������������� ������ �� �������� �� ������ �������, html-�������� ���
		// �������� jsp. ������ � ������ ������ ���� ���� � ��������������� �������, �
		// �� � �������������.

}
/*
 * ������� ������ ListUsersServlet �������� listUser �� UserServices �
 * UserServices listAll () � UserDAO ����� header - ��������� - ������������
 * �������� �� ������ Users ����� ����� doGet () �������� �������� �����
 * listUser () �� UsersServices UserServices �������� ����� listAll () �� ������
 * UserDAO � ����� listAll () �������� findWithNamedQuery () ������ JpaDAO
 * ��������� �� ����� �������� list user
 */