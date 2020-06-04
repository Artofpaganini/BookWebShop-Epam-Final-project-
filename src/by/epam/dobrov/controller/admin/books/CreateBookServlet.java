package by.epam.dobrov.controller.admin.books;


import by.epam.dobrov.dao.impl.BookDAOImpl;
import by.epam.dobrov.service.BookServices;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/admin/create_book")
/*
 * Эта аннотация используется, когда нам нужно аннотировать сервлет для
 * обработки запросов multipart/form-data (обычно используется для сервлета
 * выгрузки файлов).
 * Это откроет методы getParts () и getPart (name) для HttpServletRequest ,
 * которые можно использовать для доступа ко всем частям, а также к отдельным
 * частям.
 * Загруженный файл можно записать на диск, вызвав write (fileName) объекта
 * Part. 
 * Теперь мы рассмотрим пример сервлета UploadCustomerDocumentsServlet , который
 * демонстрирует его использование:
 */
@MultipartConfig(fileSizeThreshold = 1024 * 10, // загруженный файл временно. Если размер загруженного файла больше этот порог, он будет храниться на диске. В противном случае файл хранится в памяти (размер в байтах)  в байтах 10КБ
		maxFileSize = 1024 * 300, // это максимальный размер загружаемого файла (размер в байт)  300КБ
		maxRequestSize = 1024 * 1024 //это максимальный размер запроса, включая как загруженные файлы, так и другие данные формы (размер в байтах) 1МБ
)

public class CreateBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CreateBookServlet() {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		BookServices bookServices = new BookServices( request, response);
		bookServices.createNewBook();
	}

}
