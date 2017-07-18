package managers;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import analyzer.quiz.OptionDataAnalyzer;
import analyzer.quiz.QuestionDataAnalyzer;
import analyzer.quiz.SubquestionDataContainer;
import analyzer.quiz.subquestion.SubquestionDataAnalyzer;
import db.bean.quiz.Option;
import db.bean.quiz.Question;
import db.bean.quiz.Quiz;
import db.bean.quiz.QuizAttempt;
import db.bean.quiz.Subquestion;
import db.dao.QuizDao;
import helpers.DataCouple;
import javafx.util.Pair;

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
	
	/**
	 * Question data analyzer method. Takes data submitted from quiz creator.
	 * @param typeID of question 
	 * @param note of question 
	 * @param timeLimit value if time is limited. Otherwise, -1.
	 * @param isOrderSensitive true if time is limited for question. Otherwise, false.
	 * @param container object which saves data about subquestions of the question
	 * @return
	 */
	public Question analyzeQuestion(int typeID, String note, long timeLimit, boolean isOrderSensitive, SubquestionDataContainer container){
		Map<Integer, String> map = quizDao.getAllQuestionTypes();
		
		Object analyzer = null;
		try {
			Class<?> classObject = Class.forName("analyzer.quiz.subquestion." + map.get(typeID) + "SubquestionDataAnalyzer");
			Constructor<?> cons = classObject.getConstructor(String[].class, String[].class, String[].class, String[].class, String[].class, String.class);
			analyzer = cons.newInstance(container.getQuestions(), container.getAnswers(), container.getOptions(), container.getOptionIDs(), container.getAnswerOptions(), container.getParser());
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Subquestion> subquestionsList = ((SubquestionDataAnalyzer)analyzer).getSubquestions();	
		
		OptionDataAnalyzer optionDataAnalyzer = new OptionDataAnalyzer(container.getOptions());
		List<Option> optionsList = optionDataAnalyzer.getOptions();
		
		QuestionDataAnalyzer questionDataAnalyzer = new QuestionDataAnalyzer(typeID, note, subquestionsList, optionsList, isOrderSensitive, timeLimit);
		Question question = questionDataAnalyzer.getQuestion();
		
		return question;
	}
	
	/**
	 * Adds quiz into database
	 * @param quiz which should be added
	 */
	public void addQuiz(Quiz quiz){
		quizDao.addQuiz(quiz);
	}
	
	/**
	 * Gets all quizzes which has tag named:
	 * @param tag
	 * @return list of quizzes with current tag
	 */
	public ArrayList<Pair<String, Integer>> getQuizesByTag(String tag) {
		return quizDao.getQuizesByTag(tag);
	}
	
	/**
	 * Returns recently created quizzes
	 * @return Recently created quizzes
	 */
	public ArrayList<Integer> getRecentQuizzes() {
		return quizDao.getRecentQuizzes();
	}
	
	/**
	 * Returns popular quizzes
	 * @return popular quizzes
	 */
	public ArrayList<Integer> getPopularQuizIds() {
		return quizDao.getPopularQuizIds();
	}
	
	/**
	 * return quizzes which are created by current account
	 * @param AccountId
	 * @return ArrayList of quizIds
	 */
	public ArrayList<Integer> getMyQuizes(int AccountId) {
		return quizDao.getMyQuizes(AccountId);
	}
}
