package servlets;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.bean.Account;
import db.bean.Person;
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
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletContext context = getServletContext();
		UserDao userDataAccess = (UserDao) context.getAttribute(UserDao.CONTEXT_ATTRIBUTE_NAME);
		String userName = request.getParameter("user-name");
		String password = request.getParameter("password");
		String encryptedPassword = PasswordEncryptor.encrypt(password);
		try {
			Account currentlySigningIn = userDataAccess.getAccount(userName);
			if (currentlySigningIn != null) {
				if (currentlySigningIn.getPassword().equals(encryptedPassword)) {
					// successful try of login, go to homePage
					RequestDispatcher rd = getServletContext().getRequestDispatcher("/homepage.jsp");
					rd.forward(request, response);
				} else {
					// password was incorrect for given user-name, (decide later
					// were to redirect)
				}
			} else {
				// given user doesn't exist at all
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
