package servlets;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import db.bean.Account;
import db.dao.UserDao;
import helpers.PasswordEncryptor;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletContext context = getServletContext();
		UserDao userDataAccess = (UserDao) context.getAttribute(UserDao.CONTEXT_ATTRIBUTE_NAME);
		String userName = request.getParameter("username");
		String password = request.getParameter("password");
		String encryptedPassword = PasswordEncryptor.encrypt(password);
		try {

			// if administrator is signing in
			if (userDataAccess.isAdmin(userName, encryptedPassword)) {
				request.getSession().setAttribute("username", userName);
				response.getWriter().print("{ 'success' : true, 'location' : 'administration.jsp' }");
				return;
			}
			if (!userDataAccess.usernameIsAvailable(userName)) {
				Account currentlySigningIn = userDataAccess.getAccount(userName);

				if (currentlySigningIn.getPassword().equals(encryptedPassword)) {
					saveLoginInfo(request, response, currentlySigningIn);
					request.getSession().setAttribute("username", userName);
					response.getWriter().print("{ 'success' : true, 'location' : 'profile.jsp' }");
				} else {
					response.getWriter().write("{ 'success' : false, 'location' : 'unknown' }");
				}
			} else {
				response.getWriter().print("{ 'success' : false, 'location' : 'unknown' }");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	private void saveLoginInfo(HttpServletRequest request, HttpServletResponse response, Account currentAccount)
			throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		session.setAttribute(Account.SESSION_ATTRIBUTE_NAME, currentAccount);
	}

}
