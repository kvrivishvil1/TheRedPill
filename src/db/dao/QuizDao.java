package db.dao;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import db.DbContract;
import db.MyDbInfo;
import db.bean.quiz.Answer;
import db.bean.quiz.Quiz;
import db.bean.quiz.question.Question;
import db.bean.quiz.question.QuestionTypeContract;

public class QuizDao {
	private static final String database = MyDbInfo.MYSQL_DATABASE_NAME;
	private static final String account = MyDbInfo.MYSQL_USERNAME;
	private static final String password = MyDbInfo.MYSQL_PASSWORD;
	private static final String server = MyDbInfo.MYSQL_DATABASE_SERVER;

	public QuizDao() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return JDBC connection
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private Connection createConnection() throws ClassNotFoundException, SQLException {
		Connection connection = DriverManager.getConnection("jdbc:mysql://" + server, account, password);
		Statement stm = connection.createStatement();
		stm.executeQuery("USE " + database);
		return connection;
	}

	/**
	 * adds quiz, all its questions with their answers in database
	 * 
	 * @param quiz
	 *            quiz object which should be added
	 * @return return type tells us whether insert was successful
	 * 
	 * @throws SQLException
	 */
	/*
	public boolean addQuiz(Quiz quiz) throws SQLException {
		try (Connection connection = createConnection()) {
			int lastQuizId = addQuizToDatabase(connection, quiz);
			List<Question> currentQuizQuestions = quiz.getAllQuestions();
			for (Question currentQuestion : currentQuizQuestions) {
				int lastQuestionId = addQuestionToDatabase(connection, currentQuestion, lastQuizId);
				List<Answer> currentQuestionAnswers = currentQuestion.getAnswers();
				for (Answer currentAnswer : currentQuestionAnswers) {
					addAnswerToDatabase(connection, currentAnswer, lastQuestionId);
				}
			}
			return true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}
	*/

	/**
	 * adds single answer in database
	 * 
	 * @param connection
	 *            JDBC connection
	 * @param currAnswer
	 *            Answer object that should be added in database
	 * @param lastQuestionId
	 *            question id which the answer belongs to
	 * @throws SQLException
	 */
	/*
	private void addAnswerToDatabase(Connection connection, Answer currAnswer, int lastQuestionId) throws SQLException {
		
		 * String insertAnswerQuery = "INSERT INTO " + DbContract.answers.TABLE_NAME + " ( "
				+ DbContract.answers.COLUMN_NAME_QUESTION_ID + " , " + DbContract.answers.COLUMN_NAME_ANSWER_TYPE
				+ " , " + DbContract.answers.COLUMN_NAME_ANSWER + " ) values ( ? , ? , ? )";
		PreparedStatement stm = connection.prepareStatement(insertAnswerQuery);
		stm.setInt(1, lastQuestionId);
		stm.setBoolean(2, currAnswer.isCorrect());
		stm.setString(3, currAnswer.getAnswer());
		stm.executeUpdate();
		
	}
*/
	
	
	/**
	 * function adds single question in database *
	 * 
	 * @param connection
	 *            sql database connection
	 * @param currentQuestion
	 *            question object
	 * @param lastQuizId
	 *            quiz id which the question belongs to
	 * @return returns the unique id at which the question is added
	 * @throws SQLException
	 * 
	 * 
	 */
	/*
	private int addQuestionToDatabase(Connection connection, Question currentQuestion, int lastQuizId)
			throws SQLException {
		String insertQuestionQuery = "INSERT INTO " + DbContract.Questions.TABLE_NAME + " ( "
				+ DbContract.Questions.COLUMN_NAME_QUIZ_ID + " , " + DbContract.Questions.COLUMN_NAME_QUESTION_TYPE
				+ " , " + DbContract.Questions.COLUMN_NAME_QUESTION + " ) values ( ? , ?, ? )";
		System.out.println(insertQuestionQuery);
		PreparedStatement stm = connection.prepareStatement(insertQuestionQuery, Statement.RETURN_GENERATED_KEYS);
		stm.setInt(1, lastQuizId);
		stm.setInt(2, currentQuestion.getQuestionType());
		stm.setString(3, currentQuestion.getQuestion());
		stm.executeUpdate();
		int lastQuestionId = 0;
		ResultSet rs = stm.getGeneratedKeys();
		if (rs.next()) {
			lastQuestionId = rs.getInt(1);
		}
		return lastQuestionId;
	}
	*/

	/**
	 * inserts Quiz in database
	 * 
	 * @param connection
	 *            JDBC connection
	 * @param quiz
	 *            quiz object that should be added
	 * @return the unique id at which the quiz was added
	 * @throws SQLException
	 */
	private int addQuizToDatabase(Connection connection, Quiz quiz) throws SQLException {
		String insertQuizQuery = "INSERT INTO " + DbContract.quizzes.TABLE_NAME + " ( "
				+ DbContract.quizzes.COLUMN_NAME_QUIZ_NAME + " , " + DbContract.quizzes.COLUMN_NAME_ISREARRANGABLE + " , "
				+ DbContract.quizzes.COLUMN_NAME_ISPRACTICABLE + " ) values( ? , ? , ? )";
		System.out.println(insertQuizQuery);
		PreparedStatement stm = connection.prepareStatement(insertQuizQuery, Statement.RETURN_GENERATED_KEYS);
		stm.setString(1, quiz.getName());
		stm.setBoolean(2, quiz.isRearrangable());
		stm.setBoolean(3, quiz.isPracticable());
		stm.executeUpdate();
		int lastQuizId = 0;
		ResultSet rs = stm.getGeneratedKeys();
		if (rs.next()) {
			lastQuizId = rs.getInt(1);
		}
		return lastQuizId;
	}

