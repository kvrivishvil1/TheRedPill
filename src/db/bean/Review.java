package db.bean;

import java.util.Date;

public class Review {
	private String text;
	private int numStars;
	private int accountId;
	private int quizId;
	private Date date;
	

	public Review(String text, int numStars, int accountId, int quizId, Date date) {
		this.text = text;
		this.numStars = numStars;
		this.accountId = accountId;
		this.quizId = quizId;
		this.date = date;
	}

	public String getText() {
		return text;
	}

	public int getNumStars() {
		return numStars;
	}

	public int getAccountId() {
		return accountId;
	}

	public int getQuizId() {
		return quizId;
	}
	
	public Date getDate(){
		return date;
	}

}
