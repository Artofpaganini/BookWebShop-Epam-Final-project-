package by.epam.dobrov.service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import by.epam.dobrov.controller.frontend.cart.Cart;
import by.epam.dobrov.dao.impl.OrderDAOImpl;
import by.epam.dobrov.entity.Book;
import by.epam.dobrov.entity.BookOrder;
import by.epam.dobrov.entity.Customer;
import by.epam.dobrov.entity.OrderDetail;

public class OrderServices {
	private final static Logger LOGGER = LoggerFactory.getLogger(OrderServices.class);

	private OrderDAOImpl orderDAOImpl;
	private HttpServletRequest request;
	private HttpServletResponse response;

	public OrderServices(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;

		this.orderDAOImpl = new OrderDAOImpl();
	}

	public void listAllOrder() throws ServletException, IOException {

		listAllOrder(null);
	}

	public void listAllOrder(String message) throws ServletException, IOException {
		/*
		 * � ���� ������ �������� ������ ���� �������, � �������������� ��� �� ��������
		 * �������
		 */

		List<BookOrder> listOrder = orderDAOImpl.listAll();

		if (message != null) {

			request.setAttribute("message", message);

		}
		request.setAttribute("listOrder", listOrder);

		String listPage = "order_list.jsp";

		RequestDispatcher requestDispatcher = request.getRequestDispatcher(listPage);

		requestDispatcher.forward(request, response);

	}

	public void viewOrderDetailForAdmin() throws ServletException, IOException {
		/*
		 * ���� ����� ���������� ������ � ������
		 */

		Integer orderId = Integer.parseInt(request.getParameter("id"));

		BookOrder order = orderDAOImpl.get(orderId);

		request.setAttribute("order", order);

		String detailPage = "order_detail.jsp";

		RequestDispatcher requestDispatcher = request.getRequestDispatcher(detailPage);

		requestDispatcher.forward(request, response);
	}

	public void showCheckoutForm() throws ServletException, IOException {

		/*
		 * ���� ����� ������ ��������������� , ����� �������� ������ checkout
		 */
		String checkOutPage = "frontend/checkout.jsp";

		RequestDispatcher requestDispatcher = request.getRequestDispatcher(checkOutPage);

		requestDispatcher.forward(request, response);

	}

	public void placeOrder() throws ServletException, IOException {
		/*
		 * ����� ��������� �����, �������� ��������� ����������, ������� �� ������
		 * �������� � ����� ��� ��������� ����, ������� ��� ������ � ����������
		 */

		String recipientName = request.getParameter("recipientName");
		String recipientPhone = request.getParameter("recipientPhone");
		String address = request.getParameter("address");
		String city = request.getParameter("city");
		String zipCode = request.getParameter("zipCode");
		String country = request.getParameter("country");
		String paymentMethod = request.getParameter("paymentMethod");

		/*
		 * ������ ��������� ��� ������ ������
		 */

		BookOrder order = new BookOrder();
		/*
		 * ������������� � ������ � ������ ��� ������ � �����
		 */

		String shippingAddress = address + ", " + city + ", " + zipCode + ", " + country;

		order.setRecipientName(recipientName);
		order.setRecipientPhone(recipientPhone);
		order.setShippingAddress(shippingAddress);
		order.setPaymentMethod(paymentMethod);

		/*
		 * ������� ������, ��� ���������� � �������� ��������� ��������� � �����������
		 * ������ � ������ ������.
		 */

		HttpSession session = request.getSession();
		Customer customer = (Customer) session.getAttribute("loggedCustomer");

		/*
		 * ����������� ����� ���������� � �����
		 */
		order.setCustomer(customer);

		/*
		 * ������ ���������� ���� , ��� ����-��� ����� � �������� ��� ���-�� ���� , �
		 * ����������� �� � ����� �������� �� �������� �� ��������� � ������� addtocart
		 */

		Cart cart = (Cart) session.getAttribute("cart");
		Map<Book, Integer> items = cart.getItems();

		/*
		 * ��������� ���� ��-��(�����) ����� ����������� , ����� ������� ���������
		 * �������� ����� �������, ��� ���� ��� ������� �������� (������� ������ ���� �
		 * �����, ���-�� ���� , ����, � ���������������) ������� �������� �����
		 * �����������
		 */

		Iterator<Book> iterator = items.keySet().iterator();

		Set<OrderDetail> orderDetails = new HashSet<>();

		while (iterator.hasNext()) {
			Book book = iterator.next();
			Integer quantity = items.get(book);
			float subTotal = quantity * book.getPrice();

			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setBook(book);
			orderDetail.setBookOrder(order);
			orderDetail.setQuantity(quantity);
			orderDetail.setSubTotal(subTotal);

			orderDetails.add(orderDetail);
		}

		/*
		 * � � ����� �� ��������� ��� ��� � ���������, ������� ������� ����� ��� ���
		 * ���� � ���������� ��� ��� ���� � �����
		 */
		order.setOrderDetails(orderDetails);
		/*
		 * ������� ������ ��������� ������
		 */
		order.setOrderTotal(cart.getTotalAmount());

		/*
		 * ����� ������� ����������, ������� ������ �����, � ����� ����� �� �������
		 * �������
		 */

		orderDAOImpl.create(order);
		cart.clear();

		/*
		 * �������� ��������� ������� �� �����
		 */
		String message = "Thank you for the order";

		request.setAttribute("message", message);

		String messagePage = "/frontend/message.jsp";

		RequestDispatcher requestDispatcher = request.getRequestDispatcher(messagePage);

		requestDispatcher.forward(request, response);

	}

