package by.epam.dobrov.dao;

import java.util.List;

/**
 * 9. Система Интернет-магазин. Администратор осуществляет ведение каталога
 * Товаров. Клиент делает и оплачивает Заказ на Товары. Администратор может
 * занести неплательщиков в “черный список”.
 * 
 * @author Viktor
 *
 */
public interface GenericDAO<E> {

	public E create(E t);

	public E update(E t);

	public E get(Object id);

	public void delete(Object id);

	public List<E> listAll();

	long count();
}
