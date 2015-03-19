/**
 * 
 */
package karro.spike.weatherdataspike.YR;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * @author Karro
 *
 */
@Root(strict=false)
public class YrLocation {

	@Element
	private String name;
	
	@Element
	private String country;

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return name + ", " + country;
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
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	
}
