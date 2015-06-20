package kubatj.weatherChecker.tools;

public final class Configuration {
	public static final String LOCATION_URL = "http://dev.virtualearth.net/REST/v1/Locations";
	public static final String LOCATION_OUTPUT = "xml";
	public static final String LOCATION_BING_MAPS_KEY = "AifZrSmgxlKvzkCC9ZrGC6hhPTxAW_ZTaFGk7RJ4Ge3Qz97r5-9hRvITDAmqD9Jk";
	
	public static final String CITIES_URL = "http://www.webservicex.net/globalweather.asmx/GetCitiesByCountry";
	public static final String CITIES_DELIMITER = ";";
	
	public static final String WEATHER_URL = "http://www.webservicex.net/globalweather.asmx/GetWeather";
}
