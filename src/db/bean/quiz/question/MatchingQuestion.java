package db.bean.quiz.question;

import java.util.ArrayList;
import java.util.List;

import db.bean.quiz.Answer;

public class MatchingQuestion extends Question {
	private String question;
	private List<Answer> answers;
	
	/**
	 * Constructor for MatchingQuestion object 
	 * @param question The question's text
	 * @param answers The question's answers
	 */
	public MatchingQuestion(String question, List<Answer> answers){
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
		return QuestionTypeContract.MATCHING_QUESTION;
	}
}
