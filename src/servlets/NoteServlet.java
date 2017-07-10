package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.dao.MessageDao;
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
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getParameter("type").equals("creation")) {
			String noteHeader = request.getParameter("header");
			String note = request.getParameter("note");
			MessageDao messageDataAccess = (MessageDao) request.getServletContext()
					.getAttribute(MessageDao.CONTEXT_ATTRIBUTE_NAME);
			try {
				messageDataAccess.addAdministationNote(noteHeader, note);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (request.getParameter("type").equals("deletion")) {
			UserDao userDataAccess = (UserDao) request.getServletContext().getAttribute(UserDao.CONTEXT_ATTRIBUTE_NAME);
			String userToDelete = request.getParameter("username");
			userDataAccess.deleteUser(userToDelete);
		} else if (request.getParameter("type").equals("search")) {
			UserDao userDataAccess = (UserDao) request.getServletContext().getAttribute(UserDao.CONTEXT_ATTRIBUTE_NAME);
			String searchWord = request.getParameter("username");
			try {
				ArrayList<String> result = userDataAccess.searchUser(searchWord);
				String json = "{ 'number' : " + result.size() + ", 'array' : [ ";
				for (int i = 0; i < result.size(); i++) {
					if (i == result.size() - 1)
						json += "'"+result.get(i)+"'";
					else
						json += "'"+result.get(i) + "' , ";
				}
				json += "] }";
				response.getWriter().print(json);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

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
