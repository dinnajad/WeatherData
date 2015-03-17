/**
 * 
 */
package karro.spike.weatherdataspike.YR;

import org.simpleframework.xml.Attribute;

/**
 * @author Karro
 *
 */
public class YrWindSpeed {

	@Attribute(name="name")
	private String mName;
	
	@Attribute(name="mps")
	private String mMps;

	/**
	 * @return the name
	 */
	public String getName() {
		return mName;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		mName = name;
	}

	/**
	 * @return the mps
	 */
	public String getMps() {
		return mMps;
	}

	/**
	 * @param mps the mps to set
	 */
	public void setMps(String mps) {
		mMps = mps;
	}
}
