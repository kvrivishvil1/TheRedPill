<%@page import="managers.AccountManager"%>
<%@page import="managers.MessageManager"%>
<%@page import="managers.MainManager"%>
<%@ page import="db.bean.Person"%>
<%@ page import="db.bean.Note"%>
<%@page import="java.util.*"%>
<%@page import="java.text.*"%>
<%
	MainManager mainManagerBar = (MainManager)request.getServletContext().getAttribute(MainManager.CONTEXT_ATTRIBUTE_NAME);
	MessageManager messageManagerBar = mainManagerBar.getMessageManager();
	AccountManager accountManagerBar = mainManagerBar.getAccountManager();
	int selfAccountID = (int)request.getSession().getAttribute("accountID");
	List<Note> recentMessages = messageManagerBar.getRecentMessages(selfAccountID);
	Person selfData = accountManagerBar.getPersonByUserId(selfAccountID);
	for (int j = 0; j < recentMessages.size(); j++) {
		Person friendData = null;
		int id = 0;
		if (recentMessages.get(j).getSenderID() == selfAccountID)
			id = recentMessages.get(j).getRecieverID();
		else
			id = recentMessages.get(j).getSenderID();
		String friendUsernameD = accountManagerBar.getUsernameByUserId(id);
		friendData = accountManagerBar.getPersonByUserId(id);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String messageTime = simpleDateFormat.format(recentMessages.get(j).getDate());
%>
<div class="media">
	<div class="media-left">
		<img class="media-object"
			src=<%=("Images/" + friendData.getName() + ".jpg")%> width="50"
			height="50" />
	</div>
	<div class="media-body">
		<h4 class="media-heading">
			<a
				href=<%="conversation.jsp" + "?friendUsername=" + friendUsernameD%>>
				<%=friendData.getName()%> <%=friendData.getLastName()%>
			</a>
		</h4>
		<p style="text-align: left;">
			<%=recentMessages.get(j).getText()%>
			<span style="float: right;"> <font color="gray"> <%=messageTime%>
			</font>
			</span>
		</p>
	</div>
</div>
<hr>
<%
	}
%>