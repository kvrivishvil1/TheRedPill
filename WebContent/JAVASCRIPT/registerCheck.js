function firstLastFilled() {
	if(document.getElementsByClassName("first-name")[0].value==""||
			document.getElementsByClassName("last-name")[0].value=="") {
		document.getElementsByClassName("name-check")[0].innerHTML = "Both fields must be filled";
		
	} else if (document.getElementsByClassName("first-name")[0].value!="" &&
			document.getElementsByClassName("last-name")[0].value!="") {
		document.getElementsByClassName("name-check")[0].innerHTML = "";
	}
}

function usernameFilled() {
	if(document.getElementsByClassName("user-name")[0].value=="") {
		document.getElementsByClassName("username-check")[0].innerHTML = "Username field must be filled";
	} else {
		var nm = $('.user-name').val();
		console.log(nm);
		$.ajax({
			url: "RegisterServlet",
			type: "get",
			data: {
				type: "username",
				userName: nm
			},
			success: function(data){
				if(data == "true") {
					document.getElementsByClassName("username-check")[0].innerHTML = "";
				} else {
				}
			}
			
		});
	}
}

function emailFilled() {
	if(document.getElementsByClassName("email")[0].value=="") {
		document.getElementsByClassName("email-check")[0].innerHTML = "Email field must be filled";
	} else {
		console.log($('.email').val());
		$.ajax({
			url: "RegisterServlet",
			type: "get",
			data: {
				type: "email",
				email: $('.email').val()
			},
			success: function(data){
				if(data == "true") {
					document.getElementsByClassName("email-check")[0].innerHTML = "";
				} else {
					document.getElementsByClassName("email-check")[0].innerHTML = "User with this email is allready registered";
				}
			}
			
		});
	}
}

function passwordFilled() {
	if(document.getElementsByClassName("password")[0].value=="" ||
			document.getElementsByClassName("password-confirm")[0].value=="") {
		document.getElementsByClassName("password-check")[0].innerHTML = "Both fields must be filled";
	} else if(document.getElementsByClassName("password")[0].value !==
			document.getElementsByClassName("password-confirm")[0].value) {
		document.getElementsByClassName("password-check")[0].innerHTML = "Passwords doesn't match each other";
	} else {
		document.getElementsByClassName("password-check")[0].innerHTML = "";
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

function validate() {
	if(document.getElementsByClassName("day")[0].value == ""
			|| document.getElementsByClassName("user-name")[0].value == ""
			|| document.getElementsByClassName("first-name")[0].value == "" 
			|| document.getElementsByClassName("last-name")[0].value == ""
			|| document.getElementsByClassName("password")[0].value == ""
			|| document.getElementsByClassName("password-confirm")[0].value == "" 
			|| document.getElementsByClassName("year")[0].selectedIndex == 0 
			|| document.getElementsByClassName("month")[0].selectedIndex == 0
		) {
		alert("Fill every field");
	} else {
		document.getElementsById("registration").action="RegisterServlet" 
		document.getElementsById("registration").submit();
	}
}
