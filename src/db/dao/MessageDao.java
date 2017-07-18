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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.sun.org.apache.bcel.internal.generic.Select;

import db.DbContract;
import db.MyDbInfo;
import db.bean.Challenge;
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
	
	private Connection createConnection() throws ClassNotFoundException, SQLException {
		Connection connection = DriverManager.getConnection("jdbc:mysql://" + server, account, password);
		Statement stm = connection.createStatement();
		stm.executeQuery("USE " + database);
		return connection;
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

	/**
	 * Gets ResultSet and takes first Note type object from it.
	 * @param rs
	 * @return first Note type object
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
	 * @param note note that should be added
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
	 * @param userID current user
	 * @return list of the recent messages
	 */
	public List<Note> getRecentMessages(int userID) {
		List<Note> result = new ArrayList<Note>();
		String query = generateQueryForRecentMessages();
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
	
	/**
	 * Generates query to retrieve data from database about 
	 * most recent messages for user from each account
	 * @return query
	 */
	private String generateQueryForRecentMessages(){
		String query = "SELECT " + DbContract.messagesTable.COLUMN_NAME_SENDER_ID + ", "
				+ DbContract.messagesTable.COLUMN_NAME_RECIEVER_ID + ", " + DbContract.messagesTable.COLUMN_NAME_TEXT
				+ ", " + DbContract.messagesTable.COLUMN_NAME_TIME + " FROM " + DbContract.messagesTable.TABLE_NAME
				+ " WHERE (GREATEST(" + DbContract.messagesTable.COLUMN_NAME_RECIEVER_ID + ", "
				+ DbContract.messagesTable.COLUMN_NAME_SENDER_ID + ") , LEAST("
				+ DbContract.messagesTable.COLUMN_NAME_RECIEVER_ID + ", "
				+ DbContract.messagesTable.COLUMN_NAME_SENDER_ID + "), " + DbContract.messagesTable.COLUMN_NAME_TIME
				+ ") IN (SELECT  GREATEST(" + DbContract.messagesTable.COLUMN_NAME_RECIEVER_ID + ", "
				+ DbContract.messagesTable.COLUMN_NAME_SENDER_ID + ") AS min_id, LEAST("
				+ DbContract.messagesTable.COLUMN_NAME_RECIEVER_ID + ", "
				+ DbContract.messagesTable.COLUMN_NAME_SENDER_ID + ") AS max_id,   MAX("
				+ DbContract.messagesTable.COLUMN_NAME_TIME + ") AS time_sent FROM "
				+ DbContract.messagesTable.TABLE_NAME + " GROUP BY min_id , max_id)  AND ("
				+ DbContract.messagesTable.COLUMN_NAME_SENDER_ID + " = ? OR "
				+ DbContract.messagesTable.COLUMN_NAME_RECIEVER_ID + " = ?) GROUP BY GREATEST("
				+ DbContract.messagesTable.COLUMN_NAME_RECIEVER_ID + ", "
				+ DbContract.messagesTable.COLUMN_NAME_SENDER_ID + ") , LEAST("
				+ DbContract.messagesTable.COLUMN_NAME_RECIEVER_ID + ", "
				+ DbContract.messagesTable.COLUMN_NAME_SENDER_ID + ") ORDER BY "
				+ DbContract.messagesTable.COLUMN_NAME_TIME + " DESC;";
		return query;
	}

	/**
	 * Adds notification written by administrator in database
	 * @param header
	 * @param note
	 */
	public void addAdministationNote(String header, String note)  {
		try (Connection connection = createConnection()) {
			String query = "INSERT INTO " + DbContract.adminNotificationsTable.TABLE_NAME + " ( "
					+ DbContract.adminNotificationsTable.COLUMN_NAME_NOTE_HEADER + " , "
					+ DbContract.adminNotificationsTable.COLUMN_NAME_NOTE + " ) values ( ? , ? )";
			PreparedStatement stm = connection.prepareStatement(query);
			stm.setString(1, header);
			stm.setString(2, note);
			stm.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @return all quiz names for current account
	 */
	public HashSet<Challenge> getAllChallngesForUser(int receiverID) {
		HashSet<Challenge> challenges = new HashSet<>();
		try (Connection connection = createConnection()) {
			String query = "SELECT " + DbContract.challengesTable.COLUMN_NAME_QUIZ_CHALLENGED + ", " + DbContract.challengesTable.COLUMN_NAME_SENDER_ID + ", " + 
									DbContract.challengesTable.COLUMN_NAME_TIME_SENT +", " + DbContract.challengesTable.COLUMN_NAME_SCORE_CHALLENGED  + 
									" FROM " + DbContract.challengesTable.TABLE_NAME + " WHERE " + DbContract.challengesTable.COLUMN_NAME_RECIEVER_ID + " = ?";
			PreparedStatement stm = connection.prepareStatement(query);
			stm.setInt(1, receiverID); 
			ResultSet rs = stm.executeQuery();
			while(rs.next())
				challenges.add(new Challenge(rs.getInt(1), rs.getInt(2), receiverID, rs.getDate(3), rs.getInt(4)));
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return challenges;
	}
	
	/**
	 * Deletes challenge from database with parameters:
	 * @param quizId
	 * @param senderId
	 * @param receiverId
	 */
	public void deleteChallenge(int quizId, int senderId, int receiverId) {
		try (Connection connection = createConnection()) {
			String query = "Delete FROM " + DbContract.challengesTable.TABLE_NAME + " WHERE " + 
						DbContract.challengesTable.COLUMN_NAME_SENDER_ID + " =? AND " + 
						DbContract.challengesTable.COLUMN_NAME_RECIEVER_ID + " =? AND " + 
						DbContract.challengesTable.COLUMN_NAME_QUIZ_CHALLENGED + " =?;";
			PreparedStatement stm = connection.prepareStatement(query);
			stm.setInt(1, senderId); 
			stm.setInt(2, receiverId);
			stm.setInt(3, quizId);
			stm.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * @return first 10 notes created by administrator
	 */
	public HashMap<String, String> getAdminstrationNote() {
		String query = "SELECT * FROM " + DbContract.adminNotificationsTable.TABLE_NAME + " ORDER BY "
				+ DbContract.adminNotificationsTable.COLUMN_NAME_NOTE_ID + " DESC LIMIT 10";
		try (Connection connection = createConnection()) {
			PreparedStatement stm = connection.prepareStatement(query);
			ResultSet rs = stm.executeQuery();
			HashMap<String, String> result = new HashMap<>();
			while (rs.next()) {
				String header = rs.getString(DbContract.adminNotificationsTable.COLUMN_NAME_NOTE_HEADER);
				String text = rs.getString(DbContract.adminNotificationsTable.COLUMN_NAME_NOTE);
				result.put(header, text);
			}
			return result;
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
