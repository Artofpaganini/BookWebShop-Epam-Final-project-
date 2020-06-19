package by.epam.dobrov.dao.impl;

import java.util.List;

import by.epam.dobrov.dao.GenericDAO;
import by.epam.dobrov.dao.JpaDAO;
import by.epam.dobrov.entity.Category;

/**
 * 9. Система Интернет-магазин. Администратор осуществляет ведение каталога
 * Товаров. Клиент делает и оплачивает Заказ на Товары. Администратор может
 * занести неплательщиков в “черный список”.
 * 
 * @author Viktor
 *
 */
public class CategoryDAOImpl extends JpaDAO<Category> implements GenericDAO<Category> {

	public CategoryDAOImpl() {

	}

	@Override
	public Category create(Category category) {

		return super.create(category);
	}

	@Override
	public Category update(Category category) {

		return super.update(category);
	}

	@Override
	public Category get(Object categoryId) {

		return super.find(Category.class, categoryId);
	}

	@Override
	public void delete(Object categoryId) {
		super.delete(Category.class, categoryId);

	}

	@Override
	public List<Category> listAll() {
		return super.findByNamedQuery("Category.findAll");
	}

	@Override
	public long count() {
		long count = super.findCountByNamedQuery("Category.countAll");

		return count;
	}

	public Category findByName(String categoryName) { 
		List<Category> listCategories = super.findByNamedQuery("Category.findByName", "name", categoryName);

		if (listCategories != null && listCategories.size() > 0) {
			return listCategories.get(0);
		}
		return null;
	}

}
