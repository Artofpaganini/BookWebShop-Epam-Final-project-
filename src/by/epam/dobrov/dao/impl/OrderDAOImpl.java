package by.epam.dobrov.dao.impl;

import java.util.Date;
import java.util.List;

import by.epam.dobrov.dao.GenericDAO;
import by.epam.dobrov.dao.JpaDAO;
import by.epam.dobrov.entity.Book;
import by.epam.dobrov.entity.BookOrder;

public class OrderDAOImpl extends JpaDAO<BookOrder> implements GenericDAO<BookOrder> {

	@Override
	public BookOrder create(BookOrder bookOrder) {

		bookOrder.setOrderDate(new Date()); // для установки времени создания заказа
		/*
		 * т.к. это единственный метод оплаты и статуса заказа при его создании , то их можно вынести сюда, чтобы
		 * постоянно не вызывать
		 */
		bookOrder.setPaymentMethod("Cash on delivery");
		bookOrder.setOrderStatus("Processing");
		return super.create(bookOrder);
	}

	@Override
	public BookOrder update(BookOrder order) {

		return super.update(order);
	}

	@Override
	public BookOrder get(Object orderId) {
		return super.find(BookOrder.class, orderId);
	}

	@Override
	public void delete(Object orderId) {
		super.delete(BookOrder.class, orderId);

	}

	@Override
	public List<BookOrder> listAll() {
		 
		return super.findByNamedQuery("BookOrder.findAll");
	}

	@Override
	public long count() {
		long count  = super.findCountByNamedQuery("BookOrder.countAll");
		
		return count;
	}

}
