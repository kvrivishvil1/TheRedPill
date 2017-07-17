package analyzer.quiz.subquestion;

import java.util.ArrayList;
import java.util.List;

import analyzer.quiz.AnswerDataAnalyzer;
import db.bean.quiz.Answer;
import db.bean.quiz.Subquestion;

public class MultiAnswerSubquestionDataAnalyzer extends SubquestionDataAnalyzer {
	
	public MultiAnswerSubquestionDataAnalyzer(String[] questions, String[] answers, String[] options, String[] optionIDs, String[] answerOptions, String parser) {
		this.questions = questions;
		this.answers = answers;
		this.options = options;
		this.optionIDs = optionIDs;
		this.answerOptions = answerOptions;
		this.parser = parser;
	}

	@Override
	public List<Subquestion> getSubquestions(){
		List<Subquestion> subquestionsList = new ArrayList<Subquestion>();
		Subquestion subquestion = new Subquestion(questions[0]);
		for(int i = 0; i < answers.length; i++){
			String answerText = answers[i];
			if(parser == null || parser.equals(""))
				parser = "\n";
			AnswerDataAnalyzer analyzer = new AnswerDataAnalyzer(answerText, parser.charAt(0));
			Answer answer = analyzer.getAnswer();
			subquestion.addAnswer(answer);
		}
		subquestionsList.add(subquestion);
		return subquestionsList;
	}

}
