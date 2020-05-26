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
																					// страницу
		// админа
		List<Book> listBooks = bookDAOImpl.listAll();
		request.setAttribute("listBooks", listBooks);

		if (message != null) { // проверка на наличие сообщения , если оно не налл то выводим его
			request.setAttribute("message", message);
		}

		String listpage = "book_list.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(listpage);

		requestDispatcher.forward(request, response);
	}

	public void showNewBookForm() throws ServletException, IOException {
		/*
		 * получаем список категорий, для того чтобы потом их выбирать в бук форме, Этот
		 * метод получает список всех категорий и передает его в jsp файл где делается
		 * выпадающий эффект чтобы выбрать категорию
		 */

		List<Category> listCategory = categoryDAOImpl.listAll();

		request.setAttribute("listCategory", listCategory); // сохраняем листкатегори

		String page = "book_form.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(page);

		requestDispatcher.forward(request, response);

	}

	public void createNewBook() throws ServletException, IOException {
		LOGGER.info("Try to create a new Book");
		/*
		 * метод создания новой книги, привязывание заполнения всех видов форм,включая
		 * картинку
		 */
		Integer categoryId = Integer.parseInt(request.getParameter("category"));
		String title = request.getParameter("title");

		Book existBook = bookDAOImpl.findByTitle(title);
		if (existBook != null) { // проверяем чтобы категория не повторяющая

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
		Book newBook = new Book();
		newBook.setTitle(title);
		newBook.setAuthor(author);
		newBook.setIsbn(isbn);
		newBook.setDescription(description);

		Category category = categoryDAOImpl.get(categoryId);
		newBook.setCategory(category);
		newBook.setPrice(price);

		if (part != null && part.getSize() > 0) { // значит мы имеем какие то данные
			long size = part.getSize(); // получаем картинку в байтах
			byte[] imageBytes = new byte[(int) size];

			InputStream inputStream = part.getInputStream();// Part#getInputStream(), чтобы получить загруженный файл
															// как InputStream.
			inputStream.read(imageBytes); // чтение imageBytes.length байтов из входного потока в массив b. Возвращает
											// количество прочитанных из потока байтов
			inputStream.close();

			newBook.setImage(imageBytes); // тут получили картинку в массиве байтов
		}

		Book createdBook = bookDAOImpl.create(newBook); // для сохрания созданной книги

		if (createdBook.getBookId() > 0) {// если ИД больше 0 то книга создана
			String message = "A new book has been created successfully";

			listBooks(message);

			LOGGER.info("A new book has been created successfully");
		}

	}

}
