package kubatj.weatherChecker;

import java.io.IOException;

import javax.ws.rs.core.UriBuilder;
import javax.xml.parsers.ParserConfigurationException;

import kubatj.weatherChecker.data.Location;
import kubatj.weatherChecker.tools.Configuration;
import kubatj.weatherChecker.tools.XMLParser;

import org.xml.sax.SAXException;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class LocationClient {
	
	Location location;
	
	public LocationClient(String countryCode, String postalCode) {
		loadLocation(countryCode, postalCode);
	}
	
	public void loadLocation(String countryCode, String postalCode) {
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		UriBuilder uriBuilder = UriBuilder.fromUri(Configuration.LOCATION_URL);
		uriBuilder.queryParam("CountryRegion", countryCode);
		uriBuilder.queryParam("postalCode", postalCode);
		uriBuilder.queryParam("o", Configuration.LOCATION_OUTPUT);
		uriBuilder.queryParam("key", Configuration.LOCATION_BING_MAPS_KEY);
		WebResource service = client.resource(uriBuilder.build());
		String xml = service.get(String.class).replaceAll("^[^<]*", "");
		
		try {
			location = XMLParser.getLocationFromXml(xml);
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
	
	public Location getLocation() {
		return location;
	}
}
