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
