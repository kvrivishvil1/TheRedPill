<% if(session.getAttribute("username") == null) {
	RequestDispatcher rd = request.getRequestDispatcher("index.html");
 	rd.forward(request,response);
}%>
<%@page import="javafx.util.Pair"%>
<%@page import="java.util.ArrayList"%>
<%@page import="managers.MainManager"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="CSS/normalize.css">
<link rel="stylesheet" href="CSS/search-by-tag.css">
<title>Insert title here</title>
</head>
<body>
	<% 
		ServletContext cont = request.getServletContext();
		MainManager mainManager = (MainManager) cont.getAttribute(MainManager.CONTEXT_ATTRIBUTE_NAME);
		ArrayList<Pair<String, Integer>> quizzes = mainManager.getQuizManager().getQuizesByTag("math");
	%>
	<div class = "content">
		 <h2><%= quizzes.size() %> quizzes found with tag <%= "math" %></h2>
		  <% for(Pair pair : quizzes) { %>
		  	<div class="quiz">
		  		<div class="quiz-name">
		  			<a class="link" href="display-quiz.jsp?quizId=<%= pair.getValue()%>"> <%= pair.getKey() %></a>
		  		</div>
		  		<div class="summary">
		  			<form action="quiz-summary.jsp?quizId=<%= pair.getValue()%>">
    					<input type="submit" value="See Summary">
					</form>
		  		</div>
		  	</div>
		  <% } %>
	</div>
</body>
</html>
