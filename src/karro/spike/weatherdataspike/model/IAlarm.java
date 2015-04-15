package karro.spike.weatherdataspike.model;

/***
 * interface that defines how an IAlarm should be.  
 * @author Karro
 *
 */
public interface IAlarm {

	/***
	 * 
	 * @return true If the OneDayWeatherData triggers the alarm
	 */
	public abstract boolean checkAlarm(OneDayWeatherData data);
	public abstract String getAlarmMessage();
	public String toString();
	public void setAlarm(Alarm alarm);
	public Alarm getAlarm();
}