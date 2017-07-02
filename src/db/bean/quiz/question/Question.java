package db.bean.quiz.question;

import java.util.List;

import db.bean.quiz.Answer;

public abstract class Question {
	/**
	 * Returns the question's text
	 * @return The question's text
	 */
	public abstract String getQuestion();
	
	/**
	 * Returns the list of the question's answers
	 * @return The list of the answers
	 */
	public abstract List<Answer> getAnswers();
	
	/**
	 * Returns the type of the question
	 * @return The type of the question
	 */
	public abstract String getQuestionType();
}
