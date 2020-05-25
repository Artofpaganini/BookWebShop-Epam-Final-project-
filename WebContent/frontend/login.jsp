<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login</title>
</head>
<body>
	<jsp:directive.include file="header.jsp" /> <!-- указываем направление, чтобы включить файл вместо огромного кол-ва кода
	т.к. у нас  верних и нижний колонтикулы(части страницы) одинаковые на каждой страницы, изменяется только  main content  -->

	<div align="center">
		<h2>Please enter Login and Password:</h2>
		<form>
			Email: <input type="text" size="10"><br /> Password: <input
				type="text" size="10" /> <input type="submit" value="Login" />
		</form>
	</div>

	<jsp:directive.include file="footer.jsp" />
</body>
</html>