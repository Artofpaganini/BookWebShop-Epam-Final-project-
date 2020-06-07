package by.epam.dobrov.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import by.epam.dobrov.dao.impl.CustomerDAOImpl;
import by.epam.dobrov.dao.impl.generator.HashGenerator;
import by.epam.dobrov.entity.Customer;
import javassist.bytecode.analysis.ControlFlow.Block;

public class CustomerServices {

	private final static Logger LOGGER = LoggerFactory.getLogger(CustomerServices.class);

	private CustomerDAOImpl customerDAOImpl;
	private HttpServletRequest request;
	private HttpServletResponse response;

	public CustomerServices(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;

		customerDAOImpl = new CustomerDAOImpl();
	}

	public void listCustomer() throws ServletException, IOException {
		listCustomer(null);
	}

	public void listCustomer(String message) throws ServletException, IOException {

		LOGGER.info("Got list of the customer from DB");

		List<Customer> listCustomer = customerDAOImpl.listAll();

		if (message != null) { // проверка на наличие сообщения , если оно не налл то выводим его
			request.setAttribute("message", message);
		}

		request.setAttribute("listCustomer", listCustomer);

		String listCustomerPage = "customer_list.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(listCustomerPage);
		requestDispatcher.forward(request, response);
	}

	private void updateCustomerFieldsFromForm(Customer customer) { // метод с общими данными и присвоением их входящему
																	// покупателю

		String email = request.getParameter("email");
		String fullName = request.getParameter("fullName");
		String password = request.getParameter("password");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		String city = request.getParameter("city");
		String zipCode = request.getParameter("zipCode");
		String country = request.getParameter("country");

		if (email != null && !(email.equals(""))) {
			customer.setEmail(email);
		}
		
		customer.setFullName(fullName);

		if (password != null & !password.isEmpty()) {
			String encryptedPassword = HashGenerator.generateSHA256(password);
			customer.setPassword(encryptedPassword);
		}

		customer.setPhone(phone);
		customer.setAddress(address);
		customer.setCity(city);
		customer.setZipCode(zipCode);
		customer.setCountry(country);

	}

	public void createCustomer() throws ServletException, IOException {
		LOGGER.info("Create new Customer");
		
		String email = request.getParameter("email");

		Customer expectedCustomer = customerDAOImpl.findByEmail(email);

		String message = null;

		if (expectedCustomer != null) {
			
			message = "The customer with email: " + email + " is already exist!";

			listCustomer(message);

			LOGGER.warn("The customer with email:{} is already exist!", email);
		} else {

			Customer customer = new Customer();

			updateCustomerFieldsFromForm(customer);

			customerDAOImpl.create(customer);

			message = "New customer has been created!";

			listCustomer(message);

			LOGGER.info("New customer has been created!");

		}

	}

	public void registerCustomer() throws ServletException, IOException {
		LOGGER.info("Customer Registration");

		String email = request.getParameter("email");

		Customer expectedCustomer = customerDAOImpl.findByEmail(email);

		if (expectedCustomer != null) {
			String message = "The customer with email: " + email + " is already exist!";

			String messagePage = "/frontend/message.jsp";

			RequestDispatcher requestDispatcher = request.getRequestDispatcher(messagePage);
			request.setAttribute("message", message);
			requestDispatcher.forward(request, response);
			LOGGER.warn("The customer with email:{} is already exist!", email);
		} else {

			Customer customer = new Customer();

			updateCustomerFieldsFromForm(customer);
			customerDAOImpl.create(customer);

			String message = "The registration has been completed!<br/>" + "<a href='login'>Click here</a> to login";
			/*
			 * тут не выводим список пользователей, а выводим просто это сообщение
			 */

			String messagePage = "/frontend/message.jsp";
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(messagePage);
			request.setAttribute("message", message);
			requestDispatcher.forward(request, response);

			LOGGER.info("The registration has been completed!");

		}

	}

