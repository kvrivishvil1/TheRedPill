package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.bean.quiz.Question;
import db.bean.quiz.Quiz;
import db.dao.QuizDao;

/**
 * Servlet implementation class quizServlet
 */
@WebServlet("/QuizServlet")
public class QuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public QuizServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getParameter("type").equals("randomize")) {
			setQuizToRequest(request, response);
		}
		response.sendRedirect(""); // sad??
	}

	private void setQuizToRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Question> allQuestions = new ArrayList<>();
		int quizId = (int) request.getSession().getAttribute("quizId");
		QuizDao dataAccess = new QuizDao();
		Quiz quiz = dataAccess.getQuiz(quizId);
		allQuestions = quiz.getAllQuestions();
		if (quiz.isRearrangable()) {
			Collections.shuffle(allQuestions);
		}
		request.getSession().setAttribute("questionList", allQuestions);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
