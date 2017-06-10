<%@page import="java.time.Year"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Registration</title>
<link rel="stylesheet" href="CSS/normalize.css">
<link rel="stylesheet" href="CSS/main.css">
<script src ="JAVASCRIPT/registerCheck.js"></script>
</head>
<body>
<div class="container">
	<div class="registration-form">
		<form action="RegisterServlet" method="post">
			<div class="">
				<div class="formTitle">Name</div>
				<input type="text" name="firstname" class="first-name input-field" onfocusout="firstLastFilled()" placeholder="First">
				<input type="text" name="lastname " class="last-name input-field" onfocusout="firstLastFilled()" placeholder="Last">	
				<div class="name-check alert"></div>
			</div>
			<div class="form-line">
				<div class="formTitle">Choose your username</div>
				<input type="text" name="username" class="username input-field" onfocusout="usernameFilled()">
				<div class="username-check alert"></div>
			</div>
			<div class="form-line">
				<div class="formTitle">Create password</div>
				<input  type="password" name="password" class="password input-field" onfocusout="passwordFilled()" placeholder="password">
				<input  type="password" name="conpassword" class="password-confirm input-field" onfocusout="passwordFilled()" placeholder="Confirm">
				<div class="password-check alert"></div>
			</div>
	  		<div class="form-line">
	  			<div class="formTitle">Gender:</div>
	  			<input type="radio" name="gender" value="male" class="male"> Male
	  			<input type="radio" name="gender" value="female" class="female"> Female
	  			<div class="gender-check alert"></div>
	  		</div>
  			<div class="form-line">
  				<div class="formTitle">Birthday</div>
  				<input type="text" name="day" placeholder="Day" class="day" onfocusout="dayFilled()">
  				<select class="month select-field" onchange="dateFilled()">
  					<option selected disabled>Month</option>
  					<option value="january">January</option>
  					<option value="february">February</option>
  					<option value="march">March</option>
  					<option value="april">April</option>
  					<option value="may">May</option>
  					<option value="june">June</option>
  					<option value="july">July</option>
  					<option value="august">August</option>
  					<option value="september">September</option>
  					<option value="october">October</option>
  					<option value="november">November</option>
  					<option value="december">December</option>
  				</select>	
  				<select class="year select-field" onchange="dateFilled()">
  					<option selected disabled>Year</option>
  					<% for(int i=Year.now().getValue(); i>=Year.now().getValue()-100; i--) {
  						out.println("<option value=\"" + i + "\">" + i + "</option>"); 
  					}%>
  				</select>
				<div class="day-check alert"></div>
				<div class="month-check alert"></div>
				<div class="year-check alert"></div>
  			</div>
  			<div class="form-line">
  				<input type="submit" value="Create Account">
  			</div>
		</form>
	</div>
</div>
</body>
</html>