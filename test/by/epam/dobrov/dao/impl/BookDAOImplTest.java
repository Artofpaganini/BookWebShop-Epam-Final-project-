package by.epam.dobrov.dao.impl;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import by.epam.dobrov.entity.Book;
import by.epam.dobrov.entity.Category;
import by.epam.dobrov.entity.Users;

public class BookDAOImplTest extends BaseDAOTest {

	private static BookDAOImpl bookDAOImpl;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		BaseDAOTest.setUpClass();
		bookDAOImpl = new BookDAOImpl();

	}

	@AfterClass
	public static void tearDownClass() {
		BaseDAOTest.tearDownClass();
	}

	@Test
	public void test_ShouldCreateNewBook() throws IOException {
		Book book = new Book();

		Category category = new Category("Programming");
		category.setCategoryId(14);

		book.setCategory(category);
		book.setTitle("First Head Java");
		book.setAuthor("Bert Bates");
		book.setDescription(
				"Learning a complex new language is no easy task especially when it s an object-oriented computer programming language like Java.");
		book.setIsbn("0596009208");
		book.setPrice(38.87f);

		String imagePath = "D:\\eclipseWeb\\books images\\firstHeadJava.JPG";

		byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath)); // этот метод читает все байты и возвращает массив
																		// байтов
		book.setImage(imageBytes);

		Book createdBook = bookDAOImpl.create(book);

		assertTrue(createdBook.getBookId() > 0);

	}

	@Test
	public void test_ShouldUpdateBook() throws IOException {
		Book existBook = new Book();

		Category category = new Category("Programming");
		category.setCategoryId(14);
		existBook.setBookId(1);
		existBook.setCategory(category);
		existBook.setTitle("First Head Java (2nd Edition)");
		existBook.setAuthor("Bert Bates and Kate Sierra");
		existBook.setDescription(
				"Learning a complex new language is no easy task especially when it s an object-oriented computer programming language like Java.");
		existBook.setIsbn("0596009208");
		existBook.setPrice(39.04f);

		String imagePath = "D:\\eclipseWeb\\books images\\firstHeadJava.JPG";

		byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));
		existBook.setImage(imageBytes);

		Book updatedBook = bookDAOImpl.update(existBook);

		assertEquals(updatedBook.getTitle(), "First Head Java (2nd Edition)");
	}

	@Test
	public void test_ShouldGetBook() {
		Integer bookId = 3;

		Book book = bookDAOImpl.get(bookId);

		if (book != null) {
			System.out.println(book.getTitle());
		}
		assertNotNull(book);
	}

	@Test
	public void test_ShouldNotGetBook() {
		Integer bookId = 50;

		Book book = bookDAOImpl.get(bookId);
		assertNull(book);
	}

	@Test(expected = EntityNotFoundException.class)
	public void test_ShouldNotDeleteBook() {
		Integer bookId = 100;
		bookDAOImpl.delete(bookId);
	}

	@Test
	public void test_ShouldDeleteBook() {
		Integer bookId = 5;
		bookDAOImpl.delete(bookId);

		Book book = bookDAOImpl.get(bookId);
		assertNull(book);
	}

	@Test
	public void test_ShouldGetListAllBooks() {
		List<Book> bookList = bookDAOImpl.listAll();

		for (Book book : bookList) {
			System.out.println(book.getTitle());
		}

		assertTrue(bookList.size() > 0);
	}

	@Test
	public void test_ShouldCountBooks() {
		long countActual = bookDAOImpl.count();
		long countExpected = 1;

		assertEquals(countExpected, countActual);
	}

	@Test
	public void test_ShouldNotFindByTitle() {

		String title = "First Head Java (2nd Edition)321";
		Book book = bookDAOImpl.findByTitle(title);

		assertNull(book);
	}

	@Test
	public void test_ShouldFindByTitle() {

		String title = "First Head Java (2nd Edition)";
		Book book = bookDAOImpl.findByTitle(title);

		assertNotNull(book);
	}

	@Test
	public void test_ShouldGetListBooksByCategory() {

		int categoryId = 14;

		List<Book> listBook = bookDAOImpl.listBookByCategory(categoryId);

		assertTrue(listBook.size() > 0);

		for (Book book : listBook) {
			System.out.println(book.getTitle());
		}
	}

	@Test
	public void test_ShouldSearchBookByTitle() {

		String keyword = "Bates ";

		List<Book> listBooks = bookDAOImpl.search(keyword);

		for (Book book : listBooks) {
			System.out.println(book.getTitle());
		}
		
		assertEquals(2, listBooks.size());
		

		
	}
	
	@Test
	public void test_ShouldSearchBookByAuthor() {

		String keyword = "Bates ";

		List<Book> listBooks = bookDAOImpl.search(keyword);

		for (Book book : listBooks) {
			System.out.println(book.getTitle());
		}
		
		assertEquals(2, listBooks.size());
		
	}
	
	@Test
	public void test_ShouldSearchBookByDescription() {

		String keyword = "big picture ";

		List<Book> listBooks = bookDAOImpl.search(keyword);

		for (Book book : listBooks) {
			System.out.println(book.getTitle());
		}
		
		assertEquals(1, listBooks.size());
		
	}
	
	@Test
	public void test_ShouldShowListBestSellingBooks() {
		List<Book> topBestSellingBooks = bookDAOImpl.listBestSellingBooks();
		
		for (Book book : topBestSellingBooks) {
			System.out.println(book.getTitle());
		}
		
		assertEquals(4, topBestSellingBooks.size());
	}

}
