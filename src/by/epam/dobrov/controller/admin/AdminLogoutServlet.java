package by.epam.dobrov.controller.admin;

import by.epam.dobrov.controller.BaseServlet;
import by.epam.dobrov.service.UsersServices;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/admin/logout")
public class AdminLogoutServlet extends BaseServlet { 	// реализация  кнопки logout  , удаление сессии 
	private static final long serialVersionUID = 1L;

	public AdminLogoutServlet() {
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

// в этом методе мы удаляем атрибут сессии
		HttpSession session = request.getSession();
		session.removeAttribute("useremail");
		
		//теперь перенаправляем на страницу  ЛОГИН
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
		dispatcher.forward(request, response);
	}

}
