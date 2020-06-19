package by.epam.dobrov.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import by.epam.dobrov.dao.GenericDAO;
import by.epam.dobrov.dao.JpaDAO;
import by.epam.dobrov.entity.BookOrder;

/**
 * 9. Система Интернет-магазин. Администратор осуществляет ведение каталога
 * Товаров. Клиент делает и оплачивает Заказ на Товары. Администратор может
 * занести неплательщиков в “черный список”.
 * 
 * @author Viktor
 *
 */
public class OrderDAOImpl extends JpaDAO<BookOrder> implements GenericDAO<BookOrder> {

	@Override
	public BookOrder create(BookOrder bookOrder) {

		bookOrder.setOrderDate(new Date()); 

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

	public BookOrder get(Integer orderId, Integer customerId) {
		/*
		 * получение букордера не только по ИД заказа но и по ИД покупателя,т.к. если
		 * выбрать к примеру ид заказа 10 а потом его поменять на другой ИД то можно
		 * получить данные о заказе другого покупателя
		 */
		Map<String, Object> parameters = new HashMap<>();

		parameters.put("orderId", orderId);
		parameters.put("customerId", customerId);

		List<BookOrder> result = super.findByNamedQuery("BookOrder.findByIdAndCustomer", parameters);
		if (!result.isEmpty()) {
			return result.get(0);

		}
		return null;
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
		long count = super.findCountByNamedQuery("BookOrder.countAll");

		return count;
	}

	public List<BookOrder> listByCustomer(Integer customerId) {

		return super.findByNamedQuery("BookOrder.findByCustomer", "customerId", customerId);

	}

}
