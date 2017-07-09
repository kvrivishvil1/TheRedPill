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

}
