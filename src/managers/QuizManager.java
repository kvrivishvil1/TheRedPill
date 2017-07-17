package managers;

import java.util.ArrayList;
import java.util.HashMap;

import db.bean.quiz.Quiz;
import db.bean.quiz.QuizAttempt;
import db.dao.QuizDao;

public class QuizManager {
	public static final String CONTEXT_ATTRIBUTE_NAME = "MainManager";
	
	private QuizDao quizDao;
	
	public QuizManager(QuizDao quizDao) {
		this.quizDao = quizDao;
	}
	
	/**
	 * Calls getPersonByUserId method of quiz dao with parameters:
	 * @param attempt
	 */
	public void addQuizAttempt(QuizAttempt attempt) {
		quizDao.addQuizAttempt(attempt);
	}
	
	/**
	 * Calls getPersonByUserId method of quiz dao with parameters:
	 * @param quizID
	 * @return Quiz
	 */
	public Quiz getQuiz(int quizID) {
		return quizDao.getQuiz(quizID);
	}
	
	/**
	 * Calls getAllQuizzes method of quiz dao:
	 * @return Hashmap of all quizes
	 */
	public HashMap<Integer, String> getAllQuizzes() {
		return quizDao.getAllQuizzes();
	}
	
	/**
	 * Calls getPersonByUserId method of quiz dao:
	 * @return amount of quizzes
	 */
	public long getNumQuizzes() {
		return quizDao.getNumQuizzes();
	}
}
