/**
 * 
 */
package karro.spike.weatherdataspike.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**Holds all information about an alarm (enbart DataObjekt) 
 * @author Karro
 *
 */
@Root(strict=false)
public class Alarm  {

	/***
	 * parameter is if it is temperature, wind, rain etc.
	 */
	@Element
	private String parameter;
	@Element
	private String logicOperator;
	@Element(required=false)
	private String limit;//TODO hur se till att den aldrig är annat än float?
	@Element(required=false)
	private String limit2;
	
	@Element(required=false)
	private boolean isActive= true;
	
	public Alarm(String parameter, String logicOperator, String limit) {
		super();
		this.parameter = parameter;
		this.logicOperator = logicOperator;
		this.limit = limit;
		}
	
	public Alarm(){}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Alarm när "+ parameter + " är "
				+ logicOperator + " " + limit;
	}
	
	/**
	 * @return the parameter
	 */
	public String getParameter() {
		return parameter;
	}
	/**
	 * @param parameter the parameter to set
	 */
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	/**
	 * @return the logicOperator
	 */
	public String getLogicOperator() {
		return logicOperator;
	}
	/**
	 * @param logicOperator the logicOperator to set
	 */
	public void setLogicOperator(String logicOperator) {
		this.logicOperator = logicOperator;
	}
	/**
	 * @return the limit
	 */
	public String getLimit() {
		return limit;
	}
	/**
	 * @param limit the limit to set
	 */
	public void setLimit(String limit) {
		this.limit = limit;
	}
	/**
	 * @return the isActive
	 */
	public boolean isActive() {
		return isActive;
	}

	/**
	 * @param isActive the isActive to set
	 */
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	/**
	 * @return the limit2
	 */
	public String getLimit2() {
		return limit2;
	}
	/**
	 * @param limit2 the limit2 to set
	 */
	public void setLimit2(String limit2) {
		this.limit2 = limit2;
	}
	
	
	
	
	
}
