package db.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import db.DbContract;
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

	private Connection connection;

	public UserDao() throws ClassNotFoundException {
		try {
			createConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void createConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		connection = DriverManager.getConnection("jdbc:mysql://" + server, account, password);
		Statement stm = connection.createStatement();
		stm.executeQuery("USE " + database);
	}

	/**
	 * gets user-name String as parameter and returns Account object
	 */
	public Account getAccount(String searchedUser) throws SQLException {
		String query = "SELECT * FROM " + DbContract.AccountsTable.TABLE_NAME + " WHERE " + DbContract.AccountsTable.COLUMN_NAME_USERNAME + " = ?";
		PreparedStatement stm = connection.prepareStatement(query);
		stm.setString(1, searchedUser);
		ResultSet rs = stm.executeQuery();
		rs.last();
		if (rs.getRow() != 0) {
			return new Account((String) rs.getObject(DbContract.AccountsTable.COLUMN_NAME_USERNAME),
					(String) rs.getObject(DbContract.AccountsTable.COLUMN_NAME_PASSWORD));
		}
		return null;
	}

	/**
	 * returns Person object based on UserId (from Accounts table)
	 * 
	 * @throws SQLException
	 */
	public Person getPersonByUserId(int searchedUserId) throws SQLException {
		String query = "SELECT * FROM " + DbContract.PersonsTable.TABLE_TABLE + " WHERE " + DbContract.PersonsTable.TABLE_TABLE + "."
				+ DbContract.PersonsTable.COLUMN_NAME_PERSON_ID + " = (SELECT " + DbContract.PersonAccountMapTable.TABLE_NAME + "."
				+ DbContract.PersonAccountMapTable.COLUMN_NAME_PERSON_ID + " FROM " + DbContract.PersonAccountMapTable.TABLE_NAME + " WHERE "
				+ DbContract.PersonAccountMapTable.TABLE_NAME + "." + DbContract.PersonAccountMapTable.COLUMN_NAME_ACCOUNT_ID + " = ?)";
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
					(String) rs.getObject(DbContract.PersonsTable.COLUMN_NAME_LASTNAME), curGender,
					(Date) rs.getObject(DbContract.PersonsTable.COLUMN_NAME_BIRTHDATE));
		}
		return null;

	}
}