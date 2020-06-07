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
		 * в этом методе получаем список всех заказов, и перенаправляем его на страницу
		 * заказов
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
		 * этот метод показывает детали о заказе
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
		 * этот метод делает перенаправление , когда нажимаем кнопку checkout
		 */
		String checkOutPage = "frontend/checkout.jsp";

		RequestDispatcher requestDispatcher = request.getRequestDispatcher(checkOutPage);

		requestDispatcher.forward(request, response);

	}

	public void placeOrder() throws ServletException, IOException {
		/*
		 * метод оформляет заказ, согласно вписанной информации, сначала мы должны
		 * получить в метод всю вписанную инфу, сначала все данные о покупателе
		 */

		String recipientName = request.getParameter("recipientName");
		String recipientPhone = request.getParameter("recipientPhone");
		String address = request.getParameter("address");
		String city = request.getParameter("city");
		String zipCode = request.getParameter("zipCode");
		String country = request.getParameter("country");
		String paymentMethod = request.getParameter("paymentMethod");

		/*
		 * теперь считываем все данные заказе
		 */

		BookOrder order = new BookOrder();
		/*
		 * устанавливаем в данные о заказе все данные о юзере
		 */

		String shippingAddress = address + ", " + city + ", " + zipCode + ", " + country;

		order.setRecipientName(recipientName);
		order.setRecipientPhone(recipientPhone);
		order.setShippingAddress(shippingAddress);
		order.setPaymentMethod(paymentMethod);

		/*
		 * Создаем сессию, для покупателя и получаем экземпляр связанный с определённым
		 * именем в данной сессии.
		 */

		HttpSession session = request.getSession();
		Customer customer = (Customer) session.getAttribute("loggedCustomer");

		/*
		 * присваиваем этого покупателя в заказ
		 */
		order.setCustomer(customer);

		/*
		 * теперь возвращаем мапу , где ключ-это книга а значение это кол-во книг , и
		 * присваиваем ее в заказ значения мы получаем из выбранный с помощью addtocart
		 */

		Cart cart = (Cart) session.getAttribute("cart");
		Map<Book, Integer> items = cart.getItems();

		/*
		 * итерируем пока эл-ты(книги) будут добавляться , таким образом постоянно
		 * обновляя ордер дитеилс, при этом для каждого элемента (который хранит инфу о
		 * книге, кол-ве книг , цене, и полнойстоимости) корзины создавая новый
		 * ордердитейл
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
		 * и в конце мы отправяем это все в букрОрдер, который явяется общим как для
		 * инфы о покупателе так для инфы о книге
		 */
		order.setOrderDetails(orderDetails);
		/*
		 * выводим полную стоимость заказа
		 */
		order.setOrderTotal(cart.getTotalAmount());

		/*
		 * После полного добавления, создаем данный заказ, и после сразу же очищаем
		 * корзину
		 */

		orderDAOImpl.create(order);
		cart.clear();

		/*
		 * передаем сообщение спасибо за заказ
		 */
		String message = "Thank you for the order";

		request.setAttribute("message", message);

		String messagePage = "/frontend/message.jsp";

		RequestDispatcher requestDispatcher = request.getRequestDispatcher(messagePage);

		requestDispatcher.forward(request, response);

	}

	public void listOrderByCustomer() throws ServletException, IOException {
		/*
		 * с помощью этого метода получаем все историю заказов покупателя, изначально
		 * загружая сессию покупателя который залогинился и работаем с ней
		 */
		HttpSession session = request.getSession();
		Customer customer = (Customer) session.getAttribute("loggedCustomer");

		List<BookOrder> listOrders = orderDAOImpl.listByCustomer(customer.getCustomerId());

		/*
		 * загружаем полученный список заказов покупателя на страницу истории заказов
		 */
		request.setAttribute("listOrders", listOrders);

		String historyPage = "/frontend/order_list.jsp";

		RequestDispatcher requestDispatcher = request.getRequestDispatcher(historyPage);

		requestDispatcher.forward(request, response);
	}

	public void showEditOrderForm() throws ServletException, IOException {

		/*
		 * Метод перенаправляет со страницы со всеми заказами, на страницу конкретного
		 * заказа, где ее можно будет редактировать
		 */

		Integer orderId = Integer.parseInt(request.getParameter("id"));

		BookOrder order = orderDAOImpl.get(orderId);

		/*
		 * после получения номер заказа открывает и создаем для него сессию и работаем с
		 * ней
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
		 * мы запрашиваем ИД номера книги
		 */

		int bookId = Integer.parseInt(request.getParameter("id"));
		/*
		 * далее получаем сессию заказа, и получаем сам заказ
		 */

		HttpSession session = request.getSession();

		BookOrder order = (BookOrder) session.getAttribute("order");

		/*
		 * далее с помощью сет Ордер дитеилс мы перебираем полученный заказ, присваиваем
		 * его ордер дитейл и сравниваем его с запрошенным ИД через итератор, если
		 * совпал то удаляем и идем дальше , формально мы просто перебираем циклом наш
		 * Ид находим его и удаляем
		 */

		Set<OrderDetail> orderDetails = order.getOrderDetails(); // ошибка тут !

		Iterator<OrderDetail> iterator = orderDetails.iterator();

		while (iterator.hasNext()) {
			OrderDetail orderDetail = iterator.next();

			if (orderDetail.getBook().getBookId() == bookId) {
				/*
				 * так же выполняем ф-циб пересчета полной стоимости
				 */
				float newTotal = order.getOrderTotal() - orderDetail.getSubTotal();
				order.setOrderTotal(newTotal);
				iterator.remove();
			}
		}
		/*
		 * далее обновляем страницу ордерформ
		 */

		String editOrderFormPage = "order_form.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(editOrderFormPage);
		requestDispatcher.forward(request, response);

	}

	public void updateOrder() throws ServletException, IOException {  //РАЗОБРАТЬСЯ С ЭТИМ МЕТОДОМ КАК ОН РАБОТАЕТ 

		/*
		 * далее получаем сессию заказа, и получаем сам заказ из сессию 
		 */
		HttpSession session = request.getSession();

		BookOrder order = (BookOrder) session.getAttribute("order");

		
		String recipientName = request.getParameter("recipientName");
		String recipientPhone = request.getParameter("recipientPhone");
		String shippingAddress = request.getParameter("shippingAddress");
		String paymentMethod = request.getParameter("paymentMethod");
		String orderStatus = request.getParameter("orderStatus");

		/*
		 * выше указаны все данные покупателя которые в последующем будут изменены ниже
		 * массивы книг ИД которые указаны в заказе и массивы цен на эти книги а так же
		 * массив их кол-ва Длина arrayQuantity такая же как и у массива книг ,а вот
		 * элементы являются кол-вом этих самых книг
		 */

		String[] arrayBookId = request.getParameterValues("bookId");
		String[] arrayPrice = request.getParameterValues("price");
		String[] arrayQuantity = new String[arrayBookId.length];

		for (int i = 1; i <= arrayQuantity.length; i++) {
			/*
			 * цикл вызывает каждый элемент (кол-во книг) по отдельности
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
		 * этот метод показывает детали о заказе для покупалея
		 */

		Integer orderId = Integer.parseInt(request.getParameter("id"));

		/*
		 * сессию мы вызываем для доп проверки что бы можно было смотреть историю
		 * заказов только залогиневшегося покупателя
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
