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
public class YrSymbol {

	@Attribute(name="numberEx")
	private String numberEx;
	
	@Attribute(name="name")
	private String name;

	/**
	 * @return the numberEx
	 */
	public String getNumberEx() {
		return numberEx;
	}

	/**
	 * @param numberEx the numberEx to set
	 */
	public void setNumberEx(String numberEx) {
		this.numberEx = numberEx;
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
}
