package db.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.junit.Test;

import db.DbContract;
import db.DbContract.accountsTable;
import db.MyDbInfo;
import db.bean.Account;
import db.bean.Person;
import db.bean.Person.Gender;

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
				return new Account((String) rs.getObject(DbContract.accountsTable.COLUMN_NAME_USERNAME),
						(String) rs.getObject(DbContract.accountsTable.COLUMN_NAME_PASSWORD));
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<String> getAllUsernames() {
		ArrayList<String> allUsers = new ArrayList<>();
		String query = "Select " + DbContract.accountsTable.COLUMN_NAME_USERNAME + " From "
				+ DbContract.accountsTable.TABLE_NAME;
		try (Connection con = DriverManager.getConnection("jdbc:mysql://" + server, account, password);
				Statement stmt = con.createStatement()) {

			stmt.executeQuery("USE " + database);
			try (PreparedStatement ps = con.prepareStatement(query)) {
				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						allUsers.add(rs.getString(DbContract.accountsTable.COLUMN_NAME_USERNAME));
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
				String gender = (String) rs.getObject(DbContract.personsTable.COLUMN_NAME_GENDER);
				Gender curGender;
				if (gender.equals(DbContract.personsTable.ENUM_MALE)) {
					curGender = Gender.MALE;
				} else {
					curGender = Gender.FEMALE;
				}
				return new Person((String) rs.getObject(DbContract.personsTable.COLUMN_NAME_FIRSTNAME),
						(String) rs.getObject(DbContract.personsTable.COLUMN_NAME_LASTNAME),
						(String) rs.getObject(DbContract.personsTable.COLUMN_NAME_EMAIL), curGender,
						(Date) rs.getObject(DbContract.personsTable.COLUMN_NAME_BIRTHDATE));
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * returns user's id number based on user name
	 * 
	 * @param username
	 * @return id number of user
	 * @throws SQLException
	 */
	public int getUserIdByUserName(String username) throws SQLException {
		String query = "SELECT account_id FROM Accounts WHERE account_user_name = ?";

		try (Connection connection = createConnection()) {
			PreparedStatement stm = connection.prepareStatement(query);
			stm.setString(1, username);
			ResultSet rs = stm.executeQuery();
			if(rs.next()) {
				int result = rs.getInt("account_id");
				return result;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return -1;
	}

	private String generateQueryForAccount() {
		return "SELECT *, COUNT(1) as count FROM " + DbContract.accountsTable.TABLE_NAME + " WHERE "
				+ DbContract.accountsTable.COLUMN_NAME_USERNAME + " LIKE " + "?";
	}

	private String generateQueryForPerson() {
		return "SELECT * FROM " + DbContract.personsTable.TABLE_NAME + " WHERE " + DbContract.personsTable.TABLE_NAME
				+ "." + DbContract.personsTable.COLUMN_NAME_PERSON_ID + " = (SELECT "
				+ DbContract.personAccountMapTable.TABLE_NAME + "."
				+ DbContract.personAccountMapTable.COLUMN_NAME_PERSON_ID + " FROM "
				+ DbContract.personAccountMapTable.TABLE_NAME + " WHERE " + DbContract.personAccountMapTable.TABLE_NAME
				+ "." + DbContract.personAccountMapTable.COLUMN_NAME_ACCOUNT_ID + " = ?)";
	}

	private String generateQueryForPerson1() {
		return "SELECT *, COUNT(1) as count FROM " + DbContract.personsTable.TABLE_NAME + " WHERE "
				+ DbContract.personsTable.COLUMN_NAME_EMAIL + " like " + "?";
	}

	/**
	 * Checks if username is already in database
	 * 
	 * @param username
	 *            username which must be checked in database
	 * @return true if username isn't in database else return false
	 */
	public boolean usernameIsAvailable(String username) {
		String sql = generateQueryForAccount();
		return chechkIfAvailableInDatabase(sql, username);
	}

	/**
	 * Checks if email is already in database
	 * 
	 * @param email
	 *            email which must be checked in database
	 * @return true if email isn't in database else return false
	 */
	public boolean emailIsAvailable(String email) {
		String sql = generateQueryForPerson1();
		return chechkIfAvailableInDatabase(sql, email);
	}

	/**
	 * @param executes
	 *            this query
	 * @param compare
	 *            string which must be set on ? in query
	 * @return false if exists compare in database else true
	 */
	private boolean chechkIfAvailableInDatabase(String sql, String compare) {
		try (Connection con = DriverManager.getConnection("jdbc:mysql://" + server, UserDao.account, UserDao.password);
				Statement stmt = con.createStatement()) {
			stmt.executeQuery("USE " + database);
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, compare);
			ResultSet rs = st.executeQuery();
			rs.next();
			if (rs.getInt("count") != 0) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Adds new person in database
	 * 
	 * @param person
	 *            person who must be added in database
	 */
	private void addPerson(Person person) {
		try (Connection con = DriverManager.getConnection("jdbc:mysql://" + server, UserDao.account, UserDao.password);
				Statement stmt = con.createStatement()) {
			stmt.executeQuery("USE " + database);
			String sql = "INSERT INTO " + DbContract.personsTable.TABLE_NAME + "("
					+ DbContract.personsTable.COLUMN_NAME_FIRSTNAME + ", "
					+ DbContract.personsTable.COLUMN_NAME_LASTNAME + ", " + DbContract.personsTable.COLUMN_NAME_EMAIL
					+ ", " + DbContract.personsTable.COLUMN_NAME_GENDER + ", "
					+ DbContract.personsTable.COLUMN_NAME_BIRTHDATE + ")" + " values (?, ?, ?, ?, ?);";

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
	 * 
	 * @param account
	 *            account which must be added in database
	 */
	private void addAccount(Account account) {
		try (Connection con = DriverManager.getConnection("jdbc:mysql://" + server, UserDao.account, UserDao.password);
				Statement stmt = con.createStatement()) {
			stmt.executeQuery("USE " + database);

			String sql = "INSERT INTO " + DbContract.accountsTable.TABLE_NAME + " ("
					+ DbContract.accountsTable.COLUMN_NAME_USERNAME + ", "
					+ DbContract.accountsTable.COLUMN_NAME_PASSWORD + ", " + DbContract.accountsTable.COLUMN_NAME_STATUS
					+ ") VALUES (?, ?, 1);";

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
			String sql = "INSERT INTO " + DbContract.personAccountMapTable.TABLE_NAME + " ("
					+ DbContract.personAccountMapTable.COLUMN_NAME_ACCOUNT_ID + ", "
					+ DbContract.personAccountMapTable.COLUMN_NAME_PERSON_ID + ") VALUES (?, ?);";

			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, accountId);
			st.setInt(2, personId);
			st.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Searches id of person in database
	 * 
	 * @param person
	 *            person which we must find in database
	 * @param connection
	 *            connection with database
	 * @return id of person in database
	 * @throws SQLException
	 */
	private int getPersonId(Person person, Connection connection) throws SQLException {
		String sql = "SELECT " + DbContract.personsTable.COLUMN_NAME_PERSON_ID + " FROM "
				+ DbContract.personsTable.TABLE_NAME + " WHERE " + DbContract.personsTable.COLUMN_NAME_EMAIL + " like "
				+ "?";
		PreparedStatement st = connection.prepareStatement(sql);
		st.setString(1, person.getEmail());
		ResultSet rs = st.executeQuery();
		int personId = -1;
		if (rs.next())
			personId = rs.getInt(DbContract.personsTable.COLUMN_NAME_PERSON_ID);
		return personId;
	}

	/**
	 * Searches id of account in database
	 * 
	 * @param account
	 *            account which we must find it database
	 * @param connection
	 *            connection with database
	 * @return id of account in database
	 * @throws SQLException
	 */
	private int getAccountId(Account account, Connection connection) throws SQLException {
		String sql = "SELECT " + DbContract.accountsTable.COLUMN_NAME_ID + " FROM "
				+ DbContract.accountsTable.TABLE_NAME + " WHERE " + DbContract.accountsTable.COLUMN_NAME_USERNAME
				+ " like " + "?";
		PreparedStatement st = connection.prepareStatement(sql);
		st.setString(1, account.getUserName());
		ResultSet rs = st.executeQuery();
		int accountId = -1;
		if (rs.next())
			accountId = rs.getInt(DbContract.accountsTable.COLUMN_NAME_ID);
		return accountId;
	}

	/**
	 * Adds new user person and account in database
	 * 
	 * @param person
	 * @param account
	 */
	public void addNewUser(Person person, Account account) {
		addPerson(person);
		addAccount(account);
		addUser(person, account);
	}

	/**
	 * Deletes user person and account in database based on user name
	 * 
	 * @param username
	 */
	public void deleteUser(String username) {
		try {
			int accountID = getUserIdByUserName(username);
			int personID = getPersonIdByUserID(accountID);
			deleteUserChallenges(accountID);
			deleteUserMessages(accountID);
			deleteUserFreindships(accountID);
			deleteUserConnectionToPersonalInfo(accountID);
			deleteUserAccount(accountID);
			deleteUserPersonalInfo(personID);
			deleteAllFriendRequestsForUser(accountID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * deletes all requests where sender or receiver is current user
	 * @param accountID
	 */
	private void deleteAllFriendRequestsForUser(int accountID) {
		String query = "DELETE FROM " + DbContract.friendRequestTable.TABLE_NAME + " WHERE " + DbContract.friendRequestTable.COLUMN_NAME_SENDER_ID + "=? OR "
														+ DbContract.friendRequestTable.COLUMN_NAME_RECEIVER_ID + "=?";
		try (Connection connection = createConnection()) {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, accountID);
			statement.setInt(2, accountID);
			statement.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the person id number based on its account id number
	 * 
	 * @param AccountID
	 *            The aid number of the account
	 * @return The id number of the person
	 * @throws SQLException
	 */
	public int getPersonIdByUserID(int AccountID) throws SQLException {
		String query = "SELECT " + DbContract.personsTable.COLUMN_NAME_PERSON_ID + " FROM "
				+ DbContract.personAccountMapTable.TABLE_NAME + " WHERE "
				+ DbContract.personAccountMapTable.COLUMN_NAME_ACCOUNT_ID + " = ?";
		try (Connection connection = createConnection()) {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, AccountID);
			ResultSet rs = statement.executeQuery();
			rs.last();
			int result = rs.getInt("person_id");
			statement.close();
			return result;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * Deletes user personal information from database based on its id number
	 * 
	 * @param personID
	 *            The id number of person
	 * @throws SQLException
	 */
	private void deleteUserPersonalInfo(int personID) throws SQLException {
		String query = "DELETE FROM " + DbContract.personsTable.TABLE_NAME + " WHERE "
				+ DbContract.personsTable.COLUMN_NAME_PERSON_ID + " = ?";
		try (Connection connection = createConnection()) {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, personID);
			statement.executeUpdate();
			statement.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Deletes user account from database based on its id number of account
	 * 
	 * @param AccountID
	 *            The id number of account
	 * @throws SQLException
	 */
	private void deleteUserAccount(int accountID) throws SQLException {
		String query = "DELETE FROM " + DbContract.accountsTable.TABLE_NAME + " WHERE "
				+ DbContract.accountsTable.COLUMN_NAME_ID + " = ?";
		try (Connection connection = createConnection()) {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, accountID);
			statement.executeUpdate();
			statement.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Deletes connection between user personal information and account
	 * information
	 * 
	 * @param AccountID
	 *            The id number of account
	 * @throws SQLException
	 */
	private void deleteUserConnectionToPersonalInfo(int accountID) throws SQLException {
		String query = "DELETE FROM " + DbContract.personAccountMapTable.TABLE_NAME + " WHERE "
				+ DbContract.personAccountMapTable.COLUMN_NAME_ACCOUNT_ID + " = ?";
		try (Connection connection = createConnection()) {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, accountID);
			statement.executeUpdate();
			statement.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Deletes friendship connection between given user and other users
	 * 
	 * @param AccountID
	 *            The id number of account
	 * @throws SQLException
	 */
	private void deleteUserFreindships(int accountID) throws SQLException {
		String queryForAccountFirst = "DELETE FROM " + DbContract.friendsTable.TABLE_NAME + " WHERE "
				+ DbContract.friendsTable.COLUMN_NAME_ACCOUNT_FIRST + " = ?";
		String queyForAccountSecond = "DELETE FROM " + DbContract.friendsTable.TABLE_NAME + " WHERE "
				+ DbContract.friendsTable.COLUMN_NAME_ACCOUNT_SECOND + " = ?";
		try (Connection connection = createConnection()) {
			PreparedStatement statementForAccountFirst = connection.prepareStatement(queryForAccountFirst);
			PreparedStatement statementForAccountSecond = connection.prepareStatement(queyForAccountSecond);
			statementForAccountFirst.setInt(1, accountID);
			statementForAccountSecond.setInt(1, accountID);
			statementForAccountFirst.executeUpdate();
			statementForAccountSecond.executeUpdate();
			statementForAccountFirst.close();
			statementForAccountSecond.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Deletes user's sent messages and user's received messages based on its
	 * account id information
	 * 
	 * @param AccountID
	 *            The id number of account
	 * @throws SQLException
	 */
	private void deleteUserMessages(int accountID) throws SQLException {
		String queryForSendMessagess = "DELETE FROM " + DbContract.messagesTable.TABLE_NAME + " WHERE "
				+ DbContract.messagesTable.COLUMN_NAME_SENDER_ID + " = ?";
		String queyForRecievedMessages = "DELETE FROM " + DbContract.messagesTable.TABLE_NAME + " WHERE "
				+ DbContract.messagesTable.COLUMN_NAME_RECIEVER_ID + " = ?";
		try (Connection connection = createConnection()) {
			PreparedStatement statementForSendMessagess = connection.prepareStatement(queryForSendMessagess);
			PreparedStatement statementForRecievedChallenges = connection.prepareStatement(queyForRecievedMessages);
			statementForSendMessagess.setInt(1, accountID);
			statementForRecievedChallenges.setInt(1, accountID);
			statementForSendMessagess.executeUpdate();
			statementForRecievedChallenges.executeUpdate();
			statementForSendMessagess.close();
			statementForRecievedChallenges.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Deletes user's sent challenges and user's received challenges based on
	 * its account id information
	 * 
	 * @param AccountID
	 *            The id number of account
	 * @throws SQLException
	 */
	private void deleteUserChallenges(int accountID) throws SQLException {
		String queryForSendChallenges = "DELETE FROM " + DbContract.challengesTable.TABLE_NAME + " WHERE "
				+ DbContract.challengesTable.COLUMN_NAME_SENDER_ID + " = ?";
		String queyForRecievedChallenges = "DELETE FROM " + DbContract.challengesTable.TABLE_NAME + " WHERE "
				+ DbContract.challengesTable.COLUMN_NAME_RECIEVER_ID + " = ?";
		try (Connection connection = createConnection()) {
			PreparedStatement statementForSendChallenges = connection.prepareStatement(queryForSendChallenges);
			PreparedStatement statementForRecievedChallenges = connection.prepareStatement(queyForRecievedChallenges);
			statementForSendChallenges.setInt(1, accountID);
			statementForRecievedChallenges.setInt(1, accountID);
			statementForSendChallenges.executeUpdate();
			statementForRecievedChallenges.executeUpdate();
			statementForSendChallenges.close();
			statementForRecievedChallenges.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public long getNumUsers() throws SQLException {
		try (Connection connection = createConnection()) {
			String query = "SELECT * FROM " + DbContract.accountsTable.TABLE_NAME;
			PreparedStatement stm = connection.prepareStatement(query);
			ResultSet rs = stm.executeQuery();
			rs.last();
			return rs.getRow();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 
	 * selects all usernames containing given String
	 * 
	 * @param searchedWord
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<String> searchUser(String searchedWord)  {
		String query = "SELECT " + DbContract.accountsTable.COLUMN_NAME_USERNAME + " FROM "
				+ DbContract.accountsTable.TABLE_NAME + " WHERE " + DbContract.accountsTable.COLUMN_NAME_USERNAME
				+ " LIKE ?";
		try (Connection connection = createConnection()) {
			ArrayList<String> result = new ArrayList<>();
			PreparedStatement stm = connection.prepareStatement(query);
			stm.setString(1, searchedWord + "%");
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				result.add(rs.getString(DbContract.accountsTable.COLUMN_NAME_USERNAME));
			}
			return result;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean isAdmin(String userName, String password) {
		try (Connection connection = createConnection()) {
			String query = "SELECT * FROM " + DbContract.accountsTable.TABLE_NAME + " WHERE "
					+ DbContract.accountsTable.COLUMN_NAME_USERNAME + " LIKE ?";
			PreparedStatement stm = connection.prepareStatement(query);
			stm.setString(1, userName);
			ResultSet rs = stm.executeQuery();
			if (rs.next()) {
				if (rs.getInt(DbContract.accountsTable.COLUMN_NAME_STATUS) == 2) {
					String correctPassword = rs.getString(DbContract.accountsTable.COLUMN_NAME_PASSWORD);
					return correctPassword.equals(password);
				}
				return false;
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return false;

	}

	public void promoteAdmin(String userName) {
		try (Connection connection = createConnection()) {
			String query = "UPDATE " + DbContract.accountsTable.TABLE_NAME + " SET "
					+ DbContract.accountsTable.COLUMN_NAME_STATUS + " = 2  WHERE "
					+ DbContract.accountsTable.COLUMN_NAME_USERNAME + " = ?";
			PreparedStatement stm = connection.prepareStatement(query);
			stm.setString(1, userName);
			stm.executeUpdate();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param userID gets all usernames of users who sent friend request to current user
	 * @return arrayList of usernames
	 */
	public HashMap<String, String> getAllUsernamesFromFriendRequestsForUser(int userID) {
		HashMap<String, String> result = new HashMap<>();
		try (Connection connection = createConnection()) {
			String query = "SELECT " + DbContract.accountsTable.COLUMN_NAME_USERNAME + ", " + DbContract.personsTable.COLUMN_NAME_FIRSTNAME + ", " 
														+ DbContract.personsTable.COLUMN_NAME_LASTNAME + " FROM "
						+ "(SELECT " + DbContract.accountsTable.COLUMN_NAME_USERNAME + ", " + DbContract.accountsTable.COLUMN_NAME_ID +  " FROM " 
														+ DbContract.accountsTable.TABLE_NAME + " WHERE " + DbContract.accountsTable.COLUMN_NAME_ID + " IN "
						+ "(SELECT " + DbContract.friendRequestTable.COLUMN_NAME_SENDER_ID + " FROM " + DbContract.friendRequestTable.TABLE_NAME + " WHERE "
														+ DbContract.friendRequestTable.COLUMN_NAME_RECEIVER_ID + "=?)) as a " 
						+ "INNER JOIN "
						+ DbContract.personAccountMapTable.TABLE_NAME + " pam "
						+ "ON pam." + DbContract.personAccountMapTable.COLUMN_NAME_ACCOUNT_ID + " = a." + DbContract.accountsTable.COLUMN_NAME_ID + " "
						+ "INNER JOIN "
						+ DbContract.personsTable.TABLE_NAME +  " p "
						+ "ON p." + DbContract.personsTable.COLUMN_NAME_PERSON_ID + " = pam." + DbContract.personAccountMapTable.COLUMN_NAME_PERSON_ID + ";";
			PreparedStatement stm = connection.prepareStatement(query);
			stm.setInt(1, userID);
			ResultSet rs = stm.executeQuery();
			while(rs.next()) {
				String username = rs.getString(1);
				String firstName = rs.getString(2);
				String lastName = rs.getString(3);
				result.put(username, firstName + " " + lastName);
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * @param userIDgets all usernames of friends for current user 
	 * @return arrayList of usernames
	 */
	public HashMap<String, String> getAllFriendsForUser(int userID) {
		HashMap<String, String> result = new HashMap<>();
		try (Connection connection = createConnection()) {	
			String query = "SELECT " + DbContract.accountsTable.COLUMN_NAME_USERNAME + ", " + DbContract.personsTable.COLUMN_NAME_FIRSTNAME + ", " 
													+ DbContract.personsTable.COLUMN_NAME_LASTNAME + " FROM "
						+ "(Select " + DbContract.accountsTable.COLUMN_NAME_USERNAME + ", " + DbContract.accountsTable.COLUMN_NAME_ID + " FROM " + DbContract.accountsTable.TABLE_NAME + " WHERE " 
								+ DbContract.accountsTable.COLUMN_NAME_ID + " IN "
						+ "(SELECT " + DbContract.friendsTable.COLUMN_NAME_ACCOUNT_FIRST + " FROM " + DbContract.friendsTable.TABLE_NAME + " WHERE " 
														+ DbContract.friendsTable.COLUMN_NAME_ACCOUNT_SECOND + "=?) OR " 
								+ DbContract.accountsTable.COLUMN_NAME_ID + " IN "
						+ "(SELECT " + DbContract.friendsTable.COLUMN_NAME_ACCOUNT_SECOND + " FROM " + DbContract.friendsTable.TABLE_NAME + " WHERE " 
														+ DbContract.friendsTable.COLUMN_NAME_ACCOUNT_FIRST + "=?)) as a " 
						+ "INNER JOIN "
						+ DbContract.personAccountMapTable.TABLE_NAME + " pam "
						+ "ON pam." + DbContract.personAccountMapTable.COLUMN_NAME_ACCOUNT_ID + " = a." + DbContract.accountsTable.COLUMN_NAME_ID + " "
						+ "INNER JOIN "
						+ DbContract.personsTable.TABLE_NAME +  " p "
						+ "ON p." + DbContract.personsTable.COLUMN_NAME_PERSON_ID + " = pam." + DbContract.personAccountMapTable.COLUMN_NAME_PERSON_ID + ";";
			
			PreparedStatement stm = connection.prepareStatement(query);
			System.out.println(query);
			stm.setInt(1, userID);
			stm.setInt(2, userID);
			ResultSet rs = stm.executeQuery();
			while(rs.next()) {
				String username = rs.getString(1);
				String firstName = rs.getString(2);
				String lastName = rs.getString(3);
				result.put(username, firstName + " " + lastName);
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * adds friendship between those users in database
	 * @param first
	 * @param second
	 */
	public void addFriendshipInDatabase(String sender, String receiver) {
		DeleteFriendRequestFromDatabase(sender, receiver);
		String query = "INSERT INTO " + DbContract.friendsTable.TABLE_NAME + "(" + DbContract.friendsTable.COLUMN_NAME_ACCOUNT_FIRST + ", " 
				+ DbContract.friendsTable.COLUMN_NAME_ACCOUNT_SECOND + ")"
				+ " VALUES (?, ?);";
		int firstID;
		int secondID;
		try {
			firstID = getUserIdByUserName(sender);
			secondID = getUserIdByUserName(receiver);
			Connection connection = createConnection();
			PreparedStatement stm = connection.prepareStatement(query);
			stm.setInt(1, firstID);
			stm.setInt(2, secondID);
			stm.executeUpdate();
		} catch (SQLException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * Deletes from friend requests row which has
	 * @param sender as sender_id
	 * @param receiver as receiver_id
	 */
	public void DeleteFriendRequestFromDatabase(String sender, String receiver) {
		String query = "DELETE FROM " + DbContract.friendRequestTable.TABLE_NAME + " WHERE " + DbContract.friendRequestTable.COLUMN_NAME_SENDER_ID + "=? AND "
												+ DbContract.friendRequestTable.COLUMN_NAME_RECEIVER_ID + "=?;";
		int senderID;
		int receiverID;
		try {
			senderID = getUserIdByUserName(sender);
			receiverID = getUserIdByUserName(receiver);
			Connection connection = createConnection();
			PreparedStatement stm = connection.prepareStatement(query);
			stm.setInt(1, senderID);
			stm.setInt(2, receiverID);
			stm.executeUpdate();
		} catch (SQLException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * gets username of account with id
	 * @param accountID 
	 * @return username
	 */
	public String getUsernameByUserId(int accountID) {
		String query = "SELECT " + DbContract.accountsTable.COLUMN_NAME_USERNAME + " FROM " + DbContract.accountsTable.TABLE_NAME 
								+ " WHERE " + DbContract.accountsTable.COLUMN_NAME_ID + "= ?";
		String username = "";
		try {
			Connection connection = createConnection();
			PreparedStatement stm = connection.prepareStatement(query);
			stm.setInt(1, accountID);
			ResultSet rs = stm.executeQuery();
			rs.next();
			username = rs.getString(1);
		} catch (SQLException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		return username;
	}
	
}
