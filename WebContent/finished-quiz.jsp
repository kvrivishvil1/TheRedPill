<% if(session.getAttribute("username") == null) {
	RequestDispatcher rd = request.getRequestDispatcher("index.html");
 	rd.forward(request,response);
}%>
<%@page import="managers.AchievementManager"%>
<%@page import="managers.MainManager"%>
<%@page import="db.dao.QuizDao"%>
<%@page import="db.bean.quiz.QuizAttempt"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="CSS/quiz.css">
<title>Quiz Done</title>
</head>
<body>
	<%
		ServletContext cont = request.getServletContext();
		MainManager mainManager = (MainManager) cont.getAttribute(MainManager.CONTEXT_ATTRIBUTE_NAME);
		QuizAttempt attempt = (QuizAttempt) request.getSession().getAttribute("attempt");
		mainManager.getQuizManager().addQuizAttempt(attempt);
		QuizDao dao = new QuizDao();
		AchievementManager amng = new AchievementManager(dao);
		amng.updateAchievementsAfterQuiz(1, 1);//should take attributes from quiz
		out.write("<div class = \"container1\">");
		out.write("<h1>you have got: " + attempt.getScore() + " points</h1>");
		out.write("<p>Started at:" + attempt.getStartTime() + "</p>");
		out.write("<p>Finished at:" + attempt.getFinishTime() + "</p>");
		out.write("</div>");
	%>
</body>
</html>