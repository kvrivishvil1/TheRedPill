<!doctype html>
<%@page import="java.util.List"%>
<%@page import="db.dao.QuizDao"%>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script type="text/javascript" src="JAVASCRIPT/quizRegistrationForm.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body style="background-color:#DDC5A2">

	<div class="col-sm-2"></div>
	<div class="col-sm-8">
		<h2 style="text-align: center;">Quiz Registration Form</h2>
		<form action="QuizCreateServlet" method="post">
			<div class="form-group">
				<label>Name:</label> <input type="text" class="form-control"
					name="name" />
			</div>
			<label>Description:</label>
			<textarea class="form-control" rows="5" name="description"> </textarea>
			<label> Should the order of the questions be randomized? </label>
			<div class="radio">
				<label class="checkbox-inline"><input type="radio"
					name="randomize" value="true">Yes</label> <label
					class="checkbox-inline"><input type="radio"
					name="randomize" value="false" checked="checked">No</label>
			</div>
			<label> Should practice mode be allowed? </label>
			<div class="radio">
				<label class="checkbox-inline"><input type="radio"
					name="practice-mode" value="true">Yes</label> <label
					class="checkbox-inline"><input type="radio"
					name="practice-mode" value="false" checked="checked">No</label>
			</div>
			<label> Select the category of the quiz: <select
				name="category" >
				<option value="select-category">Select category</option>
			<%
				QuizDao quizDao = (QuizDao)request.getServletContext().getAttribute(QuizDao.CONTEXT_ATTRIBUTE_NAME);
				List<String> categoryNames = quizDao.getAllCategoryNames();
				for(int i = 0; i < categoryNames.size(); i++){
			%>
					<option value=<%= categoryNames.get(i) %>> <%= categoryNames.get(i)%></option>
					
			<%
				}
			%>
			</select>
			</label> <br>
			<div class="tags">
				<label> Tags (Each tag should start with hash('#') symbol
					and words inside tags should only be separated by underscore('_') symbol): </label> <input type="text"
					class="form-control" name="tags" />
			</div>
			<label> Time should be limited for the quiz? </label>
			<div class="radio">
				<label><input type="radio" name="time-limit" value="true">Yes
				<select name="hours" id="hours">
						<option value="0">Hours</option>
				</select>
					<select name="minutes" id="minutes">
						<option value="0">Minutes</option>
				</select>
				<select name="seconds" id="seconds">
						<option value="0">Seconds</option>
				</select>
				</label>
				<br> <label><input type="radio" name="time-limit"
					value="false" checked="checked">No</label>
			</div>
			<button style="float: right;" id="start" onclick=""
				class="btn btn-danger">Start</button>
		</form>
	</div>
	<div class="col-sm-2"></div>

</body>
</html>