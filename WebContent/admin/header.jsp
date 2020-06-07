<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div align="center">
	<div>
		<a href="${pageContext.request.contextPath }/admin/"> <img
			src="../images/logoAdmin.png" />
		</a>
	</div>

	<div>
		Welcome,
		<c:out value="${sessionScope.useremail }"></c:out>
		<!--sessionScope -	Контекст сессии. Переменные доступны в течение всей сессии пользователя, т.е. пока не будет закрыт браузер или не закончится предельное время бездействия.  -->
		| <a href="logout">Logout</a> <br /> <br />
	</div>

	<div id="headermenu">
		<!-- <b></b> делает шрифт жирным -->

		<div>
			<a href="list_users"> <img src="../images/users.png" /><br />Users
			</a>
		</div>

		<div>
			<a href="list_category"> <img src="../images/category.png" /><br />Categories
			</a>
		</div>

		<div>
			<a href="list_books"> <img src="../images/books.png" /><br />Books
			</a>
		</div>

		<div>
			<a href="list_customer"> <img src="../images/customer.png" /><br />Customers
			</a>
		</div>

		<div>
			<a href="list_order"> <img src="../images/order.png" /><br />Orders
			</a>
		</div>



	</div>
</div>