<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Create New Category</title>
<link rel="stylesheet" href="../css/style.css">
<script type="text/javascript" src=../js/jquery-3.5.1.min.js></script>
<script type="text/javascript" src=../js/jquery.validate.min.js></script>
</head>
<body>
	<jsp:directive.include file="header.jsp" />

	<div align="center">
		<hr width="60%">
		<h2>
			<c:if test="${category !=null}">Edit Category</c:if>
			<c:if test="${category ==null}">Create New Category</c:if>
		</h2>

	</div>

	<div align="center">
		<c:if test="${category !=null}">
			<form action="update_category" method="post" id="categoryForm">
				<input type="hidden" name="categoryId" value=${category.categoryId}>
		</c:if>

		<c:if test="${category == null}">
			<form action="create_category" method="post" id="categoryForm">
		</c:if>

		<table>

			<tr>
				<td>Name:</td>
				<td><input type="text" id="name" name="name" size="20"
					value="${category.name}" /></td>
			</tr>

			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td colspan="2" align="center"><input type="submit"
					value="Save"> <input type="button" value="Cancel"
					onclick="javascript:history.go(-1);"> 
				</td>
			</tr>

		</table>
		</form>
	</div>
</body>
<script type="text/javascript">
	$(document).ready(function() {
		$("#categoryForm").validate({
			rules : {
				name : "required"
			},

			messages : {
				name : "Please enter category name!"
			}
		});

	});
</script>
</html>