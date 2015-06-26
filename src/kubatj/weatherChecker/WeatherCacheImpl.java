package kubatj.weatherChecker;

import java.util.HashMap;
import java.util.Map;

import javax.jws.WebService;

import kubatj.weatherChecker.data.Location;

@WebService(serviceName="WeatherCache", targetNamespace="http://webservices/kubatj", endpointInterface="kubatj.weatherChecker.WeatherCache")
public class WeatherCacheImpl implements WeatherCache {
	private Map<String, Location> savedSelections = new HashMap<String, Location>();
	private Location dummyLocation = new Location("", "", "", "", "");

	@Override
	public String getWeatherForLocation(String locationJson) {
		System.out.println(locationJson);
		Location loc = new Location(locationJson);
		System.out.println("Trying to find weather in " + loc.getAdminDistrict2());
		WeatherClient weatherClient = new WeatherClient(loc.getLatitude(), loc.getLongitude());
		String weather = weatherClient.getWeather();
		
		return weather;
	}

	@Override
	public String loadSelection(String countryCode, String zip) {
		Location found = savedSelections.get(createKey(countryCode, zip));
		return ((found != null) ? found : dummyLocation).toJson();
	}

	@Override
	public void saveSelection(String locationJson, String countryCode, String zip) {
		Location loc = new Location(locationJson);
		savedSelections.put(createKey(countryCode, zip), loc);
	}
	
	private String createKey(String countryCode, String zip) {
		return countryCode + "/" + zip;
	}

}
