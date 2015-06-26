package kubatj.weatherChecker.data;

public class City {
	private String name;
	private String country;
	private String distance;
	private String latitude;
	private String longitude;
	
	public City(String name, String country, String distance, String lat, String lon) {
		this.name = name;
		this.country = country;
		this.distance = distance;
		this.latitude = lat;
		this.longitude = lon;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getCountry() {
		return this.country;
	}
	
	public String getDistance() {
		return this.distance;
	}

	public String getLatitude() {
		return latitude;
	}

	public String getLongitude() {
		return longitude;
	}
	
	@Override
	public String toString() {
		return this.getName() + "(" + this.getCountry() + ") - " + this.distance + "km away";
	}
}
