package analyzer.quiz.subquestion;

import java.util.ArrayList;
import java.util.List;

import analyzer.quiz.AnswerDataAnalyzer;
import db.bean.quiz.Answer;
import db.bean.quiz.Subquestion;

public abstract class SingleFieldSubquestionDataAnalyzer extends SubquestionDataAnalyzer {	
	
	public List<Subquestion> getSubquestions(){
		List<Subquestion> subquestionsList = new ArrayList<Subquestion>();
		Subquestion subquestion = new Subquestion(questions[0]);
		String answerText = answers[0];
		if(parser == null || parser.equals(""))
			parser = "\n";
		AnswerDataAnalyzer analyzer = new AnswerDataAnalyzer(answerText, parser.charAt(0));
		Answer answer = analyzer.getAnswer();
		subquestion.addAnswer(answer);
		subquestionsList.add(subquestion);
		return subquestionsList;
	}
}
