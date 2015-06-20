package kubatj.weatherChecker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.UriBuilder;
import javax.xml.parsers.ParserConfigurationException;

import kubatj.weatherChecker.data.City;
import kubatj.weatherChecker.tools.Configuration;
import kubatj.weatherChecker.tools.XMLParser;

import org.xml.sax.SAXException;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class CitiesClient {
	
	private List<City> cities;
	
	public CitiesClient(String country) {
		cities = new ArrayList<City>();
		loadCitiesToCache(country.replaceAll(" ", "%20"));
	}
	
	public void loadCitiesToCache(String country) {
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		UriBuilder uriBuilder = UriBuilder.fromUri(Configuration.CITIES_URL);
		uriBuilder.queryParam("CountryName", country);
		WebResource service = client.resource(uriBuilder.build());
		String xml = service.get(String.class).replaceAll("&lt;", "<").replaceAll("&gt;", ">");
		
		try {
			cities = XMLParser.getCitiesFromXml(xml);
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
	
	public List<City> getCities() {
		return cities;
	}
}
