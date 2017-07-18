package managers;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import db.bean.Challenge;
import db.bean.Note;
import db.dao.MessageDao;

public class MessageManager {
	private MessageDao messageDao;

	public MessageManager(MessageDao messageDao){
		this.messageDao = messageDao;
	}
	
	/**
	 * Calls messageDao addNote method with parameter Note
	 * creates Note with parameters:
	 * @param senderID
	 * @param recieverID
	 * @param text
	 * @param date
	 */
	public void sendNewNote(int senderID, int recieverID, String text, Date date){
		Note newNote = new Note(senderID, recieverID, date, text);
		messageDao.addNote(newNote);
	}
	
	/**
	 * Calls messageDao addNote method with parameters:
	 * @param userID
	 * @return Hashset of Challenges
	 */
	public HashSet<Challenge> getAllChallngesForUser(int userID) {
		return messageDao.getAllChallngesForUser(userID);
	}

	/**
	 * calls deleteChallenge if value equals Confirm
	 * else calss deleteFriendRequestFromDatabase
	 * @param value
	 * @param quizName
	 * @param senderId
	 */
	/**
	 * returns true if value equals Start Quiz
	 * else deletes change from database with parameters senderId, quizId, receiverId
	 * @param value
	 * @param senderId
	 * @param quizId
	 * @param receiverId
	 * @return
	 */
	public boolean challengeController(String value, int quizId, int senderId, int receiverId) {
		if(value.equals("Start Quiz"))
			return true;
		else {
			messageDao.deleteChallenge(quizId, senderId, receiverId);
		}
		return false;
	}
	

	/**
	 *  Calls getAdministrationNote method 
	 * @return Returns last ten administration notes
	 */
	public HashMap<String, String> getAdminstrationNote() {
		return messageDao.getAdminstrationNote();
	}
	
	/**
	 * Gets most recent messages for specified user
	 * @param userID for which recent messages should be taken
	 * @return The list of recent messages
	 */
	public List<Note> getRecentMessages(int userID){
		return messageDao.getRecentMessages(userID);
	}
	
	/**
	 * Gets conversation between two users
	 * @param firstUser first username
	 * @param secondUser second username
	 * @return The list of recent messages
	 */
	public List<Note> getConversation(int firstUser, int secondUser){
		return messageDao.getConversation(firstUser, secondUser);
	}	
}
