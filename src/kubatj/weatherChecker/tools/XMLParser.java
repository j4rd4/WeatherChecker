package kubatj.weatherChecker.tools;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import kubatj.weatherChecker.data.Location;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XMLParser {
	
	public static Location getLocationFromXml(String xmlString) throws ParserConfigurationException, SAXException, IOException {
	
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder = factory.newDocumentBuilder();
	    InputSource is = new InputSource(new ByteArrayInputStream(xmlString.getBytes()));
	    is.setEncoding("Windows-1250");
	    Document document = builder.parse(is);
	    
	    Location location;

	    try {
		    Element resourceSetsEL = (Element)document.getDocumentElement().getElementsByTagName("ResourceSets").item(0);
		    Element resourcesEL = (Element)resourceSetsEL.getElementsByTagName("Resources").item(0);
		    Element locationEL = (Element)resourcesEL.getElementsByTagName("Location").item(0);
		    
		    Element pointEL = (Element)locationEL.getElementsByTagName("Point").item(0);
		    Node latitudeEL = pointEL.getElementsByTagName("Latitude").item(0).getChildNodes().item(0);
		    Node longitudeEL = pointEL.getElementsByTagName("Longitude").item(0).getChildNodes().item(0);
		    
		    Element addressEL = (Element)locationEL.getElementsByTagName("Address").item(0);
		    Node adminDistrictEl = addressEL.getElementsByTagName("AdminDistrict").item(0).getChildNodes().item(0);
		    Node adminDistrict2El = addressEL.getElementsByTagName("AdminDistrict2").item(0).getChildNodes().item(0);
		    Node countryRegionEl = addressEL.getElementsByTagName("CountryRegion").item(0).getChildNodes().item(0);
		    
		    location = new Location(
		    		adminDistrictEl.getNodeValue(), 
		    		adminDistrict2El.getNodeValue(), 
		    		countryRegionEl.getNodeValue(), 
		    		latitudeEL.getNodeValue(), 
		    		longitudeEL.getNodeValue()
		    	);
		    
	    } catch (NullPointerException e) {
	    	location = new Location("Location not found", "Location not found", "Location not found", "", "");
	    }
	    
	    return location;
	}
}
