package kubatj.weatherChecker.tools;

import java.util.Scanner;

public class ConsoleReader {
	
	static Scanner in = null;

	public static String readLine() {
		if (in == null) {
			in = new Scanner(System.in);
		}
		return in.nextLine();
	}
}
