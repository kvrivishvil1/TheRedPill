package test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import analyzer.quiz.subquestion.FillTheBlankSubquestionDataAnalyzer;
import analyzer.quiz.subquestion.MatchingSubquestionDataAnalyzer;
import analyzer.quiz.subquestion.MultiAnswerSubquestionDataAnalyzer;
import analyzer.quiz.subquestion.MultipleChoiceMultipleAnswerSubquestionDataAnalyzer;
import analyzer.quiz.subquestion.MultipleChoiceSubquestionDataAnalyzer;
import analyzer.quiz.subquestion.PictureResponseSubquestionDataAnalyzer;
import analyzer.quiz.subquestion.QuestionResponseSubquestionDataAnalyzer;
import analyzer.quiz.subquestion.SubquestionDataAnalyzer;
import db.bean.quiz.Subquestion;

public class SubquestionDataAnalyzerTest {
	
	@Test
	public void testQuestionResponseSubquestionDataAnalyzer() {
		String[] question = new String[1];
		question[0] = "How old is Bill Gates?";
		String[] answer = new String[1];
		answer[0] = "61";
		SubquestionDataAnalyzer analyzer = new QuestionResponseSubquestionDataAnalyzer(question, answer, null, null, null, null);
		List<Subquestion> subquestions = analyzer.getSubquestions();
		
		assertEquals(subquestions.get(0).getQuestion(), "How old is Bill Gates?");
		assertEquals(subquestions.get(0).getAnswers().get(0).isCorrect("61", 100), true);
	}
	
	@Test
	public void testQuestionResponseSubquestionDataAnalyzerMultipleAnswers() {
		String[] question = new String[1];
		question[0] = "Is programming good?";
		String[] answer = new String[1];
		answer[0] = "Yes/Sure/Yes, it is";
		SubquestionDataAnalyzer analyzer = new QuestionResponseSubquestionDataAnalyzer(question, answer, null, null, null, "/");
		List<Subquestion> subquestions = analyzer.getSubquestions();
		
		assertEquals(subquestions.get(0).getQuestion(), "Is programming good?");
		assertEquals(subquestions.get(0).getAnswers().get(0).isCorrect("Yes", 100), true);
		assertEquals(subquestions.get(0).getAnswers().get(0).isCorrect("Sure", 100), true);
		assertEquals(subquestions.get(0).getAnswers().get(0).isCorrect("Yes, it is", 100), true);
	}
	
	@Test
	public void testFillTheBlankSubquestionDataAnalyzer() {
		String[] question = new String[1];
		question[0] = "My name is _____";
		String[] answer = new String[1];
		answer[0] = "Elene";
		SubquestionDataAnalyzer analyzer = new FillTheBlankSubquestionDataAnalyzer(question, answer, null, null, null, null);
		List<Subquestion> subquestions = analyzer.getSubquestions();
		
		assertEquals(subquestions.get(0).getQuestion(), "My name is _____");
		assertEquals(subquestions.get(0).getAnswers().get(0).isCorrect("Elene", 100), true);
	}
	
	@Test
	public void testFillTheBlankSubquestionDataAnalyzerMultipleAnswers() {
		String[] question = new String[1];
		question[0] = "Life is all about ____";
		String[] answer = new String[1];
		answer[0] = "Happiness-Prime numbers-Computer Science";
		SubquestionDataAnalyzer analyzer = new FillTheBlankSubquestionDataAnalyzer(question, answer, null, null, null, "-");
		List<Subquestion> subquestions = analyzer.getSubquestions();
		
		assertEquals(subquestions.get(0).getQuestion(), "Life is all about ____");
		assertEquals(subquestions.get(0).getAnswers().get(0).isCorrect("Happiness", 100), true);
		assertEquals(subquestions.get(0).getAnswers().get(0).isCorrect("Prime numbers", 100), true);
		assertEquals(subquestions.get(0).getAnswers().get(0).isCorrect("Computer Science", 100), true);
	}
	
	@Test
	public void testPictureResponseSubquestionDataAnalyzer() {
		String[] question = new String[1];
		question[0] = "www.picturelink.com";
		String[] answer = new String[1];
		answer[0] = "Monkey";
		SubquestionDataAnalyzer analyzer = new PictureResponseSubquestionDataAnalyzer(question, answer, null, null, null, null);
		List<Subquestion> subquestions = analyzer.getSubquestions();
		
		assertEquals(subquestions.get(0).getQuestion(), "www.picturelink.com");
		assertEquals(subquestions.get(0).getAnswers().get(0).isCorrect("Monkey", 100), true);
	}
	
	@Test
	public void testPictureResponseSubquestionDataAnalyzerMultipleAnswers() {
		String[] question = new String[1];
		question[0] = "www.amazingimages.ru";
		String[] answer = new String[1];
		answer[0] = "Joke,Funny joke,Very funny joke,Amazing joke";
		SubquestionDataAnalyzer analyzer = new PictureResponseSubquestionDataAnalyzer(question, answer, null, null, null, ",");
		List<Subquestion> subquestions = analyzer.getSubquestions();
		
		assertEquals(subquestions.get(0).getQuestion(), "www.amazingimages.ru");
		assertEquals(subquestions.get(0).getAnswers().get(0).isCorrect("Joke", 100), true);
		assertEquals(subquestions.get(0).getAnswers().get(0).isCorrect("Funny joke", 100), true);
		assertEquals(subquestions.get(0).getAnswers().get(0).isCorrect("Very funny joke", 100), true);
		assertEquals(subquestions.get(0).getAnswers().get(0).isCorrect("Amazing joke", 100), true);
	}
	
