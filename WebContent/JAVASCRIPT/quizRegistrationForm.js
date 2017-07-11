function addNumbersInDropDown(element, name, lowerBound, upperBound){
	for(var i=lowerBound; i <= upperBound; i++){
		var option = document.createElement('option');
		var text = i + ' ' + name;
		console.log(text);
		option.text = text;
		option.value = i;
		element.options.add(option);
	}	
}

$(document).ready(function () {	
	addNumbersInDropDown(document.getElementById('hours'), 'hours',  0, 23);
	addNumbersInDropDown(document.getElementById('minutes'), 'mins', 0, 59);
	addNumbersInDropDown(document.getElementById('seconds'), 'secs', 0, 59);
});