package karro.spike.weatherdataspike.model;

public interface IPosition {

	/***
	 * 
	 * @return
	 */
	public abstract String toString();

	/**
	 * @return the name
	 */
	public abstract String getName();

	/**
	 * @param name the name to set
	 */
	public abstract void setName(String name);

	/**
	 * @param id the Id to set
	 */
	public abstract void setId(int id);
	
	/***
	 * 
	 * @return the id
	 */
	public abstract int getId();
	
	/**
	 * @return the countryName
	 */
	public abstract String getCountryName();

	/**
	 * @param countryName the countryName to set
	 */
	public abstract void setCountryName(String countryName);

	/**
	 * @return the region
	 */
	public abstract String getRegion();

	/**
	 * @param region the region to set
	 */
	public abstract void setRegion(String region);

}