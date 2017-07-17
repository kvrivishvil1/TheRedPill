package helpers;

public class TimeConverter {
	
	public static long toSeconds(String hours, String minutes, String seconds) {
		long total = 0;
		int hoursIntegerValue = Integer.parseInt(hours);
		int minutesIntegerValue = Integer.parseInt(minutes);
		int secondsIntegerValue = Integer.parseInt(seconds);
		total = hoursIntegerValue*3600 + minutesIntegerValue*60 + secondsIntegerValue;
		return total;
	}
}
