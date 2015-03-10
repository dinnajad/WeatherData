/**
 * 
 */
package karro.spike.weatherdataspike.YR;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementUnion;
import org.simpleframework.xml.Root;

/**
 * @author Karro
 *
 */
@Root(strict=false, name="weatherdata")
public class WeatherData {

	@Element(name="forecast")
	private Forecast mForeCast;

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
	public Forecast getForeCast() {
		return mForeCast;
	}

	/**
	 * @param foreCast the foreCast to set
	 */
	public void setForeCast(Forecast foreCast) {
		mForeCast = foreCast;
	}
}