	@Test
	public void testMultiAnswerSubquestionDataAnalyzer() {
		String[] question = new String[1];
		question[0] = "List 3 states of America";
		String[] answer = new String[3];
		answer[0] = "California";
		answer[1] = "Texas";
		answer[2] = "Florida";
		SubquestionDataAnalyzer analyzer = new MultiAnswerSubquestionDataAnalyzer(question, answer, null, null, null, null);
		List<Subquestion> subquestions = analyzer.getSubquestions();
		
		assertEquals(subquestions.get(0).getQuestion(), "List 3 states of America");
		assertEquals(subquestions.get(0).getAnswers().get(0).isCorrect("California", 100), true);
		assertEquals(subquestions.get(0).getAnswers().get(1).isCorrect("Texas", 100), true);
		assertEquals(subquestions.get(0).getAnswers().get(2).isCorrect("Florida", 100), true);
	}
	
	@Test
	public void testMultiAnswerSubquestionDataAnalyzerWithParser() {
		String[] question = new String[1];
		question[0] = "What is the most importan thing in life";
		String[] answer = new String[2];
		answer[0] = "Health/Happiness";
		answer[1] = "Love/Laugh";
		SubquestionDataAnalyzer analyzer = new MultiAnswerSubquestionDataAnalyzer(question, answer, null, null, null, "/");
		List<Subquestion> subquestions = analyzer.getSubquestions();
		
		assertEquals(subquestions.get(0).getQuestion(), "What is the most importan thing in life");
		assertEquals(subquestions.get(0).getAnswers().get(0).isCorrect("Health", 100), true);
		assertEquals(subquestions.get(0).getAnswers().get(0).isCorrect("Happiness", 100), true);
		assertEquals(subquestions.get(0).getAnswers().get(1).isCorrect("Love", 100), true);
		assertEquals(subquestions.get(0).getAnswers().get(1).isCorrect("Laugh", 100), true);
	}

	@Test
	public void testMultipleChoiceSubquestionDataAnalyzer() {
		String[] question = new String[1];
		question[0] = "Who is Tom Cruise?";
		
		String[] options = new String[4];
		options[0] = "Actor";
		options[1] = "Singer";
		options[2] = "Writer";
		options[3] = "Director";
		
		String[] optionIDs = new String[4];
		optionIDs[0] = "1";
		optionIDs[1] = "3";
		optionIDs[2] = "4";
		optionIDs[3] = "6";
		
		String[] answerOptions = new String[1];
		answerOptions[0] = "1";
		
		SubquestionDataAnalyzer analyzer = new MultipleChoiceSubquestionDataAnalyzer(question, null, options, optionIDs, answerOptions, null);
		List<Subquestion> subquestions = analyzer.getSubquestions();
		
		assertEquals(subquestions.get(0).getQuestion(), "Who is Tom Cruise?");
		assertEquals(subquestions.get(0).getAnswers().get(0).isCorrect("Actor", 100), true);
	}
	
	@Test
	public void testMultipleChoiceMultipleAnswerSubquestionDataAnalyzer() {
		String[] question = new String[1];
		question[0] = "Who is Usain Bolt?";
		
		String[] options = new String[4];
		options[0] = "Sprinter";
		options[1] = "President";
		options[2] = "Writer";
		options[3] = "Fastest man in the world";
		
		String[] optionIDs = new String[4];
		optionIDs[0] = "1";
		optionIDs[1] = "2";
		optionIDs[2] = "4";
		optionIDs[3] = "5";
		
		String[] answerOptions = new String[2];
		answerOptions[0] = "1";
		answerOptions[1] = "5";
		
		SubquestionDataAnalyzer analyzer = new MultipleChoiceMultipleAnswerSubquestionDataAnalyzer(question, null, options, optionIDs, answerOptions, null);
		List<Subquestion> subquestions = analyzer.getSubquestions();
		
		assertEquals(subquestions.get(0).getQuestion(), "Who is Usain Bolt?");
		assertEquals(subquestions.get(0).getAnswers().get(0).isCorrect("Sprinter", 100), true);
		assertEquals(subquestions.get(0).getAnswers().get(1).isCorrect("Fastest man in the world", 100), true);
	}
	
	@Test
	public void testMatchingSubquestionDataAnalyzer() {
		String[] question = new String[4];
		question[0] = "Sigmund";
		question[1] = "Albert";
		question[2] = "Friedrich";
		question[3] = "Georg";
		
		String[] options = new String[4];
		options[0] = "Nietzsche";
		options[1] = "Freud";
		options[2] = "Hegel";
		options[3] = "Camus";
		
		String[] answerOptions = new String[4];
		answerOptions[0] = "2";
		answerOptions[1] = "4";
		answerOptions[2] = "1";
		answerOptions[3] = "3";
		
		SubquestionDataAnalyzer analyzer = new MatchingSubquestionDataAnalyzer(question, null, options, null, answerOptions, null);
		List<Subquestion> subquestions = analyzer.getSubquestions();
		
		assertEquals(subquestions.get(0).getQuestion(), "Sigmund");
		assertEquals(subquestions.get(1).getQuestion(), "Albert");
		assertEquals(subquestions.get(2).getQuestion(), "Friedrich");
		assertEquals(subquestions.get(3).getQuestion(), "Georg");
		
		assertEquals(subquestions.get(0).getAnswers().get(0).isCorrect("Freud", 100), true);
		assertEquals(subquestions.get(1).getAnswers().get(0).isCorrect("Camus", 100), true);
		assertEquals(subquestions.get(2).getAnswers().get(0).isCorrect("Nietzsche", 100), true);
		assertEquals(subquestions.get(3).getAnswers().get(0).isCorrect("Hegel", 100), true);
	}

}
