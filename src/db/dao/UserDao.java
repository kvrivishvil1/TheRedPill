package db.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.DbContract;
import db.MyDbInfo;
import db.bean.Account;

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
		String query = "SELECT * FROM " + DbContract.ACCOUNTS_TABLE + " where " + DbContract.USER_NAME_COLUMN + " = ?";
		PreparedStatement stm = connection.prepareStatement(query);
		stm.setString(1, searchedUser);
		ResultSet rs = stm.executeQuery();
		rs.last();
		if (rs.getRow() != 0) {
			return new Account((String) rs.getObject(DbContract.USER_NAME_COLUMN),
					(String) rs.getObject(DbContract.PASSWORD_COLUMN));
		}
		return null;
	}
}
