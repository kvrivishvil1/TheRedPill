package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.bean.quiz.Quiz;
import helpers.StringParser;
import helpers.TimeConverter;

/**
 * Servlet implementation class QuizServlet
 */
@WebServlet("/QuizCreateServlet")
public class QuizCreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public QuizCreateServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		boolean isRearrangable = Boolean.valueOf(request.getParameter("randomize"));
		boolean isPracticable = Boolean.valueOf(request.getParameter("practice-mode")); 
		String category = request.getParameter("category");
		String tags = request.getParameter("tags");
		Quiz quiz = new Quiz(name, category);
		int accountID = (Integer)request.getSession().getAttribute("accountID");
		quiz.setAccountID((long)accountID);
		quiz.setDescription(description);
		quiz.setPracticableMode(isPracticable);
		quiz.setRearrangableMode(isRearrangable);
		ArrayList<String> tagList = StringParser.parseTagString(tags);
		for(int i = 0; i < tagList.size(); i++){
			quiz.addTag(tagList.get(i));
		}
		boolean isTimeLimited = Boolean.valueOf(request.getParameter("time-limit"));
		if(isTimeLimited){
			quiz.setTimeLimit(TimeConverter.toSeconds(request.getParameter("hours"), request.getParameter("minutes"), request.getParameter("seconds")));
		}
		request.getSession().setAttribute("quiz", quiz);
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/question-form.jsp");
		rd.forward(request, response);
	}
	
}
