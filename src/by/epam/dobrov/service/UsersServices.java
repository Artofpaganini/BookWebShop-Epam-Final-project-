package by.epam.dobrov.service;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.tagext.TryCatchFinally;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import by.epam.dobrov.dao.impl.UserDAOImpl;
import by.epam.dobrov.dao.impl.generator.HashGenerator;
import by.epam.dobrov.entity.Users;

public class UsersServices {
	private final static Logger LOGGER = LoggerFactory.getLogger(UsersServices.class);

	private EntityManager entityManager;
	private UserDAOImpl userDAOImpl;
	private HttpServletRequest request;
	private HttpServletResponse response;

	public UsersServices(EntityManager entityManager, HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		this.entityManager = entityManager;

		userDAOImpl = new UserDAOImpl(entityManager);
	}

	public void listUser() throws ServletException, IOException {

		LOGGER.info("Got list of users from DB");

		List<Users> listUsers = userDAOImpl.listAll();
		request.setAttribute("listUsers", listUsers);
		// �� ������ �������, �� ������ �� jsp �������� �� ���������
		// ������ �� ���������, ����

		String listpage = "user_list.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(listpage);// ����������� ������
		/*
		 * ���������������� ��� ������, � � ����� ��������������� �������� ��� ����� ��
		 * ������� �� ����� ��������� ������������ �������� � ������� ��� ��� ���� �����
		 * �� �������� ��� ��� ������ ������ �� ��������, � ������ ���������������� ��
		 * ������ ����� �����
		 */
		requestDispatcher.forward(request, response);
		/*
		 * ������� �������������� ���, ��� ������ �������, � �������� ������ � ��� ���
		 * ��������������, ������� ����� ����, ������� ���������� �� ������� (�������
		 * ������ ����� ������) ������� ���������� �� ������� . ������ �� ����� ��
		 * ����,��� 1 ��� ��������, ��� 2 �� ��������, �������� ��������� �.�. ������
		 * �������� ������ ������ ������� �������� ������ ���������������� �� ����������
		 * ������ , �.�. � ����� ������ �� ���� jsp ����� , �� ������� ���� ���� ������
		 */
	}

	public void createUser() throws ServletException, IOException {

		LOGGER.info("Try to create a new Users");

		String email = request.getParameter("email");
		String fullName = request.getParameter("fullname");
		String password = request.getParameter("password");
		// �������� ����� ����� ������

		Users existUser = userDAOImpl.findByEmail(email);

		if (existUser != null) { // ��������� ����� ����� �� ��� ������������
			String message = " User  with this  email " + email + " is already exist!!!";
			request.setAttribute("message", message);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("message.jsp");
			requestDispatcher.forward(request, response);

			LOGGER.warn("Get warning, User  with this  email {} is already exist!!!", email);

		} else {

			Users newUser = new Users(email, fullName, password);
			userDAOImpl.create(newUser);
			listUser(); // ��������� ��������� � �������������

			LOGGER.info("Created a new User and add information: {}; {}; {};  ,in DB", email, fullName, password);
		}
	}

	public void editUser() throws ServletException, IOException {// ����� ���������� ����� �� ������� ���� ��� ���������
																	// ����� ?id=... � ��������
		LOGGER.info("Edit User by ID");
		// ����� ��� �� �����������
		int userId = Integer.parseInt(request.getParameter("id"));
		Users user = userDAOImpl.get(userId);

		/*
		 * �������� ������ �������������� � ������� ������ setAttribute(name, value),
		 * ��� name - ��������� �������� ������, � value - ���� ������, ������� �����
		 * ������������ ��������� ������.
		 */
		String destPage = "user_form.jsp";

		if (user == null) {
			destPage = "message.jsp";
			String errorMessage = "Could not find user with ID " + userId;
			request.setAttribute("message", errorMessage);

			LOGGER.warn("Could not find user with ID {}", userId);

		} else {
			/*
			 * ������������� ������ ������ ����, ����� ������ ��������� ������ �� ���������
			 * ���� �������� ������, ������ ������������ �� ����� �������� ��� ��� ������ �
			 * �������� ������������� �������
			 */
			user.setPassword(null);
			request.setAttribute("user", user);

		}

		RequestDispatcher requestDispatcher = request.getRequestDispatcher(destPage);
		requestDispatcher.forward(request, response);

		/**
		 * � ���� ������ �� �������� � ������� �� , ����� �� ����� ��� �������, ����
		 * ���� =��� �� ������, ���� ��� , �� �������� ��� ������ ��� ������ �
		 * ������������� ������� � ���������� ������ �� ����� �����
		 */
	}

