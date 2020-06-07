package by.epam.dobrov.dao.impl;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.sound.midi.Soundbank;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import by.epam.dobrov.entity.Book;
import by.epam.dobrov.entity.BookOrder;
import by.epam.dobrov.entity.Customer;
import by.epam.dobrov.entity.OrderDetail;
import by.epam.dobrov.entity.OrderDetailId;

public class OrderDAOImplTest {

	private static OrderDAOImpl orderDAOImpl;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		orderDAOImpl = new OrderDAOImpl();
		BaseDAOTest.setUpClass();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		BaseDAOTest.tearDownClass();
	}

	@Test
	public void test_ShouldCreateBookOrderWithTwoBooks() {
		BookOrder order = new BookOrder();
		Customer customer = new Customer();
		customer.setCustomerId(51);

		order.setCustomer(customer);
		order.setRecipientName("John Shelby123");
		order.setRecipientPhone("123123123");
		order.setShippingAddress("123");

		Set<OrderDetail> orderDetails = new HashSet<>();
		OrderDetail orderDetail1 = new OrderDetail();
		OrderDetail orderDetail2 = new OrderDetail();

		Book book1 = new Book(18);

		orderDetail1.setBook(book1);
		orderDetail1.setQuantity(2);
		orderDetail1.setSubTotal(39.98f);
		orderDetail1.setBookOrder(order);

		Book book2 = new Book(19);

		orderDetail2.setBook(book2);
		orderDetail2.setQuantity(1);
		orderDetail2.setSubTotal(33.99f);
		orderDetail2.setBookOrder(order);

		orderDetails.add(orderDetail1);
		orderDetails.add(orderDetail2);

		order.setOrderDetails(orderDetails);

		orderDAOImpl.create(order);

		assertTrue(order.getOrderId() > 0 && orderDetails.size() == 2);
	}

	@Test
	public void test_ShouldCreateBookOrderWithOneBooks() {
		BookOrder order = new BookOrder();
		Customer customer = new Customer();
		customer.setCustomerId(18);

		order.setCustomer(customer);
		order.setRecipientName("Artur Shelby");
		order.setRecipientPhone("12323123123");
		order.setShippingAddress("12345678");

		Set<OrderDetail> orderDetails = new HashSet<>();
		OrderDetail orderDetail = new OrderDetail();

		Book book = new Book(17);
		orderDetail.setBook(book);
		orderDetail.setQuantity(1);
		orderDetail.setSubTotal(25.65f);
		orderDetail.setBookOrder(order);

		orderDetails.add(orderDetail);

		order.setOrderDetails(orderDetails);

		orderDAOImpl.create(order);

		assertTrue(order.getOrderId() > 0);
	}

	@Test
	public void test_ShouldUpdateAddress() {

		Integer orderId = 11;
		BookOrder order = orderDAOImpl.get(orderId);
		order.setShippingAddress("Grodno123");

		orderDAOImpl.update(order);

		BookOrder updatedOrder = orderDAOImpl.get(orderId);

		assertEquals(order.getShippingAddress(), updatedOrder.getShippingAddress());
	}

	@Test
	public void test_ShouldGetBookOrderById() {
		Integer orderId = 11;
		BookOrder bookOrder = orderDAOImpl.get(orderId);

		System.out.println(bookOrder.getRecipientName());
		assertEquals(2, bookOrder.getOrderDetails().size());
	}

	@Test
	public void test_ShouldDeleteOrder() {
		Integer orderId = 1;

		orderDAOImpl.delete(orderId);

		BookOrder bookOrder = orderDAOImpl.get(orderId);

		assertNull(bookOrder);
	}

	@Test
	public void test_ShouldListAllBookOrders() {
		List<BookOrder> listOrders = orderDAOImpl.listAll();

		for (BookOrder bookOrder : listOrders) {
			System.out.println(bookOrder.getRecipientName() + " " + bookOrder.getOrderDate());
		}
		assertTrue(listOrders.size() > 0);
	}

	@Test
	public void test_ShouldCountOrders() {
		long totalOrdersQuantity = orderDAOImpl.count();

		assertEquals(2, totalOrdersQuantity);
	}

	@Test
	public void test_ShouldShowListByCustomerWithNoOrders() {
		Integer customerId = 99;

		List<BookOrder> listOrders = orderDAOImpl.listByCustomer(customerId);

		assertTrue(listOrders.isEmpty());
	}

	@Test
	public void test_ShouldShowListByCustomerWithOrders() {
		Integer customerId = 51;

		List<BookOrder> listOrders = orderDAOImpl.listByCustomer(customerId);

		assertTrue(listOrders.size() > 0);
	}

	@Test
	public void test_ShouldNotGetByIdAndCustomer() {
		Integer orderId = 9;
		Integer customerId = 99;

		
		BookOrder order = orderDAOImpl.get(orderId, customerId);
		
		assertNull(order);
	}
	
	@Test
	public void test_ShouldGetByIdAndCustomer() {
		Integer orderId = 11;
		Integer customerId = 51;

		
		BookOrder order = orderDAOImpl.get(orderId, customerId);
		
		assertNotNull(order);
	}
}
