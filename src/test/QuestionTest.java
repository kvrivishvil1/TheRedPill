package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import db.bean.quiz.Question;


public class QuestionTest {

	@Test
	public void test1() {
		Question quest = new Question(1);
		quest.setNote("note");
		quest.setQuestionID(1);
		assertEquals(quest.getQuestionType(), 1);
		assertEquals(quest.getQuestionID(), 1);
		assertEquals(quest.getNote(), "note");
		assertEquals(quest.isOrderSensitive(), false);
		assertEquals(quest.getTimeLimit(), -1);
		quest.setOrderSensitive(true);
		quest.setTimeLimit(10000);
		quest.setQuestionType(2);
		assertEquals(quest.getQuestionType(), 2);
		assertEquals(quest.isOrderSensitive(), true);
		assertEquals(quest.getTimeLimit(), 10000);
		
		//Subquestion subquest = new Subquestion(question)
		
	}
	
	
	@Test
	public void test2() {
		
	}
	
}
