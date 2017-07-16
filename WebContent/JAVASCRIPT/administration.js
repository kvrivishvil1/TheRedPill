/**
 * function slides content up and down
 */
var slide = function(header) {
	$(header).next().slideToggle();
};

/**
 * function connects to servlet and saves note info in database
 */
var validateNote = function(fieldElem) {
	if ($("#note-header").val().length == 0 || $("#note").val().length == 0) {
		alert("Both header and note are required");
	} else {
		$
				.ajax({
					url : "NoteServlet",
					type : "get",
					data : {
						type : "creation",
						header : $("#note-header").val(),
						note : $("#note").val()
					},
					success : function(response) {
						$("#note-status")
								.html(
										"<b>Thanks ! The Notification Will Be Displayed On Homepage<b>");
						$("#note-status").slideToggle();
					}
				});
	}
};
/**
 * function connects to the servlet removes account from database
 */
var removeAccount = function(buttonElem) {

	var usernameToDelete = $(buttonElem).parent().siblings(":first").children(
			":first").text();
	if (confirm("Delete User: " + usernameToDelete + " ?") == true) {
		$.ajax({
			url : "NoteServlet",
			type : "get",
			data : {
				type : "deletion",
				username : usernameToDelete
			},
			success : function(response) {
				location.reload();
			}
		});
	}

};
/**
 * function changes title of user to admin in database
 */
var promoteAdmin = function(buttonElem) {
	var usernameToPromote = $(buttonElem).parent().siblings(":first").children(
			":first").text();
	if (confirm("Promote User: " + usernameToPromote + " As Administrator?") == true) {
		$.ajax({
			url : "NoteServlet",
			type : "get",
			data : {
				type : "promotion",
				username : usernameToPromote
			},
			success : function(response) {
				location.reload();
			}
		});
	}

};
/**
 * functions connects to the servlet and works with json containing all users
 * started with given string
 */
var searchUser = function() {
	var searchedUser = $("#searched").val();
	if (searchedUser.length == 0) {
		alert("Cannot search an empty word")
	} else {
		$
				.ajax({
					url : "NoteServlet",
					type : "get",
					data : {
						type : "search",
						username : searchedUser
					},
					success : function(response) {
						$("table").empty();
						var str = JSON.stringify(eval('(' + response + ')'));
						var json = JSON.parse(str);
						var html = "";
						for (var i = 0; i < json.number; i++) {
							html += "<tr> <td>User: <p id = \"user-label\">"
									+ json.array[i]
									+ "</p></td><td><button class=\"remove-account\">remove account</button></td>"
									+ "<td><button class=\"promote-admin\">promote as admin</button></td></tr>";
						}
						$("table").html(html);

						$(".remove-account").on("click", function() {
							removeAccount(this);
						});
						$(".promote-admin").on("click", function() {
							promoteAdmin(this);
						});
					}
				});
	}
}
var removeQuiz = function(buttonElem) {
	var quizToDelete = $(buttonElem).parent().siblings(":first").children(
			":first").text();
	if (confirm("Delete Quiz: " + quizToDelete + " ?") == true) {
		$.ajax({
			url : "NoteServlet",
			type : "get",
			data : {
				type : "deletionQuiz",
				quiz : quizToDelete
			},
			success : function(response) {
				location.reload();
			}
		});
	}
}

var searchQuiz = function(buttonElem) {
	var searchedQuiz = $("#searched-quiz").val();
	if (searchedQuiz.length == 0) {
		alert("Cannot search an empty word")
	} else {
		$
				.ajax({
					url : "NoteServlet",
					type : "get",
					data : {
						type : "searchQuiz",
						quiz : searchedQuiz
					},
					success : function(response) {
						$("table").empty();
						var str = JSON.stringify(eval('(' + response + ')'));
						var json = JSON.parse(str);
						var html = "";
						for (var i = 0; i < json.number; i++) {
							html += "<tr> <td>Quiz: <p id = \"quiz-label\">"
									+ json.array[i]
									+ "</p></td><td><button class=\"remove-quiz\">Remove Quiz</button></td>"
									+ "<td><button class=\"clear-quiz-info\">Clear Quiz Info</button></td></tr>";
						}
						$("table").html(html);

						$(".remove-quiz").on("click", function() {
							removeQuiz(this);
						});
						$(".clear-quiz-info").on("click", function() {
							clearQuizInfo(this);
						});
					}
				});
	}
}
var clearQuizInfo = function(buttonElem) {
	var quizToClean = $(buttonElem).parent().siblings(":first").children(
			":first").text();
	if (confirm("Delete Quiz: " + quizToDelete + "'s History ?") == true) {
		$.ajax({
			url : "NoteServlet",
			type : "get",
			data : {
				type : "deletionQuizHistory",
				quiz : quizToclean
			},
			success : function(response) {
			}
		});
	}
}
$(document).ready(function() {
	$("#note-form").hide();
	$(".details").hide();
	// slides down information connected to the button clicked
	$("button").click(function() {
		slide(this);
	});
	// note publish button event
	$("#publish").click(function() {
		validateNote();
	});
	// remove account button event
	$(".remove-account").click(function() {
		removeAccount(this);
	});
	// promote as admin button event
	$(".promote-admin").click(function() {
		promoteAdmin(this);
	});
	// filter users by searched word
	$("#search-user").click(function() {
		searchUser();
	});
	$(".remove-quiz").click(function() {
		removeQuiz(this);
	});

	$("#search-quiz").click(function() {
		searchQuiz(this);
	});

	$(".clear-quiz-info").click(function() {
		clearQuizInfo(this);
	});
});