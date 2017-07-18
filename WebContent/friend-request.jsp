<% if(session.getAttribute("username") == null) {
	RequestDispatcher rd = request.getRequestDispatcher("index.html");
 	rd.forward(request,response);
}%>
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
<title>Insert title here</title>
</head>
<body>
	<div class="container">
		<% 
			ServletContext cont = request.getServletContext();
			MainManager mainManager = (MainManager) cont.getAttribute(MainManager.CONTEXT_ATTRIBUTE_NAME);
			String username = (String)request.getSession().getAttribute("username");
			int userID = mainManager.getAccountManager().getUserIdByUserName(username);
			HashMap <String, String> requests = mainManager.getAccountManager().getAllUsernamesFromFriendRequestsForUser(userID);
		%>
		<div class="requests">
		<h2> Respond to Your <%= requests.size() %> Friend Requests </h2>
			<% for(String usernm : requests.keySet()) { %>
			<form class="form-line" action="FriendRequestsServlet" method="post">
				<div class="request-line clearfix">
					<div class="image-div">
						<img alt="" src="images/img.jpg" class="profile-image">
					</div>
					<div class="name-div">
						<a class="name" href="profile.jsp?showProfile=<%= usernm%>"><%= requests.get(usernm) %></a>
					</div>
					<div class="buttons">
						<div class="button top">
							<input type="submit" class="confirm input" name="act" value="Confirm">
						</div>
						<div class="button">
							<input type="submit" class="delete input" name="act" value="Delete Request">
						</div>
						
						<input type="hidden" name="username" value="<%= usernm %>"> 
					</div>
				</div>
			</form>
			<% } %>
		</div>
	</div>
</body>
</html>
