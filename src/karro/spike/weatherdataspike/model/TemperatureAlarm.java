/**
 * 
 */
package karro.spike.weatherdataspike.model;

import android.util.Log;

/**
 * @author Karro
 *
 */
public class TemperatureAlarm implements IAlarm {

	private static final String TAG = "TemperatureAlarm";
	private Alarm alarm;

	@Override
	public boolean checkAlarm(OneDayWeatherData data) {
		float limit = Float.valueOf(alarm.getLimit());
		if(alarm.getLogicOperator()=="Under"){
			if(data.getMintemperature()<limit){
				//ALARM RIIIIIING
				return true;
			}
		}else if(alarm.getLogicOperator()=="Över"){
			if(data.getMaxTemperature()>limit){
				//ALARM RIIIIIING
				return true;
			}
		}else{
			Log.e(TAG, "Alarm fungerar inte : Okänd kombination " + alarm.toString());
			return false;
		}
		return false;
	}
	
	@Override
	public String getAlarmMessage() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setAlarm(Alarm alarm) {
		this.alarm = alarm;		
	}

	@Override
	public Alarm getAlarm() {
		return alarm;
	}

	@Override
	public String toString(){
	return alarm.toString();
}



}
