<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>${book.title}</title>
<link rel="stylesheet" href="css/style.css">
<script type="text/javascript" src="js/jquery-3.5.1.min.js"></script>
</head>
<body>
	<jsp:directive.include file="header.jsp" />

	<!--  cоздание таблицы  для бук дитейл, тут  юзер сможет увидеть инфу о товаре  и добавить его в корзину -->
	<div align="center">

		<table width="80%" border="0">

			<tr>
				<td colspan="3" align="left">
					<h2>${book.title}</h2> by ${book.author}
				</td>
			</tr>

			<tr>
				<td rowspan="2"><img
					src="data:image/jpg;base64,${book.base64Image }" width="240"
					height="300" /></td>

				<td valign="top" style="text-align: justify;">
					${book.description}</td>

				<td valign="top" rowspan="2" width="20%">
					<h2>$${book.price}</h2> 
					<br/><br/>
					<button id="buttonAddToCart">Add to Cart</button>
				</td>

			</tr>
		</table>
	</div>

	<jsp:directive.include file="footer.jsp" />

	<script type="text/javascript">
	
		$(document).ready(function() {
			$("#buttonAddToCart").click(function() {
				window.location = "add_to_cart?book_id=" + ${book.bookId};
			});
		});
	</script>
</body>
</html>
