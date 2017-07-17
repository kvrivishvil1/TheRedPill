package managers;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import db.bean.Account;
import db.bean.Person;
import db.bean.Person.Gender;
import db.dao.UserDao;

public class AccountManager {
	public static final String CONTEXT_ATTRIBUTE_NAME = "AccountManager";
	private UserDao userDao;
	
	public AccountManager(UserDao userDao) {
		this.userDao = userDao;
	}
	
	/**
	 * @param day
	 * @param month
	 * @param year
	 * @return java Date with parameters day month year
	 */
	private Date getDate(int day, int month, int year) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date d = null;
		try {
			d = sdf.parse(""+day+"/" + month + "/" + year);
		} catch (ParseException e) { e.printStackTrace();
			return null;}
		return d;
	}
	
	/**
	 * @param gender
	 * @return Gender with parameter gender
	 */
	private Gender getGender(String gender) {
		Gender g;
		if(gender.equals("male")) g = Gender.MALE;
		else g = Gender.FEMALE;
		return g;
	}
	
	/**
	 * Calls userdao method for adding new person and account in database
	 * @param firstName of person
	 * @param lastName of person
	 * @param email of person
	 * @param gender of person
	 * @param year of person birthday
	 * @param month of person birthday
	 * @param day of person birthday
	 * @param username of account 
	 * @param password of account
	 */
	public void addNewUser(String firstName, String lastName, String email, String gender, int year, int month, 
			int day, String username, String password) {
		Person person = new Person(firstName, lastName, email, getGender(gender), getDate(day, month, year));
		Account account = new Account(username, password);
		userDao.addNewUser(person, account);
	}
	
	/**
	 * Calls userdao method for check if 
	 * email or username is available in database
	 * @param type email or password
	 * @param email 
	 * @param username
	 * @return isn't in database already
	 */
	public boolean checkInDatabase(String type, String email, String username) {
		if (type.equals("email")) {
			return userDao.emailIsAvailable(email);
		} else{
			return userDao.usernameIsAvailable(username);
		}
	}
	
	/**
	 * Calls userdao usernameIsAvailable method 
	 * @param username
	 * @return boolean if username is available;
	 */
	public boolean usernameIsAvailable(String username) {
		return userDao.usernameIsAvailable(username);
	}
	
	/**
	 * Calls userdao getAccount method 
	 * @param username
	 * @return Account if username exists
	 *  and if not returns null
	 */
	public Account getAccount(String username) {
		return userDao.getAccount(username);
	}
	
	/**
	 * Calls userdao isAdmin method with current parameters
	 * @param username 
	 * @param password
	 * @return true if is admin and if not returns false
	 */
	public boolean isAdmin(String username, String password) {
		return userDao.isAdmin(username, password);
	}
	
	/**
	 * Calls getUserIdByUserName method of user dao with parameters:
	 * @param username
	 * @return userId
	 */
	public int getUserIdByUserName(String username) {
		return userDao.getUserIdByUserName(username);
	}
	
	/**
	 * Calls getAllUsernamesFromFriendRequestsForUser method of user dao with parameters:
	 * @param userID
	 * @return hash set of full names with keys username
	 */
	public HashMap<String, String> getAllUsernamesFromFriendRequestsForUser(int userID) {
		return userDao.getAllUsernamesFromFriendRequestsForUser(userID);
	}
	
	/**
	 * Calls getAllFriendsForUser method of user dao with parameters:
	 * @param userID
	 * @return hash set of full names with keys username
	 */
	public HashMap<String, String> getAllFriendsForUser(int userID) {
		return userDao.getAllFriendsForUser(userID);
	}
	
	/**
	 * Calls getPersonByUserId method of user dao with parameters:
	 * @param userID
	 * @return person 
	 */
	public Person getPersonByUserId(int userID) {
		return userDao.getPersonByUserId(userID);
	}
	
	/**
	 * Calls getPersonByUserId method of user dao with parameters:
	 * @param userID
	 * @return username
	 */
	public String getUsernameByUserId(int userID) {
		return userDao.getUsernameByUserId(userID);
	}
	
	/**
	 * Calls getPersonByUserId method of user dao 
	 * @return list of all usernames
	 */
	public ArrayList<String> getAllUsernames() {
		return userDao.getAllUsernames();
	}
	
	/**
	 * Calls getNumUsers method of user dao 
	 * @return amount of users
	 */
	public long getNumUsers() {
		return userDao.getNumUsers();
	}
}
