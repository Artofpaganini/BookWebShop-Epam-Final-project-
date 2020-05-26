package by.epam.dobrov.controller.admin.books;

import by.epam.dobrov.controller.BaseServlet;
import by.epam.dobrov.service.BookServices;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/admin/new_book")
public class NewBookServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;


    public NewBookServlet() {
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		BookServices bookServices = new BookServices(entityManager, request, response);
		bookServices.showNewBookForm();
		
	}

}