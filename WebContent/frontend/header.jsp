<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!--  для использования  foreach  тагов -->
<div align="center">
	<div>
		<img src="images/logo.png" />


	</div>

	<div>
		<input type="text" name="keyword" size="50" /> <input type="button"
			value="Search" /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a
			href="login">Sign in</a> | <a href="register">Register</a> | <a
			href="view_cart">Cart</a> |
	</div>

	<div>&nbsp;</div>

	<div>
		<c:forEach var="category" items="${listCategory }">
			<!--  listCategory  мы вызываем в хом сервлете , с помощью реквест атрибута -->

			<font size="+1"> <a
				href="view_category?id=${category.categoryId } "> <b> <c:out
							value="${category.name} " /> <!-- font size = +1  увеличивает размер списка категорий -->
				</b></font>
			</a>
			&nbsp;	|

		</c:forEach>
	</div>
</div>