	public void listOrderByCustomer() throws ServletException, IOException {
		/*
		 * � ������� ����� ������ �������� ��� ������� ������� ����������, ����������
		 * �������� ������ ���������� ������� ����������� � �������� � ���
		 */
		HttpSession session = request.getSession();
		Customer customer = (Customer) session.getAttribute("loggedCustomer");

		List<BookOrder> listOrders = orderDAOImpl.listByCustomer(customer.getCustomerId());

		/*
		 * ��������� ���������� ������ ������� ���������� �� �������� ������� �������
		 */
		request.setAttribute("listOrders", listOrders);

		String historyPage = "/frontend/order_list.jsp";

		RequestDispatcher requestDispatcher = request.getRequestDispatcher(historyPage);

		requestDispatcher.forward(request, response);
	}

	public void showEditOrderForm() throws ServletException, IOException {

		/*
		 * ����� �������������� �� �������� �� ����� ��������, �� �������� �����������
		 * ������, ��� �� ����� ����� �������������
		 */

		Integer orderId = Integer.parseInt(request.getParameter("id"));

		BookOrder order = orderDAOImpl.get(orderId);

		/*
		 * ����� ��������� ����� ������ ��������� � ������� ��� ���� ������ � �������� �
		 * ���
		 * 
		 */
		HttpSession session = request.getSession();

		session.setAttribute("order", order);

		String editPage = "/admin/order_form.jsp";

		RequestDispatcher requestDispatcher = request.getRequestDispatcher(editPage);

		requestDispatcher.forward(request, response);

	}

	public void removeBookFromOrder() throws ServletException, IOException {
		/*
		 * �� ����������� �� ������ �����
		 */

		int bookId = Integer.parseInt(request.getParameter("id"));
		/*
		 * ����� �������� ������ ������, � �������� ��� �����
		 */

		HttpSession session = request.getSession();

		BookOrder order = (BookOrder) session.getAttribute("order");

		/*
		 * ����� � ������� ��� ����� ������� �� ���������� ���������� �����, �����������
		 * ��� ����� ������ � ���������� ��� � ����������� �� ����� ��������, ����
		 * ������ �� ������� � ���� ������ , ��������� �� ������ ���������� ������ ���
		 * �� ������� ��� � �������
		 */

		Set<OrderDetail> orderDetails = order.getOrderDetails(); // ������ ��� !

		Iterator<OrderDetail> iterator = orderDetails.iterator();

		while (iterator.hasNext()) {
			OrderDetail orderDetail = iterator.next();

			if (orderDetail.getBook().getBookId() == bookId) {
				/*
				 * ��� �� ��������� �-��� ��������� ������ ���������
				 */
				float newTotal = order.getOrderTotal() - orderDetail.getSubTotal();
				order.setOrderTotal(newTotal);
				iterator.remove();
			}
		}
		/*
		 * ����� ��������� �������� ���������
		 */

		String editOrderFormPage = "order_form.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(editOrderFormPage);
		requestDispatcher.forward(request, response);

	}

	public void updateOrder() throws ServletException, IOException {  //����������� � ���� ������� ��� �� �������� 

		/*
		 * ����� �������� ������ ������, � �������� ��� ����� �� ������ 
		 */
		HttpSession session = request.getSession();

		BookOrder order = (BookOrder) session.getAttribute("order");

		
		String recipientName = request.getParameter("recipientName");
		String recipientPhone = request.getParameter("recipientPhone");
		String shippingAddress = request.getParameter("shippingAddress");
		String paymentMethod = request.getParameter("paymentMethod");
		String orderStatus = request.getParameter("orderStatus");

		/*
		 * ���� ������� ��� ������ ���������� ������� � ����������� ����� �������� ����
		 * ������� ���� �� ������� ������� � ������ � ������� ��� �� ��� ����� � ��� ��
		 * ������ �� ���-�� ����� arrayQuantity ����� �� ��� � � ������� ���� ,� ���
		 * �������� �������� ���-��� ���� ����� ����
		 */

		String[] arrayBookId = request.getParameterValues("bookId");
		String[] arrayPrice = request.getParameterValues("price");
		String[] arrayQuantity = new String[arrayBookId.length];

		for (int i = 1; i <= arrayQuantity.length; i++) {
			/*
			 * ���� �������� ������ ������� (���-�� ����) �� �����������
			 */
			arrayQuantity[i - 1] = request.getParameter("quantity" + i);
		}

		Set<OrderDetail> orderDetails = order.getOrderDetails();
		orderDetails.clear();

		float totalAmount = 0.0f;

		for (int i = 0; i < arrayBookId.length; i++) {
			int bookId = Integer.parseInt(arrayBookId[i]);
			int quantity = Integer.parseInt(arrayQuantity[i]);
			float price = Float.parseFloat(arrayPrice[i]);

			float subTotal = price * quantity;

			OrderDetail orderDetail = new OrderDetail();

			orderDetail.setBook(new Book(bookId));
			orderDetail.setQuantity(quantity);
			orderDetail.setSubTotal(subTotal);
			orderDetail.setBookOrder(order);

			orderDetails.add(orderDetail);

			totalAmount += subTotal;
		}

		order.setRecipientName(recipientName);
		order.setRecipientPhone(recipientPhone);
		order.setShippingAddress(shippingAddress);
		order.setPaymentMethod(paymentMethod);
		order.setOrderStatus(orderStatus);

		order.setOrderTotal(totalAmount);

		orderDAOImpl.update(order);

		String message = "The order " + order.getOrderId() + " has been updated ";

		listAllOrder(message);

	}

	public void showOrderDetailForCustomer() throws ServletException, IOException {
		/*
		 * ���� ����� ���������� ������ � ������ ��� ���������
		 */

		Integer orderId = Integer.parseInt(request.getParameter("id"));

		/*
		 * ������ �� �������� ��� ��� �������� ��� �� ����� ���� �������� �������
		 * ������� ������ ��������������� ����������
		 */

		HttpSession session = request.getSession();
		Customer customer = (Customer) session.getAttribute("loggedCustomer");

		BookOrder order = orderDAOImpl.get(orderId, customer.getCustomerId());

		request.setAttribute("order", order);

		String detailPage = "frontend/order_detail.jsp";

		RequestDispatcher requestDispatcher = request.getRequestDispatcher(detailPage);

		requestDispatcher.forward(request, response);

	}

	public void deleteOrder() throws ServletException, IOException {
		
		Integer orderId = Integer.parseInt(request.getParameter("id"));
		
		orderDAOImpl.delete(orderId);
				
		String message = "Order with ID: " + orderId + " has been deleted!";
		
		listAllOrder(message);

		
	}

}
