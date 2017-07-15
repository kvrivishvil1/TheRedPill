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
<title>Quiz</title>
</head>
<body>
	<%
		request.getSession().setAttribute("quizId", 3);//alert : temporary line 
		QuizDao dao = new QuizDao();
		int quizId = (int) request.getSession().getAttribute("quizId");
		Quiz quiz = dao.getQuiz(quizId);
	%>
	<div class="container">
		<h1><%=quiz.getName()%></h1>
		<p><%=quiz.getDescription()%></p>
		<input type="checkbox" id="single-page" /> Display On Single Page<br>
		<button id="start-quiz">Start a Quiz</button>
	</div>
	<script src="JAVASCRIPT/jquery-1.9.1.min.js"></script>
	<script src="JAVASCRIPT/display-quiz.js"></script>
</body>
</html>