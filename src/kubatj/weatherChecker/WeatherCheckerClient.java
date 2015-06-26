package kubatj.weatherChecker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.ws.Holder;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;

import kubatj.weatherChecker.data.Location;
import kubatj.weatherChecker.tools.ConsoleReader;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class WeatherCheckerClient {
	
	private WeatherChecker port = null;
	
	public WeatherCheckerClient() {
        QName serviceName = new QName("http://webservices/kubatj", "WeatherChecker");
        QName portName = new QName("http://webservices/kubatj", "WeatherCheckerImplPort");

        Service service = Service.create(serviceName);
        service.addPort(portName, SOAPBinding.SOAP11HTTP_BINDING,
                        "http://localhost:8080/WeatherChecker/services/WeatherCheckerImplPort"); 
        this.port = service.getPort(portName,  WeatherChecker.class);
	}
	
	public void printActualWeather(String countryCode, String zip) {
		Holder<Boolean> cached = new Holder<Boolean>(false);
		String weather = this.port.getWeatherByCountryAndZIP(countryCode, zip, cached);
    	printWeather(weather);
		
        if (!cached.value) {
	        
	        try {
				System.in.reset();
			} catch (IOException e1) {
				System.out.println("Input console reset failed");
			}
		    
			char selection = '\0';
			while(selection != 'y' && selection != 'n') {
				System.out.println("Are you somewhere else? (y/n)");
				try {
					selection = ConsoleReader.readLine().charAt(0);
				} catch(InputMismatchException e) {
					selection = '\0';
				}
			}
			
			if (selection == 'y') {
				Location location;
				try {
					location = this.selectAvailableCity(countryCode, zip);
				} catch (JSONException e) {
					System.out.println("Program failed when searching for stations: " + e.getMessage());
					return;
				}
                weather = port.cacheLocationByCountryAndZIP(countryCode, zip, location.toJson());
                printWeather(weather);
			}
        }
	}
	
	private Location selectAvailableCity(String countryCode, String zip) throws JSONException {
		System.out.println("Searching for cities in your neigbourhood...");
		
		String stationsJson = this.port.getStationsByCountryAndZIP(countryCode, zip);
		JSONObject jsonObj = new JSONObject(stationsJson);
		JSONArray stations = jsonObj.getJSONArray("stations");
		
		List<Location> locations = new ArrayList<Location>();
		
		System.out.println("Found '" + stations.length() + "' weather stations in your neighbourhood:");
	    System.out.println("-----------------------------");

	    for (int i = 0; i < stations.length(); i++) {
	    	JSONObject station = stations.getJSONObject(i);
	    	System.out.println("" + (i + 1) + ": " + station.getString("text"));
	    	locations.add(new Location(station.getString("location")));
	    }
	    
	    System.out.println("-----------------------------");

	    try {
			System.in.reset();
		} catch (IOException e1) {
			System.out.println("Input console reset failed");
		}
	    
		int selection = -1;
		while(selection < 1 || selection > stations.length()) {
			System.out.println("Select your nearest city by its line number:");
			try {
				selection = Integer.parseInt(ConsoleReader.readLine());
			} catch(InputMismatchException e) {
				selection = -1;
			}
		}
	    
		return locations.get(selection - 1);
	}
	
	private static void printWeather(String weather) {
		System.out.println();
    	System.out.println("---------------------------");
    	System.out.println("Current weather is:");
        System.out.println(weather);
	}
}
