package by.epam.dobrov.controller.frontend.customer;

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

/**
 * 9. Система Интернет-магазин. Администратор осуществляет ведение каталога
 * Товаров. Клиент делает и оплачивает Заказ на Товары. Администратор может
 * занести неплательщиков в “черный список”.
 * 
 * @author Viktor
 *
 */
@WebFilter("/*")
public class CustomerLoginFilter implements Filter {

	private static final String[] loginRequiredURLs = { "/view_profile", "/edit_profile", "/update_profile",
			"/checkout", "/place_order", "/view_orders", "/show_order_detail" };

	public CustomerLoginFilter() {
	
	}

	public void destroy() {
	
	}
	

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpSession session = httpServletRequest.getSession(false); 
		
		String path = httpServletRequest.getRequestURI().substring(httpServletRequest.getContextPath().length());
		
		boolean logIn = session != null && session.getAttribute("loggedCustomer") != null; 
	
		
		String requestURL = httpServletRequest.getRequestURL().toString();
		System.out.println("path : " + path);
		System.out.println("login : " + logIn);

		if (!logIn && isLoginRequired(requestURL)) {
			
			String loginPage = "/frontend/login.jsp";
			RequestDispatcher requestDispatcher = httpServletRequest.getRequestDispatcher(loginPage);
			requestDispatcher.forward(httpServletRequest, response);
			
		} else {
			chain.doFilter(request, response);
		}
	}

	private boolean isLoginRequired(String requestURL) {

		for (String loginRequiredURL : loginRequiredURLs) {
			if (requestURL.contains(loginRequiredURL)) {
				return true;
			}
		}
		return false;
	}

	public void init(FilterConfig fConfig) throws ServletException {

	}

}
