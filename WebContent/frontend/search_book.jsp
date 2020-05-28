<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Search Results for ${keyword}</title>
<link rel="stylesheet" href="../css/style.css">
</head>
<body>
	<jsp:directive.include file="header.jsp" />

	<div align="center" style="width: 80%; margin: 0 auto">
		<c:if test="${fn:length(result)==0}">
			<!-- если результат  длины  ответа == 0 то нет результатов, если нет  то выводим список получившихся результатов -->
			<h2>No Books Results for "${keyword}"</h2>
		</c:if>

		<c:if test="${fn:length(result)> 0}">
			<div align="center" style="width: 80%; margin: 0 auto">
				<h2>Search Results for "${keyword}":</h2>
				<c:forEach items="${result }" var="book">

					<div style="display: inline-block; margin: 20px">

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
		</c:if>

	</div>

	<jsp:directive.include file="footer.jsp" />
</body>
</html>