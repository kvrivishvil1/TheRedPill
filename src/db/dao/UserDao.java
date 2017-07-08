package db.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.catalina.tribes.group.AbsoluteOrder.AbsoluteComparator;

import db.DbContract;
import db.MyDbInfo;
import db.bean.Account;
import db.bean.Note;
import db.bean.Person;
import db.bean.Person.Gender;
import db.bean.User;

public class UserDao {
	public static final String CONTEXT_ATTRIBUTE_NAME = "userDataAccess";

	private static final String database = MyDbInfo.MYSQL_DATABASE_NAME;
	private static final String account = MyDbInfo.MYSQL_USERNAME;
	private static final String password = MyDbInfo.MYSQL_PASSWORD;
	private static final String server = MyDbInfo.MYSQL_DATABASE_SERVER;

	public UserDao() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private Connection createConnection() throws ClassNotFoundException, SQLException {
		Connection connection = DriverManager.getConnection("jdbc:mysql://" + server, account, password);
		Statement stm = connection.createStatement();
		stm.executeQuery("USE " + database);
		return connection;
	}

	/**
	 * gets username String as parameter and returns Account object
	 */
	public Account getAccount(String searchedUser) throws SQLException {
		try (Connection connection = createConnection()) {
			String query = generateQueryForAccount();
			PreparedStatement stm = connection.prepareStatement(query);
			stm.setString(1, searchedUser);
			ResultSet rs = stm.executeQuery();
			rs.last();
			if (rs.getRow() != 0) {
				return new Account((String) rs.getObject(DbContract.AccountsTable.COLUMN_NAME_USERNAME),
						(String) rs.getObject(DbContract.AccountsTable.COLUMN_NAME_PASSWORD));
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<String> getAllUsernames() {
		ArrayList<String> allUsers = new ArrayList<>();
		String query = "Select " + DbContract.AccountsTable.COLUMN_NAME_USERNAME + " From " + DbContract.AccountsTable.TABLE_NAME;
		try (Connection con = DriverManager.getConnection("jdbc:mysql://" + server, account, password);
				Statement stmt = con.createStatement()) {
			stmt.executeQuery("USE " + database);
			try (PreparedStatement ps = con.prepareStatement(query)) {
				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						allUsers.add(rs.getString(DbContract.AccountsTable.COLUMN_NAME_USERNAME));
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return allUsers;
	}

	/**
	 * returns Person object based on UserId (from Accounts table)
	 * 
	 * @throws SQLException
	 */
	public Person getPersonByUserId(int searchedUserId) throws SQLException {
		try (Connection connection = createConnection()) {
			String query = generateQueryForPerson();
			PreparedStatement stm = connection.prepareStatement(query);
			stm.setString(1, Integer.toString(searchedUserId));
			ResultSet rs = stm.executeQuery();
			rs.last();
			if (rs.getRow() != 0) {
				String gender = (String) rs.getObject(DbContract.PersonsTable.COLUMN_NAME_GENDER);
				Gender curGender;
				if (gender.equals(DbContract.PersonsTable.ENUM_MALE)) {
					curGender = Gender.MALE;
				} else {
					curGender = Gender.FEMALE;
				}
				return new Person((String) rs.getObject(DbContract.PersonsTable.COLUMN_NAME_FIRSTNAME),
						(String) rs.getObject(DbContract.PersonsTable.COLUMN_NAME_LASTNAME), 
						(String)rs.getObject(DbContract.PersonsTable.COLUMN_NAME_EMAIL), curGender,
						(Date) rs.getObject(DbContract.PersonsTable.COLUMN_NAME_BIRTHDATE));
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;

	}
	
	
	/**
	 * returns user's id number based on user name
	 * @param username
	 * @return id number of user
	 * @throws SQLException
	 */
	public int getUserIdByUserName(String username) throws SQLException {
		System.out.println("username " + username);
		String query = "SELECT account_id FROM Accounts WHERE account_user_name = ?";
		
		try (Connection connection = createConnection()) {
			PreparedStatement stm = connection.prepareStatement(query);
			stm.setString(1, username);
			ResultSet rs = stm.executeQuery();
			rs.last();
			int result = rs.getInt("account_id");
			return result;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	private String generateQueryForAccount() {
		return "SELECT *, COUNT(1) as count FROM " + DbContract.AccountsTable.TABLE_NAME + " WHERE "
				+ DbContract.AccountsTable.COLUMN_NAME_USERNAME + " like " + "?";
	}

	private String generateQueryForPerson() {
		return "SELECT * FROM " + DbContract.PersonsTable.TABLE_NAME + " WHERE " + DbContract.PersonsTable.TABLE_NAME
				+ "." + DbContract.PersonsTable.COLUMN_NAME_PERSON_ID + " = (SELECT "
				+ DbContract.PersonAccountMapTable.TABLE_NAME + "."
				+ DbContract.PersonAccountMapTable.COLUMN_NAME_PERSON_ID + " FROM "
				+ DbContract.PersonAccountMapTable.TABLE_NAME + " WHERE " + DbContract.PersonAccountMapTable.TABLE_NAME
				+ "." + DbContract.PersonAccountMapTable.COLUMN_NAME_ACCOUNT_ID + " = ?)";
	}
	
	private String generateQueryForPerson1() {
		return "SELECT *, COUNT(1) as count FROM " + DbContract.PersonsTable.TABLE_NAME + " WHERE "
				+ DbContract.PersonsTable.COLUMN_NAME_EMAIL + " like " + "?";
	}
	
	/**
	 * Checks if username is already in database
	 * @param username username which must be checked in database
	 * @return true if username isn't in database else return false
	 */
	public boolean usernameIsAvailable(String username) {
		String sql = generateQueryForAccount();
		return chechkIfAvailableInDatabase(sql, username);
	}
	
	/**
	 * Checks if email is already in database
	 * @param email email which must be checked in database
	 * @return true if email isn't in database else return false
	 */
	public boolean emailIsAvailable(String email) {
		String sql = generateQueryForPerson1();
		return chechkIfAvailableInDatabase(sql, email);
	}

	/**
	 * @param executes this query 
	 * @param compare string which must be set on ? in query
	 * @return false if exists compare in database else true
	 */
	private boolean chechkIfAvailableInDatabase(String sql, String compare) {
		try (Connection con = DriverManager.getConnection("jdbc:mysql://" + server, UserDao.account, UserDao.password);
				Statement stmt = con.createStatement()) {
			stmt.executeQuery("USE " + database);
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, compare);
			System.out.println(st.toString());
			ResultSet rs = st.executeQuery();
			rs.next();
			if(rs.getInt("count") != 0) {
				System.out.println(rs.getRow());
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	

	/**
	 * Adds new person in database
	 * @param person person who must be added in database
	 */
	private void addPerson(Person person) {
		try (Connection con = DriverManager.getConnection("jdbc:mysql://" + server, UserDao.account, UserDao.password);
				Statement stmt = con.createStatement()) {
			stmt.executeQuery("USE " + database);
			String sql = "INSERT INTO " + DbContract.PersonsTable.TABLE_NAME + "("
					+ DbContract.PersonsTable.COLUMN_NAME_FIRSTNAME + ", "
					+ DbContract.PersonsTable.COLUMN_NAME_LASTNAME + ", " 
					+ DbContract.PersonsTable.COLUMN_NAME_EMAIL + ", "
					+ DbContract.PersonsTable.COLUMN_NAME_GENDER + ", " 
					+ DbContract.PersonsTable.COLUMN_NAME_BIRTHDATE + ")" + 
					" values (?, ?, ?, ?, ?);";
			
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, person.getName());
			st.setString(2, person.getLastName());
			st.setString(3, person.getEmail());
			st.setString(4, "" + person.getGender());
			st.setTimestamp(5, new java.sql.Timestamp(person.getBirthDate().getTime()));
			st.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds account in database
	 * @param account account which must be added in database
	 */
	private void addAccount(Account account) {
		try (Connection con = DriverManager.getConnection("jdbc:mysql://" + server, UserDao.account, UserDao.password);
				Statement stmt = con.createStatement()) {
			stmt.executeQuery("USE " + database);

			String sql = "INSERT INTO " + DbContract.AccountsTable.TABLE_NAME + " ("
					+ DbContract.AccountsTable.COLUMN_NAME_USERNAME + ", "
					+ DbContract.AccountsTable.COLUMN_NAME_PASSWORD + ") VALUES (?, ?);";
			
			try (PreparedStatement st = con.prepareStatement(sql)) {
				st.setString(1, account.getUserName());
				st.setString(2, account.getPassword());
				st.execute();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * maps person and account to each other and adds in database
	 * @param person 
	 * @param account
	 */
	private void addUser(Person person, Account account) {
		try (Connection con = DriverManager.getConnection("jdbc:mysql://" + server, UserDao.account, UserDao.password);
				Statement stmt = con.createStatement()) {
			stmt.executeQuery("USE " + database);
			int personId = getPersonId(person, con);
			int accountId = getAccountId(account, con);
			String sql = "INSERT INTO " + DbContract.PersonAccountMapTable.TABLE_NAME + " ("
					+ DbContract.PersonAccountMapTable.COLUMN_NAME_ACCOUNT_ID + ", "
					+ DbContract.PersonAccountMapTable.COLUMN_NAME_PERSON_ID + ") VALUES (?, ?);";
			
			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, accountId);
			st.setInt(2, personId);
			System.out.println(st.toString());
			st.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Searches id of person in database
	 * @param person person which we must find in database
	 * @param connection connection with database
	 * @return id of person in database
	 * @throws SQLException
	 */
	private int getPersonId(Person person, Connection connection) throws SQLException{
		String sql = "SELECT " + DbContract.PersonsTable.COLUMN_NAME_PERSON_ID + " FROM " + 
		DbContract.PersonsTable.TABLE_NAME + " WHERE " + DbContract.PersonsTable.COLUMN_NAME_EMAIL + 
		" like " + "?";
		PreparedStatement st = connection.prepareStatement(sql);
		st.setString(1, person.getEmail());
		ResultSet rs = st.executeQuery();
		int personId = -1;
		if(rs.next())
			personId = rs.getInt(DbContract.PersonsTable.COLUMN_NAME_PERSON_ID);
		return personId;
	}
	
	/**
	 * Searches id of account in database
	 * @param account account which we must find it database
	 * @param connection connection with database
	 * @return id of account in database
	 * @throws SQLException
	 */
	private int getAccountId(Account account, Connection connection) throws SQLException {
		String sql = "SELECT " + DbContract.AccountsTable.COLUMN_NAME_ID + " FROM " + 
				DbContract.AccountsTable.TABLE_NAME + " WHERE " + DbContract.AccountsTable.COLUMN_NAME_USERNAME + 
				" like " + "?";
		PreparedStatement st = connection.prepareStatement(sql);
		st.setString(1, account.getUserName());
		ResultSet rs = st.executeQuery();
		int accountId = -1;
		if (rs.next())
			accountId = rs.getInt(DbContract.AccountsTable.COLUMN_NAME_ID);
		return accountId;
	}

	/**
	 * Adds new user person and account in database
	 * @param person 
	 * @param account
	 */
	public void addNewUser(Person person, Account account) {
		addPerson(person);
		addAccount(account);
		addUser(person, account);
	}
}
