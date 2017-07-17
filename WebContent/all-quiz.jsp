<% if(session.getAttribute("username") == null) {
	RequestDispatcher rd = request.getRequestDispatcher("index.html");
 	rd.forward(request,response);
}%>
<%@page import="managers.MainManager"%>
<%@page import="com.sun.javafx.collections.MappingChange.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="db.dao.QuizDao"%>
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
<link rel="stylesheet" href="CSS/administration.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Created Quizzes</title>
</head>
<body>
	<%
		ServletContext cont = request.getServletContext();
		MainManager mainManager = (MainManager) cont.getAttribute(MainManager.CONTEXT_ATTRIBUTE_NAME);
		HashMap<Integer, String> allQuizzes = mainManager.getQuizManager().getAllQuizzes();
	%>
	<div class="container">
		<div class="input-group">
			<input id = "searched-quiz" type="text" class="form-control" placeholder="Search for Quiz">
			<span class="input-group-btn">
				<button id="search-quiz" class="btn btn-secondary" type="button">Search Quiz</button>
			</span>
		</div>
		<table>
			<%
				for (int currentQuizId : allQuizzes.keySet()) {
					String currentQuiz = allQuizzes.get(currentQuizId);
			%>
			<tr>
				<td>Quiz:
					<p id="quiz-label"><%=currentQuiz%></p>
				</td>
				<td><button class="remove-quiz">Remove Quiz</button></td>
				<td><button class="clear-quiz-info">Clear Quiz Info</button></td>
			</tr>
			<%
				}
			%>
		</table>
	</div>

	<script src="JAVASCRIPT/jquery-1.9.1.min.js"></script>
	<script src="JAVASCRIPT/administration.js"></script>
</body>
</html>