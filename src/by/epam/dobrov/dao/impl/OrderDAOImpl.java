package by.epam.dobrov.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import by.epam.dobrov.dao.GenericDAO;
import by.epam.dobrov.dao.JpaDAO;
import by.epam.dobrov.entity.BookOrder;


public class OrderDAOImpl extends JpaDAO<BookOrder> implements GenericDAO<BookOrder> {

	@Override
	public BookOrder create(BookOrder bookOrder) {

		bookOrder.setOrderDate(new Date()); // дл€ установки времени создани€ заказа

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
		 * получение букордера не только по »ƒ заказа но и по »ƒ покупател€,т.к. если
		 * выбрать к примеру ид заказа 10 а потом его помен€ть на другой »ƒ то можно
		 * получить данные о заказе другого покупател€
		 */
		Map<String, Object> parameters = new HashMap<>();

		parameters.put("orderId", orderId);
		parameters.put("customerId", customerId);

		List<BookOrder> result = super.findByNamedQuery("BookOrder.findByIdAndCustomer", parameters);
		if(!result.isEmpty()) {
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
