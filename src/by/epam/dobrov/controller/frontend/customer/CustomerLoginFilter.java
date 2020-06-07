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

@WebFilter("/*")
public class CustomerLoginFilter implements Filter {
	/*
	 * этот фильтр будет перенаправлять со страниц - ред профиль, посмотреть историю
	 * заказов, сделать заказ, логаут , на страницу логин, если покупатель не
	 * авторизован так же идет проверка если в начале запроса есть admin то
	 * перенаправление на цепочку фильтра для админа
	 * 
	 * созданим массив в котором будем хранить все URL с который нужно будет делать
	 * редирект! и далее проверим если хотя бы есть 1 запрос похожий на те что в
	 * списке . то редирект
	 */
	private static final String[] loginRequiredURLs = { "/view_profile", "/edit_profile", "/update_profile",
			"/checkout", "/place_order", "/view_orders", "/show_order_detail" };

	public CustomerLoginFilter() {
		// TODO Auto-generated constructor stub
	}

	public void destroy() {
		// TODO Auto-generated method stub
	}
	/*
	 * проверяя атрибут с именем loggedCustomer в сеансе, нам нужно привести этот
	 * объект запроса к типу HttpServletRequest и получить сессию Нам нужно
	 * использовать перегрузку с логическим параметром со значением false, чтобы
	 * указать, что ... ... мы получаем текущий сеанс, и код не создает новый сеанс,
	 * если он не существует
	 */

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpSession session = httpServletRequest.getSession(false);
		/*
		 * далее мы получим сессию, в нашем случае залогиненного юзера, а следующая не
		 * будет создаваться пока она не будет ожидаться
		 */

		/*
		 * получение пути запроса строим URI запроса самостоятельно на основе длины
		 * контекста
		 */
		String path = httpServletRequest.getRequestURI().substring(httpServletRequest.getContextPath().length());

		/*
		 * т.к. не все запросы должны требовать аутентификациии Если запрос начинается с
		 * /admin/запрос поступает в админ-секцию (бэкэнд), мы продолжаем делать фильтр
		 * - продолжать цепочку фильтров
		 */
		if (path.startsWith("/admin/")) { // startsWith Проверяет, что текущий путь начинается с переданного пути.
			chain.doFilter(httpServletRequest, response);
			return;
		}

		/*
		 * т.е. проверяем сессия не нул после доступа к сессии атрибут не нулл то все
		 * норм тут мы создаем условия для сессии код не создает новую сессю, если он не
		 * существует, для этого ставим значение фолс и однвременно получаем свою сессию
		 * котора уже имеется
		 */
		boolean logIn = session != null && session.getAttribute("loggedCustomer") != null; // это условие проверяет
		// вошел ли покупатель или
		// нетw

		/*
		 * если клиент не залогинился и часть запроса является /view_profile, то мы
		 * переводим его на стр логина
		 */

		/*
		 * этот запрос помещается в isLoginRequired где проверяется на совпадение со
		 * словами массива URL
		 */
		String requestURL = httpServletRequest.getRequestURL().toString();
		System.out.println("path : " + path);
		System.out.println("login : " + logIn);

		/*
		 * чтобы пользователь не зашел на страницу профиля , если он не залогинен,
		 * используем это условие , если юзер не соответ условию login и при этом часть
		 * запроса начинается с viewprofile то мы переводим на страницу login
		 */
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
