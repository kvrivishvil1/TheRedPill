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
<script src="https://code.jquery.com/jquery-1.10.2.js"></script>
<script src="JAVASCRIPT/registerCheck.js"></script>
</head>
<body>
	<div class="container">
		<div class="registration-form">
			<form id="registration" onsubmit="validate(); return false;"
				action="RegisterServlet" method="post">
				<div class="">
					<div class="formTitle">Name</div>
					<input type="text" name="firstname" class="first-name input-field"
						onfocusout="firstNameFilled()" placeholder="First name"> <input
						type="text" name="lastname" class="last-name input-field"
						onfocusout="lastNameFilled()" placeholder="Last name">

					<div class="first-name-check alert"></div>
					<div class="last-name-check alert"></div>
				</div>
				<div class="form-line">
					<div class="formTitle">Choose your username</div>
					<input type="text" name="username" class="user-name input-field"
						onfocusout="usernameFilled()" placeholder="Username">
					<div class="username-check alert"></div>
				</div>
				<div class="form-line">
					<div class="formTitle">Create password</div>
					<input type="password" name="password" class="password input-field"
						onfocusout="passwordFilled()" placeholder="Password"> 
					<div class="password-check alert"></div>
					<input type="password" name="conpassword" class="password-confirm input-field" 
						onfocusout="confirmFilled()" placeholder="Confirm">
					<div class="confirm-check alert"></div>
				</div>
				<div class="form-line">
					<div class="formTitle">Enter your email</div>
					<input type="text" name="email" class="email input-field"
						onfocusout="emailFilled()" placeholder="Email">
					<div class="email-check alert"></div>
				</div>
				<div class="form-line">
					<div class="formTitle">
						Gender: <input type="radio" name="gender" value="male"
							class="male" checked>
						<div class="formGender">Male</div>
						<input type="radio" name="gender" value="female" class="female">
						<div class="formGender">Female</div>
					</div>
					<div class="gender-check alert"></div>
				</div>
				<div class="form-line">
					<div class="formTitle">Birthday</div>
					<input type="text" name="day" placeholder="Day" class="day"
						onfocusout="correctDate()"> <select
						class="month select-field" name="month" onchange="correctDate()">
						<option selected disabled>Month</option>
						<option value="1">January</option>
						<option value="2">February</option>
						<option value="3">March</option>
						<option value="4">April</option>
						<option value="5">May</option>
						<option value="6">June</option>
						<option value="7">July</option>
						<option value="8">August</option>
						<option value="9">September</option>
						<option value="10">October</option>
						<option value="11">November</option>
						<option value="12">December</option>
					</select> <select class="year select-field" name="year"
						onchange="correctDate()">
						<option selected disabled>Year</option>
						<%
							for (int i = Year.now().getValue(); i >= Year.now().getValue() - 100; i--) {
								out.println("<option value=\"" + i + "\">" + i + "</option>");
							}
						%>
					</select>
					<div class="day-check alert"></div>
				</div>
				<div class="form-line">
					<input type="submit" class="submit" value="Create account">
				</div>
			</form>
		</div>
	</div>
</body>
</html>
