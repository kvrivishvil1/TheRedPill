<% if(session.getAttribute("username") == null) {
	RequestDispatcher rd = request.getRequestDispatcher("index.html");
 	rd.forward(request,response);
}%>
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

<link rel="stylesheet" href="CSS/normalize.css">
<link rel="stylesheet" href="CSS/quiz-form.css">
</head>
<body>

	
	<div class="container">
		<h2 class="title-form">Quiz Registration Form</h2>
		<form action="QuizCreateServlet" method="post">

			<div class="form-group">

				<textarea placeholder="Enter the name" id="text" type="text"
					name="name" rows="5"
					style="overflow: hidden; word-wrap: break-word; resize: none; height: 80px;"></textarea>

			</div>

			<div class="form-group">

				<textarea placeholder="Enter the description" id="text"
					name="description" rows="5"
					style="overflow: hidden; word-wrap: break-word; resize: none; height: 160px;"></textarea>

			</div>


			<div class="form-group">

				<textarea name="tags"
					placeholder="Tags (Each tag should start with hash('#') symbol and words inside tags should only be separated by underscore('_') symbol):"
					id="text" rows="5"
					style="overflow: hidden; word-wrap: break-word; resize: none; height: 80px;"></textarea>

			</div>


			<div class="checkbox-inline" style="margin-left: 32%;">
				<label class="section-title-form"> Should the order of the
					questions be randomized? </label>
					
				<div class="radio-form" style="margin-left: 27%">
					<input type="radio" class="radio-option" id="yes-option-first"
						name="randomize" value="true">
					 <input type="radio" checked  class="radio-option" id="no-option-first" 
					 	name="randomize" value="false" checked="checked"> 
					 	
					 	<label  for="yes-option-first"><p>Yes</p></label>
					 	 <label for="no-option-first"><p>No</p></label>
					 	 
					<div class="radio-option-slider"></div>
				</div>	
			</div>


			<div class="checkbox-inline" style="margin-left: 39%;">
				<label class="section-title-form"> Should practice mode be allowed? </label>
				<div class="radio-form" style="margin-left: 12%">
				
					<input type="radio" class="radio-option" id="yes-option-second"
						name="practice-mode" value="true"> 
					<input type="radio" checked class="radio-option" id="no-option-second"
						name="practice-mode" value="false" checked="checked"> 
						
						<label for="yes-option-second"><p>Yes</p></label> 
						<label for="no-option-second"><p>No</p></label>
						
					<div class="radio-option-slider"></div>
				</div>
			</div>


			<label class="section-title-form" style="margin-left: 42%;">
				Select the category of the quiz:</label><br> 
				<select
					class="select-field" name="category" style="margin-left: 47%;">
					<option value="select-category">Select category</option>
					<%
						QuizDao quizDao = (QuizDao) request.getServletContext().getAttribute(QuizDao.CONTEXT_ATTRIBUTE_NAME);
						List<String> categoryNames = quizDao.getAllCategoryNames();
						for (int i = 0; i < categoryNames.size(); i++) {
					%>
					<option value=<%=categoryNames.get(i)%>>
						<%=categoryNames.get(i)%></option>
					<%
						}
					%>
			</select>


			<div class="checkbox-inline" style="margin-left: 39%;">
				<label class="section-title-form"> Time should be limited for the quiz? </label>
				<div class="radio-form" style="margin-left: 12%">
					<input type="radio" class="radio-option" id="yes-option-third"
						name="time-limit" value="true"> 
					<input type="radio" checked class="radio-option" id="no-option-third"
						name="time-limit" value="false" checked="checked"> 
						
						<label	for="yes-option-third"><p>Yes</p></label> 
						<label for="no-option-third"><p>No</p></label>
						
					<div class="radio-option-slider"></div>
				</div>
				
				<select class="select-field" name="hours" id="hours">
					<option value="0">Hours</option>
				</select> 
				
				<select class="select-field" name="minutes" id="minutes">
					<option value="0">Minutes</option>
					
				</select> 
				
				<select class="select-field" name="seconds" id="seconds">
					<option value="0">Seconds</option>
				</select>
				
			</div>
			<br>
			<button type="submit" id="start" class="button">Start</button>

		</form>
	</div>
	

</body>
</html>






























