package db.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import db.DbContract;
import db.MyDbInfo;
import db.bean.Note;

public class MessageDao {

	private static String account = MyDbInfo.MYSQL_USERNAME;
	private static String password = MyDbInfo.MYSQL_PASSWORD;
	private static String server = MyDbInfo.MYSQL_DATABASE_SERVER;
	private static String database = MyDbInfo.MYSQL_DATABASE_NAME;

	public MessageDao() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Finds full conversation between two users by their id.
	 * 
	 * @param firstUser
	 *            one of the user involved in the conversation
	 * @param secondUser
	 *            second one involved in the conversation
	 * @return list of their conversation, sorted by time of sent
	 */
	public List<Note> getConversation(int firstUser, int secondUser) {
		List<Note> result = new ArrayList<Note>();
		try (Connection con = DriverManager.getConnection("jdbc:mysql://" + server, account, password);
				Statement stmt = con.createStatement()) {
			stmt.executeQuery("USE " + database);
			String query = "SELECT " + DbContract.MessagesTable.COLUMN_NAME_SENDER_ID + ", "
					+ DbContract.MessagesTable.COLUMN_NAME_RECIEVER_ID + ", "
					+ DbContract.MessagesTable.COLUMN_NAME_TEXT + ", " + DbContract.MessagesTable.COLUMN_NAME_TIME
					+ " FROM " + DbContract.MessagesTable.TABLE_NAME + " WHERE ("
					+ DbContract.MessagesTable.COLUMN_NAME_SENDER_ID + " = " + firstUser + " AND "
					+ DbContract.MessagesTable.COLUMN_NAME_RECIEVER_ID + " = " + secondUser + ") OR ("
					+ DbContract.MessagesTable.COLUMN_NAME_RECIEVER_ID + " = " + firstUser + " AND "
					+ DbContract.MessagesTable.COLUMN_NAME_SENDER_ID + " = " + secondUser + ") ORDER BY "
					+ DbContract.MessagesTable.COLUMN_NAME_TIME;
			System.out.println(query);
			try (ResultSet rs = stmt.executeQuery(query)) {
				while (rs.next()) {
					Note currValue = generateNote(rs);
					result.add(currValue);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/*
	 * Gets ResultSet and takes first Note type object from it.
	 */
	private Note generateNote(ResultSet rs) {
		int senderID = 0, recieverID = 0;
		String text = "";
		Date sentTime = null;
		try {
			senderID = rs.getInt(DbContract.MessagesTable.COLUMN_NAME_SENDER_ID);
			recieverID = rs.getInt(DbContract.MessagesTable.COLUMN_NAME_RECIEVER_ID);
			text = rs.getString(DbContract.MessagesTable.COLUMN_NAME_TEXT);
			sentTime = rs.getDate(DbContract.MessagesTable.COLUMN_NAME_TIME);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Note note = new Note(senderID, recieverID, sentTime, text);
		return note;
	}

}
