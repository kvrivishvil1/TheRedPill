package db;

public class DbContract {
	/**
	 * Static class to have name agreements for message table of the project
	 * 
	 * @author Elene K
	 *
	 */
	public static class MessagesTable {
		public static final String TABLE_NAME = "Messages";
		public static final String COLUMN_NAME_MESSAGE_ID = "message_id";
		public static final String COLUMN_NAME_SENDER_ID = "sender_id";
		public static final String COLUMN_NAME_RECIEVER_ID = "reciever_id";
		public static final String COLUMN_NAME_TEXT = "message_text";
		public static final String COLUMN_NAME_TIME = "time_sent";
	}

	/**
	 * @author tamar
	 * static class to have name agreements for Persons table
	 */
	public class PersonsTable {
		public static final String TABLE_NAME = "Persons";
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
	 * @author tamar
	 *static class to have name agreements for Accounts table
	 */
	public class AccountsTable {
		public static final String TABLE_NAME = "Accounts";
		public static final String COLUMN_NAME_ID = "account_id";
		public static final String COLUMN_NAME_USERNAME = "account_user_name";
		public static final String COLUMN_NAME_PASSWORD = "account_password";
	}

	/**
	 * @author tamar
	 *static class to have name agreements for PersonAccountMap table
	 */
	public class PersonAccountMapTable {
		public static final String TABLE_NAME = "person_account_map";
		public static final String COLUMN_NAME_PERSON_ID = "person_id";
		public static final String COLUMN_NAME_ACCOUNT_ID = "account_id";
	}
	
	/**
	 * @author tamar
	 * static class to have name agreements for Quizes table
	 */
	public class Quizes {
		public static final String TABLE_NAME = "Quizes";
		public static final String COLUMN_NAME_QUIZ_ID = "quiz_id";
		public static final String COLUMN_NAME_QUIZ_NAME = "quiz_name";
		public static final String COLUMN_NAME_ISREARRANGABLE = "is_rearrangable";
		public static final String COLUMN_NAME_ISPRACTICABLE = "is_practicable";
	}

	/**
	 * @author tamar
	 *static class to have name agreements for Questions table
	 */
	public class Questions{
		public static final String TABLE_NAME = "Questions";
		public static final String COLUMN_NAME_QUIZ_ID = "quiz_id";
		public static final String COLUMN_NAME_QUESTION_ID = "question_id";
		public static final String COLUMN_NAME_QUESTION_TYPE = "question_type_id";
		public static final String COLUMN_NAME_QUESTION ="question";
	}
	
	public class Answers{
		public static final String TABLE_NAME = "Answers";
		public static final String COLUMN_NAME_ANSWER_ID = "answer_id";
		public static final String COLUMN_NAME_QUESTION_ID = "question_id";
		public static final String COLUMN_NAME_ANSWER_TYPE = "answer_type";
		public static final String COLUMN_NAME_ANSWER ="answer";
	}
}
