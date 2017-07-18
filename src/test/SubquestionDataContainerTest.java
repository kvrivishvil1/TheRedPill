package test;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

import com.mysql.fabric.xmlrpc.base.Array;

import analyzer.quiz.SubquestionDataContainer;

public class SubquestionDataContainerTest {
	
	@Test
	public void test() {
		String [] questions = {"Second Question", "First Question", "Third Question"};
		String [] answers = {"Second answer", "First answer", "Third answer"};
		String [] options = {"Second option", "First option", "Third option"};
		String [] optionIDs = {"Second optionID", "First optionID", "Third optionID"};
		String [] answerOptions = {"Second answerOption", "First answerOption", "Third answerOption"};
		String parser = "#";
		SubquestionDataContainer container = new SubquestionDataContainer(questions, answers, options, optionIDs, answerOptions, parser);
		assertEquals(true, Arrays.equals(questions, container.getQuestions()));
		assertEquals(true, Arrays.equals(answers, container.getAnswers()));
		assertEquals(true, Arrays.equals(options, container.getOptions()));
		assertEquals(true, Arrays.equals(optionIDs, container.getOptionIDs()));
		assertEquals(true, Arrays.equals(answerOptions, container.getAnswerOptions()));
		assertEquals(true, parser.equals(container.getParser()));
	}
}
