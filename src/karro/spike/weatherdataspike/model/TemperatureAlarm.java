/**
 * 
 */
package karro.spike.weatherdataspike.model;

import android.util.Log;

/** specialised alarm that deals with temperature
 * @author Karro
 *
 */
public class TemperatureAlarm implements IAlarm {

	private static final String TAG = "TemperatureAlarm";
	private Alarm alarm;

	@Override
	public boolean checkAlarm(OneDayWeatherData data) {
		boolean alarmTriggered = false;
		float limit = Float.valueOf(alarm.getLimit());
		if( alarm.getLogicOperator().equals("Under")){
			if(data.getMintemperature()<limit){
				//ALARM RIIIIIING
				alarmTriggered = true;
				Log.i(TAG, alarm.toString() + " < " + data.getMintemperature());
			}
		}else if(alarm.getLogicOperator().equals("Över")){
			if(data.getMaxTemperature()>limit){
				//ALARM RIIIIIING
				alarmTriggered = true;
				Log.i(TAG, alarm.toString() + " > " + data.getMaxTemperature());
			}
		}else{
			Log.e(TAG, "Alarm fungerar inte : Okänd kombination " + alarm.toString());
			alarmTriggered = false;
		}
		return alarmTriggered;
	}

	/***
	 * returns the custom message
	 */
	@Override
	public String getAlarmMessage() {

		return alarm.getLimit()+ " " + alarm.getMessage();
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
