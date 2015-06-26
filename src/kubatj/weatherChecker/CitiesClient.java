package kubatj.weatherChecker;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.UriBuilder;

import kubatj.weatherChecker.data.City;
import kubatj.weatherChecker.tools.Configuration;
import kubatj.weatherChecker.tools.JSONParser;

import org.codehaus.jettison.json.JSONException;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class CitiesClient {
	
	private List<City> cities;
	
	public CitiesClient(String latitude, String longitude) {
		cities = new ArrayList<City>();
		loadCitiesToCache(latitude, longitude);
	}
	
	public void loadCitiesToCache(String latitude, String longitude) {
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		UriBuilder uriBuilder = UriBuilder.fromUri(
			Configuration.CITIES_URL
			+ latitude
			+ Configuration.CITIES_DELIMITER
			+ longitude + ".json"
		);
		WebResource service = client.resource(uriBuilder.build());
		String json = service.get(String.class);
/*
		System.out.println("------------------------------------");
		System.out.println(Configuration.CITIES_URL
			+ latitude
			+ Configuration.CITIES_DELIMITER
			+ longitude + ".json");
	    System.out.println(json);
	    System.out.println("------------------------------------");
*/

		try {
			cities = JSONParser.getCitiesFromJson(json);
		} catch (JSONException ex) {
			System.out.println("Exception 'JSONException' occured while parsing the json: " + ex.getMessage());
			return;
		}
	}
	
	public List<City> getCities() {
		return cities;
	}
}
