package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.DbContract;
import db.MyDbInfo;
import db.bean.Account;
import db.bean.Note;
import db.bean.Person;
import db.bean.Person.Gender;
import db.bean.User;
import db.dao.UserDao;
import helpers.PasswordEncryptor;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	
	private static String account = MyDbInfo.MYSQL_USERNAME;
	private static String password = MyDbInfo.MYSQL_PASSWORD;
	private static String server =	"jdbc:mysql://" + MyDbInfo.MYSQL_DATABASE_SERVER;
	private static String database = MyDbInfo.MYSQL_DATABASE_NAME;
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
		System.out.println("x");
		UserDao dao = (UserDao)request.getServletContext().getAttribute(UserDao.CONTEXT_ATTRIBUTE_NAME);
		String type = request.getParameter("type");
		if(type.equals("email")) {
			String email = request.getParameter("email");
			System.out.println(email);
			boolean bool = dao.emailIsAvailable(email);
			if(bool) 
				response.getWriter().print(true);
			else 
				response.getWriter().print(false);
		} else if (type.equals("username")) {
			String username = request.getParameter("userName");
			boolean bool = dao.usernameIsAvailable(username);
			if(bool) 
				response.getWriter().print(true);
			else 
				response.getWriter().print(false);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		addNewUser(request, response);
	}
	
	private Date getDate(HttpServletRequest request) {
		int year = Integer.parseInt(request.getParameter("year"));
		int month = Integer.parseInt(request.getParameter("month"));
		int day = Integer.parseInt(request.getParameter("day"));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date d = null;
		try {
			d = sdf.parse(""+day+"/" + month + "/" + year);
		} catch (ParseException e) { e.printStackTrace();
			return null;}
		return d;
	}
	
	private Gender getGender(HttpServletRequest request) {
		String gender = request.getParameter("gender");
		Gender g;
		if(gender.equals("male")) g = Gender.MALE;
		else g = Gender.FEMALE;
		return g;
	}

	private void addNewUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserDao dao = (UserDao)request.getServletContext().getAttribute(UserDao.CONTEXT_ATTRIBUTE_NAME);
		String username = request.getParameter("username");
		String password = PasswordEncryptor.encrypt(request.getParameter("password"));
		String firstName = request.getParameter("firstname");
		String lastName = request.getParameter("lastname");
		String email = request.getParameter("email");
		
		Person person = new Person(firstName, lastName, email, getGender(request), getDate(request));
		Account account = new Account(username, password);
		dao.addNewUser(person, account);
		
		RequestDispatcher rd = request.getRequestDispatcher("index.html");
     	rd.forward(request,response);
	}
}
