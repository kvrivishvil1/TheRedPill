package Managers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
}
