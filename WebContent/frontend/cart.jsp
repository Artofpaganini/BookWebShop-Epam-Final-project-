<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Cart</title>
<link rel="stylesheet" href="css/style.css">
<script type="text/javascript" src="js/jquery-3.5.1.min.js"></script>
<script type="text/javascript" src="js/jquery.validate.min.js"></script>
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

			<form action="update_cart" method="post" id="cartForm">
				<div>
					<table border="1" cellpadding="7">
						<tr>
							<th>No</th>
							<th colspan="2">Book</th>
							<th>Quantity</th>
							<th>Price,$</th>
							<th>Subtotal,$</th>
							<th>Action</th>
						</tr>

						<c:forEach items="${cart.items}" var="item" varStatus="status">
							<tr align="center">
								<td>${status.index + 1 }</td>
								
								<td valign="middle"><img
									src="data:image/jpg;base64,${item.key.base64Image }" width="84"
									height="110" /></td>
									
								<td>${item.key.title }</td>
								
								<td><input type="hidden" name="bookId"
									value="${item.key.bookId}" /> 
									<input type="text" name="quantity${status.index + 1}"
									value="${item.value }" size="5" /></td>
									
						
								<td><fmt:formatNumber value="${item.key.price }"
										type="currency" /></td>
										
								<td><fmt:formatNumber
										value="${item.value * item.key.price}" type="currency" /></td>
								<td><a href="delete_from_cart?book_id=${item.key.bookId}">Delete</a></td>
							</tr>
						</c:forEach>
						<tr>
							<td></td>
							<td></td>
							<td></td>
							<td>Total:</td>
							<td colspan="2"><fmt:formatNumber
									value="${cart.totalAmount }" type="currency" /></td>
							<td></td>
						</tr>
					</table>
				</div>
				<div>
					<table>
						<tr>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<td></td>
							<td><button type="submit">Update</button></td>
							<td><a href="${pageContext.request.contextPath}/">Continue
									shopping</a></td>
	
							<td><a href="checkout">Checkout</a></td>
						</tr>
					</table>
				</div>
			</form>
		</c:if>
	</div>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<jsp:directive.include file="footer.jsp" />

	<script type="text/javascript">
	
	// проверка на то что  кол-во  было не ноль и то что оно является числом а не буквой или тп, а так же мин число  должно быть 1
	$(document).ready(function(){
		$("#cartForm").validate({
			rules: {
				<c:forEach items="${cart.items}" var="item" varStatus="status">
				quantity${status.index + 1}: {
					required: true,
					number: true,
					min: 1
					},
				</c:forEach>
			},
			messages:{
				<c:forEach items="${cart.items}" var="item" varStatus="status">
				quantity${status.index + 1}: {
					required: "Please enter the quantity",
					number: "Quantity must be a number" ,
					min: "Quantity must be greater than 0"
					},
				</c:forEach>
			}
			
		});
	});
	
	</script>
</body>
</html>