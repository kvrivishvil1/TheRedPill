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
</head>
<style>
textarea {
	resize: none;
}
</style>
<body style="background-color: #DDC5A2">

	<div class="col-sm-3"></div>
	<div class="col-sm-6" style="vertical-align: middle;">
		<form action="AddQuestionServlet" method="post">
			<div class="form-inline">
				<p style="display: inline-block;">
					<%=((Quiz) request.getSession().getAttribute("quiz")).getNumQuestions() + 1%>.
				</p>
				<select class="form-control" name="question-type" id="types">
					<option value="0">Select Question Type</option>
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
				<p style="text-align: center;">Please select question type</p>
			</div>

			<div id="question-note" style="display: none;">
				<div class="form-inline">
					<label> Note: <input type="text" class="form-control"
						name="note" size="60">
					</label>
				</div>
			</div>

			<button id="finish" type="submit" name="act" value="finish"
				style="float: right;" class="btn btn-danger">Finish</button>
			<button id="next" type="submit" name="act" value="next"
				style="float: right;" class="btn btn-danger">Next Question</button>
		</form>

		<div id="time-limit" style="display: none;">
			<label> Time for this question is limited <input
				type="checkbox" name="time-limited" /> <select class="hours" name="hours">
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
				<label> Symbol for parsing: <select name="parsing">
						<option value="">Select</option>
						<option value="/">/</option>
						<option value=",">,</option>
						<option value="_">_</option>
						<option value="-">-</option>
				</select>
				</label>
			</div>
			<div class="question">
				<label>Question: <input type="text" name="question-text"
					size="50">
				</label>
			</div>
			<div class="answers">
				<div class="single-answer">
					<label> Answer: <input type="text" name="answer-text"
						size="40">
					</label>
				</div>
			</div>
		</div>

		<div id="2" style="display: none;">
			<div class="parsing">
				<label> Symbol for parsing: <select name="parsing">
						<option value="">Select</option>
						<option value="/">/</option>
						<option value=",">,</option>
						<option value="_">_</option>
						<option value="-">-</option>
				</select>
				</label>
			</div>
			<div class="question">
				<label>Question: <input type="text" name="question-text"
					size="50"></label>
			</div>
			<div class="answers">
				<div class="single-answer">
					<label> Answer: <input type="text" name="answer-text"
						size="40"></label>
				</div>
			</div>
		</div>

		<div id="3" style="display: none;">
			<div class="question">
				<label>Question: </label><input type="text" name="question-text"
					size="50">
				<button type="button" class="add-button btn btn-danger">Add
					Another Answer</button>
			</div>
			<div class="answers">
				<div class="single-answer">
					<input type="radio" name="answer-option" value="1"> <input
						type="hidden" name="option-id" value="1"> <input
						type="text" name="option-text" size="62">
					<button type="button" class="remove-button  btn btn-danger"
						name="remove-button">Remove Answer</button>
				</div>
			</div>
		</div>

		<div id="4" style="display: none;">
			<div class="parsing">
				<label> Symbol for parsing: <select name="parsing">
						<option value="">Select</option>
						<option value="/">/</option>
						<option value=",">,</option>
						<option value="_">_</option>
						<option value="-">-</option>
				</select>
				</label>
			</div>
			<div class="question">
				<label>Picture Link: <input type="text" name="question-text"
					size="50"></label>
			</div>
			<div class="answers">
				<div class="single-answer">
					<label> Answer: <input type="text" name="answer-text"
						size="40">
					</label>
				</div>
			</div>
		</div>

		<div id="5" style="display: none;">
			<div class="parsing">
				<label> Symbol for parsing: <select name="parsing">
						<option value="">Select</option>
						<option value="/">/</option>
						<option value=",">,</option>
						<option value="_">_</option>
						<option value="-">-</option>
				</select>
				</label>
			</div>
			<div class="answer-order">
				<label> Order of the answers is important and they should be
					listed as entered below <input type="checkbox"
					name="order-sensitive" />
				</label>
			</div>
			<div class="question">
				<label>Question: <input type="text" name="question-text"
					size="40">
					<button type="button" class="add-button btn btn-danger">Add Another
						Answer</button></label>
			</div>
			<div class="answers">
				<div class="single-answer">
					<label> Answer: <input type="text" name="answer-text"
						size="40">
						<button type="button" class="remove-button btn btn-danger" name="remove-button">Remove
							Answer</button>
					</label>
				</div>
			</div>
		</div>

		<div id="6" style="display: none;">
			<label>Question: </label><input type="text" name="question-text"
				size="50">
			<button type="button" class="add-button btn btn-danger">Add Another Answer</button>
			<div class="answers">
				<div class="single-answer">
					<input type="checkbox" name="answer-option" value="1"> <input
						type="hidden" name="option-id" value="1"> <input
						type="text" name="option-text" size="62">
					<button type="button" class="remove-button btn btn-danger" name="remove-button">Remove
						Answer</button>
				</div>
			</div>
		</div>

		<div id="7" style="display: none;">
			<button type="button" class="add-couple-button btn btn-danger">Add Another
				Couple</button>
			<button type="button" class="remove-last-couple-button btn btn-danger">Remove
				Last Couple</button>
			<div class="couples">
				<div class="single-couple">
					<label class="question-label"> A. </label> <input type="text"
						name="question-text" size="30"> <label
						class="option-label">1. </label> <input type="text"
						name="option-text" size="30">
				</div>
			</div>
			<br>
			<div class="answers form-inline">
				<div class="single-answer" style="display: inline;">
					<label class="answer-label">A.</label> <input type="text"
						name="answer-option" size="2">
				</div>
			</div>
		</div>

	</div>

	<div class="col-sm-3"></div>
</body>
</html>