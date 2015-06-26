package kubatj.weatherChecker;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;
import javax.xml.namespace.QName;
import javax.xml.ws.Holder;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;

import kubatj.weatherChecker.data.City;
import kubatj.weatherChecker.data.Location;

@WebService(targetNamespace = "http://weatherChecker.kubatj/", endpointInterface = "kubatj.weatherChecker.WeatherChecker", portName = "WeatherCheckerImplPort", serviceName = "WeatherCheckerImplService")
public class WeatherCheckerImpl implements WeatherChecker {
	
	WeatherCache port = null;
	
	public WeatherCheckerImpl() {
		init();
	}

	@Override
	public String getWeatherByCountryAndZIP(String countryCode, String zip,
			Holder<Boolean> cached) {

        Location cachedLoc = new Location(this.port.loadSelection(countryCode, zip));
        String weather = "";
        
        if (cachedLoc.isEmpty()) {
        	cached.value = false;
        	
        	LocationClient locationClient = new LocationClient(countryCode, zip);
    		Location location = locationClient.getLocation();
            
            weather = port.getWeatherForLocation(location.toJson());

            if (weather.isEmpty()) {
            	weather = "Sorry, your exact location is not monitored.";
            }
        } else {
        	cached.value = true;
        	weather = port.getWeatherForLocation(cachedLoc.toJson());
        }
		
		return weather;
	}

	@Override
	public String getStationsByCountryAndZIP(String countryCode, String zip) {

		StringBuilder result = new StringBuilder();

    	LocationClient locationClient = new LocationClient(countryCode, zip);
		Location location = locationClient.getLocation();
		
		List<City> cities = this.getCitiesForLocation(location);
		
		result.append("{\"stations\":[");
		
		boolean firstIt = true;
		for (City city : cities) {
			if (firstIt) {
				firstIt = false;
			} else {
				result.append(',');
			}
			
			Location loc = new Location(city.getName(), city.getName(), city.getCountry(), city.getLatitude(), city.getLongitude());

			result.append("{");
			result.append("\"text\":\"" + city.toString() + "\",");
			result.append("\"location\":\"" + loc.toJson() + "\"");
			result.append("}");
		}

		result.append("]}");
		
		return result.toString();
	}

	@Override
	public String cacheLocationByCountryAndZIP(String countryCode, String zip,
			String locationJson) {

		this.port.saveSelection(locationJson, countryCode, zip);

    	return port.getWeatherForLocation(locationJson);
	}

	private void init() {
		QName serviceName = new QName("http://webservices/kubatj", "WeatherCache");
        QName portName = new QName("http://webservices/kubatj", "WeatherCacheImplPort");

        Service service = Service.create(serviceName);
        service.addPort(portName, SOAPBinding.SOAP11HTTP_BINDING,
                        "http://localhost:8080/WeatherChecker/services/WeatherCacheImplPort"); 
        this.port = service.getPort(portName,  WeatherCache.class);
	}

	private List<City> getCitiesForLocation(Location location) {
		CitiesClient citiesClient = new CitiesClient(location.getLatitude(), location.getLongitude());
		List<City> cities = citiesClient.getCities();
		
		if (cities == null) {
			cities = new ArrayList<City>();
		}
		
		return cities;
	}
}
