var position = 0;
var count = $("#number").text();
/** navigating among questions shows next question */
var goToNextQuestion = function() {
	if (position < count - 1) {
		var currId = "#ques-" + position;
		$(currId).hide();
		position++;
		var showId = "#ques-" + position;
		$(showId).show();
		if (position == count - 1) {
			$("#submit-area").show();
		}
	}
};
/** navigating among questions shows previous question */
var goToPreviousQuestion = function() {
	if (position > 0) {
		var currId = "#ques-" + position;
		$(currId).hide();
		position--;
		var showId = "#ques-" + position;
		$(showId).show();
		$("#submit-area").hide();
	}
};

$(document).ready(function() {
	$("#number").hide();
	$(".question-container").hide();
	$("#submit-area").hide();
	$("#ques-0").show();
	if(count == 1){
		$("#submit-area").show();
	}
	$("#next_question").click(function() {
		goToNextQuestion();
	});

	$("#prev-question").click(function() {
		goToPreviousQuestion();
	});
});