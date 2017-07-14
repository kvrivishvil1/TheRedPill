function firstNameFilled() {
	if($('.first-name').val() == "") {
		$('.first-name-check').html("Fill first name field");
	} else {
		$('.first-name-check').html("");
	}
}

function lastNameFilled() {
	if($('.last-name').val() == "") {
		$('.last-name-check').html("Fill last name field");
	} else {
		$('.last-name-check').html("");
	}
}

function usernameFilled() {
	var username = $('.user-name').val();
	if(username == "") {
		$('.username-check').html("Username field must be filled");
	} else {
		$.ajax({
			url: "RegisterServlet",
			type: "get",
			data: {
				type: "username",
				userName: username
			},
			success: function(data){
				if(data == "true") {
					$('.username-check').html("");
				} else {
					$('.username-check').html("User with this username is allready registered");
				}
			}
			
		});
	}
}

function emailFilled() {
	var email = $('.email').val();
	if(email == "") {
		$('.email-check').html("Email field must be filled");
	} else {
		$.ajax({
			url: "RegisterServlet",
			type: "get",
			data: {
				type: "email",
				email: email
			},
			success: function(data){
				if(data == "true") {
					$('.email-check').html("");
				} else {
					$('.email-check').html("User with this email is allready registered");
				}
			}
			
		});
	}
}

function passwordValidate() {
	console.log("soso");
	var upLetters= new RegExp('[A-Z]');
	var lowLetters= new RegExp('[a-z]');
	var nums = new RegExp('[0-9]');
	var password = $('.password').val();
	console.log(upLetters);
	console.log(lowLetters);
	console.log(nums);
	if(!(password.match(upLetters) && password.match(lowLetters) && password.match(nums) && password.length >= 6)) {
		$('.confirm-check').html("password must contain lower and uppercase letters, numbers and must contain at least 6 characters");
	}
}

function passwordFilled() {
	if($('.password').val() == "") {
		$('.password-check').html("this field must be filled");
	} else {
		$('.password-check').html("");
		$('.confirm-check').html("");
		if($('.password-confirm') != "") {
			if($('.password').val() == $('.password-confirm').val()) {
				console.log("ae");
				passwordValidate();
			} else {
				$('.confirm-check').html("Passwords must match each other");
			}
		} else {
			$('.confirm-check').html("this field must be filled");
		}
	}
}

function confirmFilled() {
	if($('.password-confirm').val() == "") {
		$('.confirm-check').html("this field must be filled");
	} else {
		$('.password-check').html("");
		$('.confirm-check').html("");
		if($('.password') != "") {
			if($('.password').val() == $('.password-confirm').val()) {
				console.log("oe")
				passwordValidate();
			} else {
				$('.confirm-check').html("Passwords must match each other");
			}
		} else {
			$('.password-check').html("this field must be filled");
		}
	}
}

function helper(index, date) {
	if(index != 0) {
		if(index == 1 || index == 3 || index == 5 || index == 7 || index == 8 || index == 10 || index == 12) {

			if(date < 1 || date > 31) {
				document.getElementsByClassName("day-check")[0].innerHTML = "insert correct date";
				return;
			}
		} else if(index == 4 || index == 6|| index == 9 || index == 11) {
			if(date < 1 || date > 30) {
				document.getElementsByClassName("day-check")[0].innerHTML = "insert correct date";
				return;
			} 
		} else {
			var year = document.getElementsByClassName("year")[0]
			if(year.selectedIndex != 0) {
				var y = year.options[year.selectedIndex].text;
				if(parseInt(y) % 4 == 0) {
					if(date < 1 || date > 29) {
						document.getElementsByClassName("day-check")[0].innerHTML = "insert correct date";
						return;
					}
				} else {
					if(date < 1 || date > 28) {
						document.getElementsByClassName("day-check")[0].innerHTML = "insert correct date";
						return;
					}
				}
			} else {
				if(date < 1 || date > 29) {
					document.getElementsByClassName("day-check")[0].innerHTML = "insert correct date";
					return;
				}
			}
		}
	}
	document.getElementsByClassName("day-check")[0].innerHTML = "";
}

function correctDate() {
	if(document.getElementsByClassName("day")[0].value=="") {
		document.getElementsByClassName("day-check")[0].innerHTML = "Day field must be filled";
		return;
	}
	var date = parseInt(document.getElementsByClassName("day")[0].value);
	
	if (isNaN(date)) {
		document.getElementsByClassName("day-check")[0].innerHTML = "insert correct date";
	} else {
		var e = document.getElementsByClassName("month")[0];
		helper(e.selectedIndex, date);
	}
}


//function lastValidation() {
//	var username = $('.user-name').val();			
//	$.ajax({
//		url: "RegisterServlet",
//		type: "get",
//		data: {
//			type: "username",
//			userName: username
//		},
//		success: function(data){
//			if(data == "true") {
//				$.ajax({
//					url: "RegisterServlet",
//					type: "get",
//					data: {
//						type: "email",
//						email: email
//					},
//					success: function(data){
//						if(data == "true") {
//
//						} else {
//							$('.email-check').html("User with this email is allready registered");
//						}
//					}
//					
//				});
//			} else {
//				$('.username-check').html("User with this username is allready registered");
//			}
//		}
//			
//	});
//}

function validate() {
	if($('.user-name').val() == "" || $('.first-name').val() == "" || $('.last-name').val() == "" ||
			$('.password').val() == "" || $('.password-confirm').val() == "" || $('.email').val() == "" || 
			$('.day-check').val != "" || $('.gender-check').val != "" || $('.email-check') != "" || 
			$('.confirm-check') != "" || $('.password-check') != "" || $('.username-check').val() != "" || 
			$('.first-name-check') != "" || $('.last-name-check') != "") { 
		alert("Fill every field");
	} else {
		//lastValidation();
		document.getElementsById("registration").action="RegisterServlet" 
		document.getElementsById("registration").submit();
	}
}
