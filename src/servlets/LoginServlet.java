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
import managers.AccountManager;
import managers.MainManager;

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
		MainManager mainManager = (MainManager) context.getAttribute(MainManager.CONTEXT_ATTRIBUTE_NAME);
		String userName = request.getParameter("username");
		String password = request.getParameter("password");
		String encryptedPassword = PasswordEncryptor.encrypt(password);
		// if administrator is signing in
		if (mainManager.getAccountManager().isAdmin(userName, encryptedPassword)) {
			request.getSession().setAttribute("accountID", 1);
			request.getSession().setAttribute("username", userName);
			response.getWriter().print("{ 'success' : true, 'location' : 'administration.jsp' }");
			return;
		}
		if (!mainManager.getAccountManager().usernameIsAvailable(userName)) {
			Account currentlySigningIn = mainManager.getAccountManager().getAccount(userName);
			if (currentlySigningIn.getPassword().equals(encryptedPassword)) {
				AccountManager accountManager = mainManager.getAccountManager();	
				request.getSession().setAttribute("accountID", accountManager.getUserIdByUserName(userName));
				saveLoginInfo(request, response, currentlySigningIn);
				request.getSession().setAttribute("accountID", accountManager.getUserIdByUserName(userName));
				request.getSession().setAttribute("username", userName);
				response.getWriter().print("{ 'success' : true, 'location' : 'homepage.jsp' }");
			} else {
				response.getWriter().write("{ 'success' : false, 'location' : 'unknown' }");
			}
		} else {
			response.getWriter().print("{ 'success' : false, 'location' : 'unknown' }");
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
