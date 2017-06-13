package db.bean;

public class User {
	private Person person;
	private Account account;

	public User(Person person, Account account) {
		this.person = person;
		this.account = account;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Person" + "\n" + person.toString());
		builder.append("\n"+ "Account" + "\n" + account.toString());
		return builder.toString();
	}
	
}
