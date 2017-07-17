package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import managers.MainManager;

/**
 * Servlet implementation class FriendRequestsServlet
 */
@WebServlet("/FriendRequestsServlet")
public class FriendRequestsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FriendRequestsServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MainManager mainManager = (MainManager)request.getServletContext().getAttribute(MainManager.CONTEXT_ATTRIBUTE_NAME);
		String value = request.getParameter("act");
		String senderUsername = request.getParameter("username");
		String receiverUsername = (String)request.getSession().getAttribute("username");
		mainManager.getAccountManager().requestController(value, senderUsername, receiverUsername);
		RequestDispatcher rd = request.getRequestDispatcher("friend-request.jsp");
     	rd.forward(request,response);
	}

}
