
<%
	if (session.getAttribute("username") == null) {
		RequestDispatcher rd = request.getRequestDispatcher("index.html");
		rd.forward(request, response);
	}
%>
<%@page import="managers.MainManager"%>
<%@page import="java.util.HashMap"%>
<%@page import="db.dao.UserDao"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="CSS/normalize.css">
<link rel="stylesheet" href="CSS/friend-request.css">

<link rel="stylesheet" href="CSS/font-awesome.min.css">
<link rel="stylesheet" href="CSS/navigation.css">

<title>friend requests</title>
</head>
<body>
	
<div class="form clearfix">
		<section> <design  href="#home"> <a
			href="homepage.jsp"><i class="fa top fa-home" aria-hidden="true"></i></a> </design> </section>
		<section> <design href="#quiz"> <a href="quiz-form.jsp"><i
			class="fa top fa-server" aria-hidden="true"></i></a> </design> </section>
		<section> <design class="active" href="#friends-request">
		<div class="friend-request">
			<a  href="friend-request.jsp"><i class="fa fa-user-plus" aria-hidden="true"></i></a>
		</div>
		</design> </section>
		<section> <design  href="#friends"> <a
			href="friends.jsp"><i class="fa top fa-address-book-o"
			aria-hidden="true"></i></a> </design> </section>
		<section> <design href="#profile"> <a
			href="profile.jsp"><i class="fa fa-address-card"
			aria-hidden="true"></i></a> </design> </section>
		<section> <design  href="#messages"> <a
			href="conversation.jsp"><i class="fa top fa-envelope-open-o"
			aria-hidden="true"></i></a> </design> </section>
		<section> <design href="#log-out"> <a href="">
			<i class="fa fa-sign-out" aria-hidden="true"></i>
		</a> </design> </section>
	</div>
	
	
	





	<div class="container">
		<%
			ServletContext cont = request.getServletContext();
			MainManager mainManager = (MainManager) cont.getAttribute(MainManager.CONTEXT_ATTRIBUTE_NAME);
			String username = (String) request.getSession().getAttribute("username");
			int userID = mainManager.getAccountManager().getUserIdByUserName(username);
			HashMap<String, String> requests = mainManager.getAccountManager()
					.getAllUsernamesFromFriendRequestsForUser(userID);
		%>
		<%
			if (requests.keySet().size() == 0) {
		%>
		<div>
			<h2 class="title-form">There is no friend Requests</h2>
		</div>
		<%
			} else {
		%>
		<div>
			<h2 class="title-form">
				Respond to Your
				<%=requests.size()%>
				Friend Requests
			</h2>
		</div>
		<%
			}
		%>
		<div class="requests">

			<%
				for (String usernm : requests.keySet()) {
			%>
			<form class="form-line" action="FriendRequestsServlet" method="post">
				<div class="request-line clearfix">
					<div class="image-div">
						<img alt="" src="images/img.jpg" class="profile-image">
					</div>
					<div class="name-div">
						<a class="name" href="profile.jsp?showProfile=<%=usernm%>"><%=requests.get(usernm)%></a>
					</div>
					<div class="buttons">
						<div class="button top">
							<input type="submit" class="confirm input" name="act"
								value="Confirm">
						</div>
						<div class="button">
							<input type="submit" class="delete input" name="act"
								value="Delete Request">
						</div>

						<input type="hidden" name="username" value="<%=usernm%>">
					</div>
				</div>
			</form>
			<%
				}
			%>
		</div>
	</div>
</body>
</html>
