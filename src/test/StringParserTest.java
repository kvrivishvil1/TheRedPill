package test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import helpers.StringParser;


public class StringParserTest {
	
	@Test
	public void testFollowedTags(){
		String tags = "#quiz#tag#test";
		ArrayList<String> tagList = StringParser.parseTagString(tags);
		assertEquals(tagList.get(0), "quiz");
		assertEquals(tagList.get(1), "tag");
		assertEquals(tagList.get(2), "test");
		
		tags = "#verytest#followed#tags";
		tagList = StringParser.parseTagString(tags);
		assertEquals(tagList.get(0), "verytest");
		assertEquals(tagList.get(1), "followed");
		assertEquals(tagList.get(2), "tags");
	}
	
	@Test
	public void testSpaceSeparatedTags(){
		String tags = "#amazing #tag #test";
		ArrayList<String> tagList = StringParser.parseTagString(tags);
		assertEquals(tagList.get(0), "amazing");
		assertEquals(tagList.get(1), "tag");
		assertEquals(tagList.get(2), "test");
		
		tags = "#best #quiz #ever";
		tagList = StringParser.parseTagString(tags);
		assertEquals(tagList.get(0), "best");
		assertEquals(tagList.get(1), "quiz");
		assertEquals(tagList.get(2), "ever");
		
		tags = "#the #most #shocking #quiz #i #have #ever #seen";
		tagList = StringParser.parseTagString(tags);
		assertEquals(tagList.get(0), "the");
		assertEquals(tagList.get(1), "most");
		assertEquals(tagList.get(2), "shocking");
		assertEquals(tagList.get(3), "quiz");
		assertEquals(tagList.get(4), "i");
		assertEquals(tagList.get(5), "have");
		assertEquals(tagList.get(6), "ever");
		assertEquals(tagList.get(7), "seen");
	}
	
	@Test
	public void testTagsWithDigits(){
		String tags = "#test1 #test2 #test3";
		ArrayList<String> tagList = StringParser.parseTagString(tags);
		assertEquals(tagList.get(0), "test1");
		assertEquals(tagList.get(1), "test2");
		assertEquals(tagList.get(2), "test3");
		
		tags = "#1234 #456789010293218 #12312311";
		tagList = StringParser.parseTagString(tags);
		assertEquals(tagList.get(0), "1234");
		assertEquals(tagList.get(1), "456789010293218");
		assertEquals(tagList.get(2), "12312311");
		
		tags = "#verytest123 #ilovethisquiznumber1 #wowquiz0";
		tagList = StringParser.parseTagString(tags);
		assertEquals(tagList.get(0), "verytest123");
		assertEquals(tagList.get(1), "ilovethisquiznumber1");
		assertEquals(tagList.get(2), "wowquiz0");
	}
	
	@Test
	public void testTagsWithUnderscore(){
		String tags = "#separating_words #it_works #i_am_sure #this_is_great_separator";
		ArrayList<String> tagList = StringParser.parseTagString(tags);
		assertEquals(tagList.get(0), "separating_words");
		assertEquals(tagList.get(1), "it_works");
		assertEquals(tagList.get(2), "i_am_sure");
		assertEquals(tagList.get(3), "this_is_great_separator");
		
		tags = "#how_separating_words_with_underscore_works #just_find #i_think";
		tagList = StringParser.parseTagString(tags);
		assertEquals(tagList.get(0), "how_separating_words_with_underscore_works");
		assertEquals(tagList.get(1), "just_find");
		assertEquals(tagList.get(2), "i_think");
		
		tags = "#underscore_separated_words_are_easier_to_read #isnt_it #yes_it_is";
		tagList = StringParser.parseTagString(tags);
		assertEquals(tagList.get(0), "underscore_separated_words_are_easier_to_read");
		assertEquals(tagList.get(1), "isnt_it");
		assertEquals(tagList.get(2), "yes_it_is");
	}
	
	@Test
	public void testTagsWithTrashBetweenTags(){
		String tags = "#separating_words this is not tag #lets_see_the_result";
		ArrayList<String> tagList = StringParser.parseTagString(tags);
		assertEquals(tagList.get(0), "separating_words");
		assertEquals(tagList.get(1), "lets_see_the_result");
		assertEquals(tagList.size(), 2);
		
		tags = "#this_is_tag #this_is_tag_too #and_also_this_one >>><<<EREASd";
		tagList = StringParser.parseTagString(tags);
		assertEquals(tagList.get(0), "this_is_tag");
		assertEquals(tagList.get(1), "this_is_tag_too");
		assertEquals(tagList.get(2), "and_also_this_one");
		assertEquals(tagList.size(), 3);
	}
	
	@Test
	public void testTagsWithTrashInsideTags(){
		String tags = "#this_is_test?? #this_is_real_test!!!";
		ArrayList<String> tagList = StringParser.parseTagString(tags);
		assertEquals(tagList.get(0), "this_is_test");
		assertEquals(tagList.get(1), "this_is_real_test");
		assertEquals(tagList.size(), 2);
	}


	@Test
	public void testParserWithSymbol(){
		String test = "This/Is/Test";
		ArrayList<String> tagList = StringParser.parseStringBy('/', test);
		assertEquals(tagList.get(0), "This");
		assertEquals(tagList.get(1), "Is");
		assertEquals(tagList.get(2), "Test");
		assertEquals(tagList.size(), 3);
	}
}
