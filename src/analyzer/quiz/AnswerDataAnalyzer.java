package analyzer.quiz;

import java.util.List;

import db.bean.quiz.Answer;
import helpers.StringParser;

public class AnswerDataAnalyzer {
	private char parserSymbol;
	private String text;

	/**
	 * Constructor for answers analyzer
	 * 
	 * @param text
	 *            which should be analyzed
	 * @param parserSymbol
	 *            which was used to separate alternative answers from each
	 *            other. If an answer has no alternatives, parser symbol should
	 *            be '\n'
	 */
	public AnswerDataAnalyzer(String text, char parserSymbol) {
		this.text = text;
		this.parserSymbol = parserSymbol;
	}

	/**
	 * Returns single Answer object for passed data
	 * 
	 * @return An analyzed Answer object
	 */
	public Answer getAnswer() {
		List<String> singleAnswersList = StringParser.parseStringBy(parserSymbol, text);
		Answer answer = new Answer(singleAnswersList);
		answer.setParserSymbol(parserSymbol);
		return answer;
	}

}
