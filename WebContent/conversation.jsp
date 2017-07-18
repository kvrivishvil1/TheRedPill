<%@page import="managers.AccountManager"%>
<%@page import="managers.MessageManager"%>
<%@page import="managers.MainManager"%>
<%@ page import="db.dao.UserDao"%>
<%@ page import="db.bean.Person"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<head>
<script src="JAVASCRIPT/messengerChat.js"></script>
<link rel="stylesheet" href="CSS/messenger.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<script type="text/javascript"
	src="http://code.jquery.com/jquery-1.7.1.min.js"></script>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<link rel="stylesheet" href="CSS/font-awesome.min.css">
<link rel="stylesheet" href="CSS/navigation.css">
	
<title>Conversation</title>
</head>
<script>
$(function() {
	$("#modal-data").load("new-message.html");
});
</script>
<style>
a {
	text-decoration: none;
}
</style>
<script src="jquery.js"></script>

<body>
	
	
	
<div class="form clearfix">
		<section> <design  href="#home"> <a
			href="homepage.jsp"><i class="fa top fa-home" aria-hidden="true"></i></a> </design> </section>
		<section> <design href="#quiz"> <a href="quiz-form.jsp"><i
			class="fa top fa-server" aria-hidden="true"></i></a> </design> </section>
		<section> <design href="#friends-request">
		<div class="friend-request">
			<a href="friend-request.jsp"><i class="fa fa-user-plus" aria-hidden="true"></i></a>
		</div>
		</design> </section>
		<section> <design  href="#friends"> <a
			href="friends.jsp"><i class="fa top fa-address-book-o"
			aria-hidden="true"></i></a> </design> </section>
		<section> <design href="#profile"> <a
			href="profile.jsp"><i class="fa fa-address-card"
			aria-hidden="true"></i></a> </design> </section>
		<section> <design class="active" href="#messages"> <a
			href="conversation.jsp"><i class="fa top fa-envelope-open-o"
			aria-hidden="true"></i></a> </design> </section>
		<section> <design href="#log-out"> <a href="">
			<i class="fa fa-sign-out" aria-hidden="true"></i>
		</a> </design> </section>
	</div>
	
	
	
	
	<div id="chat" class="scroll col-sm-8">
		<%
			List<Note> recMess = null;
			ServletContext sc = request.getServletContext();
			MainManager mainManager = (MainManager) request.getServletContext()
					.getAttribute(MainManager.CONTEXT_ATTRIBUTE_NAME);
			MessageManager messageManager = mainManager.getMessageManager();
			AccountManager accountManager = mainManager.getAccountManager();
			int selfID = (int) request.getSession().getAttribute("accountID");
			int friendID = 0;
			String friendUsername = "";
			if (request.getParameter("friendUsername") == null) {
				recMess = messageManager.getRecentMessages(selfID);
				request.getSession().setAttribute("recentMessages", recMess);
				if (recMess.size() == 0) {
					response.sendRedirect("no-conversation.html");
					return;
				}
				if (recMess.get(0).getSenderID() == selfID)
					friendID = recMess.get(0).getRecieverID();
				else
					friendID = recMess.get(0).getSenderID();
				friendUsername = accountManager.getUsernameByUserId(friendID);
			} else {
				friendUsername = request.getParameter("friendUsername");
				friendID = accountManager.getUserIdByUserName(friendUsername);
			}
			boolean isLeft = false, isRight = false;
			int currentID = 0;
			List<Note> messages = messageManager.getConversation(selfID, friendID);
			Person self = accountManager.getPersonByUserId(selfID);
			Person friend = accountManager.getPersonByUserId(friendID);
			for (int i = 0; i < messages.size(); i++) {
				Note current = messages.get(i);
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
				String time = sdf.format(messages.get(i).getDate());
				if (current.getSenderID() == selfID) {
					if (!isRight) {
						isLeft = false;
						isRight = true;
						currentID++;
		%>
		<script>	generateSelfTag("<%=self.getName()%> " + "<%=self.getLastName()%>", "Images/" + "<%=self.getName()%>" + ".jpg", <%=currentID%>); </script>
		<%
			}
		%>
		<script>	addNewSelfMessage(<%=currentID%>, "<%=current.getText()%>", "<%=time%>"); </script>

		<%
			}
				if (current.getSenderID() == friendID) {
					if (!isLeft) {
						isRight = false;
						isLeft = true;
						currentID++;
		%>
		<script>		generateFriendTag("<%=friend.getName()%> " + "<%=friend.getLastName()%>", "Images/" + "<%=friend.getName()%>" + ".jpg", <%=currentID%>); </script>
		<%
			}
		%>
		<script> 		addNewFriendMessage(<%=currentID%>, "<%=current.getText()%>", "<%=time%>   
			");
		</script>
		<%
			}
			}
		%>
	</div>
	<div class="scroll col-sm-4">
		<%@include file="chat-bar.jsp"%>
	</div>
	<form action="MessageServlet" method="post" style="display: inline;">
		<input type="hidden" name="reciever-username"
			value="<%=friendUsername%>" /> <input type="hidden" name="id"
			value="<%=request.getParameter("id")%>" /> <input
			class="col-sm-7 input-sm-7 textfield"
			placeholder="Enter your message here" type="text" name="message"
			title="title" /> <input class="button col-sm-1" type="submit"
			value="Send" />
	</form>
	<button class="button col-sm-4" data-toggle="modal"
		data-target="#new-message">Create New Conversation</button>
	<div id="modal-data"></div>
</body>
</html>