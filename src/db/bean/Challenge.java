package db.bean;

import java.util.Date;

public class Challenge extends Message {
	private int maxScore;
	private String quizChallnged;
	
	/**
	 * Constructor for a challenge object with the specified parameters
	 * @param senderID The user ID of challenge sender
	 * @param recieverID The user ID of challenge reciever
	 * @param date The challenge creation date
	 * @param link The link of this challenge
	 * @param maxScore Challenger's max score in this quiz
	 */
	public Challenge(String quizChallnged, int senderID, int recieverID, Date date, int maxScore){
		this.senderID = senderID;
		this.recieverID = recieverID;
		this.date = new Date(date.getTime());
		this.quizChallnged = quizChallnged;
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
	 * @return the sender's max score
	 */
	public int getMaxScore() {
		return maxScore;
	}
	
	public String getQuizName() {
		return quizChallnged;
	}
	
	@Override
	public String toString() {
		StringBuilder build = new StringBuilder();
		build.append("Challenge Sender: " + senderID + "\n");
		build.append("Challenge Receiver: " + recieverID + "\n");
		build.append("Challenge Date: " + date.toString() + "\n");
		build.append("Quiz name: " + quizChallnged + "\n");
		build.append("Challenge MaxScore: " + maxScore);
		return build.toString();
	}

}
