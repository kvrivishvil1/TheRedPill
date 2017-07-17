<% if(session.getAttribute("username") == null) {
	RequestDispatcher rd = request.getRequestDispatcher("index.html");
 	rd.forward(request,response);
}%>
<%@page import="managers.MainManager"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="db.bean.Challenge"%>
<%@page import="java.util.HashSet"%>
<%@page import="db.dao.MessageDao"%>
<%@page import="java.util.HashMap"%>
<%@page import="db.dao.UserDao"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="CSS/normalize.css">
<link rel="stylesheet" href="CSS/challenge.css">
</head>
<body>
<div class="content">
	<% 
		ServletContext cont = request.getServletContext();
		MainManager mainManager = (MainManager) cont.getAttribute(MainManager.CONTEXT_ATTRIBUTE_NAME);
		String username = (String)request.getSession().getAttribute("username");
		int userID = mainManager.getAccountManager().getUserIdByUserName(username);
		HashSet<Challenge> challenges = mainManager.getMessageManager().getAllChallngesForUser(userID);
	%>
	<div class="challenges">
	<h2> You have <%= challenges.size() %> Challenges </h2>
		<% for(Challenge challenge : challenges) { %>
		<% 
			DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
			formatter.setLenient(false);
			Date date = challenge.getDate();
		%>
	
	<form class="challenge-form" action="ChallengeServlet" method="post">
		<div class="each-challenge">
			<div class="challenge-info">
				<div class="challenge-info-line">
					Challenge sender: <%= mainManager.getAccountManager().getUsernameByUserId(challenge.getSenderID()) %>
				</div> 
				<div class="challenge-info-line">Sent: <%= formatter.format(date) %> </div>
				<div class="challenge-info-line">Max score: <%= challenge.getMaxScore() %> </div>
				<div class="challenge-info-line" style="margin-bottom:0px;">Quiz name: <%= mainManager.getQuizManager().getQuizName(challenge.getQuizID()) %></div>
			</div>
			<div class="challenge-buttons">
				<div class="button button-top">
					<input type="submit" class="confirm input" name="act" value="Start Quiz">
				</div>
				<div class="button">
					<input type="submit" class="delete input" name="act" value="Delete Challenge">
				</div>
				<input type="hidden" name="quizId" value="<%= challenge.getQuizID() %>"> 
				<input type="hidden" name="senderId" value="<%= challenge.getSenderID() %>">
				<input type="hidden" name="receiverId" value="<%= challenge.getRecieverID() %>">
			</div>
			<div class="spacer" style="clear: both;"></div>
		</div>
	</form>
		<% } %>
	</div>
</div>
</body>
</html>
