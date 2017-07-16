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
<link rel="stylesheet" href="CSS/challenges.css">
</head>
<body>
<div class="content">
	<% 
		UserDao dao = new UserDao();
		MessageDao md = new MessageDao();
		String username = (String)request.getSession().getAttribute("username");
		int userID = dao.getUserIdByUserName("kvrivishvil1");
		HashSet<Challenge> challenges = md.getAllChallngesForUser(userID);
	%>
	<h2> You have <%= challenges.size() %> Challenges </h2>
	<div class="challenges">
		<% for(Challenge challenge : challenges) { %>
		<% 
			DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
			formatter.setLenient(false);
			Date date = challenge.getDate();
		%>
		<form class="challenge-line" action="challengeServlet" method="post">
			<div class="challenge">
				<div class="challenge-info">
					<div class="challenge-info-line">Challenge sender: <%= dao.getUsernameByUserId(challenge.getSenderID()) %> </div> 
					<div class="challenge-info-line">Sent: <%= formatter.format(date) %> </div>
					<div class="challenge-info-line">Max score: <%= challenge.getMaxScore() %> </div>
					<div class="challenge-info-line">Quiz name: <%= challenge.getQuizName() %></div>
				</div>
				<div class="challenge-buttons">
					<div class="button">
						<input type="submit" class="confirm" name="act" value="Start Quiz">
					</div>
					<div class="button">
						<input type="submit" class="delete" name="act" value="Delete Challenge">
					</div>
					<input type="hidden" name="quizName" value="<%= challenge.getQuizName() %>"> 
				</div>
			</div>
		</form>
		<% } %>
	</div>
</div>
</body>
</html>