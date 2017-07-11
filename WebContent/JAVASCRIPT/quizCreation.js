function addNumbersInDropDown(element, symbol, lowerBound, upperBound) {
	for (var i = lowerBound; i <= upperBound; i++) {
		$('.'+element).each(function() {
			$(this).append($('<option>', {
				value : i,
				text : i + ' ' + symbol
			}));
		});
	}
}

$(document).ready(function() {
    addNumbersInDropDown("hours", 'hours', 0, 23);
    addNumbersInDropDown("minutes", 'mins', 0, 59);
    addNumbersInDropDown("seconds", 'secs', 0, 59);
    
    var previous;

	$('#types').on('focus', function() {
		previous = this.value;
	}).change(function() {
		$( '#question-form').empty();
		if(this.value != '0'){
			var note = $('#question-note').html();
			$( '#question-form').append(note);
			var limit = $('#time-limit').html()
			$( '#question-form').append(limit);
		}
		var form = $('#' + this.value).html();
		$( '#question-form').append(form);
		previous = this.value;
	});
        
    $(document).on('click', '.add-button', function() {
		console.log("moved");
		var clone = $('#question-form').find('.single-answer').last().clone();
		var value = parseInt(clone.find('input:hidden').val())+1;
		clone.find('input:hidden').val(value);
		clone.find('input:text').val("").end().appendTo('#question-form');
	});
    
    $(document).on('click', '.add-couple-button', function(){
		var type = $('#types').find(":selected").val();
		var	coupleClone = $('#question-form').find('.single-couple').last().clone();
		var clone = $('#question-form').find('.single-answer').last().clone();
		var answerValue = String.fromCharCode(clone.find('.answer-label').last().text().charCodeAt(0)+1);
		clone.find('.answer-label').text(answerValue + '.');
		coupleClone.find('.question-label').text(answerValue + '.');
		coupleClone.find('.option-label').text((parseInt(coupleClone.find('.option-label').text())+1) + '.');
		coupleClone.find('input:text').val("").end().appendTo($('#question-form').find('.couples'));
		clone.find('input:text').val("").end().appendTo($('#question-form').find('.answers'));		
	});
    
    $(document).on('click', '.remove-button', function() {
    	if( $('#question-form').find('.single-answer').length > 1 )
    		$(this).closest('.single-answer').remove();
    });
    
    $(document).on('click', '.remove-last-couple-button', function() {
    	if( $('#question-form').find('.single-couple').length > 1 ){
    		$('#question-form').find('.single-couple').last().remove();
    		$('#question-form').find('.single-answer').last().remove();
    	}
    });
});
