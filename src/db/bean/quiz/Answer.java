package db.bean.quiz;

public class Answer {
	private String answer;
	private boolean isCorrect;
	
	/**
	 * Constructor for Answer object
	 * @param answer The answer's text
	 * @param isCorrect
	 */
	public Answer(String answer, boolean isCorrect){
		this.answer = answer;
		this.isCorrect = isCorrect;
	}
	
	/**
	 * Returns the text of the answer
	 * @return The answer's text
	 */
	public String getAnswer(){
		return answer;
	}
	
	/**
	 * Returns whether answer is correct or not
	 * @return true if it is correct, false otherwise.
	 */
	public boolean isCorrect(){
		return isCorrect;
	}
	
	
}
