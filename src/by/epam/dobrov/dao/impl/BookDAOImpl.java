package by.epam.dobrov.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import by.epam.dobrov.dao.GenericDAO;
import by.epam.dobrov.dao.JpaDAO;
import by.epam.dobrov.entity.Book;
import by.epam.dobrov.entity.Users;

public class BookDAOImpl extends JpaDAO<Book> implements GenericDAO<Book> {

	public BookDAOImpl(EntityManager entityManager) {
		super(entityManager);

	}

	@Override
	public Book create(Book book) {

		return super.create(book);
	}

	@Override
	public Book update(Book book) {

		return super.update(book);
	}

	@Override
	public Book get(Object bookId) {

		return super.find(Book.class, bookId);
	}

	@Override
	public void delete(Object bookId) {

		super.delete(Book.class, bookId);
	}

	@Override
	public List<Book> listAll() {

		return super.findByNamedQuery("Book.findAll");
	}

	@Override
	public long count() {
		long count = super.findCountByNamedQuery("Book.countAll");

		return count;
	}

	public Book findByTitle(String title) {

		List<Book> bookList = super.findByNamedQuery("Book.findByTitle", "title", title);

		if (!bookList.isEmpty()) {
			return bookList.get(0);
		}
		return null;
	}

	public List<Book> listBookByCategory(int categoryId) {

		return super.findByNamedQuery("Book.findByCategory", "ñatId", categoryId);

	}

	public List<Book> search(String keyword) {

		return super.findByNamedQuery("Book.searchBook", "keyword", keyword);
	}

}

/*
 * List<Book> bookList = super.findByNamedQuery("Book.findByTitle", "title",
 * title);
 * 
 * if (usersList != null && usersList.size() > 0) { return usersList.get(0); }
 * return null;
 */