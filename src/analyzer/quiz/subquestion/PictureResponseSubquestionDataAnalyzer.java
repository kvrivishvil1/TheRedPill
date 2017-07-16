package analyzer.quiz.subquestion;

public class PictureResponseSubquestionDataAnalyzer extends SingleFieldSubquestionDataAnalyzer {

	public PictureResponseSubquestionDataAnalyzer(String[] questions, String[] answers, String[] options, String[] optionIDs, String[] answerOptions, String parser) {
		this.questions = questions;
		this.answers = answers;
		this.options = options;
		this.optionIDs = optionIDs;
		this.answerOptions = answerOptions;
		this.parser = parser;
	}
	
}
