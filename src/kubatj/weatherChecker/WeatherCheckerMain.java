package kubatj.weatherChecker;

import java.io.IOException;

import kubatj.weatherChecker.tools.ConsoleReader;

public class WeatherCheckerMain {
	
	public static void main(String[] args) throws IOException {
		System.out.print("Please enter 2-character country code (example: US, UK, CZ, ...): ");
	    String countryCode = ConsoleReader.readLine();
	    System.out.print("Please enter ZIP code: ");
	    String zipCode = ConsoleReader.readLine();
	    
	    WeatherCheckerClient client = new WeatherCheckerClient();
	    client.printActualWeather(countryCode, zipCode);
	    
	    System.out.println("Good bye");
	}
}
