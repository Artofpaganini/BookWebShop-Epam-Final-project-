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

@WebFilter("/admin/*") // * говорит что все запросы которые имеют в себе admin будут проходить через
						// этот фильтр
/*
 * –≈јЋ»«ј÷»я  фильтоа дл€ перехватывани€ всех запросов приход€щих в секцию админа 
 * ‘ильтры могут быть использованы дл€ следующих задач: –абота с ответами
 * сервера перед тем, как они будут переданы клиенту ѕерехватывание запросов от
 * клиента перед тем, как они будут отправлены на сервер мы можем иметь цепочки
 * фильтроав работает как барьер проверки , выполнено ли условие или нет
 */
public class AdminLoginFilter implements Filter {

	public AdminLoginFilter() {
		// TODO Auto-generated constructor stub
	}

	public void destroy() {
		// TODO Auto-generated method stub
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpSession session = httpServletRequest.getSession(false); // фолс имеетс€ ввиде что мы получили сессию и не
																	// создаем новую, если она не ожидаетс€
		/**
		 * 'это утверждение и определ€ет юзер залогинилс€ или нет
		 */
		boolean logIn = session != null && session.getAttribute("useremail") != null;// т.е. провер€ем сесси€ не нул
																						// после доступа к сессии
																						// атрибут не нулл то все норм

		String loginURI = httpServletRequest.getContextPath() + "/admin/login";
		boolean loginRequest = httpServletRequest.getRequestURI().equals(loginURI);
		boolean loginPage = httpServletRequest.getRequestURI().endsWith("login.jsp");

		if (logIn && (loginRequest || loginPage)) { // переводит на страницу админа . если ты не сделал логаут но вписал в урл адресе login.jsp

			RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/");
			dispatcher.forward(request, response);

		} else if (logIn || loginRequest) {
			chain.doFilter(request, response); // если юзер залогинилс€ то продолжаем нормальный поток запросов, т.е
												// переходы на соотв стр и тп
		} else {

			RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp"); // иначе возвращаем на страницу
																						// ввода логина и пасса
			dispatcher.forward(request, response);
		}

		// это услвоие работает так, т.е ты залогинилс€, сделал свои дела и вышел, и
		// если ты попытаешьс€ зайти на любую страницу где был раньше, то будет выбивать
		// страницу Ћогин
	}

	public void init(FilterConfig fConfig) throws ServletException {

	}

}
