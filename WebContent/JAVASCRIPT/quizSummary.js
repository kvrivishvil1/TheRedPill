var slide = function(header) {
	$(header).children(":nth-child(1)").slideToggle();
	$(header).children(":nth-child(2)").slideToggle();
};

jQuery(document).ready(function($){
	$(".review-info-form").hide();
	$(".review-form").click(function() {
		slide(this);
	});
});