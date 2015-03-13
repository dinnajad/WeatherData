/**
 * 
 */
package karro.spike.weatherdataspike.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**Holds all information about an alarm
 * @author Karro
 *
 */
@Root(strict=false)
public class Alarm {

	/***
	 * parameter is if it is temperature, wind, rain etc.
	 */
	@Element
	private String parameter;
	@Element
	private String logicOperator;
	@Element
	private String limit;//TODO börjar med String sen får vi ändra till det numeriska som passar bäst
	@Element
	private String limit2;
	
	public Alarm(String parameter, String logicOperator, String limit,
			String limit2) {
		super();
		this.parameter = parameter;
		this.logicOperator = logicOperator;
		this.limit = limit;
		this.limit2 = limit2;
	}
	
	public Alarm(){}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Alarm när[parameter=" + parameter + " är "
				+ logicOperator + ", limit=" + limit + " och eventuellt limit2=" + limit2
				+ "]";//TODO improve ToString
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
