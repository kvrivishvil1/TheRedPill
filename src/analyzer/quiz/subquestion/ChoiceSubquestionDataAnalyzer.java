package analyzer.quiz.subquestion;

import java.util.ArrayList;
import java.util.List;

import analyzer.quiz.AnswerDataAnalyzer;
import db.bean.quiz.Answer;
import db.bean.quiz.Subquestion;

public abstract class ChoiceSubquestionDataAnalyzer extends SubquestionDataAnalyzer {

	@Override
	public List<Subquestion> getSubquestions(){
		List<Subquestion> subquestionsList = new ArrayList<Subquestion>();
		Subquestion subquestion = new Subquestion(questions[0]);
		int j = 0; //This is index for looping answer options array
		for (int i = 0; i < options.length; i++) {
			if (answerOptions != null && j < answerOptions.length && answerOptions[j].equals(optionIDs[i])) {
				j++;
				AnswerDataAnalyzer analyzer = new AnswerDataAnalyzer(options[i], '\n');
				Answer answer = analyzer.getAnswer();
				subquestion.addAnswer(answer);
			}
		}
		subquestionsList.add(subquestion);
		return subquestionsList;
	}

}
