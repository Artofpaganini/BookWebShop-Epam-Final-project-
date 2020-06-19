package by.epam.dobrov.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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

/**
 * 9. Система Интернет-магазин. Администратор осуществляет ведение каталога
 * Товаров. Клиент делает и оплачивает Заказ на Товары. Администратор может
 * занести неплательщиков в “черный список”.
 * 
 * @author Viktor
 *
 */
public class BookServices {

	private final static Logger LOGGER = LoggerFactory.getLogger(BookServices.class);

	private BookDAOImpl bookDAOImpl;
	private CategoryDAOImpl categoryDAOImpl; // нужен для извлечения списка категорий, и занесения в опред категории ,
												// определенные книги
	private HttpServletRequest request;
	private HttpServletResponse response;

	public BookServices(HttpServletRequest request, HttpServletResponse response) {
		super();
		this.request = request;
		this.response = response;

		bookDAOImpl = new BookDAOImpl();
		categoryDAOImpl = new CategoryDAOImpl();
	}

	/**
	 * Method reads all fields and set this fields
	 * 
	 * @param book
	 * @throws IOException
	 * @throws ServletException
	 */
	public void readBookFields(Book book) throws IOException, ServletException {

		Integer categoryId = Integer.parseInt(request.getParameter("category"));
		String title = request.getParameter("title");
		String author = request.getParameter("author");
		String isbn = request.getParameter("isbn");
		String description = request.getParameter("description");
		float price = Float.parseFloat(request.getParameter("price"));

		Part part = request.getPart("bookImage");
		
		book.setTitle(title);
		book.setAuthor(author);
		book.setIsbn(isbn);
		book.setDescription(description);

		Category category = categoryDAOImpl.get(categoryId);

		book.setCategory(category);
		book.setPrice(price);

		if (part != null && part.getSize() > 0) { 
			long size = part.getSize();
			byte[] imageBytes = new byte[(int) size];

			InputStream inputStream = part.getInputStream();
			inputStream.read(imageBytes); 
			
			inputStream.close();

			book.setImage(imageBytes); 
		}
	}

	public void listBooks() throws ServletException, IOException {
		listBooks(null);
	}

	/**
	 * Method displays a list of books on the screen, with an additional message if
	 * necessary
	 * 
	 * @param message
	 * @throws ServletException
	 * @throws IOException
	 */
	public void listBooks(String message) throws ServletException, IOException {
		LOGGER.info("Trying to get list of book from DB");

		List<Book> listBooks = bookDAOImpl.listAll();
		request.setAttribute("listBooks", listBooks);

		if (message != null) { 
			request.setAttribute("message", message);
		}

		String listpage = "book_list.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(listpage);

		requestDispatcher.forward(request, response);

		LOGGER.info("Book list has been  received from DB ");
	}

	/**
	 * Method create new book
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	public void createNewBook() throws ServletException, IOException {
		LOGGER.info("Try to create a new Book");

		String title = request.getParameter("title");

		Book existBookTitle = bookDAOImpl.findByTitle(title);

		if (existBookTitle != null) {

			String message = "Book with title -  " + title + ", is already exist!!!";
			listBooks(message);

			LOGGER.warn("Get a warning, Book  with title {} is already exist!!!", title);
			return;
		}

		Book newBook = new Book();

		readBookFields(newBook);

		Book createdBook = bookDAOImpl.create(newBook); 

		if (createdBook.getBookId() > 0) {
			
			String message = "A new book has been created successfully";

			listBooks(message);

			LOGGER.info("A new book has been created successfully");
		}

	}

	/**
	 * Method check book id , if we have it , we forward on edit page if no, we got
	 * the message
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	public void editBook() throws ServletException, IOException {
		LOGGER.info("Edit Book by ID");

		Integer bookId = Integer.parseInt(request.getParameter("id"));
		Book book = bookDAOImpl.get(bookId);

		String editPage = "book_form.jsp";
	
		if (book != null) {
			List<Category> listCategory = categoryDAOImpl.listAll();

			request.setAttribute("book", book);
			request.setAttribute("listCategory", listCategory);

		} else {
			editPage = "message.jsp";
			String message = "Could not find book with ID " + bookId;
			request.setAttribute("message", message);

			LOGGER.warn("Could not find book with ID {}", bookId);
		}

		RequestDispatcher requestDispatcher = request.getRequestDispatcher(editPage);
		requestDispatcher.forward(request, response);
	}

	/**
	 * Method updated book, after checking the name
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	public void updateBook() throws ServletException, IOException {
		LOGGER.info("Update some information about Book");

		Integer bookId = Integer.parseInt(request.getParameter("bookId"));
		String title = request.getParameter("title");

		Book existBookById = bookDAOImpl.get(bookId);
		Book existBookByTitle = bookDAOImpl.findByTitle(title);

		if (existBookByTitle != null && existBookByTitle.getBookId() != existBookById.getBookId()) {

			String message = " Book with title -  " + title + ", is already exist!!!";
			listBooks(message);

			LOGGER.warn("Got warning, Book  with title {} is already exist!!!", title);
			return;
		} else {
			readBookFields(existBookById); 

			bookDAOImpl.update(existBookById); 

			String message = "The book has been updated!";

			listBooks(message); 

			LOGGER.info("The book has been updated");
		}
	}

	/**
	 * Method delete the book by id , after checking
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	public void deleteBook() throws ServletException, IOException {

		LOGGER.info("Delete Book from book list and from DB");

		Integer bookId = Integer.parseInt(request.getParameter("id"));

		Book bookById = bookDAOImpl.get(bookId);

		if (bookById == null) {
			String message = "Could not find book with ID " + bookId + ", or it might have been deleted";
			
			request.setAttribute("message", message);
			request.getRequestDispatcher("message.jsp").forward(request, response);

			LOGGER.warn("Could not find book with ID {} or it might have been deleted", bookId);
		}
		
		bookDAOImpl.delete(bookId);

		String message = "Book with ID " + bookId + " has been deleted";

		listBooks(message);

		LOGGER.info("Book with ID {} has been deleted", bookId);

	}

	/**
	 * Method get all category and show it when admin create new book
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	public void showAllCategoryInNewBookForm() throws ServletException, IOException {

		LOGGER.info("Trying to get  category  list from DB");
		List<Category> listCategory = categoryDAOImpl.listAll();

		request.setAttribute("listCategory", listCategory);

		String page = "book_form.jsp";

		RequestDispatcher requestDispatcher = request.getRequestDispatcher(page);
		requestDispatcher.forward(request, response);

		LOGGER.info("Category list has been  received from DB ");
	}

	/**
	 * Method sorts books into categories, i.e. the customer selects a category and
	 * a list of all books that have the selected category appears
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	public void listBookByCategory() throws ServletException, IOException {

		LOGGER.info("Trying to get list of books by chosen category from List of categories(from DB)");

		int categoryId = Integer.parseInt(request.getParameter("id"));

		List<Book> listBooks = bookDAOImpl.listBookByCategory(categoryId);
		Category category = categoryDAOImpl.get(categoryId);

		request.setAttribute("category", category);
		request.setAttribute("listBooks", listBooks);

		String bookPage = "frontend/list_books_by_category.jsp";

		RequestDispatcher requestDispatcher = request.getRequestDispatcher(bookPage);
		requestDispatcher.forward(request, response);

		LOGGER.info("Book list has been received from DB");
	}

	/**
	 * Method realized displays details about the selected book, as well as
	 * categories of books that you can select to view
	 */
	public void viewBookDetail() throws ServletException, IOException {

		LOGGER.info("Attempt to show book details");
		Integer bookId = Integer.parseInt(request.getParameter("id"));

		Book book = bookDAOImpl.get(bookId);

		request.setAttribute("book", book);

		String detailPage = "frontend/book_detail.jsp";

		RequestDispatcher requestDispatcher = request.getRequestDispatcher(detailPage);
		requestDispatcher.forward(request, response);

		LOGGER.info("Book details has been gotten ");
	}

	/**
	 * Method realized search of book
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	public void searchBook() throws ServletException, IOException {
		LOGGER.info("Searched of Book by some keyword was starting");
		
		String keyword = request.getParameter("keyword");

		List<Book> result = null;

		if (keyword.equals("")) {
			result = bookDAOImpl.listAll();
			LOGGER.warn("Field of Searching is empty");
		} else {
			result = bookDAOImpl.search(keyword);
			LOGGER.info("Get the result with keyword {}", keyword);
		}

		request.setAttribute("keyword", keyword);
		request.setAttribute("result", result);

		String searchResultPage = "frontend/search_book.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(searchResultPage);
		requestDispatcher.forward(request, response);

		LOGGER.info("Sent the response with result to the frontend page");
	}

}
