package db.bean.quiz;

import java.util.ArrayList;
import java.util.List;

import db.bean.quiz.question.Question;

public class Quiz {
	private String name;
	private boolean isRearrangable;
	private boolean isPracticable;
	private List<Question> questions;
	private String description;
	private String category;
	private List<String> tags;
	private long timeLimit;

	public Quiz(String name, String category) {
		this.name = name;
		this.category = category;
		tags = new ArrayList<String>();
		questions = new ArrayList<Question>();
	}

	/**
	 * Sets the name of the quiz
	 * @param name of the quiz
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the name of  the quiz
	 * 
	 * @return The name of quiz
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets quiz questions are rearrangeable or not
	 * 
	 * @param true if it is rearrange, false otherwise
	 */
	public void setRearrangableMode(boolean isRearrangable) {
		this.isRearrangable = isRearrangable;
	}

	/**
	 * Returns whether quiz questions are rearrangeable or not
	 * 
	 * @return true if they are, false otherwise.
	 */
	public boolean isRearrangable() {
		return isRearrangable;
	}

	/**
	 * Sets quiz questions are rearrangeable or not
	 * 
	 * @param true if it is practicable, false otherwise.
	 */
	public void setPracticableMode(boolean isPracticable) {
		this.isPracticable = isPracticable;
	}

	/**
	 * Returns whether quiz can be done in practice mode or not
	 * 
	 * @return true if it can be, false otherwise.
	 */
	public boolean isPracticable() {
		return isPracticable;
	}

	/**
	 * Returns all questions of quiz
	 * 
	 * @return The list of questions
	 */
	public List<Question> getAllQuestions() {
		return questions;
	}

	/**
	 * Returns the number of questions in quiz
	 * 
	 * @return The number of questions
	 */
	public int getNumQuestions() {
		return questions.size();
	}

	/**
	 * Adds the new question into quiz
	 * 
	 * @param newQuestion
	 *            The question to add
	 */
	public void addQuestion(Question newQuestion) {
		questions.add(newQuestion);
	}

	/**
	 * Sets description for the quiz
	 * 
	 * @param description, The text of the quiz description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Return description of the quiz
	 * 
	 * @return The text of the quiz description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the time limit for the quiz
	 * 
	 * @param timeLimit Time which should be the limit for the quiz
	 */
	public void setTimeLimit(long timeLimit) {
		this.timeLimit = timeLimit;
	}

	/**
	 * Return the time limit of the quiz
	 * 
	 * @return Time which is the limit for the quiz
	 */
	public long getTimeLimit() {
		return timeLimit;
	}

	/**
	 * Sets category information for the quiz
	 * 
	 * @param category The name of the quiz category
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * Returns the name of the category of the quiz
	 * 
	 * @return The name of the quiz category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * Adds tag for the quiz's tags
	 * 
	 * @param tag The name of the tag which should be added
	 */
	public void addTag(String tag) {
		tags.add(tag);
	}
	/**
	 * Removes tag from the quiz's tags
	 * @param tag The name of the tag which should be removed
	 */
	public void removeTag(String tag){
		tags.remove(tag);
	}

	/**
	 * Returns the list of tags of the quiz
	 * 
	 * @return The list of tags of the quiz
	 */
	public List<String> getTags() {
		return tags;
	}

}
