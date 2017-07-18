package test;


import static org.junit.Assert.assertEquals;

import org.junit.Test;

import db.bean.quiz.Option;

public class OptionTest {
	@Test
	public void test() {
		Option option1 = new Option("opt");
		Option option2 = new Option("opt2");
		
		assertEquals("opt", option1.getOption());
		assertEquals("opt2", option2.getOption());
		
		assertEquals(false, option1.equals(option2));
		option2.setOption("opt");
		assertEquals(true, option1.equals(option2));
		assertEquals("opt", option2.getOption());

		option1.setOptionID(1);
		assertEquals(1, option1.getOptionID());
		option2.setOptionID(2);
		assertEquals(2, option2.getOptionID());
		assertEquals(false, option1.equals(option2));
		String s = "";
		assertEquals(false, option1.equals(s));
		assertEquals(true, option1.equals(option1));
	}
}
