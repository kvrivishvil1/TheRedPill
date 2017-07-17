package db.bean;

import java.util.Date;

public class Report {
	private int quizID;
	private int accountID;
	private String reportText;
	private Date date;
	
	/**
	 * Constructor for Report object 
	 * @param quizID for which report was recorded
	 * @param accountID which reported the quiz
	 * @param reportText text of report 
	 * @param date of report 
	 */
	public Report(int quizID, int accountID, String reportText, Date date){
		this.quizID = quizID;
		this.accountID = accountID;
		this.reportText = reportText;
		this.date = date;
	}
	
	/**
	 * Gets ID of quiz which was reported 
	 * @return Quiz ID
	 */
	public int getQuizID(){
		return quizID;
	}
	
	/**
	 * Gets account ID who reported the quiz
	 * @return Quiz reporter ID
	 */
	public int getAccountID(){
		return accountID;
	}
	
	/**
	 * Gets report text
	 * @return The report text 
	 */
	public String getReportText(){
		return reportText;
	}

	/**
	 * Gets the date when the report was created
	 * @return The date of report
	 */
	public Date getDate(){
		return date;
	}
}
