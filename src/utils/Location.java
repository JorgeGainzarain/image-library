package utils;

public class Location {
	private int latitude;
	private char latitude_Ref;
	
	private int longitude;
	private char longitude_Ref;

	public Location(String s)
	{
		String[] parts1 = s.replace(" (0) ", " ").split(" ");

		latitude =  Integer.parseInt(parts1[1].replace(",", ""));
		longitude = Integer.parseInt(parts1[7].replace(",", ""));
		
		latitude_Ref = parts1[4].charAt(0);
		longitude_Ref = parts1[10].charAt(0);
	}
	
	public Location(String lat, char lat_ref, String lon, char long_ref)
	{
		String[] parts1 = lat.split(" ");
		latitude =  Integer.parseInt(parts1[0].replace(",", ""));
		parts1 = lon.split(" ");
		longitude = Integer.parseInt(parts1[0].replace(",", ""));
		latitude_Ref = lat_ref;
		longitude_Ref = long_ref;
	}
	
	@Override
	public String toString()
	{
		return "Lat: " + latitude + " " + latitude_Ref + " | "
				+ "Long: " + longitude + " " + longitude_Ref ;
	}
	

	public int getLatitude() {
		return latitude;
	}

	public void setLatitude(int latitude) {
		this.latitude = latitude;
	}

	public char getLatitude_Ref() {
		return latitude_Ref;
	}

	public void setLatitude_Ref(char latitude_Ref) {
		this.latitude_Ref = latitude_Ref;
	}

	public int getLongitude() {
		return longitude;
	}

	public void setLongitude(int longitude) {
		this.longitude = longitude;
	}

	public char getLongitude_Ref() {
		return longitude_Ref;
	}

	public void setLongitude_Ref(char longitude_Ref) {
		this.longitude_Ref = longitude_Ref;
	}
}
