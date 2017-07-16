package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import analyzer.quiz.AnswerDataAnalyzer;
import db.bean.quiz.Answer;

public class AnswerDataAnalyzerTest {
	
	@Test
	public void testAnswerAnalyzerSimple() {
		AnswerDataAnalyzer analyzer = new AnswerDataAnalyzer("this is test", '\n');
		Answer analyzerResult = analyzer.getAnswer();
		
		assertEquals(analyzerResult.getAnswers().get(0), "this is test");
		assertEquals(analyzerResult.getParserSymbol(), '\n');
	}
	
	@Test
	public void testAnswerAnalyzerWithParser() {
		AnswerDataAnalyzer analyzer = new AnswerDataAnalyzer("Donald-Trump", '-');
		Answer analyzerResult = analyzer.getAnswer();
		
		assertEquals(analyzerResult.getAnswers().get(0), "Donald");
		assertEquals(analyzerResult.getAnswers().get(1), "Trump");
		assertEquals(analyzerResult.getParserSymbol(), '-');
	}
	
	@Test
	public void testAnswerAnalyzerWithParserAndSpaces() {
		AnswerDataAnalyzer analyzer = new AnswerDataAnalyzer("Cristiano Ronaldo/Lionel Messi/Wayne Rooney", '/');
		Answer analyzerResult = analyzer.getAnswer();
		
		assertEquals(analyzerResult.getAnswers().get(0), "Cristiano Ronaldo");
		assertEquals(analyzerResult.getAnswers().get(1), "Lionel Messi");
		assertEquals(analyzerResult.getAnswers().get(2), "Wayne Rooney");
		assertEquals(analyzerResult.getParserSymbol(), '/');
	}

}
