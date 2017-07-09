package db.bean.quiz;

import java.util.ArrayList;
import java.util.List;

public class Question {

	private String note;
	private List<Subquestion> subquestions;
	private List<Option> options;
	private int type;
	private int questionID;
	private boolean isOrderSensitive;

	/**
	 * Constructor for the Question object
	 * 
	 * @param type
	 *            of the question
	 */
	public Question(int type) {
		this.type = type;
		subquestions = new ArrayList<Subquestion>();
		options = new ArrayList<Option>();
		isOrderSensitive = false;
	}

	/**
	 * Sets question whether the answers should be listed as it's creator
	 * entered or not
	 * 
	 * @param isOrderSensitive
	 *            if question's answers are order sensitive or not.
	 */
	public void setOrderSensitive(boolean isOrderSensitive) {
		this.isOrderSensitive = isOrderSensitive;
	}

	/**
	 * Returns whether question's answers are order sensitive or not
	 * 
	 * @return true if it is order sensitive, false otherwise.
	 */
	public boolean isOrderSensitive() {
		return isOrderSensitive;
	}

	/**
	 * Set the note for the question
	 * 
	 * @param note
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * Returns the question's additional information
	 * 
	 * @return The question's additional information
	 */
	public String getNote() {
		return note;
	}

	/**
	 * Adds subquestion to the question
	 * 
	 * @param subquestion
	 *            which should be added
	 */
	public void addSubquestion(Subquestion subquestion) {
		subquestions.add(subquestion);
	}

	/**
	 * Removes subquestion from the question
	 * 
	 * @param subquestion
	 *            which should be removed
	 */
	public void removeSubquestion(Subquestion subquestion) {
		subquestions.remove(subquestion);
	}

	/**
	 * Returns the list of the question's subquestions
	 * 
	 * @return The list of the subquestions
	 */
	public List<Subquestion> getSubquestions() {
		return subquestions;
	}

	/**
	 * Adds option to the question
	 * 
	 * @param option
	 *            which should be added
	 */
	public void addOption(Option option) {
		options.add(option);
	}

	/**
	 * Removes option from the question
	 * 
	 * @param option
	 *            which should be removed
	 */
	public void removeOption(Option option) {
		options.remove(option);
	}

	/**
	 * Returns the list of the answer options of the question
	 * 
	 * @return The list of the options
	 */
	public List<Option> getOptions() {
		return options;
	}

	/**
	 * Sets the type of the question
	 * 
	 * @param type
	 *            of the question
	 */
	public void setQuestionType(int type) {
		this.type = type;
	}

	/**
	 * Returns the type of the question
	 * 
	 * @return The type of the question
	 */
	public int getQuestionType() {
		return type;
	}

	/**
	 * Set the id for the question
	 * 
	 * @param questionID
	 *            id of the question
	 */
	public void setQuestionID(int questionID) {
		this.questionID = questionID;
	}

	/**
	 * Returns the id of the question in database
	 * 
	 * @return The ID of the question
	 */
	public int getQuestionID() {
		return questionID;
	}
}
