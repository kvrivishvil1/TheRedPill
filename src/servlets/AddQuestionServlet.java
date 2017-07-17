package servlets;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import analyzer.quiz.SubquestionDataContainer;
import db.bean.quiz.Question;
import db.bean.quiz.Quiz;
import db.dao.QuizDao;
import helpers.TimeConverter;
import managers.MainManager;
import managers.QuizManager;

/**
 * Servlet implementation class AddQuestionServlet
 */
@WebServlet("/AddQuestionServlet")
public class AddQuestionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddQuestionServlet() {
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
		MainManager mainManager = (MainManager)request.getServletContext().getAttribute(MainManager.CONTEXT_ATTRIBUTE_NAME);
		QuizManager quizManager = mainManager.getQuizManager();
		
		int typeID = Integer.parseInt(request.getParameter("question-type"));
		String act = request.getParameter("act");
		Quiz quiz = (Quiz) request.getSession().getAttribute("quiz");

		if (typeID == 0 && act.equals("next")) {
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/question-form.jsp");
			rd.forward(request, response);
		} else if (typeID == 0 && !act.equals("next")) {
			quizManager.addQuiz(quiz);
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/quiz-form.jsp");
			rd.forward(request, response);
		} else {
			String parser = request.getParameter("parsing");
			String note = request.getParameter("note");
			
			long timeLimit = -1;
			if (request.getParameter("time-limited") != null && request.getParameter("time-limited").equals("on")) {
				timeLimit = TimeConverter.toSeconds(request.getParameter("hours"), request.getParameter("minutes"),
						request.getParameter("seconds"));
			}

			boolean isOrderSensitive = false;
			if (request.getParameter("order-sensitive") != null
					&& request.getParameter("order-sensitive").equals("on")) {
				isOrderSensitive = true;
			}

			String[] questions = request.getParameterValues("question-text");
			String[] answers = request.getParameterValues("answer-text");
			String[] options = request.getParameterValues("option-text");
			String[] answerOptions = request.getParameterValues("answer-option");
			String[] optionIDs = request.getParameterValues("option-id");

			SubquestionDataContainer container = new SubquestionDataContainer(questions, answers, options, optionIDs,
					answerOptions, parser);
			Question analyzedQuestion = quizManager.analyzeQuestion(typeID, note, timeLimit, isOrderSensitive,
					container);

			quiz.addQuestion(analyzedQuestion);
			if (act.equals("next")) {
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/question-form.jsp");
				rd.forward(request, response);
			} else {
				quizManager.addQuiz(quiz);
			}
		}

	}

}
