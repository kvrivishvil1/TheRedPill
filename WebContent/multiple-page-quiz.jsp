<%@page import="db.bean.quiz.Question"%>
<%@page import="java.util.ArrayList"%>
<%@page import="helpers.htmlGenerator"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<script src="JAVASCRIPT/multiple-page.js"></script>
<script src="JAVASCRIPT/quiz-checker.js"></script>
<script src="https://code.jquery.com/jquery.min.js"></script>
<link rel="stylesheet" href="CSS/quiz.css">
<title>Quiz</title>
</head>
<body>
	<div class="container">

		<div id="question-container">
			<%
				ArrayList<Question> allQuestions = (ArrayList) request.getSession().getAttribute("questionList");
				for (int i = 0; i < allQuestions.size(); i++) {
					Question question = allQuestions.get(i);
					out.write("<div class  = \"question-container\" id = \"ques-" + i + "\">" + (i + 1) + ".");
					out.write(htmlGenerator.displaySingleQuestion(question, i));
					out.write("</div>");
				}
			%>
		</div>
		<div id="navigation">
			<button id="prev-question">Previous Question</button>
			<button id="next_question">Next Question</button>
		</div>
		<div id="submit-area">
			<button id="submit">Submit Answers</button>
		</div>
		<p id="number"><%=allQuestions.size()%></p>
	</div>
	<script src="JAVASCRIPT/multiple-page.js"></script>
	<script src="JAVASCRIPT/quiz-checker.js"></script>
	<script src="https://code.jquery.com/jquery.min.js"></script>
</body>
</html>