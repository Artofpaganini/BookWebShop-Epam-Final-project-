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

/**
 * 9. Система Интернет-магазин. Администратор осуществляет ведение каталога
 * Товаров. Клиент делает и оплачивает Заказ на Товары. Администратор может
 * занести неплательщиков в “черный список”.
 * 
 * @author Viktor
 *
 */
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

	/**
	 * Method update all customer fields , by parameters which inputed Method for
	 * the reducing the code
	 * 
	 * @param customer
	 */
	private void updateCustomerFieldsFromForm(Customer customer) {

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

	public void listCustomer() throws ServletException, IOException {
		listCustomer(null);
	}

	/**
	 * Method displays a list of customer on the screen, with an additional message
	 * if necessary
	 * 
	 * @param message
	 * @throws ServletException
	 * @throws IOException
	 */
	public void listCustomer(String message) throws ServletException, IOException {

		LOGGER.info("Get list of the customer from DB");

		List<Customer> listCustomer = customerDAOImpl.listAll();

		if (message != null) {
			request.setAttribute("message", message);
		}

		request.setAttribute("listCustomer", listCustomer);

		String listCustomerPage = "customer_list.jsp";
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(listCustomerPage);
		requestDispatcher.forward(request, response);
	}

	/**
	 * Method create the customer by email, and show error message if this email
	 * exist
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
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

	/**
	 * Method realized the function register form, customer eneter his information
	 * and if everything is fine , register will be successful
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
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
		
			String messagePage = "/frontend/message.jsp";

			RequestDispatcher requestDispatcher = request.getRequestDispatcher(messagePage);

			request.setAttribute("message", message);
			requestDispatcher.forward(request, response);

			LOGGER.info("The registration has been completed!");

		}

	}

	/**
	 * *Method check customer id , and if customer exist, we get a form for edit,
	 * and make a password null by visual
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
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

	/**
	 * Method realized update of info about the customer, if email existed - get
	 * error message else change form
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	public void updateCustomer() throws ServletException, IOException {

		LOGGER.info("Update some information about the customer");

		Integer customerId = Integer.parseInt(request.getParameter("customerId"));

		String email = request.getParameter("email");

		String message = null;

		Customer customerByEmail = customerDAOImpl.findByEmail(email);
		Customer customerById = customerDAOImpl.get(customerId);
		
		if (customerByEmail != null && customerByEmail.getCustomerId() != customerById.getCustomerId()) {
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

	/**
	 * Method deleted customer by id
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	public void deleteCustomer() throws ServletException, IOException {

		LOGGER.info("Try to delete customer ");

		Integer customerId = Integer.parseInt(request.getParameter("id"));

		customerDAOImpl.delete(customerId);

		String message = "The customer with ID: " + customerId + " has been deleted!";

		listCustomer(message);

		LOGGER.info("The customer with ID {} has been deleted", customerId);
	}

	/**
	 * Method redirect on login page if push sign in
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	public void showLogin() throws ServletException, IOException {

		String loginForm = "frontend/login.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(loginForm);
		requestDispatcher.forward(request, response);

	}

	/**
	 * Method redirect from login page on customer page, if customer logged
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	public void showCustomerProfile() throws ServletException, IOException {

		String loginForm = "frontend/customer_profile.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(loginForm);
		requestDispatcher.forward(request, response);

	}

	/**
	 * Method realized enter in customer page if access is opened, else - errors
	 * block or failed access , if access is opened we create a new session for
	 * customer
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	public void doLogin() throws ServletException, IOException {

		LOGGER.info("Customer is  trying to get the login access ");

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

			request.getSession().setAttribute("loggedCustomer", customer);
			showCustomerProfile();

			LOGGER.debug("Customer is authenticated");
		}

	}

	/**
	 * Method redirect from customer page on edit customer page
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	public void showCustomerProfileEditForm() throws ServletException, IOException {
		
		String editPage = "frontend/edit_profile.jsp";
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(editPage);
		requestDispatcher.forward(request, response);

	}

	/**
	 * Method updates the edited information, to update the customer information
	 * at first we need to extract the buyer object from the session in our case it is
	 * loggedCustomer
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	public void updateCustomerProfile() throws ServletException, IOException {

		Customer customer = (Customer) request.getSession().getAttribute("loggedCustomer");
	
		updateCustomerFieldsFromForm(customer);

		customerDAOImpl.update(customer);

		showCustomerProfile();
	}

}
