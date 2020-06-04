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
 * ��� ��������� ������������, ����� ��� ����� ������������ ������� ���
 * ��������� �������� multipart/form-data (������ ������������ ��� ��������
 * �������� ������).
 * ��� ������� ������ getParts () � getPart (name) ��� HttpServletRequest ,
 * ������� ����� ������������ ��� ������� �� ���� ������, � ����� � ���������
 * ������.
 * ����������� ���� ����� �������� �� ����, ������ write (fileName) �������
 * Part. 
 * ������ �� ���������� ������ �������� UploadCustomerDocumentsServlet , �������
 * ������������� ��� �������������:
 */
@MultipartConfig(fileSizeThreshold = 1024 * 10, // ����������� ���� ��������. ���� ������ ������������ ����� ������ ���� �����, �� ����� ��������� �� �����. � ��������� ������ ���� �������� � ������ (������ � ������)  � ������ 10��
		maxFileSize = 1024 * 300, // ��� ������������ ������ ������������ ����� (������ � ����)  300��
		maxRequestSize = 1024 * 1024 //��� ������������ ������ �������, ������� ��� ����������� �����, ��� � ������ ������ ����� (������ � ������) 1��
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
