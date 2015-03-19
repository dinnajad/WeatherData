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
@Root(strict= false, name="credit")
public class YrCredit {
	
@Element
private YrLink link;

/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Override
public String toString() {
	return  link.toString();
}

/**
 * @return the link
 */
public YrLink getLink() {
	return link;
}

/**
 * @param link the link to set
 */
public void setLink(YrLink link) {
	this.link = link;
}
}
