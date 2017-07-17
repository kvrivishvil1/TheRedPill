package listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import Managers.AccountManager;
import Managers.MainManager;
import db.dao.MessageDao;
import db.dao.QuizDao;
import db.dao.UserDao;

/**
 * Application Lifecycle Listener implementation class ContextListener
 *
 */
@WebListener
public class ContextListener implements ServletContextListener {
	UserDao userDataAccess;
	QuizDao quizDataAccess;
	MessageDao messageDataAccess;
	AccountManager accountManager;
	MainManager mainManager;
	

	/**
	 * Default constructor.
	 */
	public ContextListener() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent arg0) {
		userDataAccess = new UserDao();
		arg0.getServletContext().setAttribute(UserDao.CONTEXT_ATTRIBUTE_NAME, userDataAccess);
		quizDataAccess = new QuizDao();
		arg0.getServletContext().setAttribute(QuizDao.CONTEXT_ATTRIBUTE_NAME, quizDataAccess);
		messageDataAccess = new MessageDao();
		arg0.getServletContext().setAttribute(MessageDao.CONTEXT_ATTRIBUTE_NAME, messageDataAccess);
		accountManager = new AccountManager(userDataAccess);
		
		mainManager = new MainManager(accountManager);
		arg0.getServletContext().setAttribute(MainManager.CONTEXT_ATTRIBUTE_NAME, mainManager);
	}

}
