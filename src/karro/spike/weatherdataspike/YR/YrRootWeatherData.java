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
@Root(strict=false, name="weatherdata")
public class YrRootWeatherData {//TODO rename

	@Element(name="forecast")
	private YrForecast mForeCast;
	
	@Element(name="credit", required=false)
	private YrCredit mCredit;
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "WeatherData [mForeCast=" + mForeCast + "]";
	}

	/**
	 * @return the foreCast
	 */
	public YrForecast getForeCast() {
		return mForeCast;
	}

	/**
	 * @param foreCast the foreCast to set
	 */
	public void setForeCast(YrForecast foreCast) {
		mForeCast = foreCast;
	}

	/**
	 * @return the credit
	 */
	public YrCredit getCredit() {
		return mCredit;
	}

	/**
	 * @param credit the credit to set
	 */
	public void setCredit(YrCredit credit) {
		mCredit = credit;
	}
}
