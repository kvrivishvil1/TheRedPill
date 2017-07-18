<%@page import="db.bean.Account"%>
<%@page import="managers.MainManager"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>home page</title>
<link rel="stylesheet" href="CSS/normalize.css">
<link rel="stylesheet" href="CSS/homepage.css">
<link rel="stylesheet" href="CSS/font-awesome.min.css">
<script src="JAVASCRIPT/homepage.js"></script>
<script src="http://code.jquery.com/jquery-1.11.0.min.js"></script>
<script src="https://code.jquery.com/jquery.min.js"></script>
</head>
<body>
	<div class="form clearfix">
		<section> <design class="active" href="#home"> <a
			href=""><i class="fa top fa-home" aria-hidden="true"></i></a> </design> </section>
		<section> <design href="#quiz"> <a href="quiz-form.jsp"><i
			class="fa top fa-server" aria-hidden="true"></i></a> </design> </section>
		<section> <design href="#friends-request">
		<div class="friend-request">
			<a href="friend-request.jsp"><i class="fa fa-user-plus" aria-hidden="true"></i></a>
			<div class="triangle transform"></div>
			<div class="friend-request-display"><jsp:include
					page="friend-request-min.jsp"></jsp:include></div>
		</div>
		</design> </section>
		<section> <design href="#friends"> <a
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

	<div class="container clearfix">
		<div class="icon-form">
			<i class="fa fa-paint-brush  fa-5x" aria-hidden="true"> <a
				href="quizList.jsp?id=1">
					<p class="category-title-form">Art</p>
			</a>
			</i>
		</div>

		<div class="icon-form">
			<i class="fa fa-shopping-bag  fa-5x" aria-hidden="true"> <a
				href="quizList.jsp?id=2">
					<p class="category-title-form">Fashion</p>
			</a>
			</i>
		</div>

		<div class="icon-form">
			<i class="fa fa-futbol-o fa-5x" aria-hiddenF="true"> <a
				href="quizList.jsp?id=3">
					<p class="category-title-form">Sport</p>

			</a>
			</i>
		</div>
		<br>
		<div class="icon-form">
			<i class="fa fa-music  fa-5x" aria-hidden="true"> <a
				href="quizList.jsp?id=4">
					<p class="category-title-form">Music</p>
			</a>
			</i>
		</div>

		<div class="icon-form">
			<i class="fa fa-film fa-5x" aria-hidden="true"> <a
				href="quizList.jsp?id=5">
					<p class="category-title-form">Film</p>
			</a>
			</i>
		</div>


		<div class="icon-form">
			<i class="fa fa-users  fa-5x" aria-hidden="true"> <a
				href="quizList.jsp?id=6">
					<p class="category-title-form">Celebrities</p>
			</a>
			</i>
		</div>

		<br>
		<%
			ServletContext sc = request.getServletContext();
			MainManager mainManager = (MainManager) sc.getAttribute(MainManager.CONTEXT_ATTRIBUTE_NAME);
			HashMap<String, String> announcements = mainManager.getMessageManager().getAdminstrationNote();
			if (announcements != null && announcements.size() != 0) {
		%>

		<div class="quiz-section-form" style="margin-top: 5%;">Announcements</div>
		<br>
		<%
			for (String header : announcements.keySet()) {
		%>

		<div class="annoucement-form">
			<div class="header-form">
				<div class="title-form"><%=header%></div>
			</div>
			<div class="announcement-content-form"><%=announcements.get(header)%></div>
		</div>
		<%
			}
			}
		%>

		<%
			ArrayList<Integer> popularQuizzes = mainManager.getQuizManager().getPopularQuizIds();
			if (popularQuizzes != null) {
		%>


		<div class="quiz-section-form">Popular quizzes</div>
		<br>
		<%
			for (int i = 0; i < popularQuizzes.size(); i++) {
		%>

		<div class="annoucement-form">
			<div class="header-form">
				<div class="title-form"><%=mainManager.getQuizManager().getQuizName(popularQuizzes.get(i))%></div>
			</div>
			<div class="announcement-content-form">
				<div class="button" style="margin-left: 43px;">
					<a href=<%="display-quiz.jsp?quizId=" + popularQuizzes.get(i)%>><i
						class="top"> Start quiz </i> </a>
				</div>
				<div class="button"><a href=<%="quiz-summary.jsp?quizID=" + popularQuizzes.get(i)%>><i class="top">
				See quiz summary
					 </i></a></div>
			</div>
		</div>


		<%
			}
			}
		%>


		<%
			ArrayList<Integer> recentQuizzes = mainManager.getQuizManager().getRecentQuizzes();
			if (popularQuizzes != null) {
		%>

		<div class="quiz-section-form">Recently created quizzes</div>
		<br>
		<%
			for (int i = 0; i < recentQuizzes.size(); i++) {
		%>

		<div class="annoucement-form">
			<div class="header-form">
				<div class="title-form"><%=mainManager.getQuizManager().getQuizName(recentQuizzes.get(i))%></div>
			</div>
			<div class="announcement-content-form">
				<div class="button" style="margin-left: 43px;">
					<a href=<%="display-quiz.jsp?quizId=" + recentQuizzes.get(i)%>>
						<i class="top"> Start quiz </i>
					</a>
				</div>
				<div class="button"><a href=<%="quiz-summary.jsp?quizID=" + recentQuizzes.get(i)%>><i class="top">
				See quiz summary
					 </i></a></div>
			</div>
		</div>


		<%
			}
			}
		%>





		<%
			String username = (String) session.getAttribute("username");
			System.out.println(username);
			ArrayList<Integer> userQuizzes = mainManager.getQuizManager()
					.getMyQuizes(mainManager.getAccountManager().getUserIdByUserName(username));
			if (userQuizzes != null) {
		%>
		<div class="quiz-section-form">your quizzes history</div>
		<br>
		<%
			for (int i = 0; i < userQuizzes.size(); i++) {
		%>

		<div class="annoucement-form">
			<div class="header-form">
				<div class="title-form"><%=mainManager.getQuizManager().getQuizName(userQuizzes.get(i))%></div>
			</div>
			<div class="announcement-content-form">
				<div class="button" style="margin-left: 43px;">
					<a href=<%="display-quiz.jsp?quizId=" + userQuizzes.get(i)%>> <i
						class="top"> Start quiz </i>
					</a>
				</div>
				<div class="button"><a href=<%="display-quiz.jsp?quizId=" + userQuizzes.get(i)%>>
						<i class="top"> Start quiz </i> </a></div>
			</div>
		</div>


		<%
			}
			}
		%>

		<script src="JAVASCRIPT/homepage.js"></script>
		<script src="http://code.jquery.com/jquery-1.11.0.min.js"></script>
		<script src="https://code.jquery.com/jquery.min.js"></script>
</body>
</html>
