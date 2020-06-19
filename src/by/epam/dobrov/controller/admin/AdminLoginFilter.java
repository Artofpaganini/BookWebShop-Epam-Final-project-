package by.epam.dobrov.controller.admin;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@WebFilter("/admin/*") 

/**
 * 9. Система Интернет-магазин. Администратор осуществляет ведение каталога
 * Товаров. Клиент делает и оплачивает Заказ на Товары. Администратор может
 * занести неплательщиков в “черный список”.
 * 
 * @author Viktor
 *
 */
public class AdminLoginFilter implements Filter {

	public AdminLoginFilter() {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpSession session = httpServletRequest.getSession(false); 
		
		boolean logIn = session != null && session.getAttribute("useremail") != null;

		String loginURI = httpServletRequest.getContextPath() + "/admin/login";
		boolean loginRequest = httpServletRequest.getRequestURI().equals(loginURI);
		boolean loginPage = httpServletRequest.getRequestURI().endsWith("login.jsp");

		if (logIn && (loginRequest || loginPage)) { 

			RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/");
			dispatcher.forward(request, response);

		} else if (logIn || loginRequest) {
			chain.doFilter(request, response);
			
		} else {
			RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp"); 
			
			dispatcher.forward(request, response);
		}

		// это услвоие работает так, т.е ты залогинился, сделал свои дела и вышел, и
		// если ты попытаешься зайти на любую страницу где был раньше, то будет выбивать
		// страницу Логин
	}

	public void init(FilterConfig fConfig) throws ServletException {

	}

}
