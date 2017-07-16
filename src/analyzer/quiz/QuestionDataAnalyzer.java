package analyzer.quiz;

import java.util.List;

import db.bean.quiz.Option;
import db.bean.quiz.Question;
import db.bean.quiz.Subquestion;

public class QuestionDataAnalyzer {
	private int typeID;
	private String note;
	private boolean isOrderSensitive;
	private long timeLimit;
	private List<Subquestion> subquestions;
	private List<Option> options;

	/**
	 * Constructor for question analyzer
	 * 
	 * @param typeID
	 *            for question
	 * @param note
	 *            for question
	 * @param subquestions
	 *            list of subquestions
	 * @param options
	 *            list of options
	 * @param isOrderSensitive
	 *            true, if answers order is important. Otherwise, false.
	 * @param timeLimit
	 *            seconds if time is limited, -1 otherwise.
	 */
	public QuestionDataAnalyzer(int typeID, String note, List<Subquestion> subquestions, List<Option> options,
			boolean isOrderSensitive, long timeLimit) {
		this.typeID = typeID;
		this.note = note;
		this.subquestions = subquestions;
		this.options = options;
		this.isOrderSensitive = isOrderSensitive;
		this.timeLimit = timeLimit;
	}

	/**
	 * Returns single Question object for passed data
	 * 
	 * @return An analyzed Question object
	 */
	public Question getQuestion() {
		Question question = new Question(typeID);
		question.setNote(note);
		for (int i = 0; i < subquestions.size(); i++) {
			question.addSubquestion(subquestions.get(i));
		}
		for (int i = 0; i < options.size(); i++) {
			question.addOption(options.get(i));
		}
		question.setOrderSensitive(isOrderSensitive);
		question.setTimeLimit(timeLimit);
		return question;
	}

}
