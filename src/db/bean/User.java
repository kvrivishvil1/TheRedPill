package db.bean;

public class User {
	private Person person;
	private Account account;

	public User(Person person, Account accont) {
		this.person = person;
		this.account = accont;
	}

	/**
	 * @return the person
	 */
	public Person getPerson() {
		return person;
	}

	/**
	 * @return the account
	 */
	public Account getAccont() {
		return account;
	}

}
