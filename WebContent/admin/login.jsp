<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Admin Login</title>
<link rel="stylesheet" href="../css/style.css">
<script type="text/javascript" src=../js/jquery-3.5.1.min.js></script>
<script type="text/javascript" src=../js/jquery.validate.min.js></script>
</head>
<body>

	<div align="center">
		<h1>Book Shop Administration</h1>
		<h2>Admin Login</h2>

		<c:if test="${message != null }">
			<!--   проверяет если мессаге пуст то ничего не выводится а если мессаге  заполнен  то выводим сообщение об ошибке -->
			<div align="center">
				<h4 class="message">${message}</h4>
			</div>
		</c:if>



		<form id="loginForm" action="login" method="post">
			<table>
				<tr>
					<td>Email:</td>
					<td><input type="text" name="email" size="20"></td>

				</tr>
				<tr>
					<td>Password:</td>
					<td><input type="password" name="password" id="password"
						size="20"></td>
				</tr>

				<tr>
					<td colspan="2" align="center">
						<button type="submit">Login</button>
					</td>
				</tr>

			</table>
		</form>
	</div>

	<script type="text/javascript">
		// скрипт для проверки заполнения полей , если поле не заполнено то скрипт работает

		$(document).ready(function() {
			$("#loginForm").validate({
				rules : {
					email : {
						required : true, //если идет запрос, что нужно заполнить поле, то будет выводится сообщение которое внизу в мессагес
						email : true
					},

					password : "required",
				},

				messages : {
					email : {
						required : "Please enter email", // если поле пусто то будет эта надпись а если заполнено, но не имейл а мусор , то надпись ниже
						email : "Please enter an valid email address"
					},

					password : "Please enter password",
				}
			});
		});
	</script>
</body>
</html>