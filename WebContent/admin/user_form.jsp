<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><!--  тег создания циклов, определения условий, вывода информации на страницу,альтернатива скриплетам . -->
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Create New User</title>
<link rel="stylesheet" href="../css/style.css">
<script type="text/javascript" src=../js/jquery-3.5.1.min.js></script>
<script type="text/javascript" src=../js/jquery.validate.min.js></script>

</head>
<body>
	<jsp:directive.include file="header.jsp" />

	<div align="center">
		<hr width="60%">
		<h2>
			<c:if test="${user !=null}">Edit User</c:if>
			<c:if test="${user ==null}">Create New User</c:if>
		</h2>

	</div>

	<div align="center">
		<c:if test="${user != null}">
			<form action="update_user" method="post" id="userForm">
				<input type="hidden" name="userId" value="${user.usersId}">
		</c:if>
		<c:if test="${user == null}">
			<form action="create_user" method="post" id="userForm">
		</c:if>


		<table class="form">
			<tr>
				<td>Email:</td>
				<td><input type="text" id="email" name="email" size="20"
					value="${user.email}" /></td>
				<!-- value="${user.email} выводит на экран данные юзера которого мы будем edit -->
			</tr>
			<tr>
				<td>Full Name:</td>
				<td><input type="text" id="fullname" name="fullname" size="20"
					value="${user.fullName}" /></td>
			</tr>
			<tr>
				<td>Password:</td>
				<td><input type="password" id="password" name="password"
					size="20" value="${user.password}" /></td>
			</tr>

			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td colspan="2" align="center"><input type="submit"
					value="Save"> <input type="button" value="Cancel"
					onclick="javascript:history.go(-1);"> <!--  onclick  вернет на предыдущую страницу -->
				</td>
			</tr>

		</table>
		</form>
	</div>
</body>


<script type="text/javascript">
	// скрипт для проверки заполнения полей , если поле не заполнено то скрипт работает

	$(document).ready(function() {
		$("#userForm").validate({
			rules : {
				email : {
					required : true, //если идет запрос, что нужно заполнить поле, то будет выводится сообщение которое внизу в мессагес
					email : true
				},

				fullname : "required",
				
				<c:if test="${user == null}"> // если юзер = нулл то будет запрашиваться введиние пароля пароль
				password: "required"
				</c:if>

			},

			messages : {
				email : {
					required : "Please enter email", // если поле пусто то будет эта надпись а если заполнено, но не имейл а мусор , то надпись ниже
					email : "Please enter an valid email "
				},

				fullname : "Please enter fullname",
				
				<c:if test="${user == null}">
				password: "Please enter password"
				</c:if>	
			}
		});
	});
</script>
</html>