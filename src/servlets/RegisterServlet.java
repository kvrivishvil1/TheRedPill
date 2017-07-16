package servlets;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Managers.AccountManager;
import db.bean.Account;
import db.bean.Person;
import db.bean.Person.Gender;
import db.dao.UserDao;
import helpers.PasswordEncryptor;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AccountManager mng = (AccountManager) request.getServletContext().getAttribute(AccountManager.CONTEXT_ATTRIBUTE_NAME);
		String type = request.getParameter("type");
		String email = request.getParameter("email");
		String username = request.getParameter("userName");
		if(mng.checkInDatabase(type, email, username))
			response.getWriter().print(true);
		else
			response.getWriter().print(false);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AccountManager mng = (AccountManager) request.getServletContext().getAttribute(AccountManager.CONTEXT_ATTRIBUTE_NAME);
		String username = request.getParameter("username");
		String password = PasswordEncryptor.encrypt(request.getParameter("password"));
		String firstName = request.getParameter("firstname");
		String lastName = request.getParameter("lastname");
		String email = request.getParameter("email");
		String gender = request.getParameter("gender");
		int year = Integer.parseInt(request.getParameter("year"));
		int month = Integer.parseInt(request.getParameter("month"));
		int day = Integer.parseInt(request.getParameter("day"));
		mng.addNewUser(firstName, lastName, email, gender, year, month, day, username, password);
		
		RequestDispatcher rd = request.getRequestDispatcher("index.html");
     	rd.forward(request,response);
	}
}
