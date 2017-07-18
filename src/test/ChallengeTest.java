package test;
import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;

import db.bean.Challenge;
public class ChallengeTest {
	@Test
	public void Test() {
		Date dt = new Date();
		Challenge ch = new Challenge(1, 2, 3, dt , 100);
		
		assertEquals(true, ch.getDate().equals(dt));
		assertEquals(1, ch.getQuizID());
		assertEquals(2, ch.getSenderID());
		assertEquals(3, ch.getRecieverID());
		assertEquals(100,  ch.getMaxScore());
	}
}
