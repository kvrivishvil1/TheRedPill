var position = 0;
var slide = function(header) {
	$(header).next().slideToggle();
};
/**
 * connects server
 */

var randomizeOrder = function() {
	$.ajax({
		url : "QuizServlet",
		type : "get",
		data : {
			type : "randomize",
		},
		success : function(response) {
			if (response == "true") {
				if ($("#single-page").is(":checked")) {
					window.location = "single-page-quiz.jsp";
				} else {
					window.location = "multiple-page-quiz.jsp";
				}
			}
		}
	});
}
var addReviewInDb = function() {
	var text = $("#review_note").val();
	$.ajax({
		url : "QuizServlet",
		type : "get",
		data : {
			type : "addReview",
			text : text,
			numStars : 2
		},
		success : function(response) {
			if (response == "true") {
				$("#review-note-status").show();
				$("#review-note-status").html("<b>Thanks! Review Is Saved<b>");
				
			}
		}
	});
};

var addReportInDb = function() {
	var text = $("#report-note").val();
	$.ajax({
		url : "QuizServlet",
		type : "get",
		data : {
			type : "addReport",
			text : text
		},
		success : function(response) {
			if (response == "true") {
				$("#report-note-status").show();
				$("#report-note-status").html("<b>Thanks! Review Is Saved<b>");
				
			}
		}
	});
};

$(document).ready(function() {
	$(".form").hide();
	$(".review").click(function() {
		slide(this);
	});
	$(".report").click(function() {
		slide(this);
	});
	// start displaying quiz on click
	$("#start-quiz").click(function() {
		randomizeOrder();
	});
	$("#publish-review").click(function() {
		addReviewInDb();
	});
	$("#publish-report").click(function() {
		addReportInDb();
	});
});