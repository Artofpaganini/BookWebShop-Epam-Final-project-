<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>DevBrain - Online Books Shop</title>
</head>
<body>
	<jsp:directive.include file="header.jsp" />

	<div align="center">
		<br /><br /> <!-- создает отступы  -->
		<h3>This is main content:</h3>
		<!-- h3  и тп , размер шрифта, чем выше число тем меньше шрифт -->
		<h2>New Books:</h2>
		<h2>Best-seller Books:</h2>
		<h2>Most favored Books:</h2>
		<br /><br />
	</div>

	<jsp:directive.include file="footer.jsp" />
</body>
</html>