package test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import analyzer.quiz.QuestionDataAnalyzer;
import db.bean.quiz.Answer;
import db.bean.quiz.Option;
import db.bean.quiz.Question;
import db.bean.quiz.Subquestion;

public class QuestionDataAnalyzerTest {
	@Test
	public void testQuestionResponseQuestionDataAnalyzer() {				
		Subquestion subquestion = new Subquestion("When was NATO founded?");
		List<String> answersWithAlternatives = new ArrayList<String>();
		answersWithAlternatives.add("1949");
		Answer answer = new Answer(answersWithAlternatives);
		subquestion.addAnswer(answer);
		
		List<Subquestion> subquestionsList = new ArrayList<Subquestion>();
		subquestionsList.add(subquestion);
		
		List<Option> options = new ArrayList<Option>();
		
		QuestionDataAnalyzer analyzer = new QuestionDataAnalyzer(1, "No note", subquestionsList, options, false, 50);
			
		Question analyzedQuestion = analyzer.getQuestion();
		assertEquals(analyzedQuestion.getQuestionType(), 1);
		
		List<Subquestion> subquestions = analyzedQuestion.getSubquestions();
		assertEquals(subquestions.get(0).getQuestion(), "When was NATO founded?");
		
		List<Answer> answers = subquestions.get(0).getAnswers();
		assertEquals(answers.get(0).isCorrect("1949", 100), true);
		
		assertEquals(analyzedQuestion.getNote(), "No note");
		assertEquals(analyzedQuestion.getTimeLimit(), 50);
		assertEquals(analyzedQuestion.isOrderSensitive(), false);
	}
	
	@Test
	public void testFillTheBlackQuestionDataAnalyzer() {		
		Subquestion subquestion = new Subquestion("Headquarter of NATO is located in ____");
		List<String> answersWithAlternatives = new ArrayList<String>();
		answersWithAlternatives.add("Brussels");
		answersWithAlternatives.add("Belgium");
		Answer answer = new Answer(answersWithAlternatives);
		answer.setParserSymbol('/');
		subquestion.addAnswer(answer);
		
		List<Subquestion> subquestionsList = new ArrayList<Subquestion>();
		subquestionsList.add(subquestion);
		
		List<Option> options = new ArrayList<Option>();
		
		QuestionDataAnalyzer analyzer = new QuestionDataAnalyzer(2, "Important note", subquestionsList, options, true, -1);
			
		Question analyzedQuestion = analyzer.getQuestion();
		assertEquals(analyzedQuestion.getQuestionType(), 2);
		
		List<Subquestion> subquestions = analyzedQuestion.getSubquestions();
		assertEquals(subquestions.get(0).getQuestion(), "Headquarter of NATO is located in ____");
		
		List<Answer> answers = subquestions.get(0).getAnswers();
		assertEquals(answers.get(0).isCorrect("Brussels", 100), true);
		assertEquals(answers.get(0).isCorrect("Belgium", 100), true);
		assertEquals(answers.get(0).getParserSymbol(), '/');
		
		assertEquals(analyzedQuestion.getNote(), "Important note");
		assertEquals(analyzedQuestion.getTimeLimit(), -1);
		assertEquals(analyzedQuestion.isOrderSensitive(), true);
	}
	
	@Test
	public void testPictureResponseQuestionDataAnalyzer() {		
		Subquestion subquestion = new Subquestion("www.countrypicturelink.ucoz.com");
		List<String> answersWithAlternatives = new ArrayList<String>();
		answersWithAlternatives.add("Georgia");
		Answer answer = new Answer(answersWithAlternatives);
		subquestion.addAnswer(answer);
		
		List<Subquestion> subquestionsList = new ArrayList<Subquestion>();
		subquestionsList.add(subquestion);
		
		List<Option> options = new ArrayList<Option>();
		
		QuestionDataAnalyzer analyzer = new QuestionDataAnalyzer(3, "Which country is shown on picture?", subquestionsList, options, true, 1);
			
		Question analyzedQuestion = analyzer.getQuestion();
		assertEquals(analyzedQuestion.getQuestionType(), 3);
		
		List<Subquestion> subquestions = analyzedQuestion.getSubquestions();
		assertEquals(subquestions.get(0).getQuestion(), "www.countrypicturelink.ucoz.com");
		
		List<Answer> answers = subquestions.get(0).getAnswers();
		assertEquals(answers.get(0).isCorrect("Georgia", 100), true);
		
		assertEquals(analyzedQuestion.getNote(), "Which country is shown on picture?");
		assertEquals(analyzedQuestion.getTimeLimit(), 1);
		assertEquals(analyzedQuestion.isOrderSensitive(), true);
	}
}
