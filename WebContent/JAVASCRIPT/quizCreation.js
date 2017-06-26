var count = 0; 

function fillQuestionDivForQuestionResponse(){
    var mainTag = document.getElementById("question");

	var spec = document.createElement("div");
  	spec.setAttribute("id", "question-response");
    
    mainTag.appendChild(spec);
        
    var questionLabel = document.createElement("label");
    questionLabel.innerHTML = "Question: ";
    
    var questionArea = document.createElement("textarea");
    questionArea.setAttribute("rows", "1");
    questionArea.setAttribute("cols", "70");
    
    var answerLabel = document.createElement("label");
    answerLabel.innerHTML = "Answer: ";
    
    var answerArea = document.createElement("textarea");
    answerArea.setAttribute("rows", "1");
    answerArea.setAttribute("cols", "70");
    
    spec.appendChild(questionLabel);
    spec.appendChild(questionArea);
    spec.appendChild(document.createElement("br"));
    spec.appendChild(answerLabel);
    spec.appendChild(answerArea);
}

function fillQuestionDivForFillBlank(){
	var mainTag = document.getElementById("question");
    
    var spec = document.createElement("div");
    spec.setAttribute("id", "fill-blank");
    
    mainTag.appendChild(spec);
    
    var questionLabel = document.createElement("label");
    questionLabel.innerHTML = "Question: ";
    
  	var questionArea = document.createElement("textarea");
    questionArea.setAttribute("rows", "1");
    questionArea.setAttribute("cols", "70");
    
    var answerLabel = document.createElement("label");
    answerLabel.innerHTML = "Answer: ";
    
    var answerArea = document.createElement("textarea");
    answerArea.setAttribute("rows", "1");
    answerArea.setAttribute("cols", "70");
    
    spec.appendChild(questionLabel);
    spec.appendChild(questionArea);
    spec.appendChild(document.createElement("br"));
    spec.appendChild(answerLabel);
    spec.appendChild(answerArea);
    
}

function fillQuestionDivForMultiAnswer(){
	var mainTag = document.getElementById("question");
    
    var spec = document.createElement("div");
    spec.setAttribute("id", "multi-answer");
    
    mainTag.appendChild(spec);
    
    var questionLabel = document.createElement("label");
    questionLabel.innerHTML = "Question: ";
    
  	var questionArea = document.createElement("textarea");
    questionArea.setAttribute("rows", "1");
    questionArea.setAttribute("cols", "70");
    
    spec.appendChild(questionLabel);
    spec.appendChild(questionArea);
    spec.appendChild(createSingleField(true));
}

function fillQuestionDivForPictureResponse(){
	var mainTag = document.getElementById("question");
       
    var spec = document.createElement("div");
    spec.setAttribute("id", "picture-response");
    
    mainTag.appendChild(spec);
    
    spec.appendChild(createPictureResponse());
}

function removePictureResponse(id){
	var str = "picture" + id.substr(16);
	var picture = document.getElementById(str);
    picture.parentNode.removeChild(picture);
}

function addPictureResponse(id){
	var answers = document.getElementById("picture-response");
    answers.appendChild(createPictureResponse());
}

function createPictureResponse(){
	count++;
	var picture = document.createElement("div");
    var id = "picture" + count; 
    picture.setAttribute("id", id);
    
 	var questionLabel = document.createElement("label");
    questionLabel.innerHTML = "Picture link: ";
    
    var questionArea = document.createElement("textarea");
    questionArea.setAttribute("rows", "1");
    questionArea.setAttribute("cols", "70");
        
    picture.appendChild(questionLabel);
    picture.appendChild(questionArea);
          
    var answerArea = document.createElement("textarea");
    answerArea.setAttribute("id", "answerArea" + count);
    answerArea.setAttribute("rows", "1");
    answerArea.setAttribute("cols", "40");
        
    picture.appendChild(answerArea);   
    
    var addButton = document.createElement("button");
    addButton.setAttribute("id", "answerAddButt" + count);
    addButton.addEventListener('click', function(){
    	addPictureResponse(this.id);
    });
    var addButtonText = document.createTextNode("Add Another Picture");
  	addButton.appendChild(addButtonText);
    
    var removeButton = document.createElement("button");
    removeButton.setAttribute("id", "answerRemoveButt" + count);
    removeButton.addEventListener('click', function(){
    	removePictureResponse(this.id);
    });
    var removeButtonText = document.createTextNode("Remove Picture");
  	removeButton.appendChild(removeButtonText);
    
 	picture.appendChild(addButton);
    picture.appendChild(removeButton);
    
    return picture;  
}


