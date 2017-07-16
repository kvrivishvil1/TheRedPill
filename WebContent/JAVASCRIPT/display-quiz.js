var position = 0;
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

$(document).ready(function() {
	// start displaying quiz on click
	$("#start-quiz").click(function() {
		randomizeOrder();
	});
});