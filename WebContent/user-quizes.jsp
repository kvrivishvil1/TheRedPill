<%@page import="java.util.ArrayList"%>
<%@page import="managers.MainManager"%>

<link rel="stylesheet" href="CSS/normalize.css">
<link rel="stylesheet" href="CSS/recent-quizes.css">
	<div class="container-hpq">
		<% 
		ServletContext cont = request.getServletContext();
		MainManager mainManager = (MainManager) cont.getAttribute(MainManager.CONTEXT_ATTRIBUTE_NAME);
		ArrayList<Integer> quizzes = new ArrayList<>();
		String username = (String)session.getAttribute("username");
		quizzes = mainManager.getQuizManager().getMyQuizes(mainManager.getAccountManager().getUserIdByUserName(username));
		%>
		<h3>Quizes created By you</h3>
		<% for(Integer quizId : quizzes) { %>
		  	<div class="quiz">
		  		<div class="quiz-name">
		  			<a class="link" href="display-quiz.jsp?quizId=<%= quizId %>"><%= mainManager.getQuizManager().getQuizName(quizId) %></a>
		  		</div>
		  		<div class="summary">
		  			<form action="quiz-summary.jsp?quizId=<%= quizId %>">
    					<input class="input" type="submit" value="Summary">
					</form>
		  		</div>
			</div>
		<% } %>
	</div>