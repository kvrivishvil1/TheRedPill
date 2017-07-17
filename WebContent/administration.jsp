<% if(session.getAttribute("username") == null) {
	RequestDispatcher rd = request.getRequestDispatcher("index.html");
 	rd.forward(request,response);
}%>
<%@page import="managers.MainManager"%>
<%@page import="db.dao.QuizDao"%>
<%@page import="db.dao.UserDao"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="JAVASCRIPT/administration.js"></script>
<script src="https://code.jquery.com/jquery.min.js"></script>
<link
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"
	rel="stylesheet" type="text/css" />
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Admin Panel</title>
<link rel="stylesheet" href="CSS/administration.css">
</head>
<body>
	<div class="container">
		<div id="admin-info">
			<p>
				You are redirected to <b>Administrator's Page</b>. Here you can:
				Create announcements. Remove user accounts. Remove quizzes. Clear
				all history information for a particular quiz. Promote user accounts
				to administration accounts. See site statistics.
			</p>
		</div>

		<div class="stat-details">
			<button class="btn btn-secondary" type="button">Manage Users</button>
			<div class="details">
				<%
					ServletContext cont = request.getServletContext();
					MainManager mainManager = (MainManager) cont.getAttribute(MainManager.CONTEXT_ATTRIBUTE_NAME);
				%>
				<p>Total number of users: <b><%=mainManager.getAccountManager().getNumUsers()%></b><p>
				
				
				<a id="display-all" href="all-user.jsp" role="button">Display
					All Users</a>
			</div>
		</div>
		<div class="stat-details">
			<button class="btn btn-secondary" type="button">Manage
				Quizes</button>
			<div class="details">
				<p>Total number of quizzes: <b><%=mainManager.getQuizManager().getNumQuizzes()%></b><p>
				
				<a id="display-all" href="all-quiz.jsp" role="button">Display
					All Quizzes</a>
			</div>
		</div>

		<div id="note-container">
			<button class="btn btn-secondary" type="button">Create
				Announcement</button>
			<div id="note-form">
				<br> <input class="form-control" id="note-header" type="text"
					placeholder="Title"></input><br>
				<textarea class="form-control" id="note" rows="5"
					placeholder="Announcement Text"></textarea>
				<br>
				<button class="btn btn-secondary" type="button" id="publish">Publish</button>
				<p id="note-status"></p>
			</div>
		</div>
	</div>
	<script src="JAVASCRIPT/jquery-1.9.1.min.js"></script>
	<script src="JAVASCRIPT/administration.js"></script>
</body>
</html>

