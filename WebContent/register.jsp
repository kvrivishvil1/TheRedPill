<%@page import="java.time.Year"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Registration</title>
<link rel="stylesheet" href="CSS/main.css">
</head>
<body>
<div class="container">
	<div class="registration-form">
		<form action="RegisterServlet" method="post">
			<div class="">
				<div class="formTitle">Name</div>
				<input type="text" name="firstname" placeholder="First">
				<input type="text" name="lastname" placeholder="Last">	
				<div class="nameCheck"></div>
			</div>
			<div class="form-line">
				<div class="formTitle">Choose your username</div>
				<input type="text" name="username" size="46">
				<div class="usernameCheck"></div>
			</div>
			<div class="form-line">
				<div class="formTitle">Create password</div>
				<input  type="password" name="password" placeholder="password">
				<input  type="password" name="conpassword" placeholder="Confirm">
				<div class="passwordCheck"></div>
			</div>
	  		<div class="form-line">
	  			<div class="formTitle">Gender:</div>
	  			<input type="radio" name="gender" value="male" checked> Male
	  			<input type="radio" name="gender" value="female"> Female<br>
	  		</div>
  			<div class="form-line">
  				<div class="formTitle">Birthday</div>
  				<input type="text" name="day" placeholder="Day" size="1">
  				<select class="month">
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
  				<select>
  					<option selected disabled>Year</option>
  					<% for(int i=1900; i<Year.now().getValue()+1; i++) {
  						out.println("<option value=\"" + i + "\">" + i + "</option>"); 
  					}%>
  				</select>
				<div class="bdayCheck"></div>
  			</div>
  			<div class="form-line">
  				<input type="submit" value="Sign Up">
  			</div>
		</form>
	</div>
</div>
</body>
</html>