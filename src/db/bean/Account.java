package db.bean;

public class Account {
	private String userName;
	private String password;

	public Account(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	
	@Override
	public String toString() {
		StringBuilder build = new StringBuilder();
		build.append("Username: " + userName + "\n");
		build.append("Password: " + password);
		return build.toString(); 
	}
	
}
