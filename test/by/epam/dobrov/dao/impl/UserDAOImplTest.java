package by.epam.dobrov.dao.impl;

import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import by.epam.dobrov.entity.Users;

public class UserDAOImplTest extends BaseDAOTest {

	private static UserDAOImpl userDAOImpl;

	@BeforeClass
	public static void setUpClass() {

		BaseDAOTest.setUpClass();
		userDAOImpl = new UserDAOImpl();

	}

	@AfterClass
	public static void tearDownClass() {
		BaseDAOTest.tearDownClass();
	}

	@Test
	public void test_ShouldCreateNewUsers() { // проверка добавления пользователя

		Users user = new Users();
		user.setEmail("AidaShelby@gmail.com");
		user.setFullName(" Aida Shelby");
		user.setPassword("OrderByPeakyBlinders");


		user = userDAOImpl.create(user);

		assertTrue(user.getUsersId() > 0);
	}

	@Test(expected = PersistenceException.class)
	public void test_ShouldCreateUsersWithoutFieldsExpectedException() { // проверка добавлен ли пользователь без полей
		Users user = new Users();

		user = userDAOImpl.create(user);
		assertTrue(user.getUsersId() > 0);

	}

	@Test
	public void test_ShouldUpdatePasswordExistingUser() { // check the password changed or not

		Users user = new Users();

		user.setUsersId(1);
		user.setEmail("artofpaganini@gmail.com");
		user.setFullName("Victor Dobrov");
		user.setPassword("af9137edb8ab8397344f2e2fddc1af87543a46925ccc30a4ef40c66484ab68a0");

		user = userDAOImpl.update(user);

		String actual = user.getPassword();
		String expected = "af9137edb8ab8397344f2e2fddc1af87543a46925ccc30a4ef40c66484ab68a0";

		assertEquals(expected, actual);

	}

	@Test
	public void test_ShouldGetFoundUser() {
		Integer userId = 4;

		Users user = userDAOImpl.get(userId);

		if (user != null) {
			System.out.println(user.getEmail());
		}
		assertNotNull(user);

	}

	@Test
	public void test_ShouldNotGetFoundUser() {
		Integer userId = 1;

		Users user = userDAOImpl.get(userId);

		assertNull(user);

	}

	@Test
	public void test_ShouldDeleteFoundUser() {
		Integer userId = 24;

		userDAOImpl.delete(userId);

		Users user = userDAOImpl.get(userId);

		assertNull(user);

	}

	@Test(expected = EntityNotFoundException.class) // проверка на удаление не существующего юзера
	public void test_ShouldDeleteNotExistUserExpectException() {
		Integer userId = 100;

		userDAOImpl.delete(userId);

	}

	@Test
	public void test_ShouldFindAllUsers() {
		List<Users> userList = userDAOImpl.listAll();

		for (Users user : userList) {
			System.out.println(user.getFullName());
		}

		assertTrue(userList.size() > 0);
	}

	@Test
	public void test_ShouldCountAllUsers() {
		long countActual = userDAOImpl.count();
		long countExpected = 9;

		assertEquals(countExpected, countActual);
	}

	@Test
	public void test_ShouldFindUserByEmail() {
		String email = "natalia.dobrova@tut.by";
		Users user = userDAOImpl.findByEmail(email);

		assertNotNull(user);
	}

	@Test
	public void test_ShouldCheckLoginSuccess() {
		String email = "artofpaganini@gmail.com";
		String password = "LifeTooShortestForTheCrying1";

		boolean loginResult = userDAOImpl.checkLogin(email, password);

		assertTrue(loginResult);

	}

	@Test
	public void test_ShouldCheckLoginFail() {
		String email = "artofpaganini@gmail.com";
		String password = "123";

		boolean loginResult = userDAOImpl.checkLogin(email, password);

		assertFalse(loginResult);

	}

}
