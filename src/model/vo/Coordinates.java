package model.vo;

public class Coordinates implements Comparable<Coordinates> {
	/**
	 *
	 */
	public Double lat;
	
	/**
	 * 
	 */
	public Double lon;
	
	/**
	 * 
	 * @param pFirst
	 * @param pSecond
	 */
	public Coordinates(Double pLat, Double pLon) {
		lat = pLat;
		lon = pLon;
	}

	/**
	 * 
	 */
	public int compareTo(Coordinates arg0) {
		if(lat.compareTo(arg0.lat) > 0)
			return 1;
		if(lat.compareTo(arg0.lat) < 0)
			return -1;
		if(lon.compareTo(arg0.lon) > 0)
			return 1;
		if(lon.compareTo(arg0.lon) < 0)
			return -1;
		return 0;
	}
	public String toString() {
		return "Latitud: "+ lat+ " - " + "Longuitud: " + lon;
	}
	
	public int hashCode() {
		return lat.hashCode();
	}
	
	public boolean equals(Coordinates obj) {
		if(lat.equals(obj.lat) && lon.equals(obj.lon))
			return true;
		return false;
	}
}
