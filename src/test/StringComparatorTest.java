package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import helpers.StringComparator;

public class StringComparatorTest {
	
	@Test
	//Test for strings with the same length
	public void testWhenSameLength(){
		String first = "Test";
		String second = "Best";
		
		assertEquals(1, StringComparator.levenshteinDistance(first, second));
		
		first = "TestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTest";
		second = "BestBestBestBestBestBestBestBestBestBestBestBestBestBestBestBestBest";
	
		assertEquals(17, StringComparator.levenshteinDistance(first, second));
		
		first = "";
		second = "";
		for(int i = 0; i < 100; i++){
			first += (char)('a' + i%26);
			first += 'a';
			second += (char)('a' + i%26);
			second += 'b';
		}
		
		assertEquals(100, StringComparator.levenshteinDistance(first, second));
	}
	
	@Test
	//Test for strings when one of them is empty
	public void testWhenEmpty(){
		//Case when first one is empty
		String first = "";
		String second = "Test";
		assertEquals(second.length(), StringComparator.levenshteinDistance(first, second));
		
		//Case when second one is empty
		first = "Test";
		second = "";
		assertEquals(first.length(), StringComparator.levenshteinDistance(first, second));
		
		//Case when both are emtpy
		first = "";
		second = "";
		assertEquals(first.length(), StringComparator.levenshteinDistance(first, second));
	}
	
	@Test
	//Test for strings when they have different lengths
	public void testWhenDifferentLength(){
		String first = "In theory, theory and practice are the same. In practice, they’re not.";
		String second = "In theory theory and practise are the same. In practise they are not.";
		assertEquals(6, StringComparator.levenshteinDistance(first, second));
				
		first = "Always code as if the guy who ends up maintaining your code will be a violent psychopath who knows where you live.";
		second = "Code as if the guy who ends up maintaining your code, will be a violent psychopath who knows where you live.";
		assertEquals(9, StringComparator.levenshteinDistance(first, second));
		
		first = "I don't care if it works on your machine! We are not shipping your machine!";
		second = "I do not care if it works on yourmachine. we're not shipping your machine.";
		assertEquals(8, StringComparator.levenshteinDistance(first, second));
	}
	
	@Test
	//Test for strings when they are exchanged
	public void testWhenExchanged(){
		String first = "IKrxvJjnbIxBnZNrqMBILZxiIbOtcgbHkQZjfTDCJgoqisqelmzrfGDttCElnWyQBrmTplIgaNNzwztVOSOzASKyKgaHUogapxHN";
		String second = "IKrxvJjnbIxBnZNrqmBILZxiIOtcgbHkQZjFTDCJgoqrsqelmzrfGDtElnWyQBrmTplIganNzwWztVZZOSOzASKyKgaHUogapxHN";
		assertEquals(10, StringComparator.levenshteinDistance(first, second));
		assertEquals(10, StringComparator.levenshteinDistance(second, first));
		
		first = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
		second = "AaAaAaAaAaAaAaAaAaAaAaAaAaAaAaAaAaAaAaAaAaAaAaAaAaAaAaAaAaAa";
		assertEquals(30, StringComparator.levenshteinDistance(first, second));
		assertEquals(30, StringComparator.levenshteinDistance(second, first));
		
		first = "";
		second = "";
		for(int i = 0; i < 10; i++){
			for(int j = 0; j < 26; j++){
				first += (char)('a' + j);
				second += (char)('A' + j);
			}
		}
		assertEquals(260, StringComparator.levenshteinDistance(first, second));
		assertEquals(260, StringComparator.levenshteinDistance(second, first));
	}
}
