/**
 * 
 */
package karro.spike.weatherdataspike.YR;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * @author Karro
 *
 */
@Root(strict=false)
public class YrPressure {

	@Attribute(name="value")
	private String mValue;
	
	@Attribute(name="unit")
	private String mUnit;

	/**
	 * @return the value
	 */
	public String getValue() {
		return mValue;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		mValue = value;
	}

	/**
	 * @return the unit
	 */
	public String getUnit() {
		return mUnit;
	}

	/**
	 * @param unit the unit to set
	 */
	public void setUnit(String unit) {
		mUnit = unit;
	}
}
