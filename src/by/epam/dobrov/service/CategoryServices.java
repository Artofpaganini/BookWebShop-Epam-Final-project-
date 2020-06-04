package by.epam.dobrov.service;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import by.epam.dobrov.dao.impl.CategoryDAOImpl;
import by.epam.dobrov.dao.impl.UserDAOImpl;
import by.epam.dobrov.entity.Category;
import by.epam.dobrov.entity.Users;

public class CategoryServices {

	private final static Logger LOGGER = LoggerFactory.getLogger(CategoryServices.class);

	private CategoryDAOImpl categoryDAOImpl;
	private HttpServletRequest request;
	private HttpServletResponse response;

	public CategoryServices(HttpServletRequest request, HttpServletResponse response) {

		this.request = request;
		this.response = response;

		categoryDAOImpl = new CategoryDAOImpl();
	}

	public void listCategory() throws ServletException, IOException {
		listCategory(null);
	}

	public void listCategory(String message) throws ServletException, IOException {

		LOGGER.info("Got list category from DB");

		List<Category> listCategory = categoryDAOImpl.listAll();
		request.setAttribute("listCategory", listCategory);

		if (message != null) { // проверка на наличие сообщения , если оно не налл то выводим его
			request.setAttribute("message", message);
		}

		String listpage = "category_list.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(listpage);

		requestDispatcher.forward(request, response);

	}

	public void createCategory() throws ServletException, IOException {
		LOGGER.info("Try to create a new Category");
		String categoryName = request.getParameter("name");

		Category existCategory = categoryDAOImpl.findByName(categoryName);
		if (existCategory != null) { // проверяем чтобы категория не повторяющая

			String message = " Category " + categoryName + " is already exist!!!";
			request.setAttribute("message", message);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("message.jsp");
			requestDispatcher.forward(request, response);

			LOGGER.warn("Got warning, Category  with this  name {} is already exist!!!", categoryName);

		} else {

			Category category = new Category(categoryName);
			categoryDAOImpl.create(category);
			listCategory(); // обновляем информаци о категориях

			LOGGER.info("Created a new Category and add this information in DB");
		}

	}

	public void editCategory() throws ServletException, IOException {

		LOGGER.info("Edit Category by ID");

		Integer categoryId = Integer.parseInt(request.getParameter("id"));
		Category category = categoryDAOImpl.get(categoryId);

		String editPage = "category_form.jsp";
		request.setAttribute("category", category);

		RequestDispatcher requestDispatcher = request.getRequestDispatcher(editPage);

		requestDispatcher.forward(request, response);

	}

	public void updateCategory() throws ServletException, IOException {
		LOGGER.info("Update some information about User");

		Integer categoryId = Integer.parseInt(request.getParameter("categoryId"));
		String categoryName = request.getParameter("name");

		Category categoryById = categoryDAOImpl.get(categoryId);
		Category categoryByName = categoryDAOImpl.findByName(categoryName);

		if (categoryByName != null && categoryByName.getCategoryId() != categoryById.getCategoryId()) {
			String message = "Couldn't update category.Category with this name is already exist!";
			request.setAttribute("message", message);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("message.jsp");
			requestDispatcher.forward(request, response);
			LOGGER.warn("Couldn't update category.Category with this name is already exist!");

		} else {
			Category category = new Category(categoryId, categoryName);
			categoryDAOImpl.update(category);
			listCategory();
			LOGGER.info("Update category and update it in DB");
		}
		/*
		 * Введите код для отображения сообщения об ошибке, когда администратор пытается
		 * изменить категорию, которая была удалена другим администратором. Сообщение об
		 * ошибке: не удалось найти категорию с идентификатором [cat_id] '. Замените
		 * [cat_id] фактическим идентификатором значения категории.
		 */
	}

	public void deleteCategory() throws ServletException, IOException {

		LOGGER.info("Delete category from category list and from DB");

		int categoryId = Integer.parseInt(request.getParameter("id"));

		Category categoryById = categoryDAOImpl.get(categoryId);

		if (categoryById == null) { // проверка возможно эту категорию уже удалил другой админ
			String message = "Could not find category with ID " + categoryId + ", or it might have been deleted";
			request.setAttribute("message", message);
			request.getRequestDispatcher("message.jsp").forward(request, response);
		}

		categoryDAOImpl.delete(categoryId);
		String message = "Category with ID " + categoryId + " has been deleted";
		listCategory(message);

		LOGGER.info("Category with ID {} has been deleted", categoryId);

	}

}
