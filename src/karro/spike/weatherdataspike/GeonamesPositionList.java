/**
 * 
 */
package karro.spike.weatherdataspike;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.*;

/**
 * @author Karro
 *
 */
@Root(strict=false)
public class GeonamesPositionList {

	@ElementList(name="geonames")
	private List<GeonamesPosition> list;

	/**
	 * @return the list
	 */
	public List<GeonamesPosition> getList() {
		return list;
	}

	/**
	 * @param list the list to set
	 */
	public void setList(List<GeonamesPosition> list) {
		this.list = list;
	}

	public ArrayList<GeonamesPosition> toArrayList(){
		
		
		ArrayList<GeonamesPosition> lista = new ArrayList<GeonamesPosition>();
		lista.addAll(list);
		return lista;
		
	}

	public void add(GeonamesPosition read) {
		list.add(read);
		
	}
}
