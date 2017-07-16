package analyzer.quiz.subquestion;

import java.util.List;

import db.bean.quiz.Subquestion;

public abstract class SubquestionDataAnalyzer {
	protected String[] questions;
	protected String[] answers;
	protected String[] options;
	protected String[] optionIDs;
	protected String[] answerOptions;
	protected String parser;

	/**
	 * Returns the list of subquestions for passed data
	 * 
	 * @return The list of analyzed subquestions
	 */
	public abstract List<Subquestion> getSubquestions();

}
