/**
 * 
 */
package karro.spike.weatherdataspike.YR;

import org.simpleframework.xml.Attribute;

/**
 * @author Karro
 *
 */
public class WindDirection {

	@Attribute(name="name")
	private String name;
	
	@Attribute(name="code")
	private String mCode;
	
	@Attribute(name="deg")
	private String mDegree;
}
