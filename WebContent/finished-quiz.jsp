
<%
	if (session.getAttribute("username") == null) {
		RequestDispatcher rd = request.getRequestDispatcher("index.html");
		rd.forward(request, response);
	}
%>
<%@page import="managers.AchievementManager"%>
<%@page import="managers.MainManager"%>
<%@page import="db.dao.QuizDao"%>
<%@page import="db.bean.quiz.QuizAttempt"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<link
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"
	rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="CSS/quiz.css">
<link rel="stylesheet" href="CSS/font-awesome.min.css">
<link rel="stylesheet" href="CSS/navigation.css">

<title>Quiz Done</title>
</head>
<body>
	
	
	
	<div class="form clearfix">
		<section> <design  href="#home"> <a
			href="homepage.jsp"><i class="fa top fa-home" aria-hidden="true"></i></a> </design> </section>
		<section> <design href="#quiz"> <a href="quiz-form.jsp"><i
			class="fa top fa-server" aria-hidden="true"></i></a> </design> </section>
		<section> <design href="#friends-request">
		<div class="friend-request">
			<a href="friend-request.jsp"><i class="fa fa-user-plus" aria-hidden="true"></i></a>
		</div>
		</design> </section>
		<section> <design  href="#friends"> <a
			href="friends.jsp"><i class="fa top fa-address-book-o"
			aria-hidden="true"></i></a> </design> </section>
		<section> <design href="#profile"> <a
			href="profile.jsp"><i class="fa fa-address-card"
			aria-hidden="true"></i></a> </design> </section>
		<section> <design href="#messages"> <a
			href="conversation.jsp"><i class="fa top fa-envelope-open-o"
			aria-hidden="true"></i></a> </design> </section>
		<section> <design href="#log-out"> <a href="">
			<i class="fa fa-sign-out" aria-hidden="true"></i>
		</a> </design> </section>
	</div>
	
	
	
	
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
	<div id="review-container">
		<button class="review">Write Quiz Review</button>
		<div class="form">
			<textarea class="form-control" id="review_note" rows="5"
				placeholder="Review Text"></textarea>
			<br>
			<button id="publish-review">Submit Review</button>
			<p id="review-note-status"></p>
		</div>
	</div>
	<div id="report-container">
		<button class="report">Report Quiz</button>
		<div class="form">
			<textarea class="form-control" id="report-note" rows="5"
				placeholder="Comment Report"></textarea>
			<br>
			<button id="publish-report">Submit Report</button>
			<p id="report-note-status"></p>
		</div>
	</div>
	<script src="JAVASCRIPT/jquery-1.9.1.min.js"></script>
	<script src="JAVASCRIPT/display-quiz.js"></script>

</body>
</html>