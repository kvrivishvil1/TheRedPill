package analyzer.quiz;

public class SubquestionDataContainer {
	private String[] questions;
	private String[] answers;
	private String[] options;
	private String[] optionIDs;
	private String[] answerOptions;
	private String parser;

	public SubquestionDataContainer(String[] questions, String[] answers, String[] options, String[] optionIDs, String[] answerOptions, String parser){
		this.questions = questions;
		this.answers = answers;
		this.options = options;
		this.optionIDs = optionIDs;
		this.answerOptions = answerOptions;
		this.parser = parser;
	}
	
	/**
	 * Gets questions taken from form
	 * @return The array of questions
	 */
	public String[] getQuestions(){
		return questions;
	}

	/**
	 * Gets answers taken from form
	 * @return The array of answers
	 */
	public String[] getAnswers(){
		return answers;
	}
	
	/**
	 * Gets options taken from form
	 * @return The array of options
	 */
	public String[] getOptions(){
		return options;
	}
	
	/**
	 * Gets options ID taken from form
	 * @return The array of options ID
	 */
	public String[] getOptionIDs(){
		return optionIDs;
	}
	
	/**
	 * Gets answer options taken from form
	 * @return The array of answer options
	 */	
	public String[] getAnswerOptions(){
		return answerOptions;
	}
	
	/**
	 * Gets parser for answers taken from form 
	 * @return The parser for the answer
	 */
	public String getParser(){
		return parser;
	}
	
}
