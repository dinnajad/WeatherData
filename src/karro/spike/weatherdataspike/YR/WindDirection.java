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
	 * @return the code
	 */
	public String getCode() {
		return mCode;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		mCode = code;
	}

	/**
	 * @return the degree
	 */
	public String getDegree() {
		return mDegree;
	}

	/**
	 * @param degree the degree to set
	 */
	public void setDegree(String degree) {
		mDegree = degree;
	}
}
