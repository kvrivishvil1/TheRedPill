<% if(session.getAttribute("username") == null) {
	RequestDispatcher rd = request.getRequestDispatcher("index.html");
 	rd.forward(request,response);
}%>
<%@page import="helpers.htmlGenerator"%>
<%@page import="db.bean.quiz.Question"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="CSS/quiz.css">
<script src="JAVASCRIPT/quiz-checker.js"></script>
<script src="https://code.jquery.com/jquery.min.js"></script>
<title>Quiz</title>
</head>
<body>
	<div id="container">
		<%
			ArrayList<Question> questionList = (ArrayList) (request.getSession().getAttribute("questionList"));
			int numQuestions = questionList.size();
			for (int i = 0; i < numQuestions; i++) {
				Question question = questionList.get(i);
				out.write("<div class  = \"question-container\">" + (i + 1) + ".");
				out.write(htmlGenerator.displaySingleQuestion(question, i));
				out.write("</div>");
			}
			out.write("<div id=\"submit-area\"><button id=\"submit\">Submit Answers</button></div>");
		%>
		<p id="number"><%=questionList.size()%></p>
	</div>
	<script src="JAVASCRIPT/quiz-checker.js"></script>
	<script src="https://code.jquery.com/jquery.min.js"></script>
</body>
</html>