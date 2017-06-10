package db.bean;

import java.util.Date;

public class Note extends Message {
	private String note;
	
	public Note(int senderID, int recieverID, Date date, String note){
		this.senderID = senderID;
		this.recieverID = recieverID;
		this.date = new Date(date.getTime());
		this.note = note;
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
	 * Returns the note from sender
	 * @return the note from sender
	 */
	public String getNote() {
		return note;
	}

}
