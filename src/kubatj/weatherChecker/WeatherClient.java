package kubatj.weatherChecker;

import javax.ws.rs.core.UriBuilder;

import kubatj.weatherChecker.tools.Configuration;
import kubatj.weatherChecker.tools.JSONParser;

import org.codehaus.jettison.json.JSONException;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

/**
 * This is client class for the weather service. In the time of it's creation
 * it loads actual weather info in given location and that is accessible 
 * through the getWeather function as a string.
 * 
 * @author Jaroslav Kubat
 *
 */
public class WeatherClient {
	
	private String weather;
	
	public WeatherClient(String lat, String lon) {
		weather = null;
		loadWeatherToCache(lat, lon);
	}
	
	public void loadWeatherToCache(String lat, String lon) {
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		UriBuilder uriBuilder = UriBuilder.fromUri(
				Configuration.WEATHER_URL
				+ lat
				+ ","
				+ lon
				+ ".json"
			);
		
/*		
		System.out.println(Configuration.WEATHER_URL
				+ lat
				+ ","
				+ lon
				+ ".json");
*/
		
		WebResource service = client.resource(uriBuilder.build());
		String json = service.get(String.class);
		
		try {
			weather = JSONParser.getWeatherFromJson(json);
		} catch (JSONException ex) {
			System.out.println("Exception 'JSONException' occured while parsing the json: " + ex.getMessage());
			return;
		}
	}
	
	public String getWeather() {
		return weather;
	}
}
