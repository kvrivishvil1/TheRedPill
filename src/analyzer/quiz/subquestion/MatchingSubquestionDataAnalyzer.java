package analyzer.quiz.subquestion;

import java.util.ArrayList;
import java.util.List;

import analyzer.quiz.AnswerDataAnalyzer;
import db.bean.quiz.Answer;
import db.bean.quiz.Subquestion;

public class MatchingSubquestionDataAnalyzer extends SubquestionDataAnalyzer {
	
	public MatchingSubquestionDataAnalyzer(String[] questions, String[] answers, String[] options, String[] optionIDs, String[] answerOptions, String parser) {
		this.questions = questions;
		this.answers = answers;
		this.options = options;
		this.optionIDs = optionIDs;
		this.answerOptions = answerOptions;
		this.parser = parser;
	}

	@Override
	public  List<Subquestion> getSubquestions(){
		List<Subquestion> subquestionsList = new ArrayList<Subquestion>();
		for (int i = 0; i < options.length; i++) {
			Subquestion subquestion = new Subquestion(questions[i]);
			String answerText = options[Integer.parseInt(answerOptions[i]) - 1];
			AnswerDataAnalyzer analyzer = new AnswerDataAnalyzer(answerText, '\n');
			Answer answer = analyzer.getAnswer();
			subquestion.addAnswer(answer);
			subquestionsList.add(subquestion);
		}
		return subquestionsList;
	}

}