	public void updateUser() throws ServletException, IOException { //

		LOGGER.info("Update some information about User");

		int userId = Integer.parseInt(request.getParameter("userId"));
		String email = request.getParameter("email");
		String fullName = request.getParameter("fullname");
		String password = request.getParameter("password");
		String block = request.getParameter("block");

		Boolean statusBoolean = Boolean.parseBoolean(block);

		Users userById = userDAOImpl.get(userId);

		Users userByEmail = userDAOImpl.findByEmail(email);
		/*
		 * ���� �������� ����� �� �� ��� �����, �� ������� ����� �������� , ���� �������
		 * ������������ �� ������, ���� ��� �� �������������� � ��� �� �������� ������
		 * ���� ������ �� ����� ���� �� �� ������
		 */
		if (userByEmail != null && userByEmail.getUsersId() != userById.getUsersId()) {
			String message = "Could not update user. User with email " + email + " already exists.";
			request.setAttribute("message", message);

			RequestDispatcher requestDispatcher = request.getRequestDispatcher("message.jsp");
			requestDispatcher.forward(request, response);

			LOGGER.warn("Couldn't update user.User with this email is already exist!");

		} else {
			userById.setEmail(email);
			userById.setFullName(fullName);
			userById.setBlock(statusBoolean);

			if (password != null & !password.isEmpty()) {
				String encryptedPassword = HashGenerator.generateSHA256(password);
				userById.setPassword(encryptedPassword);
			}

			userDAOImpl.update(userById);

			listUser();

			LOGGER.info("User has been updated successfully");
		}

		/*
		 * � ���� �� �� ����������� �������� �������������� ������������, �� ������
		 * �������� ��������� �� �����t ���������? � ���� ����������� ����� �����������
		 * �������� ������������ �������������� - ��� ��������, ��� �� ������ ���������
		 * ������ ������ ������������
		 */

	}

// ���� �� �� ������ �� ��������� � ��� ��� �� ��� �� ���������� 
	public void deleteUser() throws ServletException, IOException { // �������� ������������

		LOGGER.info("Delete User from user list and from DB");

		int userId = Integer.parseInt(request.getParameter("id"));

		Users userById = userDAOImpl.get(userId);
		String message;

		if (userId == 1) {
			message = "The default admin user account cannot be deleted.";
			request.setAttribute("message", message);
			request.getRequestDispatcher("message.jsp").forward(request, response);

			LOGGER.warn("The default admin user account cannot be deleted.");
			return;
		}
		if (userById == null) {
			message = "Couldn't find user with ID " + userId + " or this user deleted by another admin";
			request.setAttribute("message", message);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("message.jsp");
			requestDispatcher.forward(request, response);
			LOGGER.warn("Couldn't find user with ID {} or this user deleted by another admin", userId);
		} else {
			userDAOImpl.delete(userId);
			listUser();

			LOGGER.info("Delete user");
		}

	}

	public void login() throws ServletException, IOException { // ������ ��� �������� ����� ����� 17-56

		LOGGER.info("User is  trying to get the login access ");

		String email = request.getParameter("email");
		String password = request.getParameter("password");

		boolean loginResult = userDAOImpl.checkLogin(email, password);

		/*
		 * �� ������ ������� ��������� ����� �� �������, ��� ����� ��� ������,������
		 * ����� ������� ����� �������� � ������ �������, � ��� ������� ������� ��� ����
		 * ��� ��, �� ����� ������� �������������� ��� ������ �� ������ �� �������, ���
		 * ���������� �����/��� �� ������ ��� ������ ���� - ��������� ������, ������ �
		 * ������ �������� ������ � ������ � ���������,������ ������� ������ � �������
		 * 
		 */
		if (loginResult) {

			request.getSession().setAttribute("useremail", email);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/");// �������������� �� �������� ������
			dispatcher.forward(request, response);

			LOGGER.debug("User is authenticated");
		} else {
			/*
			 * ������� ����� request.getSession().setAttribute � ������ ������� ��� �������,
			 * � ��� ��� ������ ������� ���������� �������� ����� ������ �� ����� ���� ����
			 * ������� ������� � ����� ��������� , � �������� ������� ��������� � ������,
			 * �������� �� ������� ,������� �� ������� � ������
			 */
			String message = "Login access failed! ";
			request.setAttribute("message", message);

			/*
			 * �������������� �� �������� ����� � ��������� ��� ����������� � �������
			 * �������
			 */
			RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");

			dispatcher.forward(request, response);

			LOGGER.warn("Login access failed");
		}
	}

}
