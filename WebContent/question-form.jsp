<% if(session.getAttribute("username") == null) {
	RequestDispatcher rd = request.getRequestDispatcher("index.html");
 	rd.forward(request,response);
}%>
<%@ page import="db.bean.quiz.Quiz"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<script src="https://code.jquery.com/jquery-1.10.2.js"></script>
<script type="text/javascript" src="JAVASCRIPT/quizCreation.js"></script>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="CSS/normalize.css">
<link rel="stylesheet" href="CSS/question-form.css">
</head>
<style>
textarea {
	resize: none;
}
</style>
<body>

	<div class="col-sm-3"></div>
	<div class="container">
		<form action="AddQuestionServlet" method="post">
			<div class="question-container">
				<div class="form-inline">
					<div class="number-form" style="margin-left: 85%;">
						Question#<%=((Quiz) request.getSession().getAttribute("quiz")).getNumQuestions() + 1%>
					</div>

					<br> <select class="select-form" name="question-type"
						id="types">
						<option value="0">Please select Question Type</option>
						<option value="1">Question-Response</option>
						<option value="2">Fill in the Blank</option>
						<option value="3">Multiple Choice</option>
						<option value="4">Picture-Response Questions</option>
						<option value="5">Multi-Answer Questions</option>
						<option value="6">Multiple Choice with Multiple Answers</option>
						<option value="7">Matching</option>
					</select>
				</div>

				<div id="question-form">
					<p class="section-title-form">Please select question type</p>
				</div>

				<button id="finish" type="submit" name="act" value="finish"
					class="button">Finish</button>

				<button id="next" type="submit" name="act" value="next"
					class="button" style="margin-left: 10px;">Next Question</button>


			</div>
		</form>

		<div id="question-note" style="display: none;">
			<div class="form-inline">
				<input class="area-form" placeholder="Note" type="text"
					class="form-control" name="note"
					style="overflow: hidden; word-wrap: break-word; resize: none; height: 80px; width: 700px;">
			</div>
			<br>
		</div>




		<div id="time-limit" style="display: none;">
			<label class="section-text-form"> Time for this question is
				limited <input type="checkbox" name="time-limited" /> <select
				class="hours" name="hours">
					<option value="">Hours</option>
			</select> <select class="minutes" name="minutes">
					<option value="">Minutes</option>
			</select> <select class="seconds" name="seconds">
					<option value="">Seconds</option>
			</select>
			</label>
		</div>





		<div id="0" style="display: none;">
			<p style="text-align: center;">Please select question type</p>
		</div>





		<div id="1" style="display: none;">
			<div class="parsing">
				<label class="section-text-form">Symbol for parsing: <select
					name="parsing" class="select-field ">
						<option value="">Select</option>
						<option value="/">/</option>
						<option value=",">,</option>
						<option value="_">_</option>
						<option value="-">-</option>
				</select>
				</label>
			</div>


			<br>
			<div class="question">
				<input class="area-form" placeholder="Question" type="text"
					name="question-text"
					style="overflow: hidden; word-wrap: break-word; resize: none; height: 80px; width: 700px;">
			</div>

			<br>
			<div class="answers">
				<div class="single-answer">
					<input class="area-form" placeholder="Answer" type="text"
						name="answer-text"
						style="overflow: hidden; word-wrap: break-word; resize: none; height: 80px; width: 700px;">

				</div>
			</div>



		</div>







		<div id="2" style="display: none;">
			<div class="parsing">

				<label class="section-text-form"> Symbol for parsing: <select
					name="parsing" class="select-field ">
						<option value="">Select</option>
						<option value="/">/</option>
						<option value=",">,</option>
						<option value="_">_</option>
						<option value="-">-</option>
				</select>

				</label>
			</div>
			<br>
			<div class="question">
				<input class="area-form" placeholder="Question" type="text"
					name="question-text"
					style="overflow: hidden; word-wrap: break-word; resize: none; height: 80px; width: 700px;">
			</div>

			<br>
			<div class="answers">
				<div class="single-answer">
					<input class="area-form" placeholder="Answer" type="text"
						name="answer-text"
						style="overflow: hidden; word-wrap: break-word; resize: none; height: 80px; width: 700px;">

				</div>
			</div>
		</div>








		<div id="3" style="display: none;">
			<div class="question">
				<input class="area-form" placeholder="Question" type="text"
					name="question-text"
					style="overflow: hidden; word-wrap: break-word; resize: none; height: 80px; width: 700px;">
				<br>
				<button type="button" class="add-button button"
					style="margin-left: auto">Add Another Answer</button>
			</div>


			<div class="answers">
				<div class="single-answer">
					<input type="radio" name="answer-option" value="1"> <input
						type="hidden" name="option-id" value="1"> <input
						type="text" name="option-text" class="area-form"
						placeholder="Option"
						style="width: 60%; height: 60px; padding-left: 10px;">
					<button type="button" class="remove-button  button"
						style="width: 25%; margin-left: 5px;" name="remove-button">Remove
						Answer</button>
				</div>
			</div>
		</div>





		<div id="4" style="display: none;">
			<div class="parsing">
				<label class="section-text-form"> Symbol for parsing: <select
					name="parsing" class="select-field ">
						<option value="">Select</option>
						<option value="/">/</option>
						<option value=",">,</option>
						<option value="_">_</option>
						<option value="-">-</option>
				</select>
				</label>
			</div>
			<div class="question">
				<input class="area-form" placeholder="Picture link" type="text"
					name="question-tex"
					style="overflow: hidden; word-wrap: break-word; resize: none; height: 80px; width: 700px;">
			</div>
			<div class="answers">
				<div class="single-answer">
					<input class="area-form" placeholder="Answer" type="text"
						name="answer-text"
						style="overflow: hidden; word-wrap: break-word; resize: none; height: 80px; width: 700px;">
				</div>
			</div>
		</div>




		<div id="5" style="display: none;">
			<div class="parsing">
				<label class="section-text-form"> Symbol for parsing: <select
					name="parsing" class="select-field ">
						<option value="">Select</option>
						<option value="/">/</option>
						<option value=",">,</option>
						<option value="_">_</option>
						<option value="-">-</option>
				</select>
				</label>
			</div>
			<div class="answer-order">
				<label class="section-text-form"> Order of the answers is
					important and they should be listed as entered below <input
					type="checkbox" name="order-sensitive" />
				</label>
			</div>

			<div class="question">
				<input class="area-form" placeholder="Question" type="text"
					name="question-text"
					style="overflow: hidden; word-wrap: break-word; resize: none; height: 80px; width: 700px;">
				<br>
				<button type="button" class="add-button button"
					style="margin-left: auto">Add Another Answer</button>
			</div>


			<div class="answers">
				<div class="single-answer">
					<input class="area-form" placeholder="Answer" type="text"
						name="answer-text"
						style="width: 60%; height: 60px; padding-left: 10px;">
					<button type="button" class="remove-button  button"
						style="width: 25%; margin-left: 5px;" name="remove-button">Remove
						Answer</button>
				</div>
			</div>
		</div>




		<div id="6" style="display: none;">
			<br>
			<div class="question">
				<input class="area-form" placeholder="Question" type="text"
					name="question-text"
					style="overflow: hidden; word-wrap: break-word; resize: none; height: 80px; width: 700px;">
				<br>
				<button type="button" class="add-button button"
					style="margin-left: auto">Add Another Answer</button>
			</div>


			<div class="answers">
				<div class="single-answer">
					<input type="checkbox" name="answer-option" value="1"> <input
						type="hidden" name="option-id" value="1"> <input
						class="area-form" placeholder="Answer" type="text"
						name="answer-text"
						style="width: 60%; height: 60px; padding-left: 10px;">

					<button type="button" class="remove-button  button"
						style="width: 25%; margin-left: 5px;" name="remove-button">Remove
						Answer</button>
				</div>
			</div>
		</div>




		<div id="7" style="display: none;">
			<br>
			<button type="button" class="add-couple-button button"
				style="width: 30%; margin-left: auto;">Add Another Couple</button>


			<button type="button" class="remove-last-couple-button button"
				style="margin-left: 10px; width: 30%">Remove Last Couple</button>
			<div class="couples">
				<div class="single-couple">
					<label class="question-label"> A. </label> <input type="text"
						class="area-form" name="question-text" placeholder="Question"
						style="overflow: hidden; word-wrap: break-word; resize: none; padding-left: 10px;">

					<label class="option-label">1. </label> <input type="text"
						name="option-text" class="area-form" placeholder="Option"
						style="overflow: hidden; word-wrap: break-word; resize: none; padding-left: 10px;">
				</div>
			</div>

			<br>

			<div class="answers form-inline">
				<div class="single-answer" style="display: inline;">
					<label class="answer-label">A.</label> <input type="text"
						name="answer-option" class="area-form"
						style="overflow: hidden; word-wrap: break-word; resize: none; padding-left: 20px; padding-right: 20px; padding-top: 20px; padding-bottom: 20px; width: 80px">
				</div>
			</div>
		</div>


	</div>

	<div class="col-sm-3"></div>
</body>
</html>