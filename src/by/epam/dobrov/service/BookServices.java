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
	private CategoryDAOImpl categoryDAOImpl; // нужен для извлечения списка категорий, и занесения в опред категории ,
												// определенные книги
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

	public void listBooks() throws ServletException, IOException { // перегруз метода , без сообщения, нужен для того
																	// чтобы просто обновлять список книг
		listBooks(null);
	}

	// а этот метод нужен если мы обновили список книг и при этом должны вывести
	// какое либо сообщение
	public void listBooks(String message) throws ServletException, IOException { // получение списка книг и вывод их на
		LOGGER.info("Trying to get list of book from DB"); // страницу
		// админа
		List<Book> listBooks = bookDAOImpl.listAll();
		request.setAttribute("listBooks", listBooks);

		if (message != null) { // проверка на наличие сообщения , если оно не налл то выводим его
			request.setAttribute("message", message);
		}

		String listpage = "book_list.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(listpage);

		requestDispatcher.forward(request, response);

		LOGGER.info("Book list has been  received from DB ");
	}

	public void showNewBookForm() throws ServletException, IOException {
		/*
		 * получаем список категорий, для того чтобы потом их выбирать в бук форме, Этот
		 * метод получает список всех категорий и передает его в jsp файл где делается
		 * выпадающий эффект чтобы выбрать категорию
		 */

		LOGGER.info("Trying to get  category  list from DB");
		List<Category> listCategory = categoryDAOImpl.listAll();

		request.setAttribute("listCategory", listCategory); // сохраняем листкатегори

		String page = "book_form.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(page);

		requestDispatcher.forward(request, response);
		LOGGER.info("Category list has been  received from DB ");
	}

	public void readBookFields(Book book) throws IOException, ServletException {
		Integer categoryId = Integer.parseInt(request.getParameter("category"));
		String title = request.getParameter("title");
		String author = request.getParameter("author");
		String isbn = request.getParameter("isbn");
		String description = request.getParameter("description");
		float price = Float.parseFloat(request.getParameter("price"));

		/*
		 * чтобы добавить картинку сюда, нам нужно поставить специальную анотацию
		 * говоряющую о многозадачности в сервлет классе
		 */
		/*
		 * извлечение картинки из мультипарт риквест, Этот class представляет часть или
		 * элемент формы, который был получен в пределах a multipart/form-data Запрос
		 * POST
		 */
		Part part = request.getPart("bookImage");

		/*
		 * этот код читае данные из файла загруженного в массив байтов
		 */

		book.setTitle(title);
		book.setAuthor(author);
		book.setIsbn(isbn);
		book.setDescription(description);

		Category category = categoryDAOImpl.get(categoryId);
		book.setCategory(category);
		book.setPrice(price);

		if (part != null && part.getSize() > 0) { // значит мы имеем какие то данные
			long size = part.getSize(); // получаем картинку в байтах
			byte[] imageBytes = new byte[(int) size];

			InputStream inputStream = part.getInputStream();// Part#getInputStream(), чтобы получить загруженный файл
															// как InputStream.
			inputStream.read(imageBytes); // чтение imageBytes.length байтов из входного потока в массив b. Возвращает
											// количество прочитанных из потока байтов
			inputStream.close();

			book.setImage(imageBytes); // тут получили картинку в массиве байтов
		}
	}

	public void createNewBook() throws ServletException, IOException {
		LOGGER.info("Try to create a new Book");
		/*
		 * метод создания новой книги, привязывание заполнения всех видов форм,включая
		 * картинку
		 */

		String title = request.getParameter("title");

		Book existBookTitle = bookDAOImpl.findByTitle(title);

		if (existBookTitle != null) { // проверяем чтобы категория не повторяющая

			String message = "Book with title -  " + title + ", is already exist!!!";
			listBooks(message);

			LOGGER.warn("Got warning, Book  with title {} is already exist!!!", title);
			return;
		}

		Book newBook = new Book();
		readBookFields(newBook); // будем читать все данные из этого метода

		Book createdBook = bookDAOImpl.create(newBook); // для сохрания созданной книги

		if (createdBook.getBookId() > 0) {// если ИД больше 0 то книга создана
			String message = "A new book has been created successfully";

			listBooks(message);

			LOGGER.info("A new book has been created successfully");
		}

	}

	public void editBook() throws ServletException, IOException {
		LOGGER.info("Edit Book by ID");

		Integer bookId = Integer.parseInt(request.getParameter("id"));
		Book book = bookDAOImpl.get(bookId);

		String editPage = "book_form.jsp";

		/*
		 * если полученная книга не null то вызываем список категорий и работаем с
		 * данными этой книги, иначе выводим сообщение что книги с эти ид нет!
		 */
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

	public void updateBook() throws ServletException, IOException {
		/*
		 * Метод работает ,получаем книгу по ИД, проверяем есть ли такое название и ИСБН
		 * уже в БД,если есть то возвращаем еррор сообщение, если нет , то заносим в
		 * мнимый списко книг, обновляем остальные данные , заносим в списко книг
		 * обновленную инфу
		 */
		LOGGER.info("Update some information about Book");

		Integer bookId = Integer.parseInt(request.getParameter("bookId"));
		String title = request.getParameter("title");
		String isbn = request.getParameter("isbn");

		Book existBookById = bookDAOImpl.get(bookId); // вызываем нужную нам книгу из БД по ИД
		Book existBookByTitle = bookDAOImpl.findByTitle(title);

		/*
		 * Проверка чтобы небыло книги с таким же названием
		 */
		if (existBookByTitle != null && existBookByTitle.getBookId() != existBookById.getBookId()) {

			String message = " Book with title -  " + title + ", is already exist!!!";
			listBooks(message);

			LOGGER.warn("Got warning, Book  with title {} is already exist!!!", title);
			return;
		} else {
			readBookFields(existBookById); // получили все значения из общего метода,

			bookDAOImpl.update(existBookById); // обновили эти значения

			String message = "The book has been updated!";

			listBooks(message); // обновили инфо в бд

			LOGGER.info("The book has been updated");
		}
	}

	/*
	 * метод вызывает параметр ИД книги, и удаляет его , затем обновляет список книг
	 * в БД
	 */
	public void deleteBook() throws ServletException, IOException {
		/*
		 * Этот метод удаляет книгу по выбранному, юзером, ИД
		 */
		LOGGER.info("Delete Book from book list and from DB");

		Integer bookId = Integer.parseInt(request.getParameter("id"));

		Book bookById = bookDAOImpl.get(bookId);

		if (bookById == null) { // проверка возможно эту книгу уже удалил другой админ
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

	public void listBookByCategory() throws ServletException, IOException {
		/*
		 * Этот метод сортирует книги по категориям, т.е. юзер выбирает категорию и
		 * появляется список всех книг которые имеют выбранную категорию
		 */

		LOGGER.info("Trying to get list of books by chosen category from List of categories(from DB)");

		int categoryId = Integer.parseInt(request.getParameter("id")); // получение выбранного id категории, это тот ID
																		// который весит в URL

		List<Book> listBooks = bookDAOImpl.listBookByCategory(categoryId);
		Category category = categoryDAOImpl.get(categoryId);
		List<Category> listCategory = categoryDAOImpl.listAll();// получаем полный список категорий,чтобы при выборе
																// конкретной , остальные не пропадали из header

		request.setAttribute("listCategory", listCategory);
		request.setAttribute("category", category);
		request.setAttribute("listBooks", listBooks);
		/*
		 * получили ИД категории и проверяем книги которые есть с эти ИД , и отправляем
		 * запрос на получившуюся коллецию на jsp страницу которая будет перенаправляет
		 * ьвсе это list_books_by_category.jsp
		 */

		String bookPage = "frontend/list_books_by_category.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(bookPage);
		requestDispatcher.forward(request, response);

		LOGGER.info("Book list has been received from DB");
	}

	public void viewBookDetail() throws ServletException, IOException {
		/*
		 * Этот метод выводит детали о выбранной книге , а так же категории книг которые
		 * можно выбрать для просмотра
		 */
		LOGGER.info("Attempt to show book details");
		Integer bookId = Integer.parseInt(request.getParameter("id"));

		Book book = bookDAOImpl.get(bookId);
		List<Category> listCategory = categoryDAOImpl.listAll();// получаем полный список категорий,чтобы при выборе
																// конкретной , остальные не пропадали из header

		request.setAttribute("book", book);
		request.setAttribute("listCategory", listCategory);

		String detailPage = "frontend/book_detail.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(detailPage);
		requestDispatcher.forward(request, response);

		LOGGER.info("Book details has been got ");
	}

	public void searchBook() throws ServletException, IOException {
		/*
		 * Метод получает слово которое вводит юзер , далее идет проверка если поиск
		 * будет пустым то мы выведем весь список книг что есть в магазине если поиск
		 * будет со словом то его попытаемся найти , далее этот результат отправим на
		 * стринцу jsp
		 */
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
