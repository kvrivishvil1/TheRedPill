package test;

import org.junit.Test;

import db.bean.quiz.QuizAttempt;

import static org.junit.Assert.assertEquals;

import java.util.Date;

public class quizAttemptTest {

	
	@Test
	public void test1() {
		QuizAttempt atmpt = new QuizAttempt(1, 1);
		assertEquals(atmpt.getAccountID(), 1);
		assertEquals(atmpt.getQuizID(), 1);
		atmpt.setQuizAttemptID(1);
		assertEquals(atmpt.getQuizAttemptID(), 1);
		atmpt.setScore(10);
		assertEquals(10, atmpt.getScore());
		Date date = new Date();
		atmpt.setStartTime(date);
		assertEquals(true, date.equals(atmpt.getStartTime()));
		Date date1 = new Date();
		atmpt.setFinishTime(date1);
		assertEquals(true, date1.equals(atmpt.getFinishTime()));
	}
	
	@Test
	public void equalsTest() {
		QuizAttempt atmpt = new QuizAttempt(1, 1);
		QuizAttempt atmpt1 = new QuizAttempt(2, 1);
		QuizAttempt atmpt2 = new QuizAttempt(1, 2);
		assertEquals(true, atmpt.equals(atmpt));
		assertEquals(false, atmpt.equals(atmpt1));
		assertEquals(false, atmpt.equals(atmpt2));
		String s = "";
		assertEquals(false, atmpt.equals(s));
		QuizAttempt atmpt3 = new QuizAttempt(1, 1);
		assertEquals(true, atmpt.equals(atmpt3));
		atmpt.setQuizAttemptID(1);
		assertEquals(false, atmpt.equals(atmpt3));
		atmpt3.setQuizAttemptID(1);
		assertEquals(true, atmpt.equals(atmpt3));
		atmpt.setScore(10);
		assertEquals(false, atmpt.equals(atmpt3));
		atmpt3.setScore(10);
		assertEquals(true, atmpt.equals(atmpt3));
		Date date = new Date();
		atmpt.setStartTime(date);
		assertEquals(false, atmpt.equals(atmpt3));
		atmpt3.setStartTime(date);
		assertEquals(true, atmpt.equals(atmpt3));
		Date date1 = new Date();
		atmpt.setFinishTime(date1);
		assertEquals(false, atmpt.equals(atmpt3));
		atmpt3.setFinishTime(date1);
		assertEquals(true, atmpt.equals(atmpt3));
	}
} 
