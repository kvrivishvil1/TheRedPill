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
			UserDao dao = (UserDao) cont.getAttribute(UserDao.CONTEXT_ATTRIBUTE_NAME);
			String username = (String)request.getSession().getAttribute("username");
			int userID = dao.getUserIdByUserName(username);
			HashMap <String, String> requests = dao.getAllUsernamesFromFriendRequestsForUser(userID);
		%>
		<h2> Respond to Your <%= requests.size() %> Friend Requests </h2>
		<div class="requests">
			<% for(String usernm : requests.keySet()) { %>
			<div class="request-line">
				<form class="form-line" action="FriendRequestsServlet" method="post">
					<div class="image-div">
						<img alt="" src="IMG" class="profile-image">
					</div>
					<span class="name"><%= requests.get(usernm) %></span>
					<div class="buttons">
						<input type="submit" class="confirm" name="act" value="Confirm">
						<input type="submit" class="delete" name="act" value="Delete Request">
						<input type="hidden" name="username" value="<%= usernm %>"> 
					</div>
				</form>
			</div>
			<% } %>
		</div>
	</div>
</body>
</html>
