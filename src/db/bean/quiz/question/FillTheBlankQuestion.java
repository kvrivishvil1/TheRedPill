package db.bean.quiz.question;

import java.util.ArrayList;
import java.util.List;

import db.bean.quiz.Answer;

public class FillTheBlankQuestion extends Question{
	private String question;
	private List<Answer> answers;
	
	
	/**
	 * Constructor for CompletionQuestion object 
	 * @param question The question's text
	 * @param answers The question's answers
	 */
	public FillTheBlankQuestion (String question, List<Answer> answers){
		this.question = question;
		this.answers = answers;
	}

	@Override
	public String getQuestion() {
		return question;
	}

	@Override
	public List<Answer> getAnswers() {
		return new ArrayList<>(answers);
	}

	@Override
	public int getQuestionType() {
		return QuestionTypeContract.FILL_IN_THE_BLANK;
	}

}
