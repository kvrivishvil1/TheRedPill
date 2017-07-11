package db;

public class DbContract {
	/**
	 * Static class to have name agreements for message table of the project
	 * 
	 * @author Elene K
	 *
	 */
	public static class messagesTable {
		public static final String TABLE_NAME = "messages";
		public static final String COLUMN_NAME_MESSAGE_ID = "message_id";
		public static final String COLUMN_NAME_SENDER_ID = "sender_id";
		public static final String COLUMN_NAME_RECIEVER_ID = "reciever_id";
		public static final String COLUMN_NAME_TEXT = "message_text";
		public static final String COLUMN_NAME_TIME = "time_sent";
	}

	/**
	 * @author tamar static class to have name agreements for Persons table
	 */
	public class personsTable {
		public static final String TABLE_NAME = "persons";
		public static final String COLUMN_NAME_PERSON_ID = "person_id";
		public static final String COLUMN_NAME_FIRSTNAME = "person_first_name";
		public static final String COLUMN_NAME_LASTNAME = "person_last_name";
		public static final String COLUMN_NAME_GENDER = "person_gender";
		public static final String COLUMN_NAME_EMAIL = "person_email";
		public static final String ENUM_MALE = "male";
		public static final String ENUM_FEMALE = "female";
		public static final String COLUMN_NAME_BIRTHDATE = "person_birthdate";
	}

	/**
	 * @author tamar static class to have name agreements for Accounts table
	 */
	public class accountsTable {
		public static final String TABLE_NAME = "accounts";
		public static final String COLUMN_NAME_ID = "account_id";
		public static final String COLUMN_NAME_USERNAME = "account_user_name";
		public static final String COLUMN_NAME_PASSWORD = "account_password";
		public static final String COLUMN_NAME_STATUS = "account_status";
		public static final String ENUM_USER = "user";
		public static final String ENUM_ADMIN = "admin";
	}

	/**
	 * @author tamar static class to have name agreements for PersonAccountMap
	 *         table
	 */
	public class personAccountMapTable {
		public static final String TABLE_NAME = "person_account_map";
		public static final String COLUMN_NAME_PERSON_ID = "person_id";
		public static final String COLUMN_NAME_ACCOUNT_ID = "account_id";
	}
	
	/**
	 * @author soso static class to have name agreements for friend request table
	 */
	public class friendRequestTable {
		public static final String TABLE_NAME = "friend_requests";
		public static final String COLUMN_NAME_FRIEND_REQUEST_ID = "friend_request_id";
		public static final String COLUMN_NAME_SENDER_ID = "sender_id";
		public static final String COLUMN_NAME_RECEIVER_ID = "receiver_id";
		public static final String COLUMN_NAME_TIME_SENT = "time_sent";
	}

	/**
	 * @author tamar static class to have name agreements for Quizes table
	 */
	public class quizzesTable {
		public static final String TABLE_NAME = "quizzes";
		public static final String COLUMN_NAME_QUIZ_ID = "quiz_id";
		public static final String COLUMN_NAME_QUIZ_NAME = "quiz_name";
		public static final String COLUMN_NAME_ISREARRANGABLE = "is_rearrangable";
		public static final String COLUMN_NAME_ISPRACTICABLE = "is_practicable";
		public static final String COLUMN_NAME_DESCRIPTION = "description";
		public static final String COLUMN_NAME_CATEGORY_ID = "category_id";
	}

	/**
	 * @author tamar static class to have name agreements for Questions table
	 */
	public class questionsTable {
		public static final String TABLE_NAME = "questions";
		public static final String COLUMN_NAME_QUIZ_ID = "quiz_id";
		public static final String COLUMN_NAME_QUESTION_ID = "question_id";
		public static final String COLUMN_NAME_QUESTION_TYPE = "question_type_id";
		public static final String COLUMN_NAME_QUESTION_NOTE = "question_note";
		public static final String COLUMN_NAME_ANSWER_ORDER_SENSITIVE = "answer_order_sensitivity";
	}

	/**
	 * 
	 * @author Mariami static class to have name agreements for answers table
	 */
	public class answersTable {
		public static final String TABLE_NAME = "answers";
		public static final String COLUMN_NAME_ANSWER_ID = "answer_id";
		public static final String COLUMN_NAME_ANSWER_TEXT = "answer_text";
		public static final String COLUMN_NAME_PARSER_SYMBOL = "parser_symbol";
	}

	/**
	 * 
	 * @author Mariami static class to have name agreements for subquestions
	 *         table
	 */
	public class subquestionsTable {
		public static final String TABLE_NAME = "subquestions";
		public static final String COLUMN_NAME_SUBQUESTION_ID = "subquestion_id";
		public static final String COLUMN_NAME_QUESTION_ID = "question_id";
		public static final String COLUMN_NAME_SUBQUESTION_TEXT = "subquestion_text";
	}

	/**
	 * 
	 * @author Mariami static class to have name agreements for answer and
	 *         subquestion map table
	 */
	public class answerSubquestionMapTable {
		public static final String TABLE_NAME = "answer_subquestion_map";
		public static final String COLUMN_NAME_ANSWER_ID = "answer_id";
		public static final String COLUMN_NAME_SUBQUESTION_ID = "subquestion_id";
	}

