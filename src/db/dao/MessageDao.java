package db.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
	public static String CONTEXT_ATTRIBUTE_NAME = "messageDataAccess";

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
		String query = "SELECT " + DbContract.messagesTable.COLUMN_NAME_SENDER_ID + ", "
				+ DbContract.messagesTable.COLUMN_NAME_RECIEVER_ID + ", " + DbContract.messagesTable.COLUMN_NAME_TEXT
				+ ", " + DbContract.messagesTable.COLUMN_NAME_TIME + " FROM " + DbContract.messagesTable.TABLE_NAME
				+ " WHERE (" + DbContract.messagesTable.COLUMN_NAME_SENDER_ID + " = ? AND "
				+ DbContract.messagesTable.COLUMN_NAME_RECIEVER_ID + " = ?) OR ("
				+ DbContract.messagesTable.COLUMN_NAME_RECIEVER_ID + " = ? AND "
				+ DbContract.messagesTable.COLUMN_NAME_SENDER_ID + " = ?) ORDER BY "
				+ DbContract.messagesTable.COLUMN_NAME_TIME;
		List<Note> result = new ArrayList<Note>();
		try (Connection con = DriverManager.getConnection("jdbc:mysql://" + server, account, password);
				Statement stmt = con.createStatement()) {
			stmt.executeQuery("USE " + database);
			try (PreparedStatement ps = con.prepareStatement(query)) {
				ps.setInt(1, firstUser);
				ps.setInt(2, secondUser);
				ps.setInt(3, firstUser);
				ps.setInt(4, secondUser);
				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						Note currValue = generateNote(rs);
						result.add(currValue);
					}
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
			senderID = rs.getInt(DbContract.messagesTable.COLUMN_NAME_SENDER_ID);
			recieverID = rs.getInt(DbContract.messagesTable.COLUMN_NAME_RECIEVER_ID);
			text = rs.getString(DbContract.messagesTable.COLUMN_NAME_TEXT);
			sentTime = rs.getTimestamp(DbContract.messagesTable.COLUMN_NAME_TIME);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Note note = new Note(senderID, recieverID, sentTime, text);
		return note;
	}

	/**
	 * Adds new note into database
	 * 
	 * @param note
	 *            note that should be added
	 */
	public boolean addNote(Note note) {
		boolean result = false;
		String query = "INSERT INTO " + DbContract.messagesTable.TABLE_NAME + " ("
				+ DbContract.messagesTable.COLUMN_NAME_SENDER_ID + ", "
				+ DbContract.messagesTable.COLUMN_NAME_RECIEVER_ID + ", " + DbContract.messagesTable.COLUMN_NAME_TEXT
				+ ", " + DbContract.messagesTable.COLUMN_NAME_TIME + ") VALUES (?, ?, ?, ?);";
		try (Connection con = DriverManager.getConnection("jdbc:mysql://" + server, account, password);
				Statement stmt = con.createStatement()) {
			stmt.executeQuery("USE " + database);
			try (PreparedStatement ps = con.prepareStatement(query)) {
				ps.setInt(1, note.getSenderID());
				ps.setInt(2, note.getRecieverID());
				ps.setString(3, note.getText());
				ps.setTimestamp(4, new java.sql.Timestamp(note.getDate().getTime()));
				result = ps.execute();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Looks for current user's recent messages, finds most recent ones
	 * 
	 * @param userID
	 *            current user
	 * @return list of the recent messages
	 */
	public List<Note> getRecentMessages(int userID) {
		List<Note> result = new ArrayList<Note>();
		String query = "SELECT " + DbContract.messagesTable.COLUMN_NAME_SENDER_ID + ", "
				+ DbContract.messagesTable.COLUMN_NAME_RECIEVER_ID + ", " + DbContract.messagesTable.COLUMN_NAME_TEXT
				+ ", " + DbContract.messagesTable.COLUMN_NAME_TIME + " FROM " + DbContract.messagesTable.TABLE_NAME
				+ " WHERE " + DbContract.messagesTable.COLUMN_NAME_MESSAGE_ID + " IN (SELECT MAX("
				+ DbContract.messagesTable.COLUMN_NAME_MESSAGE_ID + ") FROM " + DbContract.messagesTable.TABLE_NAME
				+ " GROUP BY (CASE WHEN " + DbContract.messagesTable.COLUMN_NAME_RECIEVER_ID + " = ? THEN "
				+ DbContract.messagesTable.COLUMN_NAME_SENDER_ID + " ELSE "
				+ DbContract.messagesTable.COLUMN_NAME_RECIEVER_ID + " END)) ORDER BY "
				+ DbContract.messagesTable.COLUMN_NAME_TIME + " DESC;";
		try (Connection con = DriverManager.getConnection("jdbc:mysql://" + server, account, password);
				Statement stmt = con.createStatement()) {
			stmt.executeQuery("USE " + database);
			try (PreparedStatement ps = con.prepareStatement(query)) {
				ps.setInt(1, userID);
				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						Note currValue = generateNote(rs);
						result.add(currValue);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public void addAdministationNote(String header, String note) throws SQLException {
		try (Connection connection = createConnection()) {
			String query = "INSERT INTO " + DbContract.AdminNotifications.TABLE_NAME + " ( "
					+ DbContract.AdminNotifications.COLUMN_NAME_NOTE_HEADER + " , "
					+ DbContract.AdminNotifications.COLUMN_NAME_NOTE + " ) values ( ? , ? )";
			PreparedStatement stm = connection.prepareStatement(query);
			stm.setString(1, header);
			stm.setString(2, note);
			stm.executeUpdate();
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
}
