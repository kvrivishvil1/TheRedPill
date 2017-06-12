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

	// Persons table names' contract
	public static final String PERSON_TABLE = "Persons";
	public static final String PERSON_ID_COLUMN = "person_id";
	public static final String NAME_COLUMN = "person_firstName";
	public static final String LAST_NAME_COLUMN = "person_lastName";
	public static final String GENDER_COLUMN = "person_gender";
	public static final String MALE = "male";
	public static final String FEMALE = "female";
	public static final String BIRTHDATE_COLUMN = "person_birthdate";

	// Accounts table names' contract
	public static final String ACCOUNTS_TABLE = "Accounts";
	public static final String ACCOUNT_ID_COLUMN = "account_id";
	public static final String USER_NAME_COLUMN = "account_userName";
	public static final String PASSWORD_COLUMN = "account_password";

	// Person_account_map table names' contract
	public static final String PERSON_ACCOUNT_MAP_TABLE = "person_account_map";
	public static final String PERSON_COLUMN = "person_id";
	public static final String ACCOUNT_COLUMN = "account_id";
}
