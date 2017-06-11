package db;

public class DbContract {
	// Persons table names' contract
	public static final String PERSON_TABLE = "Persons";
	public static final String NAME_COLUMN = "person_firstName";
	public static final String LAST_NAME_COLUMN = "person_lastName";
	public static final String GENDER_COLUMN = "person_gender";
	public static final String BIRTHDATE_COLUMN = "person_birthdate";
	
	//Accounts table names' contract
	public static final String ACCOUNTS_TABLE = "Accounts";
	public static final String USER_NAME_COLUMN = "account_userName";
	public static final String PASSWORD_COLUMN = "account_password";

	//Person_account_map table names' contract
	public static final String PERSON_ACCOUNT_MAP_TABLE = "person_account_map";
	public static final String PERSONID_COLUMN = "person_id";
	public static final String ACCOUNT_ID_COLUMN = "account_id";
}
