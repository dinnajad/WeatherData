/**
 * 
 */
package karro.spike.weatherdataspike;

import android.util.Log;

/**
 * @author Karro
 *
 */
public class GeonamesPosition {
	private static final String TAG = "GeonamesPosition";
	private String name;
	private String geonameId;
	private String countryName;
	private String region; //adminName1
	
	private float lat;
	private float lng;
	
	@Override
	public String toString(){
		return countryName+"/"+name+"/ region: "+ region;
		
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the geonameId
	 */
	public String getGeonameId() {
		return geonameId;
	}
	/**
	 * @param geonameId the geonameId to set
	 */
	public void setGeonameId(String geonameId) {
		this.geonameId = geonameId;
	}
	/**
	 * @return the countryName
	 */
	public String getCountryName() {
		return countryName;
	}
	/**
	 * @param countryName the countryName to set
	 */
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	/**
	 * @return the region
	 */
	public String getRegion() {
		return region;
	}
	/**
	 * @param region the region to set
	 */
	public void setRegion(String region) {
		this.region = region;
	}
	/**
	 * @return the lat
	 */
	public float getLat() {
		return lat;
	}
	/**
	 * @param lat the lat to set
	 */
	public void setLat(float lat) {
		this.lat = lat;
	}
	/**
	 * @return the lng
	 */
	public float getLng() {
		return lng;
	}
	/**
	 * @param lng the lng to set
	 */
	public void setLng(float lng) {
		this.lng = lng;
	}
	
	public void setLat(String lat) {
		try{
		Float latitude = Float.valueOf(lat);
		setLat(latitude);
		}catch (NumberFormatException e){
			Log.e(TAG, "Failed  to parse items", e);
		}
	}
	
	public void setLng(String lng) {
		try{
		Float longitude = Float.valueOf(lng);
		setLng(longitude);
		}catch (NumberFormatException e){
			Log.e(TAG, "Failed  to parse items", e);
		}
	}
}
