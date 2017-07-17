package managers;

import java.util.Date;
import java.util.HashSet;

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
}