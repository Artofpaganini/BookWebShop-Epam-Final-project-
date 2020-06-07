<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Create New Customer</title>
<link rel="stylesheet" href="../css/style.css">
<script type="text/javascript" src=../js/jquery-3.5.1.min.js></script>
<script type="text/javascript" src=../js/jquery.validate.min.js></script>
</head>
<body>
	<jsp:directive.include file="header.jsp" />

	<div align="center">
		<hr width="60%">
		<h2>
			<c:if test="${customer != null}">Edit Customer</c:if>
			<c:if test="${customer == null}">Create New Customer</c:if>
		</h2>

	</div>


	<div align="center">
		<c:if test="${customer != null}">
			<form action="update_customer" method="post" id="customerForm">
				<input type="hidden" name="customerId"
					value="${customer.customerId}">
		</c:if>
		<c:if test="${customer == null}">
			<form action="create_customer" method="post" id="customerForm">
		</c:if>

		<table class="form">

			<tr>
				<td>Email:</td>
				<td><input type="text" id="email" name="email" size="45"
					value="${customer.email}" /></td>

			</tr>
			<tr>
				<td>Full Name:</td>
				<td><input type="text" id="fullName" name="fullName" size="45"
					value="${customer.fullName}" /></td>
			</tr>


			<tr>
				<td>Password:</td>
				<td><input type="password" id="password" name="password"
					size="45" value="${customer.password}" /></td>
			</tr>

			<tr>
				<td>Confirm Password:</td>
				<td><input type="password" id="confirmPassword"
					name="confirmPassword" size="45" value="${customer.password}" /></td>
			</tr>

			<tr>
				<td>Phone number:</td>
				<td><input type="text" id="phone" name="phone" size="45"
					value="${customer.phone}" /></td>
			</tr>

			<tr>
				<td>Address:</td>
				<td><input type="text" id="address" name="address" size="45"
					value="${customer.address}" /></td>
			</tr>

			<tr>
				<td>City:</td>
				<td><input type="text" id="city" name="city" size="45"
					value="${customer.city}" /></td>
			</tr>

			<tr>
				<td>Zip Code:</td>
				<td><input type="text" id="zipCode" name="zipCode" size="45"
					value="${customer.zipCode}" /></td>
			</tr>
			<tr>
				<td>Country:</td>
				<td><input type="text" id="country" name="country" size="45"
					value="${customer.country}" /></td>
			</tr>

			<tr>
				<td>Block Status:</td>
				<c:if test="${customer != null}">
					<td><input type="сheckbox" id="block" name="block" size="45"
						value="${customer.block}" /></td>
				</c:if>
				<c:if test="${customer == null}">
					<td><input type="сheckbox" id="block" name="block" size="45"
						value="false" /></td>
				</c:if>

			</tr>

			<tr>
				<td>&nbsp;</td>
			</tr>

			<tr>
				<td colspan="2" align="center"><input type="submit"
					value="Save"> <input type="button" value="Cancel"
					onclick="javascript:history.go(-1);"></td>
			</tr>
		</table>
		</form>
	</div>
</body>

<script type="text/javascript">
	$(document).ready(function() {
		$("#customerForm").validate({
			rules : {
				email : {
					required : true,
					email : true
				},

				fullName : "required",

				confirmPassword : {
					equalTo : "#password"
				},

				phone : "required",
				address : "required",
				city : "required",
				zipCode : "required",
				country : "required",

			},

			messages : {
				email : {
					required : "Please enter email",
					email : "Please enter valid email"
				},

				fullName : "Please enter full name",

				confirmPassword : {

					equalTo : "Confirm password does not match with password"
				},

				phone : "Please enter phone number",
				address : "Please enter address",
				city : "Please enter city",
				zipCode : "Please enter zip code",
				country : "Please enter country"

			}
		});
	});
</script>
</html>