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
	public QuizAttempt(int quizAttemptID, int quizID, int accountID) {
		this.quizAttemptID = quizAttemptID;
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
}