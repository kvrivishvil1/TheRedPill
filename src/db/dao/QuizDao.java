package db.dao;

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
import db.bean.quiz.Option;
import db.bean.quiz.Quiz;
import db.bean.quiz.Subquestion;
import db.bean.quiz.Question;

public class QuizDao {
	private static final String database = MyDbInfo.MYSQL_DATABASE_NAME;
	private static final String account = MyDbInfo.MYSQL_USERNAME;
	private static final String password = MyDbInfo.MYSQL_PASSWORD;
	private static final String server = MyDbInfo.MYSQL_DATABASE_SERVER;
	public static final String CONTEXT_ATTRIBUTE_NAME = "quizDataAccess";
	
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

	// ----------- part of the program responsible for pushing quiz to database
	/**
	 * adds quiz, all its questions, subquestions with their answers and options
	 * in database
	 * 
	 * @param quiz
	 *            Quiz object to be added in database
	 * @return return boolean tells us whether insert was successful
	 * 
	 * @throws SQLException
	 */

	public boolean addQuiz(Quiz quiz) throws SQLException {
		try (Connection connection = createConnection()) {
			int lastQuizId = addQuizToDatabase(connection, quiz);
			addTagsToQuizInDatabase(connection, quiz, lastQuizId);
			List<Question> currentQuizQuestions = quiz.getAllQuestions();
			for (Question currentQuestion : currentQuizQuestions) {
				int lastQuestionId = addQuestionToDatabase(connection, currentQuestion, lastQuizId);
				List<Subquestion> currentSubquestions = currentQuestion.getSubquestions();
				for (Subquestion currentSubquestion : currentSubquestions) {
					int lastSubquestionId = addSubquestionToDatabase(connection, currentSubquestion, lastQuestionId);
					List<Answer> currentAnswers = currentSubquestion.getAnswers();
					for (Answer currentAnswer : currentAnswers) {
						addAnswerToDatabase(connection, currentAnswer, lastSubquestionId);
					}
				}
				List<Option> curretntOptions = currentQuestion.getOptions();
				for (Option currentOption : curretntOptions) {
					addOptionToDatabase(connection, currentOption, lastQuestionId);
				}
			}
			return true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	};

	/**
	 * 
	 * adds all tags of a quiz in tags table in database
	 * 
	 * @param connection
	 * @param quiz
	 * @param lastQuizId
	 * @throws SQLException
	 */
	private void addTagsToQuizInDatabase(Connection connection, Quiz quiz, int lastQuizId) throws SQLException {
		String query = "INSERT INTO " + DbContract.quizTagsTable.TABLE_NAME + " ( " + DbContract.quizTagsTable.COLUMN_NAME_QUIZ_ID
				+ " , " + DbContract.quizTagsTable.COLUMN_NAME_TAG_NAME + " ) VALUES ( ? , ? )";

		List<String> tags = quiz.getTags();
		for (String tag : tags) {
			PreparedStatement stm = connection.prepareStatement(query);
			stm.setInt(1, lastQuizId);
			stm.setString(2, tag);
			stm.executeUpdate();
		}
	}

	/**
	 * gets category id from categories table
	 * 
	 * @param string
	 * @param connection
	 * @return
	 * @throws SQLException
	 */
	private int selectCategory(String string, Connection connection) throws SQLException {
		String query = "SELECT * FROM " + DbContract.categoriesTable.TABLE_NAME + " WHERE "
				+ DbContract.categoriesTable.COLUMN_NAME_CATEGORY_NAME + " LIKE ?";
		PreparedStatement stm = connection.prepareStatement(query);
		stm.setString(1, string);
		ResultSet rs = stm.executeQuery();
		if (rs.next()) {
			return rs.getInt(DbContract.categoriesTable.COLUMN_NAME_CATEGORY_ID);
		}
		return 0;
	}

	/**
	 * adds single option in database
	 * 
	 * @param connection
	 * @param currentOption
	 * @param lastQuestionId
	 * @throws SQLException
	 */
	private void addOptionToDatabase(Connection connection, Option currentOption, int lastQuestionId)
			throws SQLException {
		String query = "INSERT INTO " + DbContract.questionOptionsTable.TABLE_NAME + " ( "
				+ DbContract.questionOptionsTable.COLUMN_NAME_QUESTION_ID + " , "
				+ DbContract.questionOptionsTable.COLUMN_NAME_OPTION_TEXT + " ) VALUES( ? , ? )";
		PreparedStatement stm = connection.prepareStatement(query);
		stm.setInt(1, lastQuestionId);
		stm.setString(2, currentOption.getOption());
		stm.executeUpdate();
	}

	/**
	 * adds answers in database
	 * 
	 * @param connection
	 *            JDBC connection
	 * @param currAnswer
	 *            Answer object that should be added in database
	 * @param lastQuestionId
	 *            question id which the answer belongs to
	 * @throws SQLException
	 */
	private void addAnswerToDatabase(Connection connection, Answer currentAnswer, int lastSubquestionId)
			throws SQLException {
		int lastAnswerId = addPairInAnswerSubquestionMap(connection, lastSubquestionId);
		String query = "INSERT INTO " + DbContract.answersTable.TABLE_NAME + " ( " + DbContract.answersTable.COLUMN_NAME_ANSWER_ID
				+ " , " + DbContract.answersTable.COLUMN_NAME_ANSWER_TEXT + " , "
				+ DbContract.answersTable.COLUMN_NAME_PARSER_SYMBOL + " ) values ( ? , ? , ? )";
		for (String answerText : currentAnswer.getAnswers()) {
			PreparedStatement stm = connection.prepareStatement(query);
			stm.setInt(1, lastAnswerId);
			stm.setString(2, answerText);
			stm.setString(3, "" + currentAnswer.getParserSymbol());
			stm.executeUpdate();

		}
	}

	/**
	 * add answer id and subquestion id in mapping table
	 * 
	 * @param connection
	 * @param lastSubquestionId
	 * @return
	 * @throws SQLException
	 */
	private int addPairInAnswerSubquestionMap(Connection connection, int lastSubquestionId) throws SQLException {
		String query = "INSERT INTO " + DbContract.answerSubquestionMapTable.TABLE_NAME + " ( "
				+ DbContract.answerSubquestionMapTable.COLUMN_NAME_SUBQUESTION_ID + " ) VALUES (?)";
		PreparedStatement mappingStm = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		mappingStm.setInt(1, lastSubquestionId);
		mappingStm.executeUpdate();
		int lastAnswerId = 0;
		ResultSet rs = mappingStm.getGeneratedKeys();
		if (rs.next()) {
			lastAnswerId = rs.getInt(1);
		}
		return lastAnswerId;
	}

	/**
	 * adds single subquestion in database
	 * 
	 * @param connection
	 * @param currentSubquestion
	 * @param lastQuestionId
	 * @return integer, which represents id which the subquestion was added at
	 * @throws SQLException
	 */
	private int addSubquestionToDatabase(Connection connection, Subquestion currentSubquestion, int lastQuestionId)
			throws SQLException {
		String query = "INSERT INTO " + DbContract.subquestionsTable.TABLE_NAME + "( "
				+ DbContract.subquestionsTable.COLUMN_NAME_QUESTION_ID + " , " + ""
				+ DbContract.subquestionsTable.COLUMN_NAME_SUBQUESTION_TEXT + ") VALUES (? , ?)";
		PreparedStatement stm = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		stm.setInt(1, lastQuestionId);
		stm.setString(2, currentSubquestion.getQuestion());
		stm.executeUpdate();
		int lastSubquestionId = 0;
		ResultSet rs = stm.getGeneratedKeys();
		if (rs.next()) {
			lastSubquestionId = rs.getInt(1);
		}
		return lastSubquestionId;
	}

	/**
	 * function adds single question in database *
	 * 
	 * @param connection
	 *            sql database connection
	 * @param currentQuestion
	 *            question object
	 * @param lastQuizId
	 *            quiz id which the question belongs to
	 * @return returns the unique id which the question was added at
	 * @throws SQLException
	 * 
	 */
	private int addQuestionToDatabase(Connection connection, Question currentQuestion, int lastQuizId)
			throws SQLException {
		String query = "INSERT INTO " + DbContract.questionsTable.TABLE_NAME + " ( "
				+ DbContract.questionsTable.COLUMN_NAME_QUIZ_ID + " , " + DbContract.questionsTable.COLUMN_NAME_QUESTION_TYPE
				+ " , " + DbContract.questionsTable.COLUMN_NAME_QUESTION_NOTE + " , "
				+ DbContract.questionsTable.COLUMN_NAME_ANSWER_ORDER_SENSITIVE + " ) values ( ? , ? , ? , ? )";
		PreparedStatement stm = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		stm.setInt(1, lastQuizId);
		stm.setInt(2, currentQuestion.getQuestionType());
		stm.setString(3, currentQuestion.getNote());
		stm.setBoolean(4, currentQuestion.isOrderSensitive());
		stm.executeUpdate();
		int lastQuestionId = 0;
		ResultSet rs = stm.getGeneratedKeys();
		if (rs.next()) {
			lastQuestionId = rs.getInt(1);
		}
		return lastQuestionId;
	}

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
		String query = "INSERT INTO " + DbContract.quizzesTable.TABLE_NAME + " ( " + DbContract.quizzesTable.COLUMN_NAME_QUIZ_NAME
				+ " , " + DbContract.quizzesTable.COLUMN_NAME_ISREARRANGABLE + " , "
				+ DbContract.quizzesTable.COLUMN_NAME_ISPRACTICABLE + " , " + DbContract.quizzesTable.COLUMN_NAME_DESCRIPTION
				+ " , " + DbContract.quizzesTable.COLUMN_NAME_CATEGORY_ID + " ) values( ? , ? , ? , ? , ? )";

		PreparedStatement stm = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		stm.setString(1, quiz.getName());
		stm.setBoolean(2, quiz.isRearrangable());
		stm.setBoolean(3, quiz.isPracticable());
		stm.setString(4, quiz.getDescription());
		stm.setInt(5, selectCategory(quiz.getCategory(), connection));
		stm.executeUpdate();
		int lastQuizId = 0;
		ResultSet rs = stm.getGeneratedKeys();
		if (rs.next()) {
			lastQuizId = rs.getInt(1);
		}
		return lastQuizId;
	}

