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
<link rel="stylesheet" href="CSS/friends.css">
</head>
<body>
<div class="content">
	<% 
		ServletContext cont = request.getServletContext();
		UserDao dao = (UserDao) cont.getAttribute(UserDao.CONTEXT_ATTRIBUTE_NAME);
		String username = (String)request.getSession().getAttribute("username");
		int userID = dao.getUserIdByUserName("kvrivishvil1");
		HashMap <String, String> friends = dao.getAllFriendsForUser(userID);
	%>
	<h2> You have <% friends.size();%> friends </h2>
		<div class="freinds">
			<% for(String usernm : friends.keySet()) { %>
			<div class="friend-line">
				<div class="image-div"> 
					<img alt="" src="IMG" class="profile-image">
				</div>
				<div class="name">
					<a href=""> <%= friends.get(usernm) %> </a>
				</div>
			</div>
			<% } %>
		</div>
</div>
</body>
</html>