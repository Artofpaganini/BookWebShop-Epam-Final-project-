<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div align="center">
	<div>
		<a href="${pageContext.request.contextPath }/"> <img
			src="images/logo.png" />
		</a>

	</div>

	<div>
		<form action="search" method="get">
			<input type="text" name="keyword" size="50" /> <input type="submit"
				value="Search" /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

			<c:if test="${loggedCustomer == null }">
				<a href="login">Sign in</a> | <a href="register">Register</a> | <a
					href="view_cart">Cart</a>
			</c:if>

			<c:if test="${loggedCustomer != null }">
				<a href="view_profile">Welcome, ${loggedCustomer.fullName}</a> |
				<a href="view_orders">My Orders</a> |
				<a href="view_cart">Cart</a> |
				<a href="logout">Sign out</a>
			</c:if>

		</form>
	</div>

	<div>&nbsp;</div>

	<div>
		<c:forEach var="category" items="${listCategory }">

			<font size="+1"> <a
				href="view_category?id=${category.categoryId } "> <b> <c:out
							value="${category.name} " />
				</b></font>
			</a>
			&nbsp;	|

		</c:forEach>
	</div>
</div>