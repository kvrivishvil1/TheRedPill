package db.bean;

import java.util.Date;

public class Person {

	public static enum Gender {
		MALE, FEMALE
	}

	private String name;
	private String lastName;
	private Gender gender;
	private Date birthDate;

	public Person(String name, String lastName, Gender gender, Date birthDate) {
		this.name = name;
		this.lastName = lastName;
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

}
