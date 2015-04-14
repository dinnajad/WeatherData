/**
 * 
 */
package karro.spike.weatherdataspike.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * DataObject that holds info about a weatherWarning
 * @author Karro
 *
 */
@Root(strict=false)
public class WeatherWarning {
	@Element(required=false)
	private String message ;
	@Element
	private IAlarm alarm;
	@Element
	private OneDayWeatherData oneDayWeatherData;
	
	public String toString(){
		return message + alarm.toString();		
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public IAlarm getAlarm() {
		return alarm;
	}
	public void setAlarm(IAlarm alarm) {
		this.alarm = alarm;
	}
	public OneDayWeatherData getOneDayWeatherData() {
		return oneDayWeatherData;
	}
	public void setOneDayWeatherData(OneDayWeatherData oneDayWeatherData) {
		this.oneDayWeatherData = oneDayWeatherData;
	}

}
