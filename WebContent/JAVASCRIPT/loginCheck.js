function validate() {
	if (document.getElementsByClassName("form-control")[0].value == ""
			|| document.getElementsByClassName("form-control")[1].value == "") {
		alert("Every Field Is Required");
	} else {

		$.ajax({
			url : "LoginServlet",
			type : "get",
			data : {
				username : $(".username").val(),
				password : $(".password").val()
			},
			success : function(response) {
				var str = JSON.stringify(eval('('+response+')'));
				var json = JSON.parse(str);
				if (json.success) {
					window.location = json.location;
				} else {
					$(".user-check").html(
					"<b>Incorrect Username or Password<b>"); 
				}
			}
		});
	}
}
