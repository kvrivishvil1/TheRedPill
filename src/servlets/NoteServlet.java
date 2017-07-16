package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.dao.MessageDao;
import db.dao.QuizDao;
import db.dao.UserDao;

/**
 * Servlet implementation class NoteServlet
 */
@WebServlet("/NoteServlet")
public class NoteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public NoteServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		QuizDao quizDataAccess = new QuizDao();
		UserDao userDataAccess = new UserDao();
		MessageDao messageDataAccess = new MessageDao();
		if (request.getParameter("type").equals("creation")) {
			String noteHeader = request.getParameter("header");
			String note = request.getParameter("note");
			messageDataAccess.addAdministationNote(noteHeader, note);
		} else if (request.getParameter("type").equals("deletion")) {
			String userToDelete = request.getParameter("username");
			userDataAccess.deleteUser(userToDelete);
		} else if (request.getParameter("type").equals("search")) {
			String searchWord = request.getParameter("username");
			ArrayList<String> result = userDataAccess.searchUser(searchWord);
			String json = generateGson(result);
			response.getWriter().print(json);
		} else if (request.getParameter("type").equals("promotion")) {
			String userToPromote = request.getParameter("username");
			userDataAccess.promoteAdmin(userToPromote);
		} else if (request.getParameter("type").equals("deletionQuiz")) {
			String quizName = request.getParameter("quiz");
			int id = quizDataAccess.getQuizIdByName(quizName);
			quizDataAccess.deleteQuiz(id);
		} else if (request.getParameter("type").equals("searchQuiz")) {
			String searchedQuiz = request.getParameter("quiz");
			ArrayList<String> result = quizDataAccess.searchQuizByName(searchedQuiz);
			String json = generateGson(result);
			response.getWriter().print(json);
		} else if (request.getParameter("type").equals("deletionQuizHistory")) {
			String quizToClean = request.getParameter("quiz");
			int id = quizDataAccess.getQuizIdByName(quizToClean);
			quizDataAccess.deleteQuizAttempts(id);
		}

	}

	private String generateGson(ArrayList<String> result) {
		String json = "{ 'number' : " + result.size() + ", 'array' : [ ";
		for (int i = 0; i < result.size(); i++) {
			if (i == result.size() - 1)
				json += "'" + result.get(i) + "'";
			else
				json += "'" + result.get(i) + "' , ";
		}
		json += "] }";
		return json;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
