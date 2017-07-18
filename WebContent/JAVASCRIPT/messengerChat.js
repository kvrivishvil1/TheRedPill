$(document).ready(function(){
        $('#chat').scrollTop($('#chat')[0].scrollHeight);
});
  
function generateFriendTag(name, src, id){
    var media = document.createElement("div");
    media.className = "media";
    media.id = id;
    
    var leftDiv = document.createElement("div");
    leftDiv.className = "media-left";
 
    media.appendChild(leftDiv);
			
    leftDiv.appendChild(generateImage("media-object", src, "50", "50"));
	
    var body = document.createElement("div");
    body.className = "media-body";
	
    var heading = document.createElement("h4");
    heading.className = "media-heading";
    heading.style = "text-align:left;";
    heading.innerHTML = name;	
	
    media.appendChild(body);
    body.appendChild(heading);
	
    document.getElementsByClassName("scroll col-sm-8")[0].appendChild(media);
}

function generateSelfTag(name, src, id){
    var media = document.createElement("div");
    media.className = "media";
    media.id = id;
	
    var body = document.createElement("div");
    body.className = "media-body";
	
    var heading = document.createElement("h4");
    heading.className = "media-heading";
    heading.style = "text-align:right;";
    heading.innerHTML = name;	
	
    media.appendChild(body);
    body.appendChild(heading);
	
    var rightDiv = document.createElement("div");
    rightDiv.className = "media-right";
	
    media.appendChild(rightDiv);
	
    rightDiv.appendChild(generateImage("media-object", src, "50", "50"));
	
    document.getElementsByClassName("scroll col-sm-8")[0].appendChild(media);
}

function generateImage(className, src, width, height){
	var image = document.createElement("img");
	image.className = className;
	image.src = src; 
	image.width = width;
	image.height = height;	
	return image;
}

function addNewFriendMessage(id, message, time){
    var body = document.getElementById(id).querySelector(".media-body");
	
    var text = document.createElement("p");
    text.style = "text-align:left;";
    text.innerHTML = message;
	
    var span = document.createElement("span");
    span.style = "float:right;"
	
    var font = document.createElement("font");
    font.color = "gray";
    font.innerHTML = time;
	
    span.appendChild(font);
    text.appendChild(span);
    body.appendChild(text);
}

function addNewSelfMessage(id, message, time){
    var body = document.getElementById(id).querySelector(".media-body");
	
    var text = document.createElement("p"); 
    text.style = "text-align:right;";
    text.innerHTML = message;
	
    var span = document.createElement("span");
    span.style = "float:left;"
	
    var font = document.createElement("font");
    font.color = "gray";
    font.innerHTML = time;
	
    span.appendChild(font);
	text.appendChild(span);
	body.appendChild(text);
}