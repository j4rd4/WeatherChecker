package kubatj.weatherChecker.tools;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import kubatj.weatherChecker.data.City;
import kubatj.weatherChecker.data.Location;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XMLParser {
	public static List<City> getCitiesFromXml(String xmlString) throws ParserConfigurationException, SAXException, IOException {
	
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder = factory.newDocumentBuilder();
	    InputSource is = new InputSource(new StringReader(xmlString));
	    Document document = builder.parse(is);
	
	    List<City> cities = new ArrayList<City>();
	    NodeList nodeList = document.getDocumentElement().getFirstChild().getChildNodes();
	    for (int i = 0; i < nodeList.getLength(); i++) {
	         Node node = nodeList.item(i);
	
	         if (node.getNodeType() == Node.ELEMENT_NODE) {
	              Element elem = (Element) node;
	
	              String city = elem.getElementsByTagName("City")
	                                  .item(0).getChildNodes().item(0).getNodeValue();
	
	              cities.add(new City(city));
	         }
	    }
	
	    return cities;
	}
	
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
		    Element addressEL = (Element)locationEL.getElementsByTagName("Address").item(0);
		    Node adminDistrictEl = addressEL.getElementsByTagName("AdminDistrict").item(0).getChildNodes().item(0);
		    Node adminDistrict2El = addressEL.getElementsByTagName("AdminDistrict2").item(0).getChildNodes().item(0);
		    Node countryRegionEl = addressEL.getElementsByTagName("CountryRegion").item(0).getChildNodes().item(0);
		    
		    location = new Location(adminDistrictEl.getNodeValue(), adminDistrict2El.getNodeValue(), countryRegionEl.getNodeValue());
		    
	    } catch (NullPointerException e) {
	    	location = new Location("Location not found", "Location not found", "Location not found");
	    }
	    
	    return location;
	}
	
	public static String getWeatherFromXml(String xmlString) throws ParserConfigurationException, SAXException, IOException {

		String newXml = xmlString
				.replaceAll("<string[^>]*>", "")
	    		.replaceAll(".*<\\?xml[^>]*>", "")
	    		.replaceAll("</string>", "").trim();
		
		//System.out.println("------------------------------------");
	    //System.out.println(newXml);
	    //System.out.println("------------------------------------");
	    
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder = factory.newDocumentBuilder();
	    InputSource is = new InputSource(new StringReader("<content>" + newXml + "</content>"));
	    Document document = builder.parse(is);
	    
	    String result = "";

	    NodeList nodeList = document.getDocumentElement().getFirstChild().getChildNodes();
	    for (int i = 0; i < nodeList.getLength(); i++) {
	         Node node = nodeList.item(i);
	         if (node.getNodeType() == Node.ELEMENT_NODE) {
	             result += node.getNodeName();
	             result += ": ";
	             result += node.getTextContent().trim();
	             result += "\n";
	         }
	    }
	
	    return result;
	}
}
