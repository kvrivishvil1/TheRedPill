<%@page import="db.bean.Account"%>
<%@page import="managers.MainManager"%>
<%@page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>home page</title>
<link rel="stylesheet" href="CSS/normalize.css">
<link rel="stylesheet" href="CSS/homepage.css">
<link rel="stylesheet" href="CSS/font-awesome.min.css">
<script src="JAVASCRIPT/homepage.js"></script>
<script src="http://code.jquery.com/jquery-1.11.0.min.js"></script>
<script src="https://code.jquery.com/jquery.min.js"></script>
</head>
<body>


	<div class="form clearfix">
		<section>
			<design class="active" href="#home">
				<i class="fa fa-home" aria-hidden="true"></i>
			</design>
		</section>
		<section>
			<design href="#quiz">
				<i class="fa fa-server" aria-hidden="true"></i>
			</design>
		</section>
		<section>
			<design href="#friends">
				<div class="friend-request">
					<i class="fa fa-address-book-o" aria-hidden="true"></i>
					<div class="triangle transform"></div>
					<div class="friend-request-display"><jsp:include page="friend-request-min.jsp"></jsp:include></div>
				</div>
			</design>
		</section>
		<section>
			<design href="#messages">
				<i class="fa fa-envelope-open-o" aria-hidden="true"></i>
			</design>
		</section>
	</div>


	<div class="container clearfix">
		<div class="icon-form">
			<i class="fa fa-paint-brush  fa-5x" aria-hidden="true">
				<p class="category-title-form">Art</p>
			</i>
		</div>

		<div class="icon-form">
			<i class="fa fa-shopping-bag  fa-5x" aria-hidden="true">
				<p class="category-title-form">Fashion</p>
			</i>
		</div>

		<div class="icon-form">
			<i class="fa fa-futbol-o fa-5x" aria-hiddenF="true">
				<p class="category-title-form">Sport</p>
			</i>
		</div>
		<br>
		<div class="icon-form">
			<i class="fa fa-music  fa-5x" aria-hidden="true">
				<p class="category-title-form">Music</p> 
			</i>
		</div>

		<div class="icon-form">
			<i class="fa fa-film fa-5x" aria-hidden="true">
				<p class="category-title-form">Film</p>
			</i>
		</div>


		<div class="icon-form">
			<i class="fa fa-users  fa-5x" aria-hidden="true">
				<p class="category-title-form">Celebrities</p>
			</i>
		</div>

		<br>
		<%
			ServletContext sc = request.getServletContext();
			MainManager mainManager = (MainManager) sc.getAttribute(MainManager.CONTEXT_ATTRIBUTE_NAME);
			HashMap<String, String> announcements = mainManager.getMessageManager().getAdminstrationNote();
			for (String header : announcements.keySet()) {
		%>
		<div class="annoucement-form">
			<div class="header-form">
				<div class="title-form"><%=header%></div>
			</div>
			<div class="announcement-content-form"><%=announcements.get(header)%></div>
		</div>
		<%
			}
		%>

	</div>

	<script src="JAVASCRIPT/homepage.js"></script>
	<script src="http://code.jquery.com/jquery-1.11.0.min.js"></script>
	<script src="https://code.jquery.com/jquery.min.js"></script>


</body>
</html>










