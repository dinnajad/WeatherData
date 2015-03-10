/**
 * 
 */
package karro.spike.weatherdataspike.YR;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 * @author Karro
 *
 */
@Root(strict=false,name="forecast")
//@Root(strict=false)
public class Forecast {

	@ElementList(name="tabular")
	private List<YrWetherData> mList;

	/**
	 * @return the list
	 */
	public List<YrWetherData> getList() {
		return mList;
	}

	/**
	 * @param list the list to set
	 */
	public void setList(ArrayList<YrWetherData> list) {
		mList = list;
	}
	
}
