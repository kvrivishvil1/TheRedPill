package db.bean.quiz;

import java.util.List;

import helpers.StringComparator;

public class Answer {

	private List<String> answers;
	private int answerID;
	private char parserSymbol;

	/**
	 * Constructor for Answer object
	 * 
	 * @param answers
	 *            The list of answers
	 * 
	 */
	public Answer(List<String> answers) {
		this.answers = answers;
	}

	/**
	 * Returns the list of the answers
	 * 
	 * @return The list of the answers
	 */
	public List<String> getAnswers() {
		return answers;
	}

	/**
	 * Adds answer into answers list
	 * 
	 * @param answer
	 *            which should be added
	 */
	public void addAnswer(String answer) {
		answers.add(answer);
	}
	
	/**
	 * Removes answer from answers list
	 * 
	 * @param answer
	 *            which should be removed
	 */
	public void removeAnswer(String answer) {
		answers.remove(answer);
	}

	/**
	 * Returns whether the answer is correct or not
	 * 
	 * @param answer
	 *            which should be checked
	 * @param similarityPercentage
	 *            how much percentage should be coincidence between passed
	 *            answer and the real one. If it out less than 0 or more than
	 *            100, answer will be checked on 100 percent.
	 * 
	 * @return true if it is correct, false otherwise.
	 */
	public boolean isCorrect(String answer, int similarityPercentage) {
		if(similarityPercentage > 100 || similarityPercentage < 0)
			similarityPercentage = 100;
		double maxPercent = findMaxPercent(answer, similarityPercentage);
		return maxPercent >= similarityPercentage;
	}

	//Finds maximal similarity between all possible answers and passed one
	private double findMaxPercent(String answer, int similarityPercentage) {
		double maxPercent = 0;
		for (int i = 0; i < answers.size(); i++) {
			int currentWordLength = answers.get(i).length();
			int levenshteinDistance = StringComparator.levenshteinDistance(answers.get(i), answer);
			int currentPercent = (int) (((double) currentWordLength - levenshteinDistance) / currentWordLength * 100);
			if (maxPercent < currentPercent)
				maxPercent = currentPercent;
		}
		return maxPercent;
	}

	/**
	 * Set the id of the answer
	 * 
	 * @param answerID
	 *            answer's id
	 */
	public void setAnswerID(int answerID) {
		this.answerID = answerID;
	}

	/**
	 * Returns the id of the answer
	 * 
	 * @return answer's id
	 */
	public int getAnswerID() {
		return answerID;
	}

	/**
	 * Sets the parser symbol of the answer
	 * 
	 * @param parserSymbol,
	 *            the symbol used to parse answers
	 */
	public void setParserSymbol(char parserSymbol) {
		this.parserSymbol = parserSymbol;
	}

	/**
	 * Returns the parser symbol of the answer
	 * 
	 * @return parser Symbol, the symbol used to parse answers
	 */
	public char getParserSymbol() {
		return parserSymbol;
	}

}