	/**
	 * 
	 * @author Mariami static class to have name agreements for categories table
	 */
	public class categoriesTable {
		public static final String TABLE_NAME = "categories";
		public static final String COLUMN_NAME_CATEGORY_ID = "category_id";
		public static final String COLUMN_NAME_CATEGORY_NAME = "category_name";
		public static final String COLUMN_NAME_CATEGORY_IMAGE_URL = "category_image_url";
	}

	/**
	 * 
	 * @author Mariami static class to have name agreements for challenges table
	 */
	public class challengesTable {
		public static final String TABLE_NAME = "challenges";
		public static final String COLUMN_NAME_CHALLENGE_ID = "challenge_id";
		public static final String COLUMN_NAME_QUIZ_CHALLENGED = "quiz_challenged";
		public static final String COLUMN_NAME_SENDER_ID = "sender_id";
		public static final String COLUMN_NAME_RECIEVER_ID = "reciever_id";
		public static final String COLUMN_NAME_SCORE_CHALLENGED = "score_challenged";
		public static final String COLUMN_NAME_TIME_SENT = "time_sent";
	}

	/**
	 * 
	 * @author Mariami static class to have name agreements for friends table
	 */
	public class friendsTable {
		public static final String TABLE_NAME = "friends";
		public static final String COLUMN_NAME_FIREND_ID = "friend_id";
		public static final String COLUMN_NAME_ACCOUNT_FIRST = "account_first";
		public static final String COLUMN_NAME_ACCOUNT_SECOND = "account_second";
	}

	/**
	 * 
	 * @author Mariami static class to have name agreements for question's
	 *         options table
	 */
	public class questionOptionsTable {
		public static final String TABLE_NAME = "question_options";
		public static final String COLUMN_NAME_OPTION_ID = "option_id";
		public static final String COLUMN_NAME_QUESTION_ID = "question_id";
		public static final String COLUMN_NAME_OPTION_TEXT = "option_text";
	}

	/**
	 * 
	 * @author Mariami static class to have name agreements for quiz attempts
	 *         table
	 */
	public class quizAttemptsTable {
		public static final String TABLE_NAME = "quiz_attempts";
		public static final String COLUMN_NAME_ATTEMPT_ID = "attempt_id";
		public static final String COLUMN_NAME_QUIZ_ID = "quiz_id";
		public static final String COLUMN_NAME_ACCOUNT_ID = "account_id";
		public static final String COLUMN_NAME_SCORE = "score";
		public static final String COLUMN_NAME_START_TIME = "start_time";
		public static final String COLUMN_NAME_FINISH_TIME = "finish_time";
	}

	/**
	 * 
	 * @author Mariami static class to have name agreements for quiz reports
	 *         table
	 */
	public class quizReportsTable {
		public static final String TABLE_NAME = "quiz_reports";
		public static final String COLUMN_NAME_REPORT_ID = "report_id";
		public static final String COLUMN_NAME_QUIZ_ID = "quiz_id";
		public static final String COLUMN_NAME_ACCOUNT_ID = "account_id";
		public static final String COLUMN_NAME_REPORT_TEXT = "report_text";
		public static final String COLUMN_NAME_REPORT_DATE = "report_date";

	}

	/**
	 * 
	 * @author Mariami static class to have name agreements for quiz reviews
	 *         table
	 */
	public class quizReviewsTable {
		public static final String TABLE_NAME = "quiz_reviews";
		public static final String COLUMN_NAME_REVIEW_ID = "review_id";
		public static final String COLUMN_NAME_QUIZ_ID = "quiz_id";
		public static final String COLUMN_NAME_ACCOUNT_ID = "account_id";
		public static final String COLUMN_NAME_STAR = "star";
		public static final String COLUMN_NAME_REVIEW_TEXT = "review_text";
		public static final String COLUMN_NAME_REVIEW_DATE = "review_date";
	}

	/**
	 * 
	 * @author Mariami static class to have name agreements for quiz tag table
	 */
	public class quizTagsTable {
		public static final String TABLE_NAME = "quiz_tags";
		public static final String COLUMN_NAME_QUIZ_ID = "quiz_id";
		public static final String COLUMN_NAME_TAG_NAME = "tag_name";
	}

	/**
	 * 
	 * @author Mariami static class to have name agreements for question types
	 *         table
	 */
	public class questionTypesTable {
		public static final String TABLE_NAME = "question_types";
		public static final String COLUMN_NAME_QUESTION_TYPE_ID = "question_type_id";
		public static final String COLUMN_NAME_QUESTION_TYPE_NAME = "question_type_name";
	}
	
	public class adminNotificationsTable{
		public static final String TABLE_NAME = "admin_notifications";
		public static final String COLUMN_NAME_NOTE_ID = "note_id";
		public static final String COLUMN_NAME_ADMIN_ID = "admin_id";
		public static final String COLUMN_NAME_NOTE_HEADER = "note_header";
		public static final String COLUMN_NAME_NOTE = "note";
	}
}
