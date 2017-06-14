package db.bean;

import java.util.Date;

public class Note extends Message {
	private String text;

	/**
	 * Constructor for a note object with specified parameters
	 * 
	 * @param senderID The user ID of note sender
	 * @param recieverID The user ID of note reciever
	 * @param date The note creation date
	 * @param text Content of the note
	 */
	public Note(int senderID, int recieverID, Date date, String text) {
		this.senderID = senderID;
		this.recieverID = recieverID;
		this.date = new Date(date.getTime());
		this.text = text;
	}

	@Override
	public int getSenderID() {
		return senderID;
	}

	@Override
	public int getRecieverID() {
		return recieverID;
	}

	@Override
	public Date getDate() {
		return new Date(date.getTime());
	}

	/**
	 * Returns the text from sender
	 * 
	 * @return the text from sender
	 */
	public String getText() {
		return text;
	}

	@Override
	public String toString() {
		StringBuilder build = new StringBuilder();
		build.append("Note Sender: " + senderID + "\n");
		build.append("Note Reciever: " + recieverID + "\n");
		build.append("Sending Date: " + date.toString() + "\n");
		build.append("Note: " + text);
		return build.toString();
	}
}
