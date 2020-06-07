<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Book Shop Administration</title>
<link rel="stylesheet" href="../css/style.css">
</head>
<body>
	<jsp:directive.include file="header.jsp" />

	<div align="center">
		<hr width="60%">

		<h2>Statistics</h2>

		Total UserAdmin: ${totalUsers} &nbsp;&nbsp;&nbsp;&nbsp; 
		Total Books: ${totalBooks} &nbsp;&nbsp;&nbsp;&nbsp;
		Total Categories: ${totalCategories} &nbsp;&nbsp;&nbsp;&nbsp;
		Total Customers: ${totalCustomers} &nbsp;&nbsp;&nbsp;&nbsp; 
		Total Orders: ${totalOrders} &nbsp;&nbsp;&nbsp;&nbsp;
		
		<hr width="60%">
	</div>
</body>
</html>