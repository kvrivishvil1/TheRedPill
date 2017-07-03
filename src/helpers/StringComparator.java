package helpers;

public class StringComparator {

	/**
	 * Finds minimum number of changes needed two string to become 
	 * the same
	 * @param s1 The first string
	 * @param s2 The second string
	 * @return number of changes needed
	 */
	public static int levenshteinDistance(String s1, String s2){	
		int[][] data = new int[s1.length()+1][s2.length()+1];
		
		initializeStartingParts(data, s1.length(), s2.length());
		
		for(int j = 1; j <= s2.length(); j++){
			for(int i = 1; i <= s1.length(); i++){
				int cost = 0;
				if(s1.charAt(i-1) != s2.charAt(j-1))
					cost = 1;
				data[i][j] = Math.min(data[i-1][j-1] + cost, Math.min(data[i-1][j]+1, data[i][j-1]+1));
			}
		}
		return data[s1.length()][s2.length()];
	}

	//This method is responsible for filling first row and columns with appropriated data
	private static void initializeStartingParts(int[][] data, int firstLength, int secondLength) {
		for(int i = 1; i <= firstLength; i++){
			data[i][0] = i;
		}
		
		for(int j = 1; j <= secondLength; j++){
			data[0][j] = j;
		}
	}
}
