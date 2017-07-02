package db.bean.quiz;

import java.util.ArrayList;
import java.util.List;

import db.bean.quiz.question.Question;

public class Quiz {
	private String name;
	private boolean isRearrangable;
	private boolean isPracticable;
	private ArrayList<Question> questions;

	public Quiz(String name, boolean isPracticable, boolean isRearrangable) {
		this.name = name;
		this.isPracticable = isPracticable;
		this.isRearrangable = isRearrangable;
		questions = new ArrayList<>();
	}

	/**
	 * Returns the name of quiz
	 * @return The name of quiz
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns whether quiz questions are rearrangeable or not
	 * @return true if they are, false otherwise.
	 */
	public boolean isRearrangable() {
		return isRearrangable;
	}
	
	/**
	 * Returns whether quiz can be done in practice mode or not
	 * @return true if it can be, false otherwise.
	 */
	public boolean isPracticable() {
		return isPracticable;
	}

	/**
	 * Returns all questions of quiz
	 * @return The list of questions
	 */
	public List<Question> getAllQuestions() {
		return questions;
	}

	/**
	 * Returns the number of questions in quiz
	 * @return number of questions
	 */
	public int getNumQuestions() {
		return questions.size();
	}

	/**
	 * Adds the new question into quiz 
	 * @param newQuestion The question to add
	 */
	public void addQuestion(Question newQuestion) {
		questions.add(newQuestion);
	}

}
