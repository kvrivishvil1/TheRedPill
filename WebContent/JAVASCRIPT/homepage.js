var slide = function(header) {
	$(header).children(":nth-child(2)").slideToggle();
};

jQuery(document).ready(function($){
	$(".announcement-content-form").hide();
	$(".annoucement-form").click(function() {
		slide(this);
	});
});