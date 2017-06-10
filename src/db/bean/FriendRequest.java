package db.bean;

import java.util.Date;

public class FriendRequest extends Message {
	
	public FriendRequest(int senderID, int recieverID, Date date){
		this.senderID = senderID;
		this.recieverID = recieverID;
		this.date = new Date(date.getTime());
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

}
