package db.bean;

import java.util.Date;

public abstract class Message {
	protected int senderID;
	protected int recieverID;
	protected Date date;

	/**
	 * Returns the ID of this message sender
	 * @return the senderID
	 */
	public abstract int getSenderID();
	
	/**
	 * Returns the ID of this message reciever
	 * @return the recieverID
	 */
	public abstract int getRecieverID();
	
	/**
	 * Returns the date when the message was sent
	 * @return the date
	 */
	public abstract Date getDate();	
}
