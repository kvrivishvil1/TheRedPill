package db.bean;

import java.util.Date;

public class Person {

	public static enum Gender {
		MALE, FEMALE
	}

	private String name;
	private String lastName;
	private String email;
	private Gender gender;
	private Date birthDate;

	public Person(String name, String lastName, String email, Gender gender, Date birthDate) {
		this.name = name;
		this.lastName = lastName;
		this.email = email;
		this.gender = gender;
		this.birthDate = birthDate;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return the gender
	 */
	public Gender getGender() {
		return gender;
	}

	/**
	 * @return the birthDate
	 */
	public Date getBirthDate() {
		return birthDate;
	}

	@Override
	public String toString() {
		StringBuilder build = new StringBuilder();
		build.append("First Name: " + name + "\n");
		build.append("Last Name: " + lastName + "\n");
		build.append("email: " + email + "\n");
		build.append("Gender: " + gender + "\n");
		build.append("Born: " + birthDate);
		return build.toString();
	}
	
}
