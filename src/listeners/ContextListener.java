package listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import db.dao.UserDao;

/**
 * Application Lifecycle Listener implementation class ContextListener
 *
 */
@WebListener
public class ContextListener implements ServletContextListener {
	UserDao userDataAccess;

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
	}

}
