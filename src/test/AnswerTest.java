package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import db.bean.quiz.Answer;

public class AnswerTest {
	
	@Test
	public void test1(){
		Answer answer = new Answer("Answer", true);
		assertEquals(answer.getAnswer(), "Answer");
		assertEquals(answer.isCorrect(), true);
	}
	
	@Test
	public void test2(){
		Answer answer = new Answer("Answer", false);
		assertEquals(answer.getAnswer(), "Answer");
		assertEquals(answer.isCorrect(), false);
	}
}
