package db.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import achievement.Achievement;
import achievement.Property;
import db.DbContract;
import db.MyDbInfo;
import db.bean.quiz.Answer;
import db.bean.quiz.Option;
import db.bean.quiz.Quiz;
import db.bean.quiz.QuizAttempt;
import db.bean.quiz.Subquestion;
import helpers.DataCouple;
import javafx.util.Pair;
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

	/**
	 * adds quiz, all its questions, subquestions with their answers and options
	 * in database
	 * 
	 * @param quiz
	 *            Quiz object to be added in database
	 * @return return boolean tells us whether insert was successful
	 */
	public boolean addQuiz(Quiz quiz) {
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
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return false;
	};

	/**
	 * adds all tags of a quiz in tags table in database
	 * 
	 * @param connection
	 * @param quiz
	 * @param lastQuizId
	 */
	private void addTagsToQuizInDatabase(Connection connection, Quiz quiz, int lastQuizId) {
		String query = "INSERT INTO " + DbContract.quizTagsTable.TABLE_NAME + " ( "
				+ DbContract.quizTagsTable.COLUMN_NAME_QUIZ_ID + " , " + DbContract.quizTagsTable.COLUMN_NAME_TAG_NAME
				+ " ) VALUES ( ? , ? )";

		List<String> tags = quiz.getTags();
		for (String tag : tags) {
			PreparedStatement stm;
			try {
				stm = connection.prepareStatement(query);
				stm.setInt(1, lastQuizId);
				stm.setString(2, tag);
				stm.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * gets category id from categories table
	 * 
	 * @param string
	 * @param connection
	 * @return
	 */
	private int selectCategory(String string, Connection connection) {
		String query = "SELECT * FROM " + DbContract.categoriesTable.TABLE_NAME + " WHERE "
				+ DbContract.categoriesTable.COLUMN_NAME_CATEGORY_NAME + " LIKE ?";
		PreparedStatement stm;
		try {
			stm = connection.prepareStatement(query);
			stm.setString(1, string);
			ResultSet rs = stm.executeQuery();
			if (rs.next())
				return rs.getInt(DbContract.categoriesTable.COLUMN_NAME_CATEGORY_ID);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * adds single option in database
	 * 
	 * @param connection
	 * @param currentOption
	 * @param lastQuestionId
	 */
	private void addOptionToDatabase(Connection connection, Option currentOption, int lastQuestionId) {
		String query = "INSERT INTO " + DbContract.questionOptionsTable.TABLE_NAME + " ( "
				+ DbContract.questionOptionsTable.COLUMN_NAME_QUESTION_ID + " , "
				+ DbContract.questionOptionsTable.COLUMN_NAME_OPTION_TEXT + " ) VALUES( ? , ? )";
		PreparedStatement stm;
		try {
			stm = connection.prepareStatement(query);
			stm.setInt(1, lastQuestionId);
			stm.setString(2, currentOption.getOption());
			stm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
	 */
	private void addAnswerToDatabase(Connection connection, Answer currentAnswer, int lastSubquestionId) {
		int lastAnswerId = addPairInAnswerSubquestionMap(connection, lastSubquestionId);
		String query = "INSERT INTO " + DbContract.answersTable.TABLE_NAME + " ( "
				+ DbContract.answersTable.COLUMN_NAME_ANSWER_ID + " , "
				+ DbContract.answersTable.COLUMN_NAME_ANSWER_TEXT + " , "
				+ DbContract.answersTable.COLUMN_NAME_PARSER_SYMBOL + " ) values ( ? , ? , ? )";
		for (String answerText : currentAnswer.getAnswers()) {
			PreparedStatement stm;
			try {
				stm = connection.prepareStatement(query);
				stm.setInt(1, lastAnswerId);
				stm.setString(2, answerText);
				stm.setString(3, "" + currentAnswer.getParserSymbol());
				stm.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * add answer id and subquestion id in mapping table
	 * 
	 * @param connection
	 * @param lastSubquestionId
	 * @return
	 */
	private int addPairInAnswerSubquestionMap(Connection connection, int lastSubquestionId) {
		String query = "INSERT INTO " + DbContract.answerSubquestionMapTable.TABLE_NAME + " ( "
				+ DbContract.answerSubquestionMapTable.COLUMN_NAME_SUBQUESTION_ID + " ) VALUES (?)";
		PreparedStatement mappingStm;
		int lastAnswerId = 0;
		try {
			mappingStm = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			mappingStm.setInt(1, lastSubquestionId);
			mappingStm.executeUpdate();
			ResultSet rs = mappingStm.getGeneratedKeys();
			if (rs.next())
				lastAnswerId = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
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
	 */
	private int addSubquestionToDatabase(Connection connection, Subquestion currentSubquestion, int lastQuestionId) {
		String query = "INSERT INTO " + DbContract.subquestionsTable.TABLE_NAME + "( "
				+ DbContract.subquestionsTable.COLUMN_NAME_QUESTION_ID + " , " + ""
				+ DbContract.subquestionsTable.COLUMN_NAME_SUBQUESTION_TEXT + ") VALUES (? , ?)";
		PreparedStatement stm;
		int lastSubquestionId = 0;
		try {
			stm = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stm.setInt(1, lastQuestionId);
			stm.setString(2, currentSubquestion.getQuestion());
			stm.executeUpdate();

			ResultSet rs = stm.getGeneratedKeys();
			if (rs.next()) {
				lastSubquestionId = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lastSubquestionId;
	}

	/**
	 * function adds single question in database
	 * 
	 * @param connection
	 *            sql database connection
	 * @param currentQuestion
	 *            question object
	 * @param lastQuizId
	 *            quiz id which the question belongs to
	 * @return returns the unique id which the question was added at
	 */
	private int addQuestionToDatabase(Connection connection, Question currentQuestion, int lastQuizId) {
		String query = "INSERT INTO " + DbContract.questionsTable.TABLE_NAME + " ( "
				+ DbContract.questionsTable.COLUMN_NAME_QUIZ_ID + " , "
				+ DbContract.questionsTable.COLUMN_NAME_QUESTION_TYPE + " , "
				+ DbContract.questionsTable.COLUMN_NAME_QUESTION_NOTE + " , "
				+ DbContract.questionsTable.COLUMN_NAME_ANSWER_ORDER_SENSITIVE + " ) values ( ? , ? , ? , ? )";
		PreparedStatement stm;
		int lastQuestionId = 0;
		try {
			stm = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stm.setInt(1, lastQuizId);
			stm.setInt(2, currentQuestion.getQuestionType());
			stm.setString(3, currentQuestion.getNote());
			stm.setBoolean(4, currentQuestion.isOrderSensitive());
			stm.executeUpdate();

			ResultSet rs = stm.getGeneratedKeys();
			if (rs.next())
				lastQuestionId = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
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
	 */
	private int addQuizToDatabase(Connection connection, Quiz quiz) {
		String query = "INSERT INTO " + DbContract.quizzesTable.TABLE_NAME + " ( "
				+ DbContract.quizzesTable.COLUMN_NAME_QUIZ_NAME + " , "
				+ DbContract.quizzesTable.COLUMN_NAME_ACCOUNT_ID + " , "
				+ DbContract.quizzesTable.COLUMN_NAME_ISREARRANGABLE + " , "
				+ DbContract.quizzesTable.COLUMN_NAME_ISPRACTICABLE + " , "
				+ DbContract.quizzesTable.COLUMN_NAME_DESCRIPTION + " , "
				+ DbContract.quizzesTable.COLUMN_NAME_CATEGORY_ID + " ) values( ?, ?,  ? , ? , ? , ? )";

		PreparedStatement stm;
		int lastQuizId = 0;
		try {
			stm = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stm.setString(1, quiz.getName());
			stm.setLong(2, quiz.getAccountID());
			stm.setBoolean(3, quiz.isRearrangable());
			stm.setBoolean(4, quiz.isPracticable());
			stm.setString(5, quiz.getDescription());
			stm.setInt(6, selectCategory(quiz.getCategory(), connection));
			stm.executeUpdate();
			ResultSet rs = stm.getGeneratedKeys();
			if (rs.next()) {
				lastQuizId = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lastQuizId;
	}

	/**
	 * pulls and returns quiz object from database
	 * 
	 * @param quizId
	 * @return
	 */
	public Quiz getQuiz(int quizId) {
		try (Connection connection = createConnection()) {
			Quiz currentQuiz = selectQuizFromDatabase(connection, quizId);// check
			addTagsToQuiz(currentQuiz, connection, quizId);// check
			addQuestionsToQuiz(currentQuiz, connection, quizId);
			return currentQuiz;
		} catch (ClassNotFoundException | SecurityException | IllegalArgumentException | SQLException e) {
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
	 */
	private void addTagsToQuiz(Quiz currentQuiz, Connection connection, int quizId) {
		String query = "SELECT * FROM " + DbContract.quizTagsTable.TABLE_NAME + " WHERE "
				+ DbContract.quizTagsTable.COLUMN_NAME_QUIZ_ID + " = ?";
		PreparedStatement stm;
		try {
			stm = connection.prepareStatement(query);
			stm.setInt(1, quizId);
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				currentQuiz.addTag(rs.getString(DbContract.quizTagsTable.COLUMN_NAME_TAG_NAME));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * pulls questions by quiz id and adds them to quiz object
	 * 
	 * @param currentQuiz
	 * @param connection
	 * @param quizId
	 */
	private void addQuestionsToQuiz(Quiz currentQuiz, Connection connection, int quizId) {
		String query = "SELECT * FROM " + DbContract.questionsTable.TABLE_NAME + " WHERE "
				+ DbContract.questionsTable.COLUMN_NAME_QUIZ_ID + " = ?";
		PreparedStatement stm;
		try {
			stm = connection.prepareStatement(query);
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns list of all question types found in database. Key is question ID,
	 * value is question name.
	 * 
	 * @return The pairs of question ID's and names.
	 */
	public Map<Integer, String> getAllQuestionTypes() {
		Map<Integer, String> questionTypes = new HashMap<Integer, String>();
		String query = "SELECT " + DbContract.questionTypesTable.COLUMN_NAME_QUESTION_TYPE_ID + ", "
				+ DbContract.questionTypesTable.COLUMN_NAME_QUESTION_TYPE_NAME + " FROM "
				+ DbContract.questionTypesTable.TABLE_NAME + ";";
		try (Connection con = DriverManager.getConnection("jdbc:mysql://" + server, account, password);
				Statement stmt = con.createStatement()) {
			stmt.executeQuery("USE " + database);
			try (PreparedStatement ps = con.prepareStatement(query)) {
				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						Integer questionTypeID = rs.getInt(DbContract.questionTypesTable.COLUMN_NAME_QUESTION_TYPE_ID);
						String questionTypeName = rs
								.getString(DbContract.questionTypesTable.COLUMN_NAME_QUESTION_TYPE_NAME);
						questionTypes.put(questionTypeID, questionTypeName);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return questionTypes;
	}

	/**
	 * pulls data from database representing subquestions of the given question
	 * 
	 * @param connection
	 * @param questionId
	 * @param question
	 */
	private void addSubquestionsToQuestion(Connection connection, int questionId, Question question) {
		String query = "SELECT * FROM " + DbContract.subquestionsTable.TABLE_NAME + " WHERE "
				+ DbContract.subquestionsTable.COLUMN_NAME_QUESTION_ID + " = ?";
		PreparedStatement stm;
		try {
			stm = connection.prepareStatement(query);
			stm.setInt(1, questionId);
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				Subquestion subquestion = new Subquestion(
						rs.getString(DbContract.subquestionsTable.COLUMN_NAME_SUBQUESTION_TEXT));
				subquestion.setSubquestionID(rs.getInt(DbContract.subquestionsTable.COLUMN_NAME_SUBQUESTION_ID));
				addAnswersToSubQuestion(subquestion, subquestion.getSubquestionID(), connection);
				question.addSubquestion(subquestion);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * pulls data from map table, which connects answer and subquestion
	 * 
	 * @param subquestion
	 * @param subquestionID
	 * @param connection
	 */
	private void addAnswersToSubQuestion(Subquestion subquestion, int subquestionID, Connection connection) {
		String query = "SELECT * FROM " + DbContract.answerSubquestionMapTable.TABLE_NAME + " WHERE "
				+ DbContract.answerSubquestionMapTable.COLUMN_NAME_SUBQUESTION_ID + " = ?";
		PreparedStatement stm;
		try {
			stm = connection.prepareStatement(query);
			stm.setInt(1, subquestionID);
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				int answerId = rs.getInt(DbContract.answerSubquestionMapTable.COLUMN_NAME_ANSWER_ID);
				addAnswerToSubquestion(subquestion, answerId, connection);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * pulls data from database representing answers of the ginven subquestion
	 * 
	 * @param subquestion
	 * @param answerId
	 * @param connection
	 */
	private void addAnswerToSubquestion(Subquestion subquestion, int answerId, Connection connection) {
		String query = "SELECT * FROM " + DbContract.answersTable.TABLE_NAME + " WHERE "
				+ DbContract.answersTable.COLUMN_NAME_ANSWER_ID + " = ?";
		PreparedStatement stm;
		try {
			stm = connection.prepareStatement(query);
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * add order sensitivity, id and note to given question
	 * 
	 * @param connection
	 * @param questionId
	 * @param question
	 */
	private void addDetailsToQuestion(Connection connection, int questionId, Question question) {
		question.setQuestionID(questionId);
		String query = "SELECT * FROM " + DbContract.questionsTable.TABLE_NAME + " WHERE "
				+ DbContract.questionsTable.COLUMN_NAME_QUESTION_ID + " = ?";
		PreparedStatement stm;
		try {
			stm = connection.prepareStatement(query);
			stm.setInt(1, questionId);
			ResultSet rs = stm.executeQuery();
			if (rs.next()) {
				question.setOrderSensitive(rs.getBoolean(DbContract.questionsTable.COLUMN_NAME_ANSWER_ORDER_SENSITIVE));
				question.setNote(rs.getString(DbContract.questionsTable.COLUMN_NAME_QUESTION_NOTE));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * adds options of the question in database
	 * 
	 * @param connection
	 * @param questionId
	 * @param question
	 */
	private void addOptionsToQuestion(Connection connection, int questionId, Question question) {
		String query = "SELECT * FROM " + DbContract.questionOptionsTable.TABLE_NAME + " WHERE "
				+ DbContract.questionOptionsTable.COLUMN_NAME_QUESTION_ID + " = ?";
		PreparedStatement stm;
		try {
			stm = connection.prepareStatement(query);
			stm.setInt(1, questionId);
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				Option option = new Option(rs.getString(DbContract.questionOptionsTable.COLUMN_NAME_OPTION_TEXT));
				question.addOption(option);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param typeId
	 * @return string representing type name of the given id
	 */
	public String getTypeByTypeId(int typeId) {
		try (Connection connection = createConnection()) {
			String query = "SELECT * FROM " + DbContract.questionTypesTable.TABLE_NAME + " WHERE "
					+ DbContract.questionTypesTable.COLUMN_NAME_QUESTION_TYPE_ID + " = ?";
			PreparedStatement stm = connection.prepareStatement(query);
			stm.setInt(1, typeId);
			ResultSet rs = stm.executeQuery();
			if (rs.next()) {
				return rs.getString(DbContract.questionTypesTable.COLUMN_NAME_QUESTION_TYPE_NAME);
			}
		} catch (ClassNotFoundException | SQLException e) {
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
	 */
	private Quiz selectQuizFromDatabase(Connection connection, int quizId) {
		String query = "SELECT * FROM " + DbContract.quizzesTable.TABLE_NAME + " WHERE "
				+ DbContract.quizzesTable.COLUMN_NAME_QUIZ_ID + " = ?";

		PreparedStatement stm;
		try {
			stm = connection.prepareStatement(query);
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * returns string representing name of category of the given id
	 * 
	 * @param categoryId
	 * @return string representing name of category of the given id
	 */
	public String selectCategoryName(int categoryId) {
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
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @return hashMap including id - name pairs for all quizes
	 */
	public HashMap<Integer, String> getAllQuizzes() {
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
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @return how many quizzes are in database
	 */
	public long getNumQuizzes() {
		try (Connection connection = createConnection()) {
			String query = "SELECT * FROM " + DbContract.quizzesTable.TABLE_NAME;
			PreparedStatement stm = connection.prepareStatement(query);
			ResultSet rs = stm.executeQuery();
			rs.last();
			return rs.getRow();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * Returns the list of category names found in database
	 * 
	 * @return The list of category names
	 */
	public List<String> getAllCategoryNames() {
		List<String> result = new ArrayList<String>();
		String query = "SELECT " + DbContract.categoriesTable.COLUMN_NAME_CATEGORY_NAME + " FROM "
				+ DbContract.categoriesTable.TABLE_NAME;
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

	/**
	 * Deletes whole quiz with its helper tables from database
	 * 
	 * @param quizID
	 */
	public void deleteQuiz(int quizID) {
		try (Connection connection = createConnection()) {
			String use = "use quiz_database";
			PreparedStatement stm = connection.prepareStatement(use);
			stm.execute(use);
		} catch (SQLException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		deleteQuizAttempts(quizID);
		deleteQuizReport(quizID);
		deleteQuizReviews(quizID);
		deleteQuizTags(quizID);
		deleteQuestionOptions(quizID);
		deleteAnswers(quizID);
		deleteAnswerSubquestionsMaps(quizID);
		deleteSubquestions(quizID);
		deleteQuestions(quizID);
		deleteOnlyQuiz(quizID);
	}

	/**
	 * Deletes quiz from database
	 * 
	 * @param quizID
	 */
	public void deleteOnlyQuiz(int quizID) {
		String query = "DELETE FROM " + DbContract.quizzesTable.TABLE_NAME + " WHERE "
				+ DbContract.quizzesTable.COLUMN_NAME_QUIZ_ID + "=?";
		executeUpdateWithQuizID(quizID, query);
	}

	/**
	 * Deletes all subquestions from database for questions of current quiz
	 * 
	 * @param quizID
	 */
	public void deleteSubquestions(int quizID) {
		String query = "DELETE FROM " + DbContract.subquestionsTable.TABLE_NAME + " WHERE "
				+ DbContract.subquestionsTable.COLUMN_NAME_QUESTION_ID + " IN " + "(SELECT "
				+ DbContract.questionsTable.COLUMN_NAME_QUESTION_ID + " FROM " + DbContract.questionsTable.TABLE_NAME
				+ " WHERE " + DbContract.questionsTable.COLUMN_NAME_QUIZ_ID + "=?)";
		executeUpdateWithQuizID(quizID, query);
	}

	/**
	 * Deletes all deleteAnswerSubquestionsMaps from database for questions of
	 * current quiz
	 * 
	 * @param quizID
	 */
	public void deleteAnswerSubquestionsMaps(int quizID) {
		String query = "Delete FROM " + DbContract.answerSubquestionMapTable.TABLE_NAME + " WHERE "
				+ DbContract.answerSubquestionMapTable.COLUMN_NAME_SUBQUESTION_ID + " IN " + "(SELECT "
				+ DbContract.subquestionsTable.COLUMN_NAME_SUBQUESTION_ID + " FROM "
				+ DbContract.subquestionsTable.TABLE_NAME + " WHERE "
				+ DbContract.subquestionsTable.COLUMN_NAME_QUESTION_ID + " IN " + "(SELECT "
				+ DbContract.questionsTable.COLUMN_NAME_QUESTION_ID + " FROM " + DbContract.questionsTable.TABLE_NAME
				+ " WHERE " + DbContract.questionsTable.COLUMN_NAME_QUIZ_ID + "=?))";

		executeUpdateWithQuizID(quizID, query);
	}

	/**
	 * Deletes all answers from database for questions of current quiz
	 * 
	 * @param quizID
	 */
	public void deleteAnswers(int quizID) {
		String query = "DELETE FROM " + DbContract.answersTable.TABLE_NAME + " WHERE "
				+ DbContract.answersTable.COLUMN_NAME_ANSWER_ID + " IN " + "(SELECT "
				+ DbContract.answerSubquestionMapTable.COLUMN_NAME_ANSWER_ID + " FROM "
				+ DbContract.answerSubquestionMapTable.TABLE_NAME + " WHERE "
				+ DbContract.answerSubquestionMapTable.COLUMN_NAME_SUBQUESTION_ID + " IN " + "(SELECT "
				+ DbContract.subquestionsTable.COLUMN_NAME_SUBQUESTION_ID + " FROM "
				+ DbContract.subquestionsTable.TABLE_NAME + " WHERE "
				+ DbContract.subquestionsTable.COLUMN_NAME_QUESTION_ID + " IN " + "(SELECT "
				+ DbContract.questionsTable.COLUMN_NAME_QUESTION_ID + " FROM " + DbContract.questionsTable.TABLE_NAME
				+ " WHERE " + DbContract.questionsTable.COLUMN_NAME_QUIZ_ID + "=?)))";
		executeUpdateWithQuizID(quizID, query);
	}

	/**
	 * Deletes all question options from database for questions of current quiz
	 * 
	 * @param quizID
	 */
	public void deleteQuestionOptions(int quizID) {
		String query = "DELETE FROM " + DbContract.questionOptionsTable.TABLE_NAME + " WHERE "
				+ DbContract.questionOptionsTable.COLUMN_NAME_QUESTION_ID + " IN " + "(SELECT "
				+ DbContract.questionsTable.COLUMN_NAME_QUESTION_ID + " FROM " + DbContract.questionsTable.TABLE_NAME
				+ " WHERE " + DbContract.questionsTable.COLUMN_NAME_QUIZ_ID + "=?)";
		executeUpdateWithQuizID(quizID, query);
	}

	/**
	 * Deletes all questions from database for current quiz
	 * 
	 * @param quizID
	 */
	public void deleteQuestions(int quizID) {
		String query = "DELETE FROM " + DbContract.questionsTable.TABLE_NAME + " WHERE "
				+ DbContract.questionsTable.COLUMN_NAME_QUIZ_ID + "=?";
		executeUpdateWithQuizID(quizID, query);
	}

	/**
	 * Deletes all quiz attempts from database for current quiz
	 * 
	 * @param quizID
	 */
	public void deleteQuizAttempts(int quizID) {
		String query = "DELETE FROM " + DbContract.quizAttemptsTable.TABLE_NAME + " WHERE "
				+ DbContract.quizAttemptsTable.COLUMN_NAME_QUIZ_ID + "=?";
		executeUpdateWithQuizID(quizID, query);
	}

	/**
	 * Deletes all quiz tags from database for current quiz
	 * 
	 * @param quizID
	 */
	public void deleteQuizTags(int quizID) {
		String query = "DELETE FROM " + DbContract.quizTagsTable.TABLE_NAME + " WHERE "
				+ DbContract.quizTagsTable.COLUMN_NAME_QUIZ_ID + "=?";
		executeUpdateWithQuizID(quizID, query);
	}

	/**
	 * Deletes all quiz reviews from database for current quiz
	 * 
	 * @param quizID
	 */
	public void deleteQuizReviews(int quizID) {
		String query = "DELETE FROM " + DbContract.quizReviewsTable.TABLE_NAME + " WHERE "
				+ DbContract.quizReviewsTable.COLUMN_NAME_QUIZ_ID + "=?";
		executeUpdateWithQuizID(quizID, query);
	}

	/**
	 * Deletes all quiz reports from database for current quiz
	 * 
	 * @param quizID
	 */
	public void deleteQuizReport(int quizID) {
		String query = "DELETE FROM " + DbContract.quizReportsTable.TABLE_NAME + " WHERE "
				+ DbContract.quizReportsTable.COLUMN_NAME_QUIZ_ID + "=?";
		executeUpdateWithQuizID(quizID, query);
	}

	/**
	 * Executes referenced query with QuizID setted
	 * 
	 * @param quizID
	 * @param query
	 */
	public void executeUpdateWithQuizID(int quizID, String query) {
		try (Connection connection = createConnection()) {
			PreparedStatement stm = connection.prepareStatement(query);
			stm.setInt(1, quizID);
			stm.executeUpdate();
		} catch (SQLException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Returns the list of all achievements that be earned
	 * 
	 * @return The list of all achievements
	 */
	public List<Achievement> getAllAchievementsExceptEarned() {
		List<Achievement> result = new ArrayList<Achievement>();
		String query = "SELECT " + DbContract.achievementsTable.COLUMN_NAME_ACHIEVEMENT_ID + ", "
				+ DbContract.achievementsTable.COLUMN_NAME_ACHIEVEMENT_NAME + " FROM "
				+ DbContract.achievementsTable.TABLE_NAME + " WHERE "
				+ DbContract.achievementsTable.COLUMN_NAME_ACHIEVEMENT_NAME + " NOT IN (SELECT "
				+ DbContract.achievementsTable.COLUMN_NAME_ACHIEVEMENT_NAME + " FROM "
				+ DbContract.achievementsTable.TABLE_NAME + " a, " + DbContract.accountAchievementsTable.TABLE_NAME
				+ " aa WHERE " + "a." + DbContract.achievementsTable.COLUMN_NAME_ACHIEVEMENT_ID + " = " + "aa."
				+ DbContract.accountAchievementsTable.COLUMN_NAME_ACHIEVEMENT_ID + " AND aa."
				+ DbContract.accountAchievementsTable.COLUMN_NAME_ACCOUNT_ID + " = ?);";
		System.out.println(query);
		try (Connection con = DriverManager.getConnection("jdbc:mysql://" + server, account, password);
				Statement stmt = con.createStatement()) {
			stmt.executeQuery("USE " + database);
			try (PreparedStatement ps = con.prepareStatement(query)) {
				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						int achievementID = rs.getInt(DbContract.achievementsTable.COLUMN_NAME_ACHIEVEMENT_ID);
						String name = rs.getString(DbContract.achievementsTable.COLUMN_NAME_ACHIEVEMENT_NAME);
						List<Property> properties = getPropertiesByAchievementID(con, achievementID);
						Achievement newAchievement = new Achievement(achievementID, name, properties);
						result.add(newAchievement);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	// Finds all properties for a single achievement by ID
	private List<Property> getPropertiesByAchievementID(Connection con, int achievementID) {
		List<Property> properties = new ArrayList<Property>();
		String query = "SELECT p." + DbContract.propertiesTable.COLUMN_NAME_PROPERTY_ID + ", "
				+ DbContract.propertiesTable.COLUMN_NAME_PROPERTY_PARAMETER + ", "
				+ DbContract.propertiesTable.COLUMN_NAME_PROPERTY_BOUND + ", "
				+ DbContract.propertiesTable.COLUMN_NAME_PROPERTY_BOUND_TYPE + " FROM "
				+ DbContract.propertiesTable.TABLE_NAME + " as p, " + DbContract.achievementPropertiesTable.TABLE_NAME
				+ " as ap " + " WHERE p." + DbContract.propertiesTable.COLUMN_NAME_PROPERTY_ID + " = ap."
				+ DbContract.achievementPropertiesTable.COLUMN_NAME_PROPERTY_ID + " AND ap."
				+ DbContract.achievementPropertiesTable.COLUMN_NAME_ACHIEVEMENT_ID + " = ?;";
		try (Statement stmt = con.createStatement()) {
			stmt.executeQuery("USE " + database);
			try (PreparedStatement ps = con.prepareStatement(query)) {
				ps.setInt(1, achievementID);
				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						Property currentProperty = generateProperty(rs);
						properties.add(currentProperty);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return properties;
	}

	// From result set gets and analyzes data for a single property
	private Property generateProperty(ResultSet rs) {
		int propertyID = 0, activationBoundValue = 0;
		String propertyParameter = "", activationBoundType = "";
		try {
			propertyID = rs.getInt("p." + DbContract.propertiesTable.COLUMN_NAME_PROPERTY_ID);
			propertyParameter = rs.getString(DbContract.propertiesTable.COLUMN_NAME_PROPERTY_PARAMETER);
			activationBoundValue = rs.getInt(DbContract.propertiesTable.COLUMN_NAME_PROPERTY_BOUND);
			activationBoundType = rs.getString(DbContract.propertiesTable.COLUMN_NAME_PROPERTY_BOUND_TYPE);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new Property(propertyID, propertyParameter, activationBoundValue, activationBoundType);
	}

	/**
	 * Adds data about unlocked achievements for specified user.
	 * 
	 * @param unlockedAchievementIDs
	 *            unlocked achievement ID's
	 * @param accountID
	 *            for whom the data should be updated
	 */
	public void addUnlockedAchievements(List<Integer> unlockedAchievementIDs, int accountID) {
		String query = "INSERT INTO " + DbContract.accountAchievementsTable.TABLE_NAME + " ( "
				+ DbContract.accountAchievementsTable.COLUMN_NAME_ACCOUNT_ID + ", "
				+ DbContract.accountAchievementsTable.COLUMN_NAME_ACHIEVEMENT_ID + ") VALUES (?, ?);";
		try (Connection con = DriverManager.getConnection("jdbc:mysql://" + server, account, password);
				Statement stmt = con.createStatement()) {
			stmt.executeQuery("USE " + database);
			try (PreparedStatement ps = con.prepareStatement(query)) {
				for (int i = 0; i < unlockedAchievementIDs.size(); i++) {
					ps.setInt(1, accountID);
					ps.setInt(2, unlockedAchievementIDs.get(i));
					ps.execute();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * searches particular quizzes by name
	 * 
	 * @param searchedWord
	 * @return
	 */
	public ArrayList<String> searchQuizByName(String searchedWord) {
		try (Connection connection = createConnection()) {
			ArrayList<String> quizes = new ArrayList<>();
			String query = "SELECT " + DbContract.quizzesTable.COLUMN_NAME_QUIZ_ID + " , "
					+ DbContract.quizzesTable.COLUMN_NAME_QUIZ_NAME + " FROM " + DbContract.quizzesTable.TABLE_NAME
					+ " WHERE " + DbContract.quizzesTable.COLUMN_NAME_QUIZ_NAME + " LIKE ?";
			PreparedStatement stm = connection.prepareStatement(query);
			stm.setString(1, searchedWord + "%");
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				String name = rs.getString(DbContract.quizzesTable.COLUMN_NAME_QUIZ_NAME);
				quizes.add(name);
			}
			return quizes;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void addQuizAttempt(QuizAttempt attempt) {
		try (Connection connection = createConnection()) {
			String query = "INSERT INTO " + DbContract.quizAttemptsTable.TABLE_NAME + " ("
					+ DbContract.quizAttemptsTable.COLUMN_NAME_QUIZ_ID + ", "
					+ DbContract.quizAttemptsTable.COLUMN_NAME_ACCOUNT_ID + ", "
					+ DbContract.quizAttemptsTable.COLUMN_NAME_SCORE + ", "
					+ DbContract.quizAttemptsTable.COLUMN_NAME_START_TIME + ", "
					+ DbContract.quizAttemptsTable.COLUMN_NAME_FINISH_TIME + " ) VALUES (? , ?, ?, ?, ?)";
			PreparedStatement stm = connection.prepareStatement(query);
			stm.setInt(1, attempt.getQuizID());
			stm.setInt(2, attempt.getAccountID());
			stm.setInt(3, attempt.getScore());
			stm.setTimestamp(4, new java.sql.Timestamp(attempt.getStartTime().getTime()));
			stm.setTimestamp(5, new java.sql.Timestamp(attempt.getFinishTime().getTime()));
			stm.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {

		}
	}

	public int getQuizIdByName(String quizName) {
		try (Connection connection = createConnection()) {
			String query = "SELECT * FROM " + DbContract.quizzesTable.TABLE_NAME + " WHERE "
					+ DbContract.quizzesTable.COLUMN_NAME_QUIZ_NAME + " LIKE ?";
			PreparedStatement stm = connection.prepareStatement(query);
			stm.setString(1, quizName);
			ResultSet rs = stm.executeQuery();
			if (rs.next()) {
				return rs.getInt(DbContract.quizzesTable.COLUMN_NAME_QUIZ_ID);
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * Returns the name of the quiz which has given id number
	 * 
	 * @param quizID
	 *            The id number of the quiz
	 * @return The name of the quiz
	 */
	public String getQuizName(int quizID) {
		String query = "SELECT " + DbContract.quizzesTable.COLUMN_NAME_QUIZ_NAME + " FROM "
				+ DbContract.quizzesTable.TABLE_NAME + " WHERE " + DbContract.quizzesTable.COLUMN_NAME_QUIZ_ID + " = ?";
		try (Connection connection = createConnection()) {
			PreparedStatement stm = connection.prepareStatement(query);
			stm.setInt(1, quizID);
			ResultSet rs = stm.executeQuery();
			rs.last();
			String result = rs.getString("quiz_name");
			return result;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * Returns the description of the quiz which has given id number
	 * 
	 * @param quizID
	 *            The id number of the quiz
	 * @return The description of the quiz
	 */
	public String getQuizDescription(int quizID) {
		String query = "SELECT " + DbContract.quizzesTable.COLUMN_NAME_DESCRIPTION + " FROM "
				+ DbContract.quizzesTable.TABLE_NAME + " WHERE " + DbContract.quizzesTable.COLUMN_NAME_QUIZ_ID + " = ?";
		try (Connection connection = createConnection()) {
			PreparedStatement stm = connection.prepareStatement(query);
			stm.setInt(1, quizID);
			ResultSet rs = stm.executeQuery();
			rs.last();
			String result = rs.getString("Description");
			return result;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * Returns the user name of the creator of the quiz
	 * 
	 * @param quizID
	 *            id number of the quiz
	 * @return The name of the quiz creator
	 */
	public String getQuizCreator(int quizID) {
		String query = "SELECT " + DbContract.accountsTable.COLUMN_NAME_USERNAME + " FROM "
				+ DbContract.accountsTable.TABLE_NAME + " WHERE " + DbContract.accountsTable.COLUMN_NAME_ID
				+ " = ( SELECT " + DbContract.quizzesTable.COLUMN_NAME_ACCOUNT_ID + " FROM "
				+ DbContract.quizzesTable.TABLE_NAME + " WHERE " + DbContract.quizzesTable.COLUMN_NAME_QUIZ_ID
				+ " = ?)";
		try (Connection connection = createConnection()) {
			PreparedStatement stm = connection.prepareStatement(query);
			stm.setInt(1, quizID);
			ResultSet rs = stm.executeQuery();
			rs.last();
			String result = rs.getString("account_user_name");
			return result;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			e.printStackTrace();
		}
		return "";

	}

	/**
	 * Returns the last performances from quiz history for the quiz with given
	 * id number The result is map which contains user name and his attempt
	 * score in this quiz
	 * 
	 * @param quizID
	 *            the id of the quiz
	 * @return All attempt performances for this quiz
	 */
	public ArrayList<DataCouple> getLastPerformances(int quizID) {
		ArrayList<DataCouple> result = new ArrayList<DataCouple>();
		String query = "SELECT " + DbContract.accountsTable.TABLE_NAME + "."
				+ DbContract.accountsTable.COLUMN_NAME_USERNAME + " , " + DbContract.quizAttemptsTable.TABLE_NAME + "."
				+ DbContract.quizAttemptsTable.COLUMN_NAME_SCORE + " FROM " + DbContract.quizAttemptsTable.TABLE_NAME
				+ " , " + DbContract.accountsTable.TABLE_NAME + " WHERE " + DbContract.quizAttemptsTable.TABLE_NAME
				+ "." + DbContract.quizAttemptsTable.COLUMN_NAME_ACCOUNT_ID + " = "
				+ DbContract.accountsTable.TABLE_NAME + "." + DbContract.accountsTable.COLUMN_NAME_ID + " AND "
				+ DbContract.quizAttemptsTable.TABLE_NAME + "." + DbContract.quizAttemptsTable.COLUMN_NAME_QUIZ_ID
				+ " = ? ";
		try (Connection connection = createConnection()) {
			PreparedStatement stm = connection.prepareStatement(query);
			stm.setInt(1, quizID);
			ResultSet rs = stm.executeQuery();
			int usernameColumn = rs.findColumn(DbContract.accountsTable.COLUMN_NAME_USERNAME);
			int scoreColumn = rs.findColumn(DbContract.quizAttemptsTable.COLUMN_NAME_SCORE);
			while (rs.next()) {
				String username = rs.getString(usernameColumn);
				int score = rs.getInt(scoreColumn);
				DataCouple couple = new DataCouple(username, score);
				result.add(couple);
			}
			return result;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Returns the last performances from quiz history for the quiz with given
	 * id number result data is ordered by the score results in the attempts of
	 * the quiz. The result is map which contains user name and his attempt
	 * score in this quiz
	 * 
	 * @param quizID
	 *            the id of the quiz
	 * @return All attempt performances for this quiz, ordered by score
	 */
	public ArrayList<DataCouple> getLastPerformsOrderByScore(int quizID) {
		ArrayList<DataCouple> result = new ArrayList<DataCouple>();
		String query = "SELECT " + DbContract.accountsTable.TABLE_NAME + "."
				+ DbContract.accountsTable.COLUMN_NAME_USERNAME + " , " + DbContract.quizAttemptsTable.TABLE_NAME + "."
				+ DbContract.quizAttemptsTable.COLUMN_NAME_SCORE + " FROM " + DbContract.quizAttemptsTable.TABLE_NAME
				+ " , " + DbContract.accountsTable.TABLE_NAME + " WHERE " + DbContract.quizAttemptsTable.TABLE_NAME
				+ "." + DbContract.quizAttemptsTable.COLUMN_NAME_ACCOUNT_ID + " = "
				+ DbContract.accountsTable.TABLE_NAME + "." + DbContract.accountsTable.COLUMN_NAME_ID + " AND "
				+ DbContract.quizAttemptsTable.TABLE_NAME + "." + DbContract.quizAttemptsTable.COLUMN_NAME_QUIZ_ID
				+ " = ? " + " ORDER BY " + DbContract.quizAttemptsTable.TABLE_NAME + "."
				+ DbContract.quizAttemptsTable.COLUMN_NAME_SCORE + " desc;";

		try (Connection connection = createConnection()) {
			PreparedStatement stm = connection.prepareStatement(query);
			stm.setInt(1, quizID);
			ResultSet rs = stm.executeQuery();
			int usernameColumn = rs.findColumn(DbContract.accountsTable.COLUMN_NAME_USERNAME);
			int scoreColumn = rs.findColumn(DbContract.quizAttemptsTable.COLUMN_NAME_SCORE);
			while (rs.next()) {
				String username = rs.getString(usernameColumn);
				int score = rs.getInt(scoreColumn);
				DataCouple couple = new DataCouple(username, score);
				result.add(couple);
			}
			return result;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Returns the last performances from quiz history for the quiz with given
	 * id number result data is ordered by the date of the quiz attempt of the
	 * quiz. The result is map which contains user name and his attempt score in
	 * this quiz
	 * 
	 * @param quizID
	 *            the id of the quiz
	 * @return All attempt performances for this quiz, ordered by date
	 */
	public ArrayList<DataCouple> getLastPerformsOrderByDate(int quizID) {
		ArrayList<DataCouple> result = new ArrayList<DataCouple>();

		String query = "SELECT " + DbContract.accountsTable.TABLE_NAME + "."
				+ DbContract.accountsTable.COLUMN_NAME_USERNAME + " , " + DbContract.quizAttemptsTable.TABLE_NAME + "."
				+ DbContract.quizAttemptsTable.COLUMN_NAME_SCORE + " FROM " + DbContract.quizAttemptsTable.TABLE_NAME
				+ " , " + DbContract.accountsTable.TABLE_NAME + " WHERE " + DbContract.quizAttemptsTable.TABLE_NAME
				+ "." + DbContract.quizAttemptsTable.COLUMN_NAME_ACCOUNT_ID + " = "
				+ DbContract.accountsTable.TABLE_NAME + "." + DbContract.accountsTable.COLUMN_NAME_ID + " AND "
				+ DbContract.quizAttemptsTable.TABLE_NAME + "." + DbContract.quizAttemptsTable.COLUMN_NAME_QUIZ_ID
				+ " = ? " + " ORDER BY " + DbContract.quizAttemptsTable.TABLE_NAME + "."
				+ DbContract.quizAttemptsTable.COLUMN_NAME_FINISH_TIME;
		try (Connection connection = createConnection()) {
			PreparedStatement stm = connection.prepareStatement(query);
			stm.setInt(1, quizID);
			ResultSet rs = stm.executeQuery();
			int usernameColumn = rs.findColumn("account_user_name");
			int scoreColumn = rs.findColumn("score");
			while (rs.next()) {
				String username = rs.getString(usernameColumn);
				int score = rs.getInt(scoreColumn);
				DataCouple couple = new DataCouple(username, score);
				result.add(couple);
			}
			return result;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Returns the last performances from quiz history for the quiz with given
	 * id number result data is ordered by the the time duration which was used
	 * in this attempt of the quiz. The result is map which contains user name
	 * and his attempt score in this quiz
	 * 
	 * @param quizID
	 *            the id of the quiz
	 * @return All attempt performances for this quiz, ordered by time duration
	 */
	public ArrayList<DataCouple> getLastPerformsOrderByTime(int quizID) {
		ArrayList<DataCouple> result = new ArrayList<DataCouple>();
		String query = "SELECT " + DbContract.accountsTable.TABLE_NAME + "."
				+ DbContract.accountsTable.COLUMN_NAME_USERNAME + " , " + DbContract.quizAttemptsTable.TABLE_NAME + "."
				+ DbContract.quizAttemptsTable.COLUMN_NAME_SCORE + " FROM " + DbContract.quizAttemptsTable.TABLE_NAME
				+ " , " + DbContract.accountsTable.TABLE_NAME + " WHERE " + DbContract.quizAttemptsTable.TABLE_NAME
				+ "." + DbContract.quizAttemptsTable.COLUMN_NAME_ACCOUNT_ID + " = "
				+ DbContract.accountsTable.TABLE_NAME + "." + DbContract.accountsTable.COLUMN_NAME_ID + " AND "
				+ DbContract.quizAttemptsTable.TABLE_NAME + "." + DbContract.quizAttemptsTable.COLUMN_NAME_QUIZ_ID
				+ " = ? " + " ORDER BY (" + DbContract.quizAttemptsTable.TABLE_NAME + "."
				+ DbContract.quizAttemptsTable.COLUMN_NAME_FINISH_TIME + " - " + DbContract.quizAttemptsTable.TABLE_NAME
				+ "." + DbContract.quizAttemptsTable.COLUMN_NAME_START_TIME + " );";
		try (Connection connection = createConnection()) {
			PreparedStatement stm = connection.prepareStatement(query);
			stm.setInt(1, quizID);
			ResultSet rs = stm.executeQuery();
			int usernameColumn = rs.findColumn("account_user_name");
			int scoreColumn = rs.findColumn("score");
			while (rs.next()) {
				String username = rs.getString(usernameColumn);
				int score = rs.getInt(scoreColumn);
				DataCouple couple = new DataCouple(username, score);
				result.add(couple);
			}
			return result;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Returns the last performances from quiz history for the quiz with given
	 * id number, which takes place atcurrent day result data is ordered by the
	 * score of the attempt of the quiz. The result is map which contains user
	 * name and his attempt score in this quiz
	 * 
	 * @param quizID
	 *            the id of the quiz
	 * @return All attempt performances at current day with best scores, for
	 *         this quiz
	 */
	public ArrayList<DataCouple> getTopPerformancesToday(int quizID) {
		ArrayList<DataCouple> result = new ArrayList<DataCouple>();
		String query = "SELECT " + DbContract.accountsTable.TABLE_NAME + "."
				+ DbContract.accountsTable.COLUMN_NAME_USERNAME + " , " + DbContract.quizAttemptsTable.TABLE_NAME + "."
				+ DbContract.quizAttemptsTable.COLUMN_NAME_SCORE + " FROM " + DbContract.quizAttemptsTable.TABLE_NAME
				+ " , " + DbContract.accountsTable.TABLE_NAME + " WHERE " + DbContract.quizAttemptsTable.TABLE_NAME
				+ "." + DbContract.quizAttemptsTable.COLUMN_NAME_ACCOUNT_ID + " = "
				+ DbContract.accountsTable.TABLE_NAME + "." + DbContract.accountsTable.COLUMN_NAME_ID + " AND "
				+ DbContract.quizAttemptsTable.TABLE_NAME + "." + DbContract.quizAttemptsTable.COLUMN_NAME_QUIZ_ID
				+ " = ? " + " AND " + DbContract.quizAttemptsTable.TABLE_NAME + "."
				+ DbContract.quizAttemptsTable.COLUMN_NAME_FINISH_TIME + " = ?" + " ORDER BY "
				+ DbContract.quizAttemptsTable.TABLE_NAME + "." + DbContract.quizAttemptsTable.COLUMN_NAME_SCORE
				+ " LIMIT  " + " ? ";

		try (Connection connection = createConnection()) {
			PreparedStatement stm = connection.prepareStatement(query);
			stm.setInt(1, quizID);
			Date date = new Date();
			stm.setTimestamp(2, new java.sql.Timestamp(date.getTime()));
			stm.setInt(3, 10);
			ResultSet rs = stm.executeQuery();
			int usernameColumn = rs.findColumn(DbContract.accountsTable.COLUMN_NAME_USERNAME);
			int scoreColumn = rs.findColumn(DbContract.quizAttemptsTable.COLUMN_NAME_SCORE);
			while (rs.next()) {
				String username = rs.getString(usernameColumn);
				int score = rs.getInt(scoreColumn);
				DataCouple couple = new DataCouple(username, score);
				result.add(couple);
			}
			return result;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Returns the last performances from quiz history for the quiz with given
	 * id number, which takes place at current day result data is ten
	 * performances for this quiz. The result is map which contains user name
	 * and his attempt score in this quiz
	 * 
	 * @param quizID
	 *            the id of the quiz
	 * @return All attempt performances at current day, for this quiz
	 */
	public ArrayList<DataCouple> getRecentPerformances(int quizID) {
		ArrayList<DataCouple> result = new ArrayList<DataCouple>();
		String query = "SELECT " + DbContract.accountsTable.TABLE_NAME + "."
				+ DbContract.accountsTable.COLUMN_NAME_USERNAME + " , " + DbContract.quizAttemptsTable.TABLE_NAME + "."
				+ DbContract.quizAttemptsTable.COLUMN_NAME_SCORE + " FROM " + DbContract.quizAttemptsTable.TABLE_NAME
				+ " , " + DbContract.accountsTable.TABLE_NAME + " WHERE " + DbContract.quizAttemptsTable.TABLE_NAME
				+ "." + DbContract.quizAttemptsTable.COLUMN_NAME_ACCOUNT_ID + " = "
				+ DbContract.accountsTable.TABLE_NAME + "." + DbContract.accountsTable.COLUMN_NAME_ID + " AND "
				+ DbContract.quizAttemptsTable.TABLE_NAME + "." + DbContract.quizAttemptsTable.COLUMN_NAME_QUIZ_ID
				+ " = ? " + " AND " + DbContract.quizAttemptsTable.TABLE_NAME + "."
				+ DbContract.quizAttemptsTable.COLUMN_NAME_FINISH_TIME + " = ?" + " LIMIT  " + " ? ";
		try (Connection connection = createConnection()) {
			PreparedStatement stm = connection.prepareStatement(query);
			stm.setInt(1, quizID);
			Date date = new Date();
			stm.setTimestamp(2, new java.sql.Timestamp(date.getTime()));
			stm.setInt(3, 10);
			ResultSet rs = stm.executeQuery();
			int usernameColumn = rs.findColumn(DbContract.accountsTable.COLUMN_NAME_USERNAME);
			int scoreColumn = rs.findColumn(DbContract.quizAttemptsTable.COLUMN_NAME_SCORE);
			while (rs.next()) {
				String username = rs.getString(usernameColumn);
				int score = rs.getInt(scoreColumn);
				DataCouple couple = new DataCouple(username, score);
				result.add(couple);
			}
			return result;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Returns the max score of the quiz with given id number
	 * 
	 * @param quizID
	 *            The id number of the quiz
	 * @return max score of the quiz
	 */
	public int getQuizMaxScore(int quizID) {
		int result = 0;
		String query = "SELECT  COUNT(1) FROM " + DbContract.quizzesTable.TABLE_NAME + " , "
				+ DbContract.questionsTable.TABLE_NAME + " , " + DbContract.subquestionsTable.TABLE_NAME + " , "
				+ DbContract.answerSubquestionMapTable.TABLE_NAME + " , " + " WHERE "
				+ DbContract.quizzesTable.TABLE_NAME + "." + DbContract.quizzesTable.COLUMN_NAME_QUIZ_ID + " = "
				+ DbContract.questionsTable.TABLE_NAME + "." + DbContract.questionsTable.COLUMN_NAME_QUIZ_ID + " AND "
				+ DbContract.questionsTable.TABLE_NAME + "." + DbContract.questionsTable.COLUMN_NAME_QUESTION_ID + " = "
				+ DbContract.subquestionsTable.COLUMN_NAME_QUESTION_ID + " AND "
				+ DbContract.subquestionsTable.TABLE_NAME + "."
				+ DbContract.subquestionsTable.COLUMN_NAME_SUBQUESTION_ID + " = "
				+ DbContract.answerSubquestionMapTable.TABLE_NAME + "."
				+ DbContract.answerSubquestionMapTable.COLUMN_NAME_SUBQUESTION_ID + " AND "
				+ DbContract.quizzesTable.TABLE_NAME + "." + DbContract.quizzesTable.COLUMN_NAME_QUIZ_ID + "=?";

		try (Connection connection = createConnection()) {
			PreparedStatement stm = connection.prepareStatement(query);
			stm.setInt(1, quizID);
			ResultSet rs = stm.executeQuery();
			rs.last();
			result = rs.getInt("count(1)");
			return result;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Returns the list of all achievements that be earned
	 * 
	 * @return The list of all achievements
	 */
	public List<Achievement> getAllAchievements() {
		List<Achievement> result = new ArrayList<Achievement>();
		String query = "SELECT " + DbContract.achievementsTable.COLUMN_NAME_ACHIEVEMENT_ID + ", "
				+ DbContract.achievementsTable.COLUMN_NAME_ACHIEVEMENT_NAME + " FROM "
				+ DbContract.achievementsTable.TABLE_NAME + ";";
		try (Connection con = DriverManager.getConnection("jdbc:mysql://" + server, account, password);
				Statement stmt = con.createStatement()) {
			stmt.executeQuery("USE " + database);
			try (PreparedStatement ps = con.prepareStatement(query)) {
				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						int achievementID = rs.getInt(DbContract.achievementsTable.COLUMN_NAME_ACHIEVEMENT_ID);
						String name = rs.getString(DbContract.achievementsTable.COLUMN_NAME_ACHIEVEMENT_NAME);
						List<Property> properties = getPropertiesByAchievementID(con, achievementID);
						Achievement newAchievement = new Achievement(achievementID, name, properties);
						result.add(newAchievement);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}


	/**
	 * Returns the list of all achievements that be earned
	 * 
	 * @return The list of all achievements
	 */
	public List<Achievement> getAllAchievementsForUser(int accountID) {
		List<Achievement> result = new ArrayList<Achievement>();
		String query = "SELECT "
				+ " a." + DbContract.achievementsTable.COLUMN_NAME_ACHIEVEMENT_ID + ", "
				+ DbContract.achievementsTable.COLUMN_NAME_ACHIEVEMENT_NAME + " FROM "
				+ DbContract.achievementsTable.TABLE_NAME + " a, " + DbContract.accountAchievementsTable.TABLE_NAME
				+ " aa WHERE " + "a." + DbContract.achievementsTable.COLUMN_NAME_ACHIEVEMENT_ID + " = " + "aa."
				+ DbContract.accountAchievementsTable.COLUMN_NAME_ACHIEVEMENT_ID + " AND aa."
				+ DbContract.accountAchievementsTable.COLUMN_NAME_ACCOUNT_ID + " = ?";
		System.out.println(query);
		try (Connection con = DriverManager.getConnection("jdbc:mysql://" + server, account, password);
				Statement stmt = con.createStatement()) {
			stmt.executeQuery("USE " + database);
			try (PreparedStatement ps = con.prepareStatement(query)) {
				ps.setInt(1, accountID);
				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						int achievementID = rs.getInt(DbContract.achievementsTable.COLUMN_NAME_ACHIEVEMENT_ID);
						String name = rs.getString(DbContract.achievementsTable.COLUMN_NAME_ACHIEVEMENT_NAME);
						List<Property> properties = getPropertiesByAchievementID(con, achievementID);
						Achievement newAchievement = new Achievement(achievementID, name, properties);
						result.add(newAchievement);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Gets best score of the quiz by quizID
	 * @param quizID for which best score should be found
	 * @return The best score in quiz
	 */
	public int getQuizBestScore(int quizID) {
		int bestScore = 0;
		try (Connection con = DriverManager.getConnection("jdbc:mysql://" + server, account, password);
				Statement stmt = con.createStatement()) {
			stmt.executeQuery("USE " + database);
			String query = "SELECT MAX(" + DbContract.quizAttemptsTable.COLUMN_NAME_SCORE + ") AS best_score FROM "
					+ DbContract.quizAttemptsTable.TABLE_NAME + " WHERE "
					+ DbContract.quizAttemptsTable.COLUMN_NAME_QUIZ_ID + " = ? GROUP BY "
					+ DbContract.quizAttemptsTable.COLUMN_NAME_QUIZ_ID + ";";
			try (PreparedStatement ps = con.prepareStatement(query)) {
				ps.setInt(1, quizID);
				try (ResultSet rs = ps.executeQuery()) {
					if (rs.next()) {
						bestScore = rs.getInt("best_score");
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bestScore;
	}
	
	/**
	 * Returns the number of quizzes taken by particular user
	 * @param accountID for user
	 * @return The number of quizzes taken
	 */
	public int getQuizTakeCountForUser(int accountID) {
		int takeCount = 0;
		String query = "SELECT COUNT(1) AS cnt FROM " + DbContract.quizAttemptsTable.TABLE_NAME + " WHERE "
				+ DbContract.quizAttemptsTable.COLUMN_NAME_ACCOUNT_ID + " = ?";
		try (Connection con = DriverManager.getConnection("jdbc:mysql://" + server, account, password);
				Statement stmt = con.createStatement()) {
			stmt.executeQuery("USE " + database);
			try (PreparedStatement ps = con.prepareStatement(query)) {
				ps.setInt(1, accountID);
				try (ResultSet rs = ps.executeQuery()) {
					if (rs.next()) {
						takeCount = rs.getInt("cnt");
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return takeCount;
	}
	
	/**
	 * Returns the number of quizzes taken by particular user
	 * @param accountID for user
	 * @return The number of quizzes taken
	 */
	public int getQuizCreateCountForUser(int accountID) {
		int createCount = 0;
		String query = "SELECT COUNT(1) AS cnt FROM " + DbContract.quizzesTable.TABLE_NAME + " WHERE "
				+ DbContract.quizzesTable.COLUMN_NAME_ACCOUNT_ID + " = ?";
		try (Connection con = DriverManager.getConnection("jdbc:mysql://" + server, account, password);
				Statement stmt = con.createStatement()) {
			stmt.executeQuery("USE " + database);
			try (PreparedStatement ps = con.prepareStatement(query)) {
				ps.setInt(1, accountID);
				try (ResultSet rs = ps.executeQuery()) {
					if (rs.next()) {
						createCount = rs.getInt("cnt");
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return createCount;
	}

	/**
	 * Gets all quizzes which has tag named:
	 * @param tag
	 * @return list of quizzes with current tag
	 */
	public ArrayList<Pair<String, Integer>> getQuizesByTag(String tag) {
		ArrayList<Pair<String, Integer>> list = new ArrayList<>();
		String query = "SELECT " + DbContract.quizzesTable.COLUMN_NAME_QUIZ_NAME + ", " + DbContract.quizzesTable.COLUMN_NAME_QUIZ_ID + 
						" FROM " + DbContract.quizzesTable.TABLE_NAME + " WHERE " + DbContract.quizzesTable.COLUMN_NAME_QUIZ_ID + " IN " +
						"(SELECT " + DbContract.quizTagsTable.COLUMN_NAME_QUIZ_ID  + " FROM " + DbContract.quizTagsTable.TABLE_NAME + 
						" WHERE " + DbContract.quizTagsTable.COLUMN_NAME_TAG_NAME + " LIKE " + "?)";
		try (Connection con = DriverManager.getConnection("jdbc:mysql://" + server, account, password);
				Statement stmt = con.createStatement()) {
			stmt.executeQuery("USE " + database);
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, tag);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) 
				list.add(new Pair<String, Integer>(rs.getString(1), rs.getInt(2)));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
}
