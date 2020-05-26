<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div align="center">
	<div>
		<img src="../images/logoAdmin.png" />
		
		
	</div>

	<div>
		Welcome,
		<c:out value="${sessionScope.useremail }"></c:out>  <!--sessionScope -	Контекст сессии. Переменные доступны в течение всей сессии пользователя, т.е. пока не будет закрыт браузер или не закончится предельное время бездействия.  -->
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
			<a href="customer"> <img src="../images/customer.png" /><br />Customers
			</a>
		</div>

		<div>
			<a href="orders"> <img src="../images/order.png" /><br />Orders
			</a>
		</div>

		<div>
			<a href="blackList"> <img src="../images/black_list.png" /><br />BlackList
			</a>
		</div>



	</div>
</div>