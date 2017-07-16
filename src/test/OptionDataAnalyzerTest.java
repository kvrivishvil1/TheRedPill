package test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import analyzer.quiz.OptionDataAnalyzer;
import db.bean.quiz.Option;


public class OptionDataAnalyzerTest {
	@Test
	public void testOptionDataAnalyzerSimple() {
		String[] options = new String[1];
		options[0] = "It is an option";
		OptionDataAnalyzer analyzer = new OptionDataAnalyzer(options);
		List<Option> analyzerResult = analyzer.getOptions();
	
		assertEquals(analyzerResult.get(0).getOption(), "It is an option");
	}
	
	@Test
	public void testOptionDataAnalyzerMultiple() {
		String[] options = new String[3];
		options[0] = "First option";
		options[1] = "Second option";
		options[2] = "Third option";
		OptionDataAnalyzer analyzer = new OptionDataAnalyzer(options);
		List<Option> analyzerResult = analyzer.getOptions();
	
		assertEquals(analyzerResult.get(0).getOption(), "First option");
		assertEquals(analyzerResult.get(1).getOption(), "Second option");
		assertEquals(analyzerResult.get(2).getOption(), "Third option");
	}
	
	@Test
	public void testOptionDataAnalyzerNull() {
		String[] options = null;
		OptionDataAnalyzer analyzer = new OptionDataAnalyzer(options);
		List<Option> analyzerResult = analyzer.getOptions();
	
		assertEquals(analyzerResult.size(), 0);
	}

}