	/**
	 * pulls and returns quiz object from database
	 * 
	 * @param quizId
	 * @return
	 * @throws SQLException
	 */
	/*
	public Quiz getQuiz(int quizId) throws SQLException {
		try (Connection connection = createConnection()) {
			Quiz currentQuiz = selectQuizFromDatabase(connection, quizId);
			addQuestionsToQuiz(currentQuiz, connection, quizId);
			return currentQuiz;
		} catch (ClassNotFoundException | SecurityException | IllegalArgumentException e) {
			e.printStackTrace();
		}
		return null;
	}
*/
	/**
	 * pulls questions by quiz id and adds them to quiz object
	 * 
	 * @param currentQuiz
	 * @param connection
	 * @param quizId
	 * @throws SQLException
	 */
	/*
	private void addQuestionsToQuiz(Quiz currentQuiz, Connection connection, int quizId) throws SQLException {
		String selectQuestionsQuery = "SELECT * FROM " + DbContract.Questions.TABLE_NAME + " WHERE "
				+ DbContract.Questions.COLUMN_NAME_QUIZ_ID + " = ?";
		PreparedStatement stm = connection.prepareStatement(selectQuestionsQuery);
		stm.setInt(1, quizId);
		ResultSet rs = stm.executeQuery();
		while (rs.next()) {
			int type = rs.getInt(DbContract.Questions.COLUMN_NAME_QUESTION_TYPE);
			int questionId = rs.getInt(DbContract.Questions.COLUMN_NAME_QUESTION_ID);
			try {
				// create desired Question object by reflection
				Class<?> cl = Class.forName("db.bean.quiz.question." + QuestionTypeContract.getClassName(type));
				Constructor<?> cons = cl.getConstructor(String.class, List.class);
				List<Answer> currentQuestionAnswers = selectAnswers(connection, questionId);
				Object o = cons.newInstance(rs.getString(DbContract.Questions.COLUMN_NAME_QUESTION),
						currentQuestionAnswers);
				currentQuiz.addQuestion((Question) o);
			} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
					| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}

		}
	}
*/
	
	/**
	 * pulls answers for desired question id and returns their list
	 * 
	 * @param connection
	 * @param questionId
	 * @return
	 * @throws SQLException
	 */
	/*
	private List<Answer> selectAnswers(Connection connection, int questionId) throws SQLException {
		String answersQuery = "SELECT * FROM " + DbContract.answers.TABLE_NAME + " WHERE "
				+ DbContract.answers.COLUMN_NAME_QUESTION_ID + " = ?";
		PreparedStatement answerStm = connection.prepareStatement(answersQuery);
		answerStm.setInt(1, questionId);
		ResultSet rs = answerStm.executeQuery();
		List<Answer> currentQuestionAnswers = new ArrayList<>();
		while (rs.next()) {
			Answer currentAnswer = new Answer(rs.getString(DbContract.Answers.COLUMN_NAME_ANSWER),
					rs.getBoolean(DbContract.answers.COLUMN_NAME_ANSWER_TYPE));
			currentQuestionAnswers.add(currentAnswer);
		}
		return currentQuestionAnswers;
	}
*/
	/**
	 * pulls quiz info from database and creates quiz object
	 * @param connection
	 * @param quizId
	 * @return
	 * @throws SQLException
	 */
	/*
	private Quiz selectQuizFromDatabase(Connection connection, int quizId) throws SQLException {
		String selectQuizQuery = "SELECT * FROM " + DbContract.quizzes.TABLE_NAME + " WHERE "
				+ DbContract.quizzes.COLUMN_NAME_QUIZ_ID + " = ?";
		PreparedStatement stm = connection.prepareStatement(selectQuizQuery);
		stm.setInt(1, quizId);
		ResultSet rs = stm.executeQuery();
		if (rs.next()) {
			return new Quiz(rs.getString(DbContract.quizzes.COLUMN_NAME_QUIZ_NAME),
					rs.getBoolean(DbContract.quizzes.COLUMN_NAME_ISPRACTICABLE),
					rs.getBoolean(DbContract.quizzes.COLUMN_NAME_ISREARRANGABLE));
		}
		return null;
	}
*/
	/**
	 * returns hashMap including id - name pairs for all quizes
	 * 
	 * @return
	 * @throws SQLException
	 */
	/**
	 * @return
	 * @throws SQLException
	 */
	public Map<Integer, String> getAllQuizzes() throws SQLException {
		try (Connection connection = createConnection()) {
			Map<Integer, String> quizes = new HashMap<>();
			String query = "SELECT " + DbContract.quizzes.COLUMN_NAME_QUIZ_ID + " , "
					+ DbContract.quizzes.COLUMN_NAME_QUIZ_NAME + " FROM " + DbContract.quizzes.TABLE_NAME;
			PreparedStatement stm = connection.prepareStatement(query);
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(DbContract.quizzes.COLUMN_NAME_QUIZ_ID);
				String name = rs.getString(DbContract.quizzes.COLUMN_NAME_QUIZ_NAME);
				quizes.put(id, name);
			}
			return quizes;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
