<%@page import="db.bean.quiz.QuizAttempt"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Quiz Done</title>
</head>
<body>

	<%
		QuizAttempt attempt = (QuizAttempt) request.getSession().getAttribute("attempt");
		out.write("<h1>you have got: " + attempt.getScore() + " points</h1>");
		out.write("<p>Started at:"+attempt.getStartTime()+"</p>");
		out.write("<p>Finished at:"+attempt.getFinishTime()+"</p>");
	%>
</body>
</html>