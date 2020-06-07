<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>My Order Details</title>
<link rel="stylesheet" href="/css/style.css">
</head>
<body>

	<jsp:directive.include file="header.jsp" />

	<c:if test="${order == null }">
		<div align ="center">
			<h2>Sorry, you can't see this order details</h2>
		</div>
	</c:if>

	<c:if test="${order != null }">
		<div align="center">
			<h2>Your Order ID: ${order.orderId}</h2>

		</div>

		<div align="center">

			<table border="1" cellpadding="7">
				<tr>
					<td><b>Order Status:</b></td>
					<td>${order.orderStatus}</td>
				</tr>

				<tr>
					<td><b>Order Date:</b></td>
					<td>${order.orderDate}</td>
				</tr>

				<tr>
					<td><b>Total Amount,$:</b></td>
					<td>${order.orderTotal}</td>
				</tr>

				<tr>
					<td><b>Recipient name:</b></td>
					<td>${order.recipientName}</td>
				</tr>

				<tr>
					<td><b>Recipient phone:</b></td>
					<td>${order.recipientPhone}</td>
				</tr>

				<tr>
					<td><b>Ship To:</b></td>
					<td>${order.shippingAddress}</td>
				</tr>

				<tr>
					<td><b>Payment Method:</b></td>
					<td>${order.paymentMethod}</td>
				</tr>

			</table>
		</div>

		<div align="center">
			<h2>Ordered Books</h2>
			<table border="1" cellpadding="7">
				<tr>
					<th>No</th>
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
						<td><img style="vertical-align: middle;"
							src="data:image/jpg;base64,${orderDetail.book.base64Image}"
							width="48" height="64" /> ${orderDetail.book.title }</td>
						<td>${orderDetail.book.author }</td>
						<td>${orderDetail.book.price }$</td>
						<td>${orderDetail.quantity }</td>
						<td>${orderDetail.subTotal }$</td>
					</tr>
				</c:forEach>

				<tr>
					<td colspan="4" align="right">Total:</td>
					<td>${orderDetail.quantity}</td>
					<td>${order.orderTotal }</td>
				</tr>
			</table>
		</div>
	</c:if>
	&nbsp;&nbsp;&nbsp;
	<jsp:directive.include file="footer.jsp" />
</body>
</html>