package test;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import db.bean.Account;

public class AccountTest {
	@Test
	public void test() {
		Account account = new Account("soso", "1234");
		assertEquals(true, account.getUserName().equals("soso"));
		assertEquals(true, account.getPassword().equals("1234"));
		assertEquals("Username: soso" + "\n" + "Password: 1234", account.toString());
	}
}
