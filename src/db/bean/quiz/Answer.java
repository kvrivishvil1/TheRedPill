package db.bean.quiz;

import java.util.List;

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
	 * Returns whether the answer is correct or not
	 * 
	 * @return true if it is correct, false otherwise.
	 */
	public boolean isCorrect(String answer) {
		return true;
	}

	/**
	 * Set the id of the answer 
	 * @param answerID answer's id
	 */
	public void setAnswerID( int answerID){
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
	 * @param parserSymbol, the symbol used to parse answers
	 */
	public void setParserSymbol(char parserSymbol){
		this.parserSymbol = parserSymbol;
	}
	
	
	/**
	 * Returns the parser symbol of the answer
	 * @return parser Symbol, the symbol used to parse answers
	 */
	public char getParserSymbol(){
		return parserSymbol;
	}

}
