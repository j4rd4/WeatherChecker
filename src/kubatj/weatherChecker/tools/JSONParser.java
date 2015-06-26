package kubatj.weatherChecker.tools;

import java.util.ArrayList;
import java.util.List;

import kubatj.weatherChecker.data.City;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class JSONParser {
	public static List<City> getCitiesFromJson(String jsonString) throws JSONException {
		JSONObject jsonObj = new JSONObject(jsonString);
			
		JSONObject location = jsonObj.getJSONObject("location");
		JSONObject nbws = location.getJSONObject("nearby_weather_stations");
		JSONObject pws = nbws.getJSONObject("pws");
		JSONArray stations = pws.getJSONArray("station");
		
	    List<City> cities = new ArrayList<City>();
	    
	    for (int i = 0; i < stations.length(); i++) {
	    	JSONObject station = stations.getJSONObject(i);
	
	         cities.add(new City(
	        		 station.getString("city"), 
	        		 station.getString("country"), 
	        		 station.getString("distance_km"), 
	        		 station.getString("lat"), 
	        		 station.getString("lon")
	        		 )
	         );
	    }
	
	    return cities;
	}
	
	public static String getWeatherFromJson(String jsonString) throws JSONException {
		JSONObject jsonObj = new JSONObject(jsonString);
		JSONObject observation = jsonObj.getJSONObject("current_observation");
		
		//System.out.println("------------------------------------");
	    //System.out.println(jsonString);
	    //System.out.println("------------------------------------");
	    
	    StringBuilder result = new StringBuilder();

	    result.append("Weather: " + observation.getString("weather") + "\n");
	    result.append("Temperature: " + observation.getString("temp_c") + "°C\n");
	    result.append("Feels like: " + observation.getString("feelslike_c") + "°C\n");
	    result.append("Relative humidity: " + observation.getString("relative_humidity") + "\n");
	    result.append("Wind: " + observation.getString("wind_string") + "\n");
	    result.append("Pressure: " + observation.getString("pressure_mb") + "hPa\n");
	    result.append("Location: " + observation.getJSONObject("observation_location").getString("full") + "\n");
	
	    return result.toString();
	}

}
