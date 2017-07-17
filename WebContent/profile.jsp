<% if(session.getAttribute("username") == null) {
	RequestDispatcher rd = request.getRequestDispatcher("index.html");
 	rd.forward(request,response);
}
%>
<%@page import="managers.MainManager"%>
<%@ page import="db.dao.UserDao"%>
<%@ page import="db.bean.Person"%>
<%@page import="java.time.Year"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Profile</title>
<link rel="stylesheet" href="CSS/normalize.css">
<link rel="stylesheet" href="CSS/profile.css">
</head>

<body>
	<div>
		<%
			ServletContext cont = request.getServletContext();
			MainManager mainManager = (MainManager) cont.getAttribute(MainManager.CONTEXT_ATTRIBUTE_NAME);
			int userId;
			if(request.getParameter("showProfile") != null) {
				userId = mainManager.getAccountManager().getUserIdByUserName((String)request.getParameter("showProfile"));
			} else {
				userId = mainManager.getAccountManager().getUserIdByUserName((String)session.getAttribute("username"));
			}
			Person currentUser = mainManager.getAccountManager().getPersonByUserId(userId);
		%>
	</div>
	
	<div class ="container">
		<table>
			<th colspan="2">
				<div class = "image-form"> <img style="max-width:300px; height: auto; " alt="Avatar"
	 				src="http://www.iconninja.com/files/868/454/539/avatar-anonym-person-user-default-unknown-head-icon.svg"></div>		
			</th>
			
			<tr >
				<td class = ""> User Name:</td>
				<% if(request.getParameter("showProfile") == null) { %>
					<td><%= session.getAttribute("username") %></td>
				<% } else { %>
					<td><%= (String)request.getParameter("showProfile") %></td>
				<% } %>
			</tr>
			
			<tr>
				<td>First Name:</td>
				<td><%= currentUser.getName() %></td>
			</tr>
			
			<tr>
				<td>Last Name:</td>
				<td><%= currentUser.getLastName() %></td>
			</tr>
			
			<tr>
				<td>Date of birth:</td>
				<td><%= currentUser.getBirthDate() %></td>
			</tr>
			
			<tr>
				<td>Gender:</td>
				<td> <%= currentUser.getGender() %></td>
			</tr>
			
			<tr>
				<td>Email:</td>
				<td><a href="mailto:theredphill@freeuni.edu.ge"><%= currentUser.getEmail() %></a></td>
			</tr>
			
			<tr>
				<td>Status:</td>
				<td> user</td>
			</tr>
			
		</table>
		
	</div>	
		
	<div class="columns">
	  
	  <ul class="awards">
	    <li class="header">Quizzes you created</li>
	    <li class="number">0</li>
	    <li class="top">Top 3 quiz</li>
	    <li>first</li>
	    <li>second</li>
	    <li>third</li>
	    
	    <li class="header"><a href="#" class="button">See all</a></li>
	  </ul>
	</div>
	
	<div class="columns" style="margin-top: 8%;">
	  <ul class="awards">
	    <li class="header" style="background-color:	#523634">Achievement rank</li>
	    <li class="number" style="background-color:	hsl(36, 100%, 55%)">#0</li>
	    <li class="top">next achievements</li>
	    <li>achievement#1</li>
	    <li>achievement#2</li>
	    <li>achievement#3</li>
	    
	    <li class="header" style="background-color:	#523634">
	    	<a href="#" class="button" style="background-color: hsl(36, 100%, 55%) ">See all</a>
	    </li>
	  </ul>
	</div>
	
	<div class="columns">
	  <ul class="awards">
	    <li class="header">Quizzes you played</li>
	    <li class="number">0</li>
	    <li class="top">Top 3 quiz</li>
	    <li>first</li>
	    <li>second</li>
	    <li>third</li>
	    
	    <li class="header" ><a href="#" class="button" >See all</a></li>
	  </ul>
	</div>


</body>
</html>