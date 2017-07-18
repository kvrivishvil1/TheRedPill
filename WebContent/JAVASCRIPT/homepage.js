var slide = function(header) {
	$(header).children(":nth-child(2)").slideToggle();
};
var logout = function(){
	console.log("sss");
	$
	.ajax({
		url : "LogOutServlet",
		type : "get",
		data : {
			type : "logout",
		},
		success : function(response) {
			if(response == "true")
				window.location = "index.html";
		}
	});
}

function logout() {
	console.log("soso");
	document.getElementsById("logout").action="LogOutServlet"
	document.getElementsById("logout").submit();
}

jQuery(document).ready(function($){
	$(".announcement-content-form").hide();
	$(".annoucement-form").click(function() {
		slide(this);
	});
	$("#click-log-out").click(function() {
		logout();
	});
});
