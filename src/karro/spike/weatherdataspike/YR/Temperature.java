/**
 * 
 */
package karro.spike.weatherdataspike.YR;

import org.simpleframework.xml.Attribute;

/**
 * @author Karro
 *
 */
public class Temperature {

	
	@Attribute(name = "value")
	private String mTemperature;
	
	@Attribute( name="unit")
	private String unit;
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Temperature [mTemperature=" + mTemperature + ", unit=" + unit
				+ "]";
	}

	/**
	 * @return the temperature
	 */
	public String getTemperature() {
		return mTemperature;
	}

	/**
	 * @param temperature the temperature to set
	 */
	public void setTemperature(String temperature) {
		mTemperature = temperature;
	}
}
