package test;

import static org.junit.Assert.assertEquals;

import java.awt.List;
import java.util.ArrayList;

import org.junit.Test;

import db.bean.quiz.Answer;

public class AnswerTest {
	
	@Test
	public void test1(){
		ArrayList<String> answerList = new ArrayList<String>();
		answerList.add("answer");
		Answer answer = new Answer(answerList);
		assertEquals(answer.getAnswers().get(0), "answer");
	}
	
}
