<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Cart</title>
<link rel="stylesheet" href="css/style.css">
</head>
<body>

	<jsp:directive.include file="header.jsp" />

	<div align="center">
		<h2>Your Cart Details</h2>


		<c:if test="${message != null }">
			<div align="center">
				<h4 class="message">${message}</h4>
			</div>
		</c:if>

		<c:set var="cart" value="${sessionScope['cart']}" />


		<c:if test="${cart.totalItems == 0 }">
			<h2>There is no items in your cart</h2>
		</c:if>

		<c:if test="${cart.totalItems > 0 }">
			<div>
				<form>
					<table border="1" cellpadding="7">
						<tr>
							<th>No</th>
							<th colspan="2">Book</th>
							<th>Quantity</th>
							<th>Price</th>
							<th>Subtotal</th>

							<th><a href=""><b>Clear Cart</b></a></th>

						</tr>

						<c:forEach items="${cart.items}" var="item" varStatus="status">
							<tr align="center">
								<td>${status.index + 1 }</td>
								<td valign="middle"><img
									src="data:image/jpg;base64,${item.key.base64Image }" width="84"
									height="110" /></td>
								<td>${item.key.title }</td>
								<td>${item.value }</td>
								<td><fmt:formatNumber value="${item.key.price }"
										type="currency" /></td>
								<td><fmt:formatNumber
										value="${item.value * item.key.price}" type="currency" /></td>
								<td ><a href="delete_from_cart?book_id=${item.key.bookId}">Delete</a></td>
							</tr>
						</c:forEach>
						<tr>
							<td></td>
							<td></td>
							<td></td>
							<td>Total:</td>
							<td colspan="2"><fmt:formatNumber
									value="${cart.totalAmount }" type="currency" /></td>
						</tr>

					</table>
				</form>

			</div>
		</c:if>
	</div>

	<jsp:directive.include file="footer.jsp" />
</body>
</html>