/**
 * 
 */
package karro.spike.weatherdataspike.Geonames;

import karro.spike.weatherdataspike.model.IPosition;

import org.simpleframework.xml.*;

import android.util.Log;

/**
 * @author Karro
 *
 */
@Root(name="geoname", strict=false)
public class GeonamesPosition implements IPosition {
	private static final String TAG = "GeonamesPosition";
	
	@Element(name="name")
	private String name;
	
	@Element
	private String geonameId;
	
	@Element
	private String countryName;
	
	@Element(required=false, name="adminName1")
	private String region; //adminName1
	
	@Element
	private String lat;
	
	@Element
	private String lng;
	
	
	/* (non-Javadoc)
	 * @see karro.spike.weatherdataspike.Geonames.IPosition#toString()
	 */
	@Override
	public String toString(){
		return countryName+"/"+region+"/"+name;
		
	}
	/* (non-Javadoc)
	 * @see karro.spike.weatherdataspike.Geonames.IPosition#getName()
	 */
	@Override
	public String getName() {
		return name;
	}
	/* (non-Javadoc)
	 * @see karro.spike.weatherdataspike.Geonames.IPosition#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the geonameId
	 */
	public String getGeonameId() {
		return geonameId;
	}
	/* (non-Javadoc)
	 * @see karro.spike.weatherdataspike.Geonames.IPosition#setGeonameId(java.lang.String)
	 */
	public void setGeonameId(String geonameId) {
		this.geonameId = geonameId;
	}
	/* (non-Javadoc)
	 * @see karro.spike.weatherdataspike.Geonames.IPosition#getCountryName()
	 */
	@Override
	public String getCountryName() {
		return countryName;
	}
	/* (non-Javadoc)
	 * @see karro.spike.weatherdataspike.Geonames.IPosition#setCountryName(java.lang.String)
	 */
	@Override
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	/* (non-Javadoc)
	 * @see karro.spike.weatherdataspike.Geonames.IPosition#getRegion()
	 */
	@Override
	public String getRegion() {
		return region;
	}
	/* (non-Javadoc)
	 * @see karro.spike.weatherdataspike.Geonames.IPosition#setRegion(java.lang.String)
	 */
	@Override
	public void setRegion(String region) {
		this.region = region;
	}
	
	@Override
	public void setId(int id) {
		geonameId=Integer.toString( id);		
	}
	
	@Override
	public int getId() {
		return Integer.parseInt(geonameId);
	}
	
	//TODO return the LatLng in some way
	/**
	 * @return the lat
	 *//*
	public float getLat() {
		return lat;
	}
	*//**
	 * @param lat the lat to set
	 *//*
	public void setLat(float lat) {
		this.lat = lat;
	}
	*//**
	 * @return the lng
	 *//*
	public float getLng() {
		return lng;
	}
	*//**
	 * @param lng the lng to set
	 *//*
	public void setLng(float lng) {
		this.lng = lng;
	}*/
	
/*	public void setLat(String lat) {
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
	}*/
}
