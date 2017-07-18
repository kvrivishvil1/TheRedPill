package test;
import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;

import db.bean.Person;
import db.bean.Person.Gender;

public class personTest {
	@Test
	public void test() {
		Date date = new Date();
		Person person = new Person("soso", "kvrivishvili", "soso@gmail.com", Gender.MALE, date);
		assertEquals(true, person.getName().equals("soso"));
		assertEquals(true, person.getLastName().equals("kvrivishvili"));
		assertEquals(true, person.getEmail().equals("soso@gmail.com"));
		assertEquals(true, person.getGender().equals(Gender.MALE));
		assertEquals(true, person.getBirthDate().equals(date));
	}
}
