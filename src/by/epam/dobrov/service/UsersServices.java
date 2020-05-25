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
		// не совсем понятно, но дальше на jsp странице мы извлекаем
		// данные из листЮзерс, сюда

		String listpage = "user_list.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(listpage);// присваиваем объкту
		/*
		 * реквестДиспречер наш запрос, и в метод ГетРиквестДиспр передаем наш адрес на
		 * который мы хотим перевести пользователя риеквест и респонс тут для того чтобы
		 * мы понимали что наш запрос никуда не девается, а просто перенаправляется на
		 * другой внутр адрес
		 */
		requestDispatcher.forward(request, response);
		/*
		 * форвард перенаправялет сам, без ведома клиента, а редирект скажет о том что
		 * перенаправляет, разница между ними, редиект происходит на клиенте (браузер
		 * делает новый запрос) форвард происходит на сервере . клиент не знает об
		 * этом,при 1 УРЛ меняется, при 2 не меняется, редирект медленнее т.к. больше
		 * операций ВАЖНЫЙ МОМЕНТ форвард работает только перенаправлением на внутренние
		 * адреса , т.е. в нашем случае на наши jsp файлы , на внешние типа гугл нельзя
		 */
	}

	public void createUser() throws ServletException, IOException {

		LOGGER.info("Try to create a new Users");

		String email = request.getParameter("email");
		String fullName = request.getParameter("fullname");
		String password = request.getParameter("password");
		// возможно нужен будет статус

		Users existUser = userDAOImpl.findByEmail(email);

		if (existUser != null) { // проверяем чтобы имейл не был повтояющимся
			String message = " User  with this  email " + email + " is already exist!!!";
			request.setAttribute("message", message);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("message.jsp");
			requestDispatcher.forward(request, response);

			LOGGER.warn("Get warning, User  with this  email {} is already exist!!!", email);

		} else {

			Users newUser = new Users(email, fullName, password);
			userDAOImpl.create(newUser);
			listUser(); // обновляем информаци о пользователях

			LOGGER.info("Created a new User and add information: {}; {}; {};  ,in DB", email, fullName, password);
		}
	}

	public void editUser() throws ServletException, IOException {// метод вытягивает номер ИД который идет при наведении
																	// после ?id=... и вызывает
		LOGGER.info("Edit User by ID");
		// метод гет из ЮзерДаоИМПЛ
		int userId = Integer.parseInt(request.getParameter("id"));
		Users user = userDAOImpl.get(userId);

		/*
		 * Передача данных осуществляется с помощью метода setAttribute(name, value),
		 * где name - строковое название данных, а value - сами данные, которые могут
		 * представлять различные данные.
		 */
		String destPage = "user_form.jsp";

		if (user == null) {
			destPage = "message.jsp";
			String errorMessage = "Could not find user with ID " + userId;
			request.setAttribute("message", errorMessage);

			LOGGER.warn("Could not find user with ID {}", userId);

		} else {
			/*
			 * устанавливаем пароль равным нулю, чтобы пароль оставался пустым по умолчанию
			 * если оставить пустым, пароль пользователя не будет обновлен это для работы с
			 * функцией зашифрованным паролем
			 */
			user.setPassword(null);
			request.setAttribute("user", user);

		}

		RequestDispatcher requestDispatcher = request.getRequestDispatcher(destPage);
		requestDispatcher.forward(request, response);

		/**
		 * в этом методе мы получаем с помощью Ид , юзера со всеми его данными, если
		 * юзер =нул то ошибка, если нет , то обнуляем его пароль для работы с
		 * зашифрованным паролем и отправляем запрос на этого юзера
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
		 * идет проверка юзера на ид или имейл, на которые хотим заменить , если таковые
		 * обнаружились то ошибка, если нет то перезаписываем а так же проверка пароля
		 * есть пароль не равен нулл ли не пустой
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
		 * и если он не принадлежит текущему редактирующему пользователю, мы должны
		 * показать сообщение об ошибкt правильно? и если электронная почта принадлежит
		 * текущему пользователю редактирования - это означает, что мы просто обновляем
		 * другие данные пользователя
		 */

	}

// если ид не найден то сообщение о том что ид уже не существует 
	public void deleteUser() throws ServletException, IOException { // удаление пользователя

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

	public void login() throws ServletException, IOException { // понять как работает метод Логин 17-56

		LOGGER.info("User is  trying to get the login access ");

		String email = request.getParameter("email");
		String password = request.getParameter("password");

		boolean loginResult = userDAOImpl.checkLogin(email, password);

		/*
		 * Мы должны хранить состояние изера на сервере, для этого исп Сессии,сессии
		 * могут хранить любые значения в памяти сервера, и они гораздо быстрее чем если
		 * исп ДБ, мы можем удалить состояниеюезра как только он выйдет из системы, для
		 * реализации логИН/АУТ мы должны исп сессии Шаги - получение сессии, задаем и
		 * храним атрибутс сессии с именем и значением,читаем атрибут сессии и удаляем
		 * 
		 */
		if (loginResult) {

			request.getSession().setAttribute("useremail", email);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/");// перенаправляем на страницу админа
			dispatcher.forward(request, response);

			LOGGER.debug("User is authenticated");
		} else {
			/*
			 * разница между request.getSession().setAttribute и просто риквест сет артибут,
			 * в том что просто риквест полученное значение живет только то время пока этот
			 * риквест активен и потом удаляется , а значение которое храниться в сессии,
			 * остается на сервере ,столько же сколько и сессия
			 */
			String message = "Login access failed! ";
			request.setAttribute("message", message);

			/*
			 * перенаправляем на страницу ЛОгин и добавляем там предложение с ошибкой
			 * доступа
			 */
			RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");

			dispatcher.forward(request, response);

			LOGGER.warn("Login access failed");
		}
	}

}
