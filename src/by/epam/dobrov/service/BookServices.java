package by.epam.dobrov.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import by.epam.dobrov.dao.impl.BookDAOImpl;
import by.epam.dobrov.dao.impl.CategoryDAOImpl;
import by.epam.dobrov.entity.Book;
import by.epam.dobrov.entity.Category;

public class BookServices {

	private final static Logger LOGGER = LoggerFactory.getLogger(BookServices.class);

	private EntityManager entityManager;
	private BookDAOImpl bookDAOImpl;
	private CategoryDAOImpl categoryDAOImpl; // ����� ��� ���������� ������ ���������, � ��������� � ����� ��������� ,
												// ������������ �����
	private HttpServletRequest request;
	private HttpServletResponse response;

	public BookServices(EntityManager entityManager, HttpServletRequest request, HttpServletResponse response) {
		super();
		this.entityManager = entityManager;
		this.request = request;
		this.response = response;
		bookDAOImpl = new BookDAOImpl(entityManager);
		categoryDAOImpl = new CategoryDAOImpl(entityManager);
	}

	public void listBooks() throws ServletException, IOException { // �������� ������ , ��� ���������, ����� ��� ����
																	// ����� ������ ��������� ������ ����
		listBooks(null);
	}

	// � ���� ����� ����� ���� �� �������� ������ ���� � ��� ���� ������ �������
	// ����� ���� ���������
	public void listBooks(String message) throws ServletException, IOException { // ��������� ������ ���� � ����� �� ��
																					// ��������
		// ������
		List<Book> listBooks = bookDAOImpl.listAll();
		request.setAttribute("listBooks", listBooks);

		if (message != null) { // �������� �� ������� ��������� , ���� ��� �� ���� �� ������� ���
			request.setAttribute("message", message);
		}

		String listpage = "book_list.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(listpage);

		requestDispatcher.forward(request, response);
	}

	public void showNewBookForm() throws ServletException, IOException {
		/*
		 * �������� ������ ���������, ��� ���� ����� ����� �� �������� � ��� �����, ����
		 * ����� �������� ������ ���� ��������� � �������� ��� � jsp ���� ��� ��������
		 * ���������� ������ ����� ������� ���������
		 */

		List<Category> listCategory = categoryDAOImpl.listAll();

		request.setAttribute("listCategory", listCategory); // ��������� ������������

		String page = "book_form.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(page);

		requestDispatcher.forward(request, response);

	}

	public void createNewBook() throws ServletException, IOException {
		LOGGER.info("Try to create a new Book");
		/*
		 * ����� �������� ����� �����, ������������ ���������� ���� ����� ����,�������
		 * ��������
		 */
		Integer categoryId = Integer.parseInt(request.getParameter("category"));
		String title = request.getParameter("title");

		Book existBook = bookDAOImpl.findByTitle(title);
		if (existBook != null) { // ��������� ����� ��������� �� �����������

			String message = " Book with title " + title + " is already exist!!!";
			listBooks(message);
			
			LOGGER.warn("Got warning, Book  with title {} is already exist!!!", title);
			return;
		}

		String author = request.getParameter("author");
		String isbn = request.getParameter("isbn");
		String description = request.getParameter("description");
		float price = Float.parseFloat(request.getParameter("price"));
		/*
		 * ����� �������� �������� ����, ��� ����� ��������� ����������� ��������
		 * ���������� � ��������������� � ������� ������
		 */
		/*
		 * ���������� �������� �� ���������� �������, ���� class ������������ ����� ���
		 * ������� �����, ������� ��� ������� � �������� a multipart/form-data ������
		 * POST
		 */
		Part part = request.getPart("bookImage");

		/*
		 * ���� ��� ����� ������ �� ����� ������������ � ������ ������
		 */
		Book newBook = new Book();
		newBook.setTitle(title);
		newBook.setAuthor(author);
		newBook.setIsbn(isbn);
		newBook.setDescription(description);

		Category category = categoryDAOImpl.get(categoryId);
		newBook.setCategory(category);
		newBook.setPrice(price);

		if (part != null && part.getSize() > 0) { // ������ �� ����� ����� �� ������
			long size = part.getSize(); // �������� �������� � ������
			byte[] imageBytes = new byte[(int) size];

			InputStream inputStream = part.getInputStream();// Part#getInputStream(), ����� �������� ����������� ����
															// ��� InputStream.
			inputStream.read(imageBytes); // ������ imageBytes.length ������ �� �������� ������ � ������ b. ����������
											// ���������� ����������� �� ������ ������
			inputStream.close();

			newBook.setImage(imageBytes); // ��� �������� �������� � ������� ������
		}

		Book createdBook = bookDAOImpl.create(newBook); // ��� �������� ��������� �����

		if (createdBook.getBookId() > 0) {// ���� �� ������ 0 �� ����� �������
			String message = "A new book has been created successfully";

			listBooks(message);

			LOGGER.info("A new book has been created successfully");
		}

	}

}
