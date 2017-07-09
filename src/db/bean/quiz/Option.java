package db.bean.quiz;

public class Option {

	private String option;
	private int optionID;

	/**
	 * Constructor for the Option object
	 * 
	 * @param option
	 */
	public Option(String option) {
		this.option = option;
	}

	/**
	 * Sets the option text for the option
	 * @param option The text of the option
	 */
	public void setOption(String option){
		this.option = option;
	}
	
	
	/**
	 * Returns one of the option of the answer
	 * 
	 * @return The text of the option
	 */
	public String getOption() {
		return option;
	}

	/**
	 * Sets the id for the option
	 * 
	 * @param optionID ID of the option
	 */
	public void setOptionID(int optionID) {
		this.optionID = optionID;
	}

	/**
	 * Returns the id of the option in database
	 * 
	 * @return The id of the option
	 */
	public int getOptionID() {
		return optionID;
	}
}
