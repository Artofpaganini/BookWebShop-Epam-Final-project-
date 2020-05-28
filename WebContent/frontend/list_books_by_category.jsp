<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Books in ${category.name }</title>
<!--   тут будет выводиться имя выбранной нами категории -->
<link rel="stylesheet" href="css/style.css">
</head>
<body>
	<jsp:directive.include file="header.jsp" />

	<div align="center">
		<h2>${category.name }</h2>
		<!-- вывод  имен категорий на экран-->

	</div>

	<div align="center" style="width: 80%; margin: 0 auto">
		<!-- style="width:80% указали ширину строк,т.е сколько картинок сможет поместиться в 1 полосе -->
		<c:forEach items="${listBooks }" var="book">
			<!-- вывод книг и их данных на экран согласно их категориям -->
			<div style="float: left; display: inline-block; margin: 20px">
				<!--  float: left  говорит что написано все слева направо-->

				<!--  inline-block вывод книг  в строку( в ряд горизонтально)-->
				<div>
					<a href="view_book?id=${book.bookId}"> <img
						src="data:image/jpg;base64,${book.base64Image }" width="128"
						height="164" />
					</a>
				</div>
				<div>
					<a href="view_book?id=${book.bookId}"> <b>${book.title}</b>
					</a>
				</div>

				<div>by ${book.author}</div>

				<div>
					<b>${book.price}$</b>
				</div>

			</div>

		</c:forEach>
	</div>

	<jsp:directive.include file="footer.jsp" />
</body>
</html>