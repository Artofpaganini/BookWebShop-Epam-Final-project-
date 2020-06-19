<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>DevBrain - Online Books Shop</title>
<link rel="stylesheet" href="../css/style.css">
</head>
<body>
	<jsp:directive.include file="header.jsp" />

	<div align="center" style="width: 80%; margin: 0 auto">
	
		<h2>Best-Seller Books</h2>
		<c:forEach items="${listBestSellignBooks}" var="book"> 
		
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

	<jsp:directive.include file="footer.jsp" />
</body>
</html>