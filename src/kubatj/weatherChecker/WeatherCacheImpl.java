package kubatj.weatherChecker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import kubatj.weatherChecker.data.City;
import kubatj.weatherChecker.data.Location;
import kubatj.weatherChecker.tools.Configuration;

//@TODO Zmenit sluzbu pocasi (norska sluzba), pojmenovat parametry, klient -> sluzba + novy maly klient
// + na github + readme

@WebService(serviceName="WeatherCache", targetNamespace="http://webservices/kubatj", endpointInterface="kubatj.weatherChecker.WeatherCache")
public class WeatherCacheImpl implements WeatherCache {
	private Map<String, Location> savedSelections = new HashMap<String, Location>();
	private Location dummyLocation = new Location("", "", "");

	@Override
	public String getWeatherForLocation(String location) {
		Location loc = new Location(location);
		System.out.println("Trying to find weather in " + loc.getAdminDistrict2());
		WeatherClient weatherClient = new WeatherClient(loc.getCountryRegion(), loc.getAdminDistrict2());
		String weather = weatherClient.getWeather();
		
		if (weather.isEmpty()) {
			System.out.println("Trying to find weather in " + loc.getAdminDistrict());
			weatherClient.loadWeatherToCache(loc.getCountryRegion(), loc.getAdminDistrict());
			weather = weatherClient.getWeather();
		}
		
		return weather;
	}

	@Override
	public String getCitiesForLocation(String location) {
		Location loc = new Location(location);
		CitiesClient citiesClient = new CitiesClient(loc.getCountryRegion());
		List<City> cities = citiesClient.getCities();
		
		if (cities == null) {
			cities = new ArrayList<City>();
		}
		
		StringBuilder builder = new StringBuilder();
		for (City city : cities) {
			builder.append(city.toString());
			builder.append(Configuration.CITIES_DELIMITER);
		}
		
		return builder.toString();
	}

	@Override
	public String loadSelection(String zip) {
		Location found = savedSelections.get(zip);
		return ((found != null) ? found : dummyLocation).toJson();
	}

	@Override
	public void saveSelection(String location, String zip) {
		Location loc = new Location(location);
		savedSelections.put(zip, loc);
	}

}
