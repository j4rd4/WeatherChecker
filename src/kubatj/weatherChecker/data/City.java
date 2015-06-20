package kubatj.weatherChecker.data;

public class City {
	private String name;
	
	public City(String n) {
		this.name = n;
	}
	
	public String getName() {
		return this.name;
	}
	
	@Override
	public String toString() {
		return getName();
	}
}
