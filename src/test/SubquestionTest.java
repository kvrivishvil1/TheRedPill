package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import db.bean.quiz.Answer;
import db.bean.quiz.Subquestion;

public class SubquestionTest {
	
	@Test
	public void testGetAnswers(){
		Subquestion subquestion = new Subquestion("Is this answer correct");
		
		ArrayList<String> answerListFirst = new ArrayList<String>();
		answerListFirst.add("yes");
		answerListFirst.add("sure");
		answerListFirst.add("absolutely");
		Answer answerFirst = new Answer(answerListFirst);
		
		ArrayList<String> answerListSecond = new ArrayList<String>();
		answerListSecond.add("this is test");
		answerListSecond.add("answer is 1");
		answerListSecond.add("not sure");
		Answer answerSecond = new Answer(answerListSecond);
		
		subquestion.addAnswer(answerFirst);
		subquestion.addAnswer(answerSecond);
		
		ArrayList<String> userAnswers = new ArrayList<String>();
		userAnswers.add("yes");
		assertEquals(subquestion.countCorrectAnswers(userAnswers, 100), 1);
		
		userAnswers.add("sure");
		userAnswers.add("absolutely");
		assertEquals(subquestion.countCorrectAnswers(userAnswers, 100), 1);
		
		userAnswers.add("this is test");
		assertEquals(subquestion.countCorrectAnswers(userAnswers, 100), 2);
	}
	
	@Test
	public void testGettersAndSetters() {
		Subquestion subquestion = new Subquestion("Correct?");
		ArrayList<Answer> answers = new ArrayList<>();
		
		ArrayList<String> answerListFirst = new ArrayList<String>();
		answerListFirst.add("yes");
		answerListFirst.add("no");
		answerListFirst.add("maybe");
		Answer answerFirst = new Answer(answerListFirst);
		answers.add(answerFirst);
		subquestion.addAnswer(answerFirst);
		
		ArrayList<String> answerListSecond = new ArrayList<String>();
		answerListSecond.add("no");
		answerListSecond.add("no!");
		answerListSecond.add("maybe!");
		Answer answerSecond = new Answer(answerListSecond);
		answers.add(answerSecond);
		subquestion.addAnswer(answerSecond);
		
		assertEquals(true, answers.equals(subquestion.getAnswers()));
		assertEquals(true, "Correct?".equals(subquestion.getQuestion()));
		subquestion.setQuestion("not Correct?");
		assertEquals(true, "not Correct?".equals(subquestion.getQuestion()));
		subquestion.removeAnswer(answerFirst);
		answers.remove(answerFirst);
		assertEquals(true, answers.equals(subquestion.getAnswers()));
		subquestion.setSubquestionID(1);
		assertEquals(1, subquestion.getSubquestionID());
	}
	
	@Test
	public void testEquals() {
		Subquestion subquestion1 = new Subquestion("Correct?");
		Subquestion subquestion2 = new Subquestion("what?");
		
		ArrayList<String> answerListFirst = new ArrayList<String>();
		answerListFirst.add("yes");
		answerListFirst.add("no");
		answerListFirst.add("maybe");
		Answer answerFirst = new Answer(answerListFirst);
		subquestion1.addAnswer(answerFirst);
		subquestion2.addAnswer(answerFirst);
		
		ArrayList<String> answerListSecond = new ArrayList<String>();
		answerListSecond.add("no");
		answerListSecond.add("no!");
		answerListSecond.add("maybe!");
		Answer answerSecond = new Answer(answerListSecond);
		subquestion1.addAnswer(answerSecond);
		
		assertEquals(false, subquestion1.equals(subquestion2));
		assertEquals(true, subquestion1.equals(subquestion1));
		String s = "";
		assertEquals(false, subquestion1.equals(s));
		subquestion2.addAnswer(answerSecond);
		assertEquals(false, subquestion1.equals(subquestion2));
		subquestion2.setQuestion("Correct?");
		assertEquals(true, subquestion1.equals(subquestion2));
		subquestion1.setSubquestionID(1);
		assertEquals(false, subquestion1.equals(subquestion2));
	}
}
