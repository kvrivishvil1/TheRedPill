var countQuestions = $("#number").text();

var slide = function(header) {
	$(header).next().slideToggle();
};

var checkQuestionResponse = function(position, answerArea) {
	var answer = answerArea.val();
	$.ajax({
		url : "QuizServlet",
		type : "get",
		data : {
			type : "checkAnswer",
			position : position,
			async : false,
			answer : answer,
			precision : 90
		},
		success : function(response) {
		}
	});
};
var checkFillBlank = function(position, answerArea) {
	var answer = answerArea.val();
	$.ajax({
		url : "QuizServlet",
		type : "get",
		data : {
			type : "checkAnswer",
			position : position,
			async : false,
			answer : answer,
			precision : 90
		},
		success : function(response) {

		}
	});
};
var checkMultChoice = function(position, answerArea) {
	var answer = answerArea.attr("value");
	$.ajax({
		url : "QuizServlet",
		async : false,
		type : "get",
		data : {
			type : "checkAnswer",
			position : position,
			async : false,
			answer : answer,
			precision : 100
		},
		success : function(response) {

		}
	});
};
var checkPicResponse = function(position, answerArea) {
	var answer = answerArea.val();
	$.ajax({
		url : "QuizServlet",
		type : "get",
		data : {
			type : "checkAnswer",
			position : position,
			async : false,
			answer : answer,
			precision : 90
		},
		success : function(response) {

		}
	});
};
var checkMultAnswer = function(position, answerArea) {
	var answer = "";
	$(".multiple-answer").each(function() {
		answer += $(this).val() + "/";
	});
	$.ajax({
		url : "QuizServlet",
		type : "get",
		data : {
			type : "checkAnswer",
			position : position,
			async : false,
			answer : answer,
			precision : 90
		},
		success : function(response) {
			while (!response == "true") {
				var i = 1;
			}
		}
	});
};
var checkMultChoiceMultAnswer = function(position, answerArea) {
	var answer = "";
	answerArea.each(function() {
		answer += $(this).val() + "/";
	});
	$.ajax({
		url : "QuizServlet",
		type : "get",
		data : {
			type : "checkAnswer",
			async : false,
			position : position,
			answer : answer,
			precision : 100
		},
		success : function(response) {

		}
	});
};
var checkMatching = function(position) {
	var subQuestion = 0;

	$(".match-opt").each(function() {
		var answer = $(this).children().filter(":selected").val();
		$.ajax({
			url : "QuizServlet",
			type : "get",
			data : {
				type : "checkAnswerForMatching",
				async : false,
				position : position,
				answer : answer,
				precision : 100,
				subQuestion : subQuestion
			},
			success : function(response) {
				
			}
		});
		subQuestion++;
	});
};
var recordFinishState = function() {
	
	$.ajax({
		url : "QuizServlet",
		type : "get",
		data : {
			type : "finishState",
		},
		success : function(response) {
			if (response == "true") {
				window.location = "finished-quiz.jsp";
			}
		}
	});
}
$(document)
		.ready(
				function() {
					$("#number").hide();
					$("#submit")
							.click(
									function() {
										for (var i = 0; i < countQuestions; i++) {
											var className = $("#" + i).parent()
													.attr('class');
											if (className == "question-response") {
												checkQuestionResponse(i, $(
														"#" + i).children()
														.first());
											} else if (className == "fill-blank") {
												checkFillBlank(i, $("#" + i)
														.children().first());
											} else if (className == "mult-choice") {
												checkMultChoice(i, $("#" + i)
														.children().filter(
																":checked"));
											} else if (className == "pic-response") {
												checkPicResponse(i, $("#" + i)
														.children().first());
											} else if (className == "mult-answer") {
												checkMultAnswer(i, $("#" + i)
														.children());
											} else if (className == "mult-choice-mult-answer") {
												checkMultChoiceMultAnswer(i, $(
														"#" + i).children()
														.filter(":checked"));
											} else if (className == "matching") {
												checkMatching(i);
											}
										}
										recordFinishState();
									});
				});