package test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import db.bean.quiz.Answer;
import db.bean.quiz.Quiz;
import db.bean.quiz.question.MultipleChoiceQuestion;

public class QuizTest {
	
	@Test
	public void test1(){
		Quiz quiz = new Quiz("Quiz", true, true);
		assertEquals(quiz.getName(), "Quiz");
		assertEquals(quiz.isPracticable(), true);
		assertEquals(quiz.isRearrangable(), true);
		
		quiz = new Quiz("Quiz", true, false);
		assertEquals(quiz.getName(), "Quiz");
		assertEquals(quiz.isPracticable(), true);
		assertEquals(quiz.isRearrangable(), false);
		
		quiz = new Quiz("Quiz", false, true);
		assertEquals(quiz.getName(), "Quiz");
		assertEquals(quiz.isPracticable(), false);
		assertEquals(quiz.isRearrangable(), true);
		
		quiz = new Quiz("Quiz", false, false);
		assertEquals(quiz.getName(), "Quiz");
		assertEquals(quiz.isPracticable(), false);
		assertEquals(quiz.isRearrangable(), false);
	}
	
	@Test
	public void test2(){
		Quiz quiz = new Quiz("Quiz", true, true);
		
		List<Answer> answers = new ArrayList<Answer>();
		Answer first = new Answer("Fine", true);
		Answer second = new Answer("Not working", false);
		answers.add(first);
		answers.add(second);
		
		MultipleChoiceQuestion question = new MultipleChoiceQuestion("How does it work?", answers);
		
		quiz.addQuestion(question);
		
		assertEquals(quiz.getNumQuestions(), 1);
		assertEquals(quiz.getAllQuestions().get(0), question);
	}

}