	// -------- part of the program responsible for pulling quiz from DB

	/**
	 * pulls and returns quiz object from database
	 * 
	 * @param quizId
	 * @return
	 * @throws SQLException
	 */
	public Quiz getQuiz(int quizId) throws SQLException {

		try (Connection connection = createConnection()) {
			Quiz currentQuiz = selectQuizFromDatabase(connection, quizId);// check
			addTagsToQuiz(currentQuiz, connection, quizId);// check
			addQuestionsToQuiz(currentQuiz, connection, quizId);
			return currentQuiz;
		} catch (ClassNotFoundException | SecurityException | IllegalArgumentException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * pulls data from database representing tags of the given quiz
	 * 
	 * @param currentQuiz
	 * @param connection
	 * @param quizId
	 * @throws SQLException
	 */
	private void addTagsToQuiz(Quiz currentQuiz, Connection connection, int quizId) throws SQLException {
		String query = "SELECT * FROM " + DbContract.quizTagsTable.TABLE_NAME + " WHERE "
				+ DbContract.quizTagsTable.COLUMN_NAME_QUIZ_ID + " = ?";
		PreparedStatement stm = connection.prepareStatement(query);
		stm.setInt(1, quizId);
		ResultSet rs = stm.executeQuery();
		while (rs.next()) {
			currentQuiz.addTag(rs.getString(DbContract.quizTagsTable.COLUMN_NAME_TAG_NAME));
		}

	}

	/**
	 * pulls questions by quiz id and adds them to quiz object
	 * 
	 * @param currentQuiz
	 * @param connection
	 * @param quizId
	 * @throws SQLException
	 */
	private void addQuestionsToQuiz(Quiz currentQuiz, Connection connection, int quizId) throws SQLException {
		String query = "SELECT * FROM " + DbContract.questionsTable.TABLE_NAME + " WHERE "
				+ DbContract.questionsTable.COLUMN_NAME_QUIZ_ID + " = ?";
		PreparedStatement stm = connection.prepareStatement(query);
		stm.setInt(1, quizId);
		ResultSet rs = stm.executeQuery();
		while (rs.next()) {
			int typeId = rs.getInt(DbContract.questionsTable.COLUMN_NAME_QUESTION_TYPE);
			int questionId = rs.getInt(DbContract.questionsTable.COLUMN_NAME_QUESTION_ID);
			Question question = new Question(typeId);
			addOptionsToQuestion(connection, questionId, question);
			addDetailsToQuestion(connection, questionId, question);
			addSubquestionsToQuestion(connection, questionId, question);
			currentQuiz.addQuestion(question);
		}
	}

	/**
	 * pulls data from database representing subquestions of the given question
	 * 
	 * @param connection
	 * @param questionId
	 * @param question
	 * @throws SQLException
	 */
	private void addSubquestionsToQuestion(Connection connection, int questionId, Question question)
			throws SQLException {
		String query = "SELECT * FROM " + DbContract.subquestionsTable.TABLE_NAME + " WHERE "
				+ DbContract.subquestionsTable.COLUMN_NAME_QUESTION_ID + " = ?";
		PreparedStatement stm = connection.prepareStatement(query);
		stm.setInt(1, questionId);
		ResultSet rs = stm.executeQuery();
		while (rs.next()) {
			Subquestion subquestion = new Subquestion(
					rs.getString(DbContract.subquestionsTable.COLUMN_NAME_SUBQUESTION_TEXT));
			subquestion.setSubquestionID(rs.getInt(DbContract.subquestionsTable.COLUMN_NAME_SUBQUESTION_ID));
			addAnswersToSubQuestion(subquestion, subquestion.getSubquestionID(), connection);
			question.addSubquestion(subquestion);
		}
	}

	/**
	 * pulls data from map table, which connects answer and subquestion
	 * 
	 * @param subquestion
	 * @param subquestionID
	 * @param connection
	 * @throws SQLException
	 */
	private void addAnswersToSubQuestion(Subquestion subquestion, int subquestionID, Connection connection)
			throws SQLException {
		String query = "SELECT * FROM " + DbContract.answerSubquestionMapTable.TABLE_NAME + " WHERE "
				+ DbContract.answerSubquestionMapTable.COLUMN_NAME_SUBQUESTION_ID + " = ?";
		PreparedStatement stm = connection.prepareStatement(query);
		stm.setInt(1, subquestionID);
		ResultSet rs = stm.executeQuery();
		while (rs.next()) {
			int answerId = rs.getInt(DbContract.answerSubquestionMapTable.COLUMN_NAME_ANSWER_ID);
			addAnswerToSubquestion(subquestion, answerId, connection);
		}
	}

	/**
	 * 
	 * pulls data from database representing answers of the ginven subquestion
	 * 
	 * @param subquestion
	 * @param answerId
	 * @param connection
	 * @throws SQLException
	 */
	private void addAnswerToSubquestion(Subquestion subquestion, int answerId, Connection connection)
			throws SQLException {
		String query = "SELECT * FROM " + DbContract.answersTable.TABLE_NAME + " WHERE "
				+ DbContract.answersTable.COLUMN_NAME_ANSWER_ID + " = ?";
		PreparedStatement stm = connection.prepareStatement(query);
		stm.setInt(1, answerId);
		ResultSet rs = stm.executeQuery();
		List<String> answers = new ArrayList<>();
		char parserSymbol = 0;
		while (rs.next()) {
			parserSymbol = rs.getString(DbContract.answersTable.COLUMN_NAME_PARSER_SYMBOL).charAt(0);
			answers.add(rs.getString(DbContract.answersTable.COLUMN_NAME_ANSWER_TEXT));
		}
		Answer answer = new Answer(answers);
		answer.setParserSymbol(parserSymbol);
		answer.setAnswerID(answerId);
		subquestion.addAnswer(answer);
	}

	/**
	 * add order sensitivity, id and note to given question
	 * 
	 * @param connection
	 * @param questionId
	 * @param question
	 * @throws SQLException
	 */
	private void addDetailsToQuestion(Connection connection, int questionId, Question question) throws SQLException {
		question.setQuestionID(questionId);
		String query = "SELECT * FROM " + DbContract.questionsTable.TABLE_NAME + " WHERE "
				+ DbContract.questionsTable.COLUMN_NAME_QUESTION_ID + " = ?";
		PreparedStatement stm = connection.prepareStatement(query);
		stm.setInt(1, questionId);
		ResultSet rs = stm.executeQuery();
		if (rs.next()) {
			question.setOrderSensitive(rs.getBoolean(DbContract.questionsTable.COLUMN_NAME_ANSWER_ORDER_SENSITIVE));
			question.setNote(rs.getString(DbContract.questionsTable.COLUMN_NAME_QUESTION_NOTE));
		}

	}

	/**
	 * adds options of the question in database
	 * 
	 * @param connection
	 * @param questionId
	 * @param question
	 * @throws SQLException
	 */
	private void addOptionsToQuestion(Connection connection, int questionId, Question question) throws SQLException {
		String query = "SELECT * FROM " + DbContract.questionOptionsTable.TABLE_NAME + " WHERE "
				+ DbContract.questionOptionsTable.COLUMN_NAME_QUESTION_ID + " = ?";
		PreparedStatement stm = connection.prepareStatement(query);
		stm.setInt(1, questionId);
		ResultSet rs = stm.executeQuery();
		while (rs.next()) {
			Option option = new Option(rs.getString(DbContract.questionOptionsTable.COLUMN_NAME_OPTION_TEXT));
			question.addOption(option);
		}
	}

	/**
	 * @param typeId
	 * @return string representing type name of the given id
	 * @throws SQLException
	 */
	public String getTypeByTypeId(int typeId) throws SQLException {
		try (Connection connection = createConnection()) {
			String query = "SELECT * FROM " + DbContract.questionTypesTable.TABLE_NAME + " WHERE "
					+ DbContract.questionTypesTable.COLUMN_NAME_QUESTION_TYPE_ID + " = ?";
			PreparedStatement stm = connection.prepareStatement(query);
			stm.setInt(1, typeId);
			ResultSet rs = stm.executeQuery();
			if (rs.next()) {
				return rs.getString(DbContract.questionTypesTable.COLUMN_NAME_QUESTION_TYPE_NAME);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * pulls quiz info from database and creates quiz object
	 * 
	 * @param connection
	 * @param quizId
	 * @return
	 * @throws SQLException
	 */
	private Quiz selectQuizFromDatabase(Connection connection, int quizId) throws SQLException {
		String query = "SELECT * FROM " + DbContract.quizzesTable.TABLE_NAME + " WHERE "
				+ DbContract.quizzesTable.COLUMN_NAME_QUIZ_ID + " = ?";

		PreparedStatement stm = connection.prepareStatement(query);
		stm.setInt(1, quizId);
		ResultSet rs = stm.executeQuery();

		if (rs.next()) {
			int categoryId = rs.getInt(DbContract.quizzesTable.COLUMN_NAME_CATEGORY_ID);
			String category = selectCategoryName(categoryId);
			String name = rs.getString(DbContract.quizzesTable.COLUMN_NAME_QUIZ_NAME);
			String description = rs.getString(DbContract.quizzesTable.COLUMN_NAME_DESCRIPTION);
			boolean isRearrangable = rs.getBoolean(DbContract.quizzesTable.COLUMN_NAME_ISREARRANGABLE);
			boolean isPracticable = rs.getBoolean(DbContract.quizzesTable.COLUMN_NAME_ISPRACTICABLE);
			Quiz quiz = new Quiz(name, category);
			quiz.setDescription(description);
			quiz.setPracticableMode(isPracticable);
			quiz.setRearrangableMode(isRearrangable);
			return quiz;
		}
		return null;
	}

	/**
	 * returns string representing name of category of the given id
	 * 
	 * @param categoryId
	 * @return
	 * @throws SQLException
	 */
	public String selectCategoryName(int categoryId) throws SQLException {
		try (Connection connection = createConnection()) {
			String query = "SELECT * FROM " + DbContract.categoriesTable.TABLE_NAME + " WHERE "
					+ DbContract.categoriesTable.COLUMN_NAME_CATEGORY_ID + " = ?";
			PreparedStatement stm = connection.prepareStatement(query);
			stm.setLong(1, categoryId);
			ResultSet rs = stm.executeQuery();
			String categoryName = null;
			if (rs.next()) {
				categoryName = rs.getString(DbContract.categoriesTable.COLUMN_NAME_CATEGORY_NAME);
			}
			return categoryName;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * returns hashMap including id - name pairs for all quizes
	 * 
	 * @return
	 * @throws SQLException
	 */
	public HashMap<Integer, String> getAllQuizzes() throws SQLException {
		try (Connection connection = createConnection()) {
			HashMap<Integer, String> quizes = new HashMap<>();
			String query = "SELECT " + DbContract.quizzesTable.COLUMN_NAME_QUIZ_ID + " , "
					+ DbContract.quizzesTable.COLUMN_NAME_QUIZ_NAME + " FROM " + DbContract.quizzesTable.TABLE_NAME;
			PreparedStatement stm = connection.prepareStatement(query);
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(DbContract.quizzesTable.COLUMN_NAME_QUIZ_ID);
				String name = rs.getString(DbContract.quizzesTable.COLUMN_NAME_QUIZ_NAME);
				quizes.put(id, name);
			}
			return quizes;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	public long getNumQuizes() throws SQLException {
		try (Connection connection = createConnection()) {
			String query = "SELECT * FROM " + DbContract.quizzesTable.TABLE_NAME;
			PreparedStatement stm = connection.prepareStatement(query);
			ResultSet rs = stm.executeQuery();
			rs.last();
			return rs.getRow();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * Returns the list of category names found in database
	 * @return The list of category names
	 */
	public List<String> getAllCategoryNames(){
		List<String> result = new ArrayList<String>();
		String query = "SELECT " + DbContract.categoriesTable.COLUMN_NAME_CATEGORY_NAME + " FROM " + DbContract.categoriesTable.TABLE_NAME;
		try (Connection con = DriverManager.getConnection("jdbc:mysql://" + server, account, password);
				Statement stmt = con.createStatement()) {
			stmt.executeQuery("USE " + database);
			try (PreparedStatement ps = con.prepareStatement(query)) {
				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						result.add(rs.getString(DbContract.categoriesTable.COLUMN_NAME_CATEGORY_NAME));
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}	
}
