package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jdt.internal.compiler.batch.Main;

import db.bean.Note;
import db.dao.MessageDao;
import db.dao.UserDao;
import managers.AccountManager;
import managers.MainManager;
import managers.MessageManager;

/**
 * Servlet implementation class NewMessageServlet
 */
@WebServlet("/NewMessageServlet")
public class NewMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public NewMessageServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		MainManager mainManager = (MainManager) request.getServletContext()
				.getAttribute(MainManager.CONTEXT_ATTRIBUTE_NAME);
		MessageManager messageManager = mainManager.getMessageManager();
		AccountManager accountManager = mainManager.getAccountManager();

		String recieverUsername = request.getParameter("reciever-username");
		String text = request.getParameter("message");
		Date date = new Date();

		int senderID = (int) request.getSession().getAttribute("accountID");
		int recieverID = accountManager.getUserIdByUserName(recieverUsername);

		messageManager.sendNewNote(senderID, recieverID, text, date);
		response.sendRedirect("conversation.jsp" + "?friendUsername=" + recieverUsername);
		
		doGet(request, response);
	}

}
