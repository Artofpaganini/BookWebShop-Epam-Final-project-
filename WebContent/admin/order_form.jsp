<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Edit Order</title>
<link rel="stylesheet" href="../css/style.css">
<script type="text/javascript" src="../js/jquery-3.5.1.min.js"></script>
<script type="text/javascript" src="../js/jquery.validate.min.js"></script>
</head>
<body>

	<jsp:directive.include file="header.jsp" />

	<div align="center">
		<h2>Edit Order with ID:${order.orderId}</h2>

	</div>

	<c:if test="${message != null }">

		<div align="center">
			<h4 class="message">${message}</h4>
		</div>
	</c:if>


	<form action="update_order" method="post" id="orderForm">
		<div align="center">

			<table>
				<tr>
					<td><b>Order By:</b></td>
					<td>${order.customer.fullName}</td>
				</tr>

				<tr>
					<td><b>Order Date:</b></td>
					<td>${order.orderDate}</td>
				</tr>

				<tr>
					<td><b>Recipient name:</b></td>
					<td><input type="text" name="recipientName"
						value="${order.recipientName}" size="70" /></td>

				</tr>

				<tr>
					<td><b>Recipient phone:</b></td>
					<td><input type="text" name="recipientPhone"
						value="${order.recipientPhone}" size="70" /></td>
				</tr>

				<tr>
					<td><b>Ship To:</b></td>
					<td><input type="text" name="shippingAddress"
						value="${order.shippingAddress}" size="70" /></td>
				</tr>

				<tr>
					<td><b>Payment Method:</b></td>
					<td><select name="paymentMethod">
							<option value="Cash on Delivery">Cash on Delivery</option>
					</select></td>
				</tr>

				<tr>
					<td><b>Order Status:</b></td>
					<td><select name="orderStatus">
							<option value="Processing">Processing</option>
							<option value="Cancelled">Cancelled</option>
							<option value="Shipping">Shipping</option>
							<option value="Delivered">Delivered</option>
							<option value="Completed">Completed</option>
					</select></td>
				</tr>

			</table>
		</div>

		<div align="center">
			<h2>Ordered Books</h2>
			<table border="1" cellpadding="7">
				<tr>
					<th>Index</th>
					<th>Book Title</th>
					<th>Author</th>
					<th>Price</th>
					<th>Quantity</th>
					<th>Subtotal</th>


				</tr>


				<c:forEach items="${order.orderDetails }" var="orderDetail"
					varStatus="status">

					<tr>
						<td>${status.index + 1 }</td>
						<td>${orderDetail.book.title }</td>
						<td>${orderDetail.book.author }</td>
						<td>
							<input type ="hidden" name="price" value="${orderDetail.book.price}"/> 
							${orderDetail.book.price }$</td>
						<td>
							<input type ="hidden" name="bookId" value="${orderDetail.book.bookId}"/> 
							<input type="text" name="quantity${status.index + 1}" value="${orderDetail.quantity}" size="5" />
						</td>
						<td>${orderDetail.subTotal }$</td>
						<td>
						<a href="remove_book_from_order?id=${orderDetail.book.bookId }">Remove</a></td>
					</tr>

				</c:forEach>
				<tr>
					<td colspan="4" align="right">Total:</td>
					<td>${orderDetail.quantity}</td>
					<td>${order.orderTotal }</td>
					<td></td>
				</tr>
			</table>
		</div>
		<div align="center">
			<br /> <input type="submit" value="Save" />
			&nbsp;&nbsp;&nbsp;&nbsp; <input type="button" value="Cancel"
				onclick="javascript:window.location.href='list_order';" />
		</div>
	</form>

	<script type="text/javascript">
	

		$(document).ready(function() {
			$("#orderForm").validate({
				rules : {
					recipientName : "required",
					recipientPhone : "required",
					shippingAddress : "required",
					
					<c:forEach items="${order.orderDetails}" var="book" varStatus="status">
						quantity${status.index + 1}: {
						required: true,
						number: true,
						min: 1
					},
					</c:forEach>
				},

				messages : {
					recipientName : "Please enter recipient name",
					recipientPhone : "Please enter recipient phone",
					shippingAddress : "Please enter address",
					
					<c:forEach items="${order.orderDetails}" var="book" varStatus="status">
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