package kubatj.weatherChecker;

import java.io.IOException;

import javax.ws.rs.core.UriBuilder;
import javax.xml.parsers.ParserConfigurationException;

import kubatj.weatherChecker.tools.Configuration;
import kubatj.weatherChecker.tools.XMLParser;

import org.xml.sax.SAXException;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class WeatherClient {
	
	private String weather;
	
	public WeatherClient(String country, String city) {
		weather = null;
		loadWeatherToCache(country, city);
	}
	
	public void loadWeatherToCache(String country, String city) {
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		UriBuilder uriBuilder = UriBuilder.fromUri(Configuration.WEATHER_URL);
		uriBuilder.queryParam("CountryName", country);
		uriBuilder.queryParam("CityName", city);
		WebResource service = client.resource(uriBuilder.build());
		String xml = service.get(String.class).replaceAll(".*\\?>", "").replaceAll("&lt;", "<").replaceAll("&gt;", ">");
		
		try {
			weather = XMLParser.getWeatherFromXml(xml);
		} catch (ParserConfigurationException ex) {
			System.out.println("Exception 'ParserConfigurationException' occured while parsing the xml: " + ex.getMessage());
			return;
		} catch (SAXException ex) {
			System.out.println("Exception 'SAXException' occured while parsing the xml: " + ex.getMessage());
			return;
		} catch (IOException ex) {
			System.out.println("Exception 'IOException' occured while parsing the xml: " + ex.getMessage());
			return;
		}
	}
	
	public String getWeather() {
		return weather;
	}
}
