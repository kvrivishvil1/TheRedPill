package test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import db.bean.quiz.Answer;
import db.bean.quiz.Quiz;
import db.bean.quiz.Question;

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
	
	@Test 
	public void test3() {
		Quiz quiz = new Quiz("Quiz", "Math");
		ArrayList<String> tags = new ArrayList<>();
		
		quiz.setAccountID(1);
		quiz.setDescription("description");
		quiz.setPracticableMode(true);
		quiz.setRearrangableMode(true);
		quiz.setTimeLimit(10000);
		quiz.addTag("Mathmatics");
		tags.add("Mathmatics");
		quiz.addTag("CS");
		tags.add("CS");
		
		assertEquals(quiz.getName(), "Quiz");
		assertEquals(quiz.getCategory(), "Math");
		assertEquals(quiz.getAccountID(), 1);
		assertEquals(quiz.getDescription(), "description");
		assertEquals(quiz.isPracticable(), true);
		assertEquals(quiz.isRearrangable(), true);
		assertEquals(quiz.getTimeLimit(), 10000);
		assertEquals(quiz.getTags(), tags);
		
		quiz.removeTag("CS");
		tags.remove("CS");
		assertEquals(quiz.getTags(), tags);
		
		quiz.setName("Quizz");
		assertEquals(quiz.getName(), "Quizz");
		
		quiz.setCategory("Mathh");
		assertEquals(quiz.getCategory(), "Mathh");
	}

}
