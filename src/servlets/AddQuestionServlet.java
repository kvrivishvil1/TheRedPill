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

import db.bean.quiz.Answer;
import db.bean.quiz.Question;
import db.bean.quiz.Quiz;
import db.bean.quiz.Subquestion;
import db.bean.quiz.Option;
import db.dao.QuizDao;
import helpers.StringParser;

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
		int type = Integer.parseInt(request.getParameter("question-type"));
		Quiz quiz = (Quiz) request.getSession().getAttribute("quiz");
		String act = request.getParameter("act");
		Question question = new Question(type);
		if (type == 0 && act.equals("next")) {
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/question-form.jsp");
			rd.forward(request, response);
		} else if (type == 0 && !act.equals("next")) {
			QuizDao dao = new QuizDao();
				dao.addQuiz(quiz);
		} else {
			question.setNote(request.getParameter("note"));
			if(request.getParameter("time-limited") != null && request.getParameter("time-limited").equals("on")){
				int hours = Integer.parseInt(request.getParameter("hours"));
				int minutes = Integer.parseInt(request.getParameter("minutes"));
				int seconds = Integer.parseInt(request.getParameter("seconds"));
				int total = hours*3600 + minutes*60 + seconds;
				if(total > 0)
					question.setTimeLimit(total);
			}
			if(request.getParameter("order-sensitive") != null && request.getParameter("order-sensitive").equals("on"))
				question.setOrderSensitive(true);
			if (type != 7) {
				String parser = request.getParameter("parsing");
				Subquestion subquestion = new Subquestion(request.getParameter("question-text"));
				String[] answers = request.getParameterValues("answer-text");
				if(answers != null){
					for (int i = 0; i < answers.length; i++) {
						ArrayList<String> singleAnswersList = new ArrayList<String>();
						char parserSymbol = 's';
						if (parser != null && !parser.equals("")) {
							parserSymbol = parser.charAt(0);
							singleAnswersList = StringParser.parseStringBy(parserSymbol, answers[i]);
						} else {
							singleAnswersList.add(answers[i]);
						}
						Answer answer = new Answer(singleAnswersList);
						if (parser != null && !parser.equals("")) {
							answer.setParserSymbol(parserSymbol);
						}
						subquestion.addAnswer(answer);
					}
				}
				String[] options = request.getParameterValues("option-text");
				String[] optionIDs = request.getParameterValues("option-id");
				String[] answerOptions = request.getParameterValues("answer-option");
				int index = 0;
				if (options != null) {
					for (int i = 0; i < options.length; i++) {
						System.out.println(index);
						if (index < answerOptions.length && answerOptions[index].equals(optionIDs[i])) {
							index++;
							ArrayList<String> singleAnswersList = new ArrayList<String>();
							singleAnswersList.add(options[i]);
							Answer answer = new Answer(singleAnswersList);
							subquestion.addAnswer(answer);
						}
						Option option = new Option(options[i]);
						question.addOption(option);
					}
				}
				question.addSubquestion(subquestion);
				quiz.addQuestion(question);
			} else {
				String[] options = request.getParameterValues("option-text");
				String[] questions = request.getParameterValues("question-text");
				String[] correctAnswers = request.getParameterValues("answer-option");
				for (int i = 0; i < options.length; i++) {
					Option option = new Option(options[i]);
					Subquestion subquestion = new Subquestion(questions[i]);
					ArrayList<String> answers = new ArrayList<String>();
					answers.add(options[Integer.parseInt(correctAnswers[i]) - 1]);
					subquestion.addAnswer(new Answer(answers));
					question.addSubquestion(subquestion);
					question.addOption(option);
				}
				quiz.addQuestion(question);
			}
			if (act.equals("next")) {
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/question-form.jsp");
				rd.forward(request, response);
			} else {
				QuizDao dao = new QuizDao();
				dao.addQuiz(quiz);
			}
		}

	}

}
