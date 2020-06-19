<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Books Management</title>
	<link rel="stylesheet" href="../css/style.css">
	<script type="text/javascript" src=../js/jquery-3.5.1.min.js></script>
	<script type="text/javascript" src=../js/jquery.validate.min.js></script>
</head>
<body>
	<jsp:directive.include file="header.jsp" />

	<div align="center">
		<h2>Books Management</h2>
		<h3>
			<a href="new_book">Create New Book</a>
		</h3>
	</div>

	<c:if test="${message !=null }">
		<div align="center">
			<h4 class="message">${message}</h4>
		</div>
	</c:if>

	<div align="center">
		<table border="1" cellpadding="7">
			<tr>
				<th>Index</th>
				<th>ID</th>
				<th>Image</th>
				<th>Title</th>
				<th>Author</th>
				<th>Category</th>
				<th>Price</th>
				<th>Actions</th>
			</tr>
			<c:forEach var="book" items="${listBooks}" varStatus="status">
				<tr>
					<td>${status.index+1}</td>
					<td>${book.bookId}</td>
					<td><img src="data:image/jpg;base64,${book.base64Image }"
						width="84" height="110" /> 
					</td>

					<td>${book.title}</td>
					<td>${book.author}</td>
					<td>${book.category.name}</td>
					<td>${book.price}$</td>

					<td><a href="edit_book?id=${book.bookId }">Edit</a> &nbsp <a
						href="javascript:confirmDelete(${book.bookId })">Delete</a> &nbsp</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<script>
		function confirmDelete(bookId) {
			if (confirm('Are you sure? You want to delete the book with ID '
					+ bookId + '?')) {
				window.location = 'delete_book?id=' + bookId; // URL адрес  + ид книги которую удаляем
			}
		}
	</script>
</body>
</html>