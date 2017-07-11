package helpers;

import java.util.ArrayList;

public class StringParser {

	/**
	 * Gets string in which quiz tags are written and parses it with the
	 * following rules: tag should be started with '#' symbol and it should only
	 * contain alphabetic characters, digits and '_' symbol
	 * 
	 * @param tags
	 *            String which should be parsed
	 * @return The list of tags
	 */
	public static ArrayList<String> parseTagString(String tags) {
		ArrayList<String> result = new ArrayList<String>();
		String currentTag = "";
		boolean tagStarted = false;
		for (int i = 0; i < tags.length(); i++) {
			char currentChar = tags.charAt(i);
			if (tagStarted && (Character.isAlphabetic(currentChar) || Character.isDigit(currentChar)
					|| currentChar == '_')) {
				currentTag += currentChar;
			} else {
				if (currentTag != "") {
					result.add(currentTag);
					currentTag = "";
				}
				tagStarted = (currentChar == '#');
			}
		}
		if (currentTag != "") {
			result.add(currentTag);
		}
		return result;
	}
	
	/**
	 * Parses string with one symbol and returns it's list
	 * @param parserSymbol which should be used for parsing
	 * @param stringToParse which should be parsed
	 * @return The list of the parsed string
	 */
	public static ArrayList<String> parseStringBy(char parserSymbol, String stringToParse) {
		ArrayList<String> result = new ArrayList<String>();
		String[] parsedList = stringToParse.split(Character.toString(parserSymbol));
		for(int i = 0; i < parsedList.length; i++){
			result.add(parsedList[i]);
		}
		return result;
	}
}