	public void editCustomer() throws ServletException, IOException {

		LOGGER.info("Edit some information about the customer ");
		Integer customerId = Integer.parseInt(request.getParameter("id"));

		Customer customer = customerDAOImpl.get(customerId);

		String editCustomerPage = "customer_form.jsp";

		if (customer != null) {

			customer.setPassword(null);
			request.setAttribute("customer", customer);

		} else {
			String message = "Couldn't find  the customer with ID: " + customerId;

			request.setAttribute("message", message);

			LOGGER.warn("Could not find customer with ID {}", customerId);
		}

		RequestDispatcher requestDispatcher = request.getRequestDispatcher(editCustomerPage);
		requestDispatcher.forward(request, response);

		LOGGER.info("Edit customer has been successfully ");
	}

	public void updateCustomer() throws ServletException, IOException {

		LOGGER.info("Update some information about the customer");

		Integer customerId = Integer.parseInt(request.getParameter("customerId"));

		String email = request.getParameter("email");

		String message = null;

		Customer customerByEmail = customerDAOImpl.findByEmail(email);

		if (customerByEmail != null && customerByEmail.getCustomerId() != customerId) {
			message = "Email: " + email + " is already exist";
			listCustomer(message);
			LOGGER.warn("Got warning, Customer  with email {} is already exist!!!", email);

		} else {

			String block = request.getParameter("block");
			Boolean status = Boolean.parseBoolean(block);

			customerByEmail.setBlock(status);

			updateCustomerFieldsFromForm(customerByEmail);
			customerDAOImpl.update(customerByEmail);

			message = "The customer has been updated!";

			listCustomer(message);

			LOGGER.info("Customer has been updated");

		}

	}

	public void deleteCustomer() throws ServletException, IOException {

		LOGGER.info("Try to delete customer ");

		Integer customerId = Integer.parseInt(request.getParameter("id"));

		customerDAOImpl.delete(customerId);

		String message = "The customer with ID: " + customerId + " has been deleted!";

		listCustomer(message);

		LOGGER.info("The customer with ID {} has been deleted", customerId);
	}
	

	public void showLogin() throws ServletException, IOException {
		/*
		 * метод для перенаправления на страницу логин, при нажатии sign in
		 */
		String loginForm = "frontend/login.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(loginForm);
		requestDispatcher.forward(request, response);

	}

	public void showCustomerProfile() throws ServletException, IOException {
		/*
		 * метод для перенаправления на страницу логин, при нажатии sign in
		 */
		String loginForm = "frontend/customer_profile.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(loginForm);
		requestDispatcher.forward(request, response);

	}

	public void doLogin() throws ServletException, IOException {

		LOGGER.info("User is  trying to get the login access ");

		String email = request.getParameter("email");
		String password = request.getParameter("password");

		Customer customer = customerDAOImpl.checkLogin(email, password);

		if (customer == null) {
			String message = "Access failed,please try again!";
			request.setAttribute("message", message);

			showLogin();

			LOGGER.warn("Login access failed");
		} else if (customer.isBlock() == true) {

			String message = "Access blocked, for the information please contact administrator!";
			request.setAttribute("message", message);

			showLogin();

			LOGGER.warn("Access blocked, for the information please contact administrator!");

		} else {
			/*
			 * гет сессион - мы открываем сессию для конткретного покупателя
			 */

			request.getSession().setAttribute("loggedCustomer", customer);
			showCustomerProfile();

			LOGGER.debug("Customer is authenticated");
		}

	}

	public void showCustomerProfileEditForm() throws ServletException, IOException {
		/*
		 * метод переводит на страницу редактирования профиля при нажатии edit profile
		 */
		String editPage = "frontend/edit_profile.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(editPage);
		requestDispatcher.forward(request, response);

	}

	public void updateCustomerProfile() throws ServletException, IOException {
		/*
		 * метод обновляет отредактированную информацию чтобы обновить инфу о покупателе
		 * сначала нам нужно извлечь обект покупатель из сессии в нашем случае это
		 * loggedCustomer
		 */

		Customer customer = (Customer) request.getSession().getAttribute("loggedCustomer");
		/*
		 * после извлечения мы получаем все значения которые были в ред форме
		 */
		updateCustomerFieldsFromForm(customer);

		customerDAOImpl.update(customer);

		/*
		 * после апдейта перед на страницу пользователя customer profile
		 */

		showCustomerProfile();
	}

}
