package managers;

import java.util.ArrayList;
import java.util.HashMap;

import db.bean.quiz.Quiz;
import db.bean.quiz.QuizAttempt;
import db.dao.QuizDao;
import helpers.DataCouple;

public class QuizManager {
	public static final String CONTEXT_ATTRIBUTE_NAME = "MainManager";

	private QuizDao quizDao;

	public QuizManager(QuizDao quizDao) {
		this.quizDao = quizDao;
	}

	/**
	 * Calls getPersonByUserId method of quiz dao with parameters:
	 * 
	 * @param attempt
	 */
	public void addQuizAttempt(QuizAttempt attempt) {
		quizDao.addQuizAttempt(attempt);
	}

	/**
	 * Calls getPersonByUserId method of quiz dao with parameters:
	 * 
	 * @param quizID
	 * @return Quiz
	 */
	public Quiz getQuiz(int quizID) {
		return quizDao.getQuiz(quizID);
	}

	/**
	 * Calls getAllQuizzes method of quiz dao:
	 * 
	 * @return Hashmap of all quizes
	 */
	public HashMap<Integer, String> getAllQuizzes() {
		return quizDao.getAllQuizzes();
	}

	/**
	 * Calls getPersonByUserId method of quiz dao:
	 * 
	 * @return amount of quizzes
	 */
	public long getNumQuizzes() {
		return quizDao.getNumQuizzes();
	}

	/**
	 * Calls getQuizName method of quiz dao:
	 * 
	 * @param quizID
	 *            the id of the quiz
	 * @return name of the quiz
	 */
	public String getQuizName(int quizID) {
		return quizDao.getQuizName(quizID);
	}

	/**
	 * Calls getQuizDescription method of quiz dao:
	 * 
	 * @param quizID
	 *            the id of the quiz
	 * @return description of the quiz
	 */
	public String getQuizDescription(int quizID) {
		return quizDao.getQuizDescription(quizID);
	}

	/**
	 * Calls getQuizName method of quiz dao:
	 * 
	 * @param quizID
	 *            the id of the quiz
	 * @return creator of the quiz
	 */
	public String getQuizCreator(int quizID) {
		return quizDao.getQuizCreator(quizID);
	}

	/**
	 * Calls getLastPerformances method of quiz dao:
	 * 
	 * @param quizID
	 *            the id of the quiz
	 * @return Last Performances
	 */
	public ArrayList<DataCouple> getLastPerformances(int quizID) {
		return quizDao.getLastPerformances(quizID);
	}

	/**
	 * Calls getLastPerformsOrderByScore method of quiz dao:
	 * 
	 * @param quizID
	 *            the id of the quiz
	 * @return Last Performs Order By Score
	 */
	public ArrayList<DataCouple> getLastPerformsOrderByScore(int quizID) {
		return quizDao.getLastPerformsOrderByScore(quizID);
	}

	/**
	 * Calls getLastPerformsOrderByDate method of quiz dao:
	 * 
	 * @param quizID
	 *            the id of the quiz
	 * @return Last Performs Order By Date
	 */
	public ArrayList<DataCouple> getLastPerformsOrderByDate(int quizID) {
		return quizDao.getLastPerformsOrderByDate(quizID);
	}

	/**
	 * Calls getLastPerformsOrderByTime method of quiz dao:
	 * 
	 * @param quizID
	 *            the id of the quiz
	 * @return Last Performs Order By Time
	 */
	public ArrayList<DataCouple> getLastPerformsOrderByTime(int quizID) {
		return quizDao.getLastPerformsOrderByTime(quizID);
	}

	/**
	 * Calls getTopPerformancesToday method of quiz dao:
	 * 
	 * @param quizID
	 *            the id of the quiz
	 * @return Top Performances Today
	 */
	public ArrayList<DataCouple> getTopPerformancesToday(int quizID) {
		return quizDao.getTopPerformancesToday(quizID);
	}

	/**
	 * Calls getRecentPerformances method of quiz dao:
	 * 
	 * @param quizID
	 *            the id of the quiz
	 * @return Recent Performances
	 */
	public ArrayList<DataCouple> getRecentPerformances(int quizID) {
		return quizDao.getRecentPerformances(quizID);
	}

	/**
	 * Calls getQuizMaxScore method of quiz dao:
	 * 
	 * @param quizID
	 *            the id of the quiz
	 * @return Quiz Max Score
	 */
	public int getQuizMaxScore(int quizID) {
		return quizDao.getQuizMaxScore(quizID);
	}
}
