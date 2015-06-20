package kubatj.weatherChecker;

import java.io.IOException;
import java.util.InputMismatchException;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;

import kubatj.weatherChecker.data.Location;
import kubatj.weatherChecker.tools.Configuration;
import kubatj.weatherChecker.tools.ConsoleReader;

public class WeatherCacheClient {
	public static void printActualWeather(String countryCode, String zip) {
        QName serviceName = new QName("http://webservices/kubatj", "WeatherCache");
        QName portName = new QName("http://webservices/kubatj", "WeatherCacheImplPort");

        Service service = Service.create(serviceName);
        service.addPort(portName, SOAPBinding.SOAP11HTTP_BINDING,
                        "http://localhost:8080/WeatherChecker/services/WeatherCacheImplPort"); 
        WeatherCache port = service.getPort(portName,  WeatherCache.class);
        
        Location cached = new Location(port.loadSelection(zip));
        String weather = "";
        
        if (cached.getCountryRegion().isEmpty()) {
        	System.out.println("Searching for your selection...");
    		
    		LocationClient locationClient = new LocationClient(countryCode, zip);
    		Location location = locationClient.getLocation();
    		
    	    System.out.println("Locality found: " + location.toString());
            
            weather = port.getWeatherForLocation(location.toJson());

            if (weather.isEmpty()) {
            	String cityName = selectAvailableCity(port, location);
            	location = new Location(location.getAdminDistrict(), cityName, location.getCountryRegion());
        	    System.out.println("Locality found: " + location.toString());
                weather = port.getWeatherForLocation(location.toJson());
                port.saveSelection(location.toJson(), zip);
            }
        } else {
        	System.out.println("Selection cached");
        	weather = port.getWeatherForLocation(cached.toJson());
        }
        
        if (weather.isEmpty()) {
            System.out.println("Error while searching for your location!");
        } else {
        	System.out.println();
        	System.out.println("---------------------------");
        	System.out.println("Current weather is:");
	        System.out.println(weather);
        }
	}
	
	private static String selectAvailableCity(WeatherCache port, Location location) {
		System.out.println("Sorry, your exact location is not monitored.");
		System.out.println("Searching for cities in your country '" + location.getCountryRegion() + "'...");
		
		String[] cities = port.getCitiesForLocation(location.toJson()).split(Configuration.CITIES_DELIMITER);
		
		System.out.println("Found '" + cities.length + "' cities in your country:");
	    System.out.println("-----------------------------");
		for (int i = 0; i < cities.length; i++) {
			System.out.println("" + (i + 1) + ": " + cities[i].toString());
		}
	    System.out.println("-----------------------------");

	    try {
			System.in.reset();
		} catch (IOException e1) {
			System.out.println("Input console reset failed");
		}
	    
		int selection = -1;
		while(selection < 1 || selection > cities.length) {
			System.out.println("Select your nearest city by its line number:");
			try {
				selection = Integer.parseInt(ConsoleReader.readLine());
			} catch(InputMismatchException e) {
				selection = -1;
			}
		}
	    
		return cities[selection - 1].toString();
	}
}
