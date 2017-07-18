package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.bean.Report;
import db.bean.Review;
import db.bean.quiz.Question;
import db.bean.quiz.Quiz;
import db.bean.quiz.QuizAttempt;
import db.dao.QuizDao;
import helpers.StringParser;

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
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getParameter("type").equals("randomize")) {
			setQuizToSession(request, response);
			setAttemptToSession(request, response);
			response.getWriter().print(true);
		} else if (request.getParameter("type").equals("checkAnswer")) {
			checkAnswer(request, response);
			response.getWriter().print(true);
		} else if (request.getParameter("type").equals("checkAnswerForMatching")) {
			checkAnswerForMatching(request, response);
			response.getWriter().print(true);
		} else if (request.getParameter("type").equals("finishState")) {
			QuizAttempt attempt = (QuizAttempt) request.getSession().getAttribute("attempt");
			attempt.setFinishTime(new Date());
			request.getSession().setAttribute("attempt", attempt);
			response.getWriter().print(true);
		} else if (request.getParameter("type").equals("addReview")) {
			// add review in db
			int accountId = 1;// (int)
								// request.getSession().getAttribute("accountID");
			int quizId = 1;// request.getParameter("quizId");
			int numStars = Integer.parseInt(request.getParameter("numStars"));
			String text = request.getParameter("text");
			Review review = new Review(text, numStars, accountId, quizId, new Date());
			QuizDao dao = new QuizDao();
			dao.addReview(review);
			response.getWriter().print(true);
		} else if (request.getParameter("type").equals("addReport")) {
			// add report in db
			int quizID = 1;// get quiz id from somewhere
			int accountID = 1;// (int)
								// request.getSession().getAttribute("accountID");
			String reportText = request.getParameter("text");
			Report report = new Report(quizID, accountID, reportText, new Date());
			QuizDao dao = new QuizDao();
			dao.addReport(report);
			response.getWriter().print(true);
		}
	}

	/**
	 * sets attempt object to session
	 * @param request
	 * @param response
	 */
	private void setAttemptToSession(HttpServletRequest request, HttpServletResponse response) {
		int quizID = (int) request.getSession().getAttribute("quizId");
		int accountID = 1; // alert! temporary line
		// Integer.parseInt((String)
		// request.getSession().getAttribute("accountId"));
		QuizAttempt attempt = new QuizAttempt(quizID, accountID);
		attempt.setStartTime(new Date());
		attempt.setScore(0);
		request.getSession().setAttribute("attempt", attempt);
	}

	/**
	 * compares asnwers of matching questions subquestions to user inputs'
	 * 
	 * @param request
	 * @param response
	 */
	private void checkAnswerForMatching(HttpServletRequest request, HttpServletResponse response) {
		int countCorrect = 0;
		int questionPosition = Integer.parseInt(request.getParameter("position"));
		int subQuestionPosition = Integer.parseInt(request.getParameter("subQuestion"));
		int precision = Integer.parseInt(request.getParameter("precision"));

		String answer = request.getParameter("answer");
		ArrayList<Question> questions = (ArrayList<Question>) request.getSession().getAttribute("questionList");
		Question question = questions.get(questionPosition);
		countCorrect += question.getSubquestions().get(subQuestionPosition)
				.countCorrectAnswers(StringParser.parseStringBy('/', answer), precision);
		updateScore(request, countCorrect);

	}

	/**
	 * change score after single question is checked
	 * 
	 * @param request
	 * @param countCorrect
	 */
	private void updateScore(HttpServletRequest request, int countCorrect) {
		QuizAttempt attempt = (QuizAttempt) request.getSession().getAttribute("attempt");
		attempt.setScore(attempt.getScore() + countCorrect);
		request.getSession().setAttribute("attempt", attempt);
	}

	/**
	 * compares answers to user inputs
	 * 
	 * @param request
	 * @param response
	 */
	private void checkAnswer(HttpServletRequest request, HttpServletResponse response) {
		int countCorrect = 0;
		int questionPosition = Integer.parseInt(request.getParameter("position"));
		int precision = Integer.parseInt(request.getParameter("precision"));
		String answer = request.getParameter("answer");
		ArrayList<Question> questions = (ArrayList<Question>) request.getSession().getAttribute("questionList");
		Question question = questions.get(questionPosition);
		countCorrect += question.getSubquestions().get(0).countCorrectAnswers(StringParser.parseStringBy('/', answer),
				precision);
		updateScore(request, countCorrect);

	}

	/**
	 * sets ArrayList of questions to session, randomizes it if necessary
	 * 
	 * @param request
	 * @param response
	 */
	private void setQuizToSession(HttpServletRequest request, HttpServletResponse response)
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