function createSingleField(isMulti){
	count++;
	var field = document.createElement("div");
    var id = "answer" + count; 
    field.setAttribute("id", id);
      
    var area = document.createElement("textarea");
    area.setAttribute("id", "answerArea" + count);
    area.setAttribute("rows", "1");
    area.setAttribute("cols", "40");
        
    field.appendChild(area);    
    var addButton = document.createElement("button");
    addButton.setAttribute("id", "answerAddButt" + count);
    addButton.addEventListener('click', function(){
    	if(isMulti){
    		addAnotherSingleField(true);
        } else {
        	addAnotherSingleField(false);
        }
    });
    var addButtonText = document.createTextNode("Add Another Answer");
  	addButton.appendChild(addButtonText);
    
    var removeButton = document.createElement("button");
    removeButton.setAttribute("id", "answerRemoveButt" + count);
    removeButton.addEventListener('click', function(){
    	removeSingleChoiceField(this.id);
    });
    var removeButtonText = document.createTextNode("Remove Answer");
  	removeButton.appendChild(removeButtonText);
    
  	field.appendChild(addButton);
  	field.appendChild(removeButton);
    
    return field;  
}

function fillQuestionDivForMultipleChoice(){
	var div = document.getElementById("question");
       
    var spec = document.createElement("div");
    spec.setAttribute("id", "multiple-choice");
    
    var area = document.createElement("textarea");
    area.setAttribute("rows", "1");
    area.setAttribute("cols", "50");
    
    div.appendChild(spec);
    
    spec.appendChild(area);
    spec.appendChild(createSingleChoiceField(true));
}

function fillQuestionDivForMultipleChoiceAnswer(){
	var mainTag = document.getElementById("question");
       
    var spec = document.createElement("div");
    spec.setAttribute("id", "multiple-choice-answer");
    
    var area = document.createElement("textarea");
    area.setAttribute("rows", "1");
    area.setAttribute("cols", "50");
    
    mainTag.appendChild(spec);
    
    spec.appendChild(area);
    spec.appendChild(createSingleChoiceField(false));
}

function addAnotherSingleField(isMulti){
	var div;
	if(isMulti){
		div = document.getElementById("multi-answer");
    }
    if(!isMulti) {
    	div = document.getElementById("picture-response");
    }
    div.appendChild(createSingleField(true));
}

function addAnotherSingleAnswer(){
	var answers = document.getElementById("multiple-choice");
    answers.appendChild(createSingleChoiceField(true));
}

function addAnotherMultiAnswer(){
	var answers = document.getElementById("multiple-choice-answer");
    answers.appendChild(createSingleChoiceField(false));
}

function removeSingleChoiceField(id){
	var str = "answer" + id.substr(16);
	var answer = document.getElementById(str);
    answer.parentNode.removeChild(answer);
}

function createSingleChoiceField(isSingle){
	count++;
	var answer = document.createElement("div");
    var id = "answer" + count; 
    answer.setAttribute("id", id);
    
	var inp = document.createElement("input");
    if(isSingle){
    	inp.setAttribute("type", "radio");
    } else {
    	inp.setAttribute("type", "checkbox");
    }
    inp.setAttribute("name", "answer");
    
    var area = document.createElement("textarea");
    area.setAttribute("id", "answerArea" + count);
    area.setAttribute("rows", "1");
    area.setAttribute("cols", "30");
        
    answer.appendChild(inp);
    answer.appendChild(area);    
    var addButton = document.createElement("button");
    addButton.setAttribute("id", "answerAddButt" + count);
    if(isSingle){
   		addButton.setAttribute("onClick", "addAnotherSingleAnswer()");
    } else {
   		addButton.setAttribute("onClick", "addAnotherMultiAnswer()");
    }
    var addButtonText = document.createTextNode("Add Another Answer");
  	addButton.appendChild(addButtonText);
    
    var removeButton = document.createElement("button");
    removeButton.setAttribute("id", "answerRemoveButt" + count);
    removeButton.addEventListener('click', function(){
    	removeSingleChoiceField(this.id);
    });
    var removeButtonText = document.createTextNode("Remove Answer");
  	removeButton.appendChild(removeButtonText);
    
 	answer.appendChild(addButton);
    answer.appendChild(removeButton);
    
    return answer;    
}

function fillQuestionDivForMatching(){
	var mainTag = document.getElementById("question");
       
    var spec = document.createElement("div");
    spec.setAttribute("id", "matching");
       
    var addCoupleButton = document.createElement("button");
    addCoupleButton.setAttribute("id", "addCouple" + count);
    addCoupleButton.addEventListener('click', function(){
    	addCouple();
    });
    var addCoupleText = document.createTextNode("Add Another Couple");
  	addCoupleButton.appendChild(addCoupleText);
    
    var removeCoupleButton = document.createElement("button");
    removeCoupleButton.setAttribute("id", "removeCouple" + count);
    removeCoupleButton.addEventListener('click', function(){
    	removeCouple(count);
    });
    var removeCoupleText = document.createTextNode("Remove Last Couple");
  	removeCoupleButton.appendChild(removeCoupleText);
    
    spec.appendChild(addCoupleButton);
    spec.appendChild(removeCoupleButton);
    
    mainTag.appendChild(spec);
    
    var correctAnswersBar = document.createElement("div");
    correctAnswersBar.id = "correctAnswers";
    
	var firstQuestion = document.createElement("label");  
    firstQuestion.id = "questionNum" + 1;
    firstQuestion.innerHTML = "A ";
    correctAnswersBar.appendChild(firstQuestion);
    
    var firstAnswer = document.createElement("textarea");
    firstAnswer.setAttribute("id", "answerNum" + count);
    firstAnswer.setAttribute("rows", "1");
    firstAnswer.setAttribute("cols", "3");
    correctAnswersBar.appendChild(firstAnswer);
    
    mainTag.appendChild(document.createElement("br"));
    mainTag.appendChild(correctAnswersBar);
    
    spec.appendChild(createCouple());
}

function addCouple(){
	var div = document.getElementById("matching");
    div.appendChild(createCouple());
    var answers = document.getElementById("correctAnswers");
 
	var newQuestion = document.createElement("label");  
    newQuestion.id = "questionNum" + count;
    newQuestion.innerHTML = " " + String.fromCharCode(64+count) + " ";
    answers.appendChild(newQuestion);
    
    var newAnswer = document.createElement("textarea");
    newAnswer.setAttribute("id", "answerNum" + count);
    newAnswer.setAttribute("rows", "1");
    newAnswer.setAttribute("cols", "3");
    answers.appendChild(newAnswer);
}

function removeCouple(lastId){
	var str = "answer" + lastId
	var fields = document.getElementById(str);
    fields.parentNode.removeChild(fields);
    
    var quest = "questionNum" + count;
    questNum = document.getElementById(quest);
    questNum.parentNode.removeChild(questNum);
    
    var answ = "answerNum" + count;
    answNum = document.getElementById(answ);
    answNum.parentNode.removeChild(answNum);
    
    count--;
}


function createCouple(){
	count++;
	var couple = document.createElement("div");
    var id = "answer" + count; 
    couple.setAttribute("id", id);
             
    var alp = document.createTextNode("  " + String.fromCharCode(64+count) + ". ");         
             
    var areaStart = document.createElement("textarea");
    areaStart.setAttribute("rows", "1");
    areaStart.setAttribute("cols", "30");
    
    var num = document.createTextNode("  " + count + ". ");
    
    var areaFinish = document.createElement("textarea");
    areaFinish.setAttribute("rows", "1");
    areaFinish.setAttribute("cols", "30");
    
    couple.appendChild(alp);
    couple.appendChild(areaStart);
    couple.appendChild(num);
    couple.appendChild(areaFinish);
	
    return couple;
}


$(document).ready(function () {
    var previous;

    $("select").on('focus', function () {
        previous = this.value;
    }).change(function() {
    	count = 0;
    	var parent = document.getElementById("question");
    	while (parent != null && parent.hasChildNodes()) {
    		parent.removeChild(parent.lastChild);
		}
        if(this.value == "question-response"){
        	fillQuestionDivForQuestionResponse();
        }
        if(this.value == "fill-blank"){
        	fillQuestionDivForFillBlank();
        }
        if(this.value == "multiple-choice"){
        	fillQuestionDivForMultipleChoice();
        }
        if(this.value == "picture-response"){
        	fillQuestionDivForPictureResponse();
        }
        if(this.value == "multi-answer"){
        	 fillQuestionDivForMultiAnswer();
        } 
        if(this.value == "multiple-choice-answer"){
        	 fillQuestionDivForMultipleChoiceAnswer();
        }
        if(this.value == "matching"){
        	fillQuestionDivForMatching();
        }
    });
});
    