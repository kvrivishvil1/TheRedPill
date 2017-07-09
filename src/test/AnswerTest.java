package test;

import static org.junit.Assert.assertEquals;

import java.awt.List;
import java.util.ArrayList;

import org.junit.Test;

import db.bean.quiz.Answer;

public class AnswerTest {
	
	@Test
	public void testGetAnswers(){
		ArrayList<String> answerList = new ArrayList<String>();
		answerList.add("answer");
		Answer answer = new Answer(answerList);
		assertEquals(answer.getAnswers().get(0), "answer");
	}
	
	@Test
	public void testGetAnswersList(){
		ArrayList<String> answerList = new ArrayList<String>();
		answerList.add("answer");
		answerList.add("ans");
		answerList.add("very very answer");
		Answer answer = new Answer(answerList);
		assertEquals(answer.getAnswers(), answerList);
	}
	
	@Test
	//This tests checks if the method works correctly with two different lists
	//with the same elements in it
	public void testTestGetsAnswersDifferentLists(){
		ArrayList<String> answerList = new ArrayList<String>();
		answerList.add("answer");
		answerList.add("ans");
		answerList.add("very very answer");
		ArrayList<String> answerListCopy = new ArrayList<String>();
		answerListCopy.add("answer");
		answerListCopy.add("ans");
		answerListCopy.add("very very answer");
		Answer answer = new Answer(answerList);
		assertEquals(answer.getAnswers(), answerListCopy);
		
		answer = new Answer(answerListCopy);
		assertEquals(answer.getAnswers(), answerList);
	}
	
	@Test
	public void testIsCorrectWithHundredPercentAllCorrects(){
		ArrayList<String> answerList = new ArrayList<String>();
		answerList.add("This is test to check isCorrect method");
		answerList.add("This is test where I am testing isCorrect method");
		answerList.add("This test finds out whether isCorrect methods works correctly or not");
		answerList.add("This test finds out whether that isCorrect method works correctly");
		
		Answer answer = new Answer(answerList);
		assertEquals(answer.isCorrect("This is test to check isCorrect method", 100), true);
		assertEquals(answer.isCorrect("This is test where I am testing isCorrect method", 100), true);
		assertEquals(answer.isCorrect("This test finds out whether isCorrect methods works correctly or not", 100), true);
		assertEquals(answer.isCorrect("This test finds out whether that isCorrect method works correctly", 100), true);
	}
	
	@Test
	public void testIsCorrectWithHundredPercentAllIncorrects(){
		ArrayList<String> answerList = new ArrayList<String>();
		answerList.add("What happens when incorrect answers are passed?");
		answerList.add("This will not be the same as passed parameter");
		answerList.add("Testing is not as easy as it seems");
		answerList.add("Another answer and then we should start testing");
		
		Answer answer = new Answer(answerList);
		assertEquals(answer.isCorrect("Incorrect answer", 100), false);
		assertEquals(answer.isCorrect("This should return false", 100), false);
		assertEquals(answer.isCorrect("This should also return false", 100), false);
		assertEquals(answer.isCorrect("Correct answers are not passed", 100), false);
	}
	
	@Test
	public void testIsCorrectWithHundredPercentAddingAnswers(){
		ArrayList<String> answerList = new ArrayList<String>();
		
		Answer answer = new Answer(answerList);
		assertEquals(answer.isCorrect("Incorrect answer", 100), false);
		assertEquals(answer.isCorrect("This should return false", 100), false);
		
		answer.addAnswer("Lets add another answer");
		assertEquals(answer.isCorrect("Lets add another answer", 100), true);
		assertEquals(answer.isCorrect("Is this correct or not", 100), false);
		
		answer.addAnswer("More answers are added");
		assertEquals(answer.isCorrect("More answers are added", 100), true);
		assertEquals(answer.isCorrect("Lets add another answer", 100), true);
		assertEquals(answer.isCorrect("Is this correct or not", 100), false);
		
		answer.addAnswer("Is this correct or not");
		assertEquals(answer.isCorrect("More answers are added", 100), true);
		assertEquals(answer.isCorrect("Lets add another answer", 100), true);
		assertEquals(answer.isCorrect("Is this correct or not", 100), true);
	}
	
	
	@Test
	public void testIsCorrectWithLevenshtein(){
		ArrayList<String> answerList = new ArrayList<String>();
		answerList.add("This is test for levenshtein method");
		answerList.add("This is test where I am testing levenshtein method");
		answerList.add("This is test where I am going to test if levenshtein method works");
		
		Answer answer = new Answer(answerList);
		assertEquals(answer.isCorrect("This is test for levenshtein metho", 90), true);
		assertEquals(answer.isCorrect("This test where I am testing levenshtein method", 90), true);
		assertEquals(answer.isCorrect("This is test where I am going to test if levenshtein method works", 90), true);
		assertEquals(answer.isCorrect("This is test where I going to test if levenshtein method works", 90), true);
	}
	
	@Test
	public void testIsCorrectWithLevenshteinIncorrect(){
		ArrayList<String> answerList = new ArrayList<String>();
		answerList.add("Programming is good");
		
		Answer answer = new Answer(answerList);
		assertEquals(answer.isCorrect("Programming is bad", 95), false);
		assertEquals(answer.isCorrect("Programming is not good", 95), false);
		
		answer.addAnswer("Programming is great");
		assertEquals(answer.isCorrect("Programming is goood", 98), false);
		assertEquals(answer.isCorrect("Programming is greet", 98), false);
		assertEquals(answer.isCorrect("Programming are great", 90), false);
	}
	
	@Test
	public void testIsCorrectWithLevenshteinMixed(){
		ArrayList<String> answerList = new ArrayList<String>();
		answerList.add("This is mixed test");
		
		Answer answer = new Answer(answerList);
		assertEquals(answer.isCorrect("This is mixed test", 95), true);
		assertEquals(answer.isCorrect("This is mixed tst", 90), true);
		assertEquals(answer.isCorrect("This is test", 90), false);
		assertEquals(answer.isCorrect("This is test", 83), false);
		
		answerList.add("This test is correct");
		assertEquals(answer.isCorrect("This is mixed test", 95), true);
		assertEquals(answer.isCorrect("This is test is correct", 80), true);
		assertEquals(answer.isCorrect("This is test is incorrect", 80), false);
		assertEquals(answer.isCorrect("This is test is incorrect", 90), false);
	}
	
	@Test
	public void testIsCorrectWithLevenshteinOutOfBoundsPercents(){
		ArrayList<String> answerList = new ArrayList<String>();
		answerList.add("Negative percent");
		answerList.add("More than hundred percent");
		
		Answer answer = new Answer(answerList);
		assertEquals(answer.isCorrect("Negative percent", -50), true);
		assertEquals(answer.isCorrect("Negative percent", -1), true);
		assertEquals(answer.isCorrect("More than hundred percent", 1290), true);
		assertEquals(answer.isCorrect("More than hundred percent", 120), true);
	}
}
