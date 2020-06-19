package by.epam.dobrov.dao.impl;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import by.epam.dobrov.entity.Category;

public class CategoryDAOImplTest extends BaseDAOTest {

	private static CategoryDAOImpl categoryDAOImpl;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		BaseDAOTest.setUpClass();
		categoryDAOImpl = new CategoryDAOImpl();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		BaseDAOTest.tearDownClass();
	}

	@Test
	public void test_ShouldCreateCategory() {
		Category category = new Category("Comics");
		categoryDAOImpl.create(category);

		assertTrue(category != null && category.getCategoryId() > 0);
	}

	@Test
	public void test_ShouldUpdateCategory() {

		Category category1 = new Category("Comics");
		category1.setCategoryId(1);

		Category category2 = categoryDAOImpl.update(category1);

		assertEquals(category1.getName(), category2.getName());
	}

	@Test
	public void test_ShouldGetCategory() {

		Integer categoryId = 1;

		Category category = categoryDAOImpl.get(categoryId);

		if (category != null) {
			System.out.println(category.getName());
		}
		assertNotNull(category);

	}

	@Test
	public void test_ShouldDeleteCategory() {
		Integer categoryId = 9;

		categoryDAOImpl.delete(categoryId);

		Category category = categoryDAOImpl.get(categoryId);

		assertNull(category);
	}

	@Test
	public void test_ShouldFindCategoryList() {

		List<Category> categoryList = categoryDAOImpl.listAll();

		for (Category category : categoryList) {
			System.out.println(category.getName());
		}

		assertTrue(categoryList.size() > 0);
	}

	@Test
	public void testCount() {
		long countActual = categoryDAOImpl.count();
		long countExpected = 4;

		assertEquals(countExpected, countActual);
	}

	@Test
	public void test_ShouldFindByName() {

		String name = "Comics";
		Category category = categoryDAOImpl.findByName(name);

		assertNotNull(category);
	}

	@Test
	public void test_ShouldNotFindByName() {

		String name = "Astrology";
		Category category = categoryDAOImpl.findByName(name);

		assertNull(category);
	}

}
