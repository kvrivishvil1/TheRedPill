<% if(session.getAttribute("username") == null) {
	RequestDispatcher rd = request.getRequestDispatcher("index.html");
 	rd.forward(request,response);
}%>
<%@page import="managers.MainManager"%>
<%@page import="db.bean.quiz.Quiz"%>
<%@page import="db.dao.QuizDao"%>
<%@page import="db.bean.quiz.Question"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="JAVASCRIPT/display-quiz.js"></script>
<script src="https://code.jquery.com/jquery.min.js"></script>
<link rel="stylesheet" href="CSS/quiz.css">
<title>Quiz</title>
</head>
<body>
	<%
		ServletContext cont = request.getServletContext();
		MainManager mainManager = (MainManager) cont.getAttribute(MainManager.CONTEXT_ATTRIBUTE_NAME);
		int quizId;
		if(request.getParameter("quizId") != null) {
			quizId = Integer.parseInt(request.getParameter("quizId"));
		} else {
			quizId = (int) request.getSession().getAttribute("quizId");
		}
		request.getSession().setAttribute("quizId", quizId);
		Quiz quiz = mainManager.getQuizManager().getQuiz(quizId);
	%>
	<div class="container1">
		<h1><%=quiz.getName()%></h1>
		<p><%=quiz.getDescription()%></p>
		<p><input type="checkbox" id="single-page" /> Display On Single Page</p><br>
		<button id="start-quiz">Start a Quiz</button>
	</div>
	<script src="JAVASCRIPT/jquery-1.9.1.min.js"></script>
	<script src="JAVASCRIPT/display-quiz.js"></script>
</body>
</html>
