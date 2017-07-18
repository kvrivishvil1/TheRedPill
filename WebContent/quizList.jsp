<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="managers.MainManager"%>
<%@page import="java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>quizzes</title>
<link rel="stylesheet" href="CSS/normalize.css">
<link rel="stylesheet" href="CSS/quizList.css">
<script src="JAVASCRIPT/quizList.js"></script>
<script src="http://code.jquery.com/jquery-1.11.0.min.js"></script>
<script src="https://code.jquery.com/jquery.min.js"></script>
</head>
<body>



<div class = "container">

	<%
		ServletContext sc = request.getServletContext();
		MainManager mainManager = (MainManager) sc.getAttribute(MainManager.CONTEXT_ATTRIBUTE_NAME);
		int categoryID = (Integer.parseInt((String)request.getParameter("id")));
		ArrayList<Integer> quizzes = mainManager.getQuizManager().getCategoryQuizzes(categoryID);
		%>

		<div class="category-form"><%=mainManager.getQuizManager().getCategoryName(categoryID) %></div>

	<%	if (quizzes == null || quizzes.size() == 0){ %>

	<div class="quiz-section-form">Nothing to show</div>
	<br>
	<%
		} else {
			for (int i = 0; i < quizzes.size(); i++) {
	%>
	
	<div class="annoucement-form">
			<div class="header-form">
				<div class="title-form"><%=mainManager.getQuizManager().getQuizName(quizzes.get(i))%></div>
			</div>
			<div class="announcement-content-form">
				<div class="button" >
					<a href=<%="display-quiz.jsp?quizId=" + quizzes.get(i)%>><i class="top">
						Start quiz </i> </a>
				</div>
				<div class="button">See quiz summary</div>
			</div>
		</div>
	

	<%
		}
	}
	%>
</div>
	<script src="JAVASCRIPT/quizList.js"></script>
	<script src="http://code.jquery.com/jquery-1.11.0.min.js"></script>
	<script src="https://code.jquery.com/jquery.min.js"></script>
</body>
</html>