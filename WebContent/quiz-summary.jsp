<% if(session.getAttribute("username") == null) {
	RequestDispatcher rd = request.getRequestDispatcher("index.html");
 	rd.forward(request,response);
}%>
<%@page import="managers.MainManager"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="db.dao.QuizDao"%>
<%@page import="java.time.Year"%>
<%@page import="java.util.Date"%>
<%@ page import="db.bean.quiz.Quiz"%>
<%@ page import="db.bean.Review"%>
<%@ page import="helpers.DataCouple"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Quiz Summary</title>
<link rel="stylesheet" href="CSS/normalize.css">
<link rel="stylesheet" href="CSS/quiz-summary.css">

<style>
div.scrolForm {
	width: 300px;
	height: 400px;
	background: silver;
	color: black;
	margin-left: 28%;
	margin-top: 10%;
	border-radius: 25px;
	overflow: scroll;
}
</style>
</head>
<body>

	<div>

		<%
			ServletContext sc = request.getServletContext();
			int quizID = (int) session.getAttribute("quizID");
			String quizName = (String) session.getAttribute("quizID");
			MainManager mainManager = (MainManager) sc.getAttribute(MainManager.CONTEXT_ATTRIBUTE_NAME);
			String quizDescription = mainManager.getQuizManager().getQuizDescription(quizID);
			ArrayList<DataCouple> lastPerformances = mainManager.getQuizManager().getLastPerformances(quizID);
			ArrayList<DataCouple> highestPerformsOrderByDate = mainManager.getQuizManager()
					.getLastPerformsOrderByDate(quizID);
			ArrayList<DataCouple> highestPerformsOrderByScore = mainManager.getQuizManager()
					.getLastPerformsOrderByScore(quizID);
			ArrayList<DataCouple> highestPerformsOrderByTime = mainManager.getQuizManager()
					.getLastPerformsOrderByTime(quizID);
			ArrayList<DataCouple> topPerformancesToday = mainManager.getQuizManager().getTopPerformancesToday(quizID);
			ArrayList<DataCouple> RecentPerformances = mainManager.getQuizManager().getRecentPerformances(quizID);
		%>

	</div>

	<div class="title-bar">
		<section>
		<design class="active" href="#home">Home</design></section>
		<section>
		<design href="#quiz">Quiz</design></section>
		<section>
		<design href="#friends">Friends</design></section>
		<section>
		<design href="#messages">Messages</design></section>
		<section>
		<design href="#achievements">Achievements</design></section>
	</div>

	<h2 class="nameForm">
		quiz name :
		<%=mainManager.getQuizManager().getQuizName(quizID)%>
	</h2>
	<h3 class="creatorForm">
		quiz creator:
		<%=mainManager.getQuizManager().getQuizCreator(quizID)%>
	</h3>

	<div class="container">
		<button class="sliderButton" style="margin-left: 10px"
			onclick="plusDivs(-1)">&#10094;</button>
		<button class="sliderButton" style="margin-left: 90%"
			onclick="plusDivs(1)">&#10095;</button>


		<div class="mySlides">
			<div class="section">
				<div class="sectionTitleForm">Description</div>
				<div class="sectionTextForm">
					<%=quizDescription%>
				</div>
			</div>
		</div>
		<div class="mySlides">
			<div class="section">
				<div class="sectionTitleForm">Past performances</div>
				<div class="sectionTextForm">
					<%
						if (lastPerformances.size() == 0) {
							out.println("Nothing to show");
						} else {
					%>
					<div id="table-wrapper">
						<div id="table-scroll">
							<table>
								<thead>
									<tr>
										<th>User name</th>
										<th>Score</th>
									</tr>
								</thead>

								<tbody>
									<%
										for (int i = 0; i < lastPerformances.size(); i++) {
												out.println("<tr> <td>" + lastPerformances.get(i).getUsername() + "</td>  <td> "
														+ lastPerformances.get(i).getScore() + "</td>  </tr>");
											}
										}
									%>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>





		<div class="mySlides">
			<div class="section">
				<div class="sectionTitleForm">Highest performances ordered by
					score</div>
				<div class="sectionTextForm">
					<%
						if (lastPerformances.size() == 0) {
							out.println("Nothing to show");
						} else {
					%>
					<div id="table-wrapper">
						<div id="table-scroll">
							<table>
								<thead>
									<tr>
										<th>User name</th>
										<th>Score</th>
									</tr>
								</thead>

								<tbody>
									<%
										for (int i = 0; i < highestPerformsOrderByScore.size(); i++) {
												out.println("<tr> <td>" + highestPerformsOrderByScore.get(i).getUsername() + "</td>  <td> "
														+ highestPerformsOrderByScore.get(i).getScore() + "</td>  </tr>");
											}
										}
									%>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="mySlides">
			<div class="section">
				<div class="sectionTitleForm">Highest performances ordered by
					date</div>
				<div class="sectionTextForm">
					<%
						if (lastPerformances.size() == 0) {
							out.println("Nothing to show");
						} else {
					%>
					<div id="table-wrapper">
						<div id="table-scroll">
							<table>
								<thead>
									<tr>
										<th>User name</th>
										<th>Score</th>
									</tr>
								</thead>

								<tbody>
									<%
										for (int i = 0; i < highestPerformsOrderByDate.size(); i++) {
												out.println("<tr> <td>" + highestPerformsOrderByDate.get(i).getUsername() + "</td>  <td> "
														+ highestPerformsOrderByDate.get(i).getScore() + "</td>  </tr>");
											}
										}
									%>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="mySlides">
			<div class="section">
				<div class="sectionTitleForm">Highest performances ordered by
					time</div>
				<div class="sectionTextForm">
					<%
						if (lastPerformances.size() == 0) {
							out.println("Nothing to show");
						} else {
					%>
					<div id="table-wrapper">
						<div id="table-scroll">
							<table>
								<thead>
									<tr>
										<th>User name</th>
										<th>Score</th>
									</tr>
								</thead>

								<tbody>
									<%
										for (int i = 0; i < highestPerformsOrderByTime.size(); i++) {
												out.println("<tr> <td>" + highestPerformsOrderByTime.get(i).getUsername() + "</td>  <td> "
														+ highestPerformsOrderByTime.get(i).getScore() + "</td>  </tr>");
											}
										}
									%>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>


		<div class="mySlides">
			<div class="section">
				<div class="sectionTitleForm">Top performances today</div>
				<div class="sectionTextForm">
					<%
						if (topPerformancesToday.size() == 0) {
							out.println("Nothing to show");
						} else {
					%>
					<div id="table-wrapper">
						<div id="table-scroll">
							<table>
								<thead>
									<tr>
										<th>User name</th>
										<th>Score</th>
									</tr>
								</thead>

								<tbody>
									<%
										for (int i = 0; i < topPerformancesToday.size(); i++) {
												out.println("<tr> <td>" + topPerformancesToday.get(i).getUsername() + "</td>  <td> "
														+ topPerformancesToday.get(i).getScore() + "</td>  </tr>");
											}
										}
									%>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>



		<div class="mySlides">
			<div class="section">
				<div class="sectionTitleForm">Resent Performances</div>
				<div class="sectionTextForm">
					<%
						if (RecentPerformances.size() == 0) {
							out.println("Nothing to show");
						} else {
					%>
					<div id="table-wrapper">
						<div id="table-scroll">
							<table>
								<thead>
									<tr>
										<th>User name</th>
										<th>Score</th>
									</tr>
								</thead>

								<tbody>
									<%
										for (int i = 0; i < RecentPerformances.size(); i++) {
												out.println("<tr> <td>" + RecentPerformances.get(i).getUsername() + "</td>  <td> "
														+ RecentPerformances.get(i).getScore() + "</td>  </tr>");
											}
										}
									%>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>




		<div class="mySlides">
			<div class="section">
				<div class="sectionTitleForm">statistic</div>

				<%
					int maxScore = mainManager.getQuizManager().getQuizMaxScore(quizID);
					int first = 0;
					int second = 0;
					int third = 0;
					int fourth = 0;
					int fifth = 0;

					for (int i = 0; i < lastPerformances.size(); i++) {
						int score = lastPerformances.get(i).getScore();
						if (score <= maxScore * 20 / 100) {
							first++;

						} else if (score <= maxScore * 40 / 100) {
							second++;
						}
						if (score <= maxScore * 60 / 100) {
							third++;
						}
						if (score <= maxScore * 80 / 100) {
							fourth++;
						}
						if (score <= maxScore) {
							fifth++;
						}
					}
					int allScore = first + second + third + fourth + fifth;
				%>
				<ul class="chart">
					<li>
						<div class="span" style="height: <%=first * 100 / allScore%>%"></div>
						<div class="chartTextForm">0-20</div>
					</li>

					<li>
						<div class="span" style="height: <%=second * 100 / allScore%>%"></div>
						<div class=chartTextForm>20-40</div>

					</li>
					<li>
						<div class="span" style="height: <%=third * 100 / allScore%>%"></div>
						<div class="chartTextForm">40-60</div>
					</li>
					<li>
						<div class="span" style="height: <%=fourth * 100 / allScore%>%"></div>
						<div class="chartTextForm">60-80</div>
					</li>
					<li>
						<div class="span" style="height: <%=fifth * 100 / allScore%>%"></div>
						<div class="chartTextForm">80-100</div>
					</li>
				</ul>

			</div>
		</div>


	</div>


<div class = "reviews">
	
	<%
	
	for(Review  review : mainManager.getQuizManager().getQuizReviews(quizID)){
	%>
	<br>
		<div class  = "review-form"><%= review.getText() %> 
			<div class = "review-info-form"> username : <%= mainManager.getAccountManager().getUsernameByUserId(review.getAccountId()) %> </div>
			<div class = "review-info-form"> date : <%= review.getDate() %></div>
		</div>
	
	<%}%>
	
	<%

		for (int i = 0; i < mainManager.getQuizManager().getQuizStars(quizID); i++) {
			if( i == 0 ){
	%>
		 <i class="fa fa-star fa-5x" aria-hidden="true" style = "color : hsl(51, 100%, 55%); margin-left: 30%;"></i>
		
	<%}else{%>
	 	<i class="fa fa-star fa-5x" aria-hidden="true" style = "color : hsl(51, 100%, 55%); margin-left: 10px;"></i>
	<%} }%>

</div>
<br>



	<div>
		<button class="button" style="margin-left: 6%; margin-top: 20px">
			Edit quiz</button>
		<button class="button" style="margin-left: 30%; margin-top: 20px">
			Start quiz</button>
		<button class="button" style="margin-left: 30%; margin-top: 20px">
			practice mode</button>

	</div>


	<script>
		var slideIndex = 1;
		showDivs(slideIndex);

		function plusDivs(n) {
			showDivs(slideIndex += n);
		}

		function showDivs(n) {
			var i;
			var x = document.getElementsByClassName("mySlides");
			if (n > x.length) {
				slideIndex = 1
			}
			if (n < 1) {
				slideIndex = x.length
			}
			for (i = 0; i < x.length; i++) {
				x[i].style.display = "none";
			}
			x[slideIndex - 1].style.display = "block";

		}
	</script>

</body>
</html>