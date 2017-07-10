<%@page import="db.dao.UserDao"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="JAVASCRIPT/administration.js"></script>
<script src="https://code.jquery.com/jquery.min.js"></script>
<link
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"
	rel="stylesheet" type="text/css" />
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="CSS/administration.css">
<title>Registered Users</title>
</head>
<body>
	<%
		ServletContext context = request.getServletContext();
		UserDao userDataAccess = (UserDao) context.getAttribute(UserDao.CONTEXT_ATTRIBUTE_NAME);
		ArrayList<String> allUsers = userDataAccess.getAllUsernames();
	%>
	<div class="container">
		<div class="input-group">
			<input id="searched" type="text" class="form-control"
				placeholder="Search for User"> <span class="input-group-btn">
				<button id="search-user" class="btn btn-secondary" type="button">Search
					User</button>
			</span>
		</div>
		<table>
			<%
				for (String currentUser : allUsers) {
			%>
			<tr>
				<td>User:
				<p id = "user-label"><%=currentUser%></p>
				</td>
				<td><button class="remove-account">remove account</button></td>
				<td><button class="promote-admin">promote as admin</button></td>
			</tr>
			<%
				}
			%>
		</table>
	</div>
	<script src="JAVASCRIPT/jquery-1.9.1.min.js"></script>
	<script src="JAVASCRIPT/administration.js"></script>
</body>
</html>