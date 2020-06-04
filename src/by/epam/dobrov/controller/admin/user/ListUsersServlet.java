package by.epam.dobrov.controller.admin.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epam.dobrov.service.UsersServices;

@WebServlet("/admin/list_users") // через этот URL мы посылаем запрос к этому сервлету, запрос идет в метод ДУГЕТ
									// и передаем в него request
public class ListUsersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ListUsersServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) // Гет для получения данных с
																					// сервера,для изменения и получения
			throws ServletException, IOException {

		UsersServices usersServices = new UsersServices(request, response);
		usersServices.listUser(); // весь бизнес код находится в listUser

	} // перенаправить запрос из сервлета на другой сервлет, html-страницу или
		// страницу jsp. Причем в данном случае речь идет о перенаправлении запроса, а
		// не о переадресации.

}
/*
 * Принцип работы ListUsersServlet вызывает listUser из UserServices и
 * UserServices listAll () в UserDAO далее header - заголовок - пользователь
 * нажимает на ссылку Users далее метод doGet () сервлета вызывает метод
 * listUser () из UsersServices UserServices вызывает метод listAll () из класса
 * UserDAO и метод listAll () вызывает findWithNamedQuery () класса JpaDAO
 * результат мы имеем страницу list user
 */
