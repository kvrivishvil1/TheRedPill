package test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import db.bean.quiz.Option;
import db.bean.quiz.Question;
import db.bean.quiz.Subquestion;


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
	}
	
	
	@Test
	public void test2() {
		Question quest = new Question(1);
		ArrayList<Option> options = new ArrayList<>(); 
		Option opt = new Option("soso");
		options.add(opt);
		Option opt1 = new Option("elene");
		options.add(opt1);
		Option opt2 = new Option("mariami");
		options.add(opt2);
		Option opt3 = new Option("tamari");
		options.add(opt3);
		
		quest.addOption(opt);
		quest.addOption(opt1);
		quest.addOption(opt2);
		quest.addOption(opt3);
		
		assertEquals(true, options.equals(quest.getOptions()));
		quest.removeOption(opt1);
		options.remove(opt1);
		assertEquals(true, options.equals(quest.getOptions()));
		
		ArrayList<Subquestion> subquestions = new ArrayList<>();
		Subquestion subquestion = new Subquestion("question");
		Subquestion subquestion2 = new Subquestion("question2");
		quest.addSubquestion(subquestion);
		quest.addSubquestion(subquestion2);
		subquestions.add(subquestion);
		subquestions.add(subquestion2);
		assertEquals(true, subquestions.equals(quest.getSubquestions()));
		subquestions.remove(subquestion);
		assertEquals(false, subquestions.equals(quest.getSubquestions()));
		quest.removeSubquestion(subquestion);
		assertEquals(true, subquestions.equals(quest.getSubquestions())); 
	}
	
}
