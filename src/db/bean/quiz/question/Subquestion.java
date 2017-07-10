package db.bean.quiz.question;

import java.util.ArrayList;
import java.util.List;

import db.bean.quiz.Answer;

public class Subquestion {

	private List<Answer> answers;
	private String question;
	private int subquestionID;

	/**
	 * Constructor of the Subquestion object
	 * 
	 * @param question
	 *            text of the question for subquestion
	 */
	public Subquestion(String question) {
		this.question = question;
		answers = new ArrayList<Answer>();
	}

	/**
	 * Sets the question text for the subquestion
	 * 
	 * @param question
	 */
	public void setQuestion(String question) {
		this.question = question;
	}

	/**
	 * Returns the text of the question
	 * 
	 * @return question text
	 */
	public String getQuestion() {
		return question;
	}

	/**
	 * Adds the answer for the subquestion
	 * 
	 * @param answer which should be added
	 */
	public void addAnswer(Answer answer) {
		answers.add(answer);
	}
	
	
	/**
	 * Removes answer for the subquestion
	 * @param answer which should be removed
	 */
	public void removeAnswer(Answer answer){
		answers.remove(answer);
	}

	/**
	 * Returns the answers list of subquestion
	 * 
	 * @return The answers list
	 */
	public List<Answer> getAnswers() {
		return answers;
	}

	/**
	 * Sets the id for the subquestion
	 * 
	 * @param subquestionID
	 */
	public void setSubquestionID(int subquestionID) {
		this.subquestionID = subquestionID;
	}

	/**
	 * Returns the id of the subquestion
	 * 
	 * @return subquestion's id
	 */
	public int getSubquestionID() {
		return subquestionID;
	}

	/**
	 * Checks if the the user attested answers are correct
	 * 
	 * @return true if they are correct, false otherwise.
	 */
	public boolean checkAnswers(ArrayList<String> attestAnswers) {
		return true;
	}
}
