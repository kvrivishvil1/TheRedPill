package test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import db.bean.quiz.Answer;
import db.bean.quiz.Quiz;
import db.bean.quiz.question.Question;

public class QuizTest {
	
	@Test
	public void test1(){
		Quiz quiz = new Quiz("Quiz", "Chemistry");
		assertEquals(quiz.getName(), "Quiz");
		assertEquals(quiz.getCategory(), "Chemistry");
		
		quiz = new Quiz("Quiz", "Mathematics");
		assertEquals(quiz.getName(), "Quiz");
		assertEquals(quiz.getCategory(), "Mathematics");
		
		
		quiz = new Quiz("Quiz","Computer Science");
		assertEquals(quiz.getName(), "Quiz");
		assertEquals(quiz.getCategory(), "Computer Science");
		
		quiz = new Quiz("Quiz", "History");
		assertEquals(quiz.getName(), "Quiz");
		assertEquals(quiz.getCategory(), "History");
		
		quiz = new Quiz("Quiz", "Biology");
		assertEquals(quiz.getName(), "Quiz");
		assertEquals(quiz.getCategory(), "Biology");
	}
	
	@Test
	public void test2(){
		Quiz quiz = new Quiz("Quiz", "Biology");
		
		List<Answer> answers = new ArrayList<Answer>();
		ArrayList<String> firstAnswer = new ArrayList<String>();
		firstAnswer.add("first");
		Answer first = new Answer(firstAnswer);
		ArrayList<String> secondAnswer = new ArrayList<String>();
		firstAnswer.add("second");
		Answer second = new Answer(secondAnswer);
		answers.add(first);
		answers.add(second);
		
		 Question question = new Question(1);
		
		quiz.addQuestion(question);
		
		assertEquals(quiz.getNumQuestions(), 1);
		assertEquals(quiz.getAllQuestions().get(0), question);
	}

}
