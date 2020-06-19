<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Manage Users</title>
<link rel="stylesheet" href="../css/style.css">
</head>
<body>
	<jsp:directive.include file="header.jsp" />

	<div align="center">
		<h2>Users Management</h2>
		<h3>
			<a href="user_form.jsp">Create New User</a>
		</h3>
	</div>


	<div align="center">
		<table border="1" cellpadding="7">
			<tr>
				<th>Index</th>
				<th>ID</th>
				<th>Email</th>
				<th>Full Name</th>
				<th>Actions</th>
			</tr>
			<c:forEach var="user" items="${listUsers}" varStatus="status">
				<tr>
					<td>${status.index+1}</td>
					<td>${user.usersId}</td>
					<td>${user.email}</td>
					<td>${user.fullName}</td>


					<td><a href="edit_user?id=${user.usersId }">Edit</a> &nbsp <a
						href="javascript:confirmDelete(${user.usersId})">Delete</a> &nbsp</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<script>
		function confirmDelete(userId) {
			if (confirm('Are you sure? You want to delete the user with ID '
					+ userId + '?')) {
				window.location = 'delete_user?id=' + userId;
			}
		}
	</script>
</body>
</html>