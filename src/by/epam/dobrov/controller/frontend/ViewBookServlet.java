package by.epam.dobrov.controller.frontend;

import by.epam.dobrov.controller.BaseServlet;
import by.epam.dobrov.service.BookServices;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/view_book")
public class ViewBookServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

  
    public ViewBookServlet() {
     
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BookServices bookServices = new BookServices(entityManager, request, response);
		bookServices.viewBookDetail();
		
	}

}
