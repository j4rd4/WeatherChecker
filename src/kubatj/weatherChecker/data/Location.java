package kubatj.weatherChecker.data;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class Location {
	private String adminDistrict;
	private String adminDistrict2;
	private String countryRegion;
	private String latitude;
	private String longitude;
	
	public Location(String ad, String ad2, String cr, String lat, String lon) {
		adminDistrict = ad;
		adminDistrict2 = ad2;
		countryRegion = cr;
		latitude = lat;
		longitude = lon;
	}
	
	public Location(String json) {
		try {
			JSONObject jsonObj = new JSONObject(json);
			
			adminDistrict = jsonObj.getString("adminDistrict");
			adminDistrict2 = jsonObj.getString("adminDistrict2");
			countryRegion = jsonObj.getString("countryRegion");
			latitude = jsonObj.getString("latitude");
			longitude = jsonObj.getString("longitude");
		} catch (JSONException e) {
			
		}
	}

	public String getAdminDistrict() {
		return adminDistrict;
	}

	public String getAdminDistrict2() {
		return adminDistrict2;
	}

	public String getCountryRegion() {
		return countryRegion;
	}

	public String getLatitude() {
		return latitude;
	}

	public String getLongitude() {
		return longitude;
	}
	
	@Override
	public String toString() {
		return countryRegion + " -> " + adminDistrict + ((adminDistrict2 == null) ? "" : " -> " + adminDistrict2);
	}
	
	public String toJson() {
		return "{ "
				+ "'countryRegion':'" + countryRegion + "', "
				+ "'adminDistrict':'" + adminDistrict + ((adminDistrict2 == null) ? "" : "', 'adminDistrict2':'" + adminDistrict2) + "', "
				+ "'latitude':'" + latitude + "', "
				+ "'longitude':'" + longitude + "'"
				+ "}";
	}
	
	public boolean isEmpty() {
		return this.getCountryRegion() == null || this.getCountryRegion().isEmpty();
	}
}
