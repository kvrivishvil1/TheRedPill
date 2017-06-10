package db.bean;

import java.util.Date;

public class Challenge extends Message {
	private String link;
	private int maxScore;
	
	public Challenge(int senderID, int recieverID, Date date, String link, int maxScore){
		this.senderID = senderID;
		this.recieverID = recieverID;
		this.date = new Date(date.getTime());
		this.link = link;
		this.maxScore = maxScore;
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
	 * Returns the link of this challenge
	 * @return the link
	 */
	public String getLink() {
		return link;
	}
	
	/**
	 * Returns the sender's max score
	 * @return the sender's max score
	 */
	public int getMaxScore() {
		return maxScore;
	}

}
