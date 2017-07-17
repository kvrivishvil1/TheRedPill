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
 * Servlet implementation class ChallengeServlet
 */
@WebServlet("/ChallengeServlet")
public class ChallengeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChallengeServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MainManager mainManager = (MainManager)request.getServletContext().getAttribute(MainManager.CONTEXT_ATTRIBUTE_NAME);
		String value = request.getParameter("act");
		int quizId = Integer.parseInt(request.getParameter("quizId"));
		int senderId = Integer.parseInt(request.getParameter("senderId"));
		int receiverId = Integer.parseInt(request.getParameter("receiverId"));
		boolean action = mainManager.getMessageManager().challengeController(value, quizId, senderId, receiverId);
		RequestDispatcher rd;
		if(action) {
			rd = request.getRequestDispatcher("display-quiz.jsp");
		} else {
			rd = request.getRequestDispatcher("challenges.jsp");
		}
     	rd.forward(request,response);
		doGet(request, response);
	}

}
