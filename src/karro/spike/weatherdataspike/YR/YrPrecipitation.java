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
public class YrPrecipitation {

	
	@Attribute(name="maxvalue",required=false)
	private String mMaxValue;
	
	@Attribute(name="minvalue",required=false)
	private String mMinvalue;
	
	@Attribute(name="value",required=true)
	private String mValue;

	/**
	 * @return the maxValue
	 */
	public String getMaxValue() {
		return mMaxValue;
	}

	/**
	 * @param maxValue the maxValue to set
	 */
	public void setMaxValue(String maxValue) {
		mMaxValue = maxValue;
	}

	/**
	 * @return the minvalue
	 */
	public String getMinvalue() {
		return mMinvalue;
	}

	/**
	 * @param minvalue the minvalue to set
	 */
	public void setMinvalue(String minvalue) {
		mMinvalue = minvalue;
	}

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
	
}
