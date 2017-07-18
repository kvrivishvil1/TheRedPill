<%@page import="java.util.HashMap"%>
<%@page import="managers.MainManager"%>
<style>
a, a:visited, a:hover, a:active {
	text-decoration: none;
	color: inherit;
	color: blue;
}

h4{
	margin: 5px;
}

.container1 {
	display:inline;
	color:red;
	width: 400px;
}

.requests {
	width: 390px;
	margin: 0 auto;
}

.form-line {
	padding-top: 4px;
	padding-bottom: 4px;
}

.request-line {
	width: 100%;
	position: relative;
	border-bottom: solid black 1px;
}

.profile-image {
	height: 45px;
	width: 45px;
}

.image-div {
	display: inline-block;
	float: left;
}

.name-div {
	display: inline-block;
	height: 100%;
	float: left;
	font-size: 15px;
	line-height: 45px;
	margin-left: 5px;
}

.buttons {
	position: absolute;
	top: 50%;
	transform: translateY(-50%);
	right: 0;
}

.button {
	display: inline-block;
}

.confirm {
	background-color: #2874A6;
}

.delete {
	background-color: #ba001b;
}

.top {
	margin-bottom: 10px;
}

.input {
	width: 65px;
	font-size:15px;
	height: 20px;
	border-radius: 10px;
	outline: none;
	border: none;
}
</style>
<div class="container1">
		<% 
			ServletContext cont = request.getServletContext();
			MainManager mainManager = (MainManager) cont.getAttribute(MainManager.CONTEXT_ATTRIBUTE_NAME);
			String username = (String)request.getSession().getAttribute("username");
			int userID = mainManager.getAccountManager().getUserIdByUserName("kvrivishvil1");
			HashMap <String, String> requests = mainManager.getAccountManager().getAllUsernamesFromFriendRequestsForUser(userID);
		%>
		<div class="requests">
		<h4> Respond to Your <%= requests.size() %> Friend Requests </h4>
		<div>
			<a href="friend-request.jsp" style="text-align:left;"> see all </a>
		</div>
		<div class="requests">
			<% for(String usernm : requests.keySet()) { %>
			<form class="form-line" action="FriendRequestsServlet" method="post">
				<div class="request-line clearfix">
					<div class="image-div">
						<img alt="" src="images/img.jpg" class="profile-image">
					</div>
					<div class="name-div">
						<a class="name" href="profile.jsp?showProfile=<%= usernm%>"><%= requests.get(usernm) %></a>
					</div>
					<div class="buttons">
						<div class="button top">
							<input type="submit" class="confirm input" name="act" value="Confirm">
						</div>
						<div class="button">
							<input type="submit" class="delete input" name="act" value="Delete">
						</div>
						
						<input type="hidden" name="username" value="<%= usernm %>"> 
					</div>
				</div>
			</form>
			<% } %>
		</div>
	</div>
