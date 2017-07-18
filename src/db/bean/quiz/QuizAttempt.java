package db.bean.quiz;

import java.util.Date;

public class QuizAttempt {
	private int quizAttemptID;
	private int quizID;
	private int accountID;
	private int score;
	private Date startTime;
	private Date finishTime;
	
	/**
	 * Constructor for a quiz attempt object with the specified parameters
	 * @param quizID 
	 * @param accountID 
	 */
	public QuizAttempt(int quizID, int accountID) {
		this.quizID = quizID;
		this.accountID = accountID;
	}
	
	/**
	 * sets score to quiz attempt object
	 * @param score
	 */
	public void setScore(int score) {
		this.score = score;
	}
	
	/**
	 * sets start time to quiz attempt object
	 * @param startTime
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	/**
	 * sets finish time to quiz attempt object
	 * @param finishTime
	 */
	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}
	
	/**
	 * sets quiz attempt id to quiz attempt object
	 * @param quizAttemptID
	 */
	public void setQuizAttemptID(int quizAttemptID) {
		this.quizAttemptID = quizAttemptID;
	}
	
	/**
	 * @return quiz id
	 */
	public int getQuizID() {
		return quizID;
	}
	
	/**
	 * @return account id
	 */
	public int getAccountID() {
		return accountID;
	}
	
	/**
	 * @return score
	 */
	public int getScore() {
		return score;
	}
	
	/**
	 * @return start time
	 */
	public Date getStartTime() {
		return startTime;
	}
	
	/**
	 * @return finish time
	 */
	public Date getFinishTime() {
		return finishTime;
	}

	/**
	 * @return quiz attempt id
	 */
	public int getQuizAttemptID() {
		return quizAttemptID;
	}
	
	@Override
	public boolean equals(Object object) {
		if(this == object) 
			return true;
		if(!(object instanceof QuizAttempt))
			return false; 
		QuizAttempt attempt = (QuizAttempt)object; 
		boolean first = quizID == attempt.getQuizID() && 
				accountID == attempt.getAccountID() && 
				quizAttemptID == attempt.getQuizAttemptID() && 
				score == attempt.getScore();
		boolean second = true;
		boolean third = true;
		if(startTime != null)
			second = startTime.equals(attempt.startTime);
		if(finishTime != null)
			third = finishTime.equals(attempt.getFinishTime());
		return first && second && third;
	}
}