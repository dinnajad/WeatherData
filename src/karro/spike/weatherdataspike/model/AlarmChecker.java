/**
 * 
 */
package karro.spike.weatherdataspike.model;

import java.util.ArrayList;

import karro.spike.weatherdata.R;
import karro.spike.weatherdataspike.MainActivity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * @author Karro
 *
 */
public class AlarmChecker {
	private static final String TAG = "AlarmChecker";

	private Context context;
	private int lastnotificationNumber;// make this a variable, stored in keeper? to not resend messages every time data updates?
	private int numberOfNotificationsSent;
	private ForecastKeeper keeper;
	private WeatherWarningKeeper warningKeeper;

	/***
	 * Checks alarms 
	 * @param context
	 */
	public AlarmChecker(Context context){
		this.context= context;
		lastnotificationNumber = 001;
	}

	/***
	 * checks the alarms
	 * @param dataPerDay
	 */
	public void verifyAlarms(ArrayList<OneDayWeatherData> dataPerDay) {
		AlarmKeeper keeper = AlarmKeeper.readFromFile(context);
		//ArrayList<IAlarm> alarms = new ArrayList<IAlarm>();
		ArrayList<IAlarm> alarms = keeper.getAlarms();
		//Log.i(TAG,"Antal alarm ut�ver de tv� h�rdkodade: " + alarms.size());
		//b�rjar med h�rdkodat alarm: om temperaturen �r under 0 grader
		
		/*TemperatureAlarm tempAlarm = new TemperatureAlarm();
		Alarm alarm = new Alarm();
		alarm.setLimit("-10");
		alarm.setLogicOperator("�ver");
		alarm.setParameter("Temperaturen");
		alarm.setMessage("EttKalltMeddelande");
		tempAlarm.setAlarm(alarm);
		alarms.add(tempAlarm);
		TemperatureAlarm tempAlarm2 = new TemperatureAlarm();
		Alarm alarm2 = new Alarm();
		alarm2.setLimit("10");
		alarm2.setLogicOperator("Under");
		alarm2.setParameter("Temperaturen");
		alarm2.setMessage("kolla ett meddelande!");
		tempAlarm2.setAlarm(alarm2);

		alarms.add(tempAlarm2);*/
		  warningKeeper = WeatherWarningKeeper.readFromFile(context);
		  warningKeeper.clearWarnings();
		//f�r varje dag
		for (OneDayWeatherData oneDayWeatherData : dataPerDay) {
			//kolla varje alarm
			Log.i(TAG, "oneDayWeatherData: " + oneDayWeatherData.toString());
			for (IAlarm iAlarm : alarms) {	
				
				if(iAlarm.getAlarm().isActive()){//TODO add check if alarm is null
					if(iAlarm.checkAlarm(oneDayWeatherData)){
						//ALARM RIIIIIING
						createWarning(iAlarm, oneDayWeatherData);
					}
				}
			}

		}
		warningKeeper.saveToPersistence(context);
		Log.i(TAG, "returning from verifyAlarms");
		
	}

	/***
	 * Creates the appropriate warning, notification if the app is not active
	 * @param iAlarm
	 * @param oneDayWeatherData 
	 */
	private void createWarning(IAlarm iAlarm, OneDayWeatherData oneDayWeatherData) {
		WeatherWarning warning = new WeatherWarning();
		warning.setAlarm(iAlarm);
		warning.setMessage(iAlarm.getAlarmMessage());
		warning.setOneDayWeatherData(oneDayWeatherData);
		warningKeeper.addWarning(warning);
		
		/*if(!isAppActive()){
			SendNotification(iAlarm.getAlarmMessage());
		}*/	
		//TODO byt till koden ovan och testa!!
		SendNotification(iAlarm.getAlarmMessage());
		}

	/***
	 * Sends a Notification with a custom message
	 * @param msg
	 */
	protected void SendNotification(String msg){
		Intent i = new Intent(context, MainActivity.class);
		PendingIntent pi= PendingIntent.getActivity(context, 0, i,PendingIntent.FLAG_UPDATE_CURRENT);
		numberOfNotificationsSent++;
		if(msg==null){
			msg=context.getString(karro.spike.weatherdata.R.string.frost);
			}
		
		NotificationCompat.Builder mBuilder= new NotificationCompat.Builder(context);

		mBuilder.setContentTitle("FrostVarning").setSmallIcon(R.drawable.thermometer_snowflake)
		.setContentText("Du har "+ numberOfNotificationsSent + " v�dervarningar "+ msg)
		/*
		 * Sets the big view "big text" style and supplies the
		 * text that will be displayed
		 * in the detail area of the expanded notification.
		 * These calls are ignored by the support library for
		 * pre-4.1 devices.
		 */
		.setStyle(new NotificationCompat.BigTextStyle()
		.bigText(msg+" .."+ context.getString(karro.spike.weatherdata.R.string.frost_warning_big)
				+ " Du har "+ numberOfNotificationsSent + " v�dervarningar"))
				.setAutoCancel(true)
				.setContentIntent(pi);
		NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

		manager.notify(lastnotificationNumber, mBuilder.build());		
	}

	/***
	 * checks if the app is active
	 * @return
	 */
	public boolean isAppActive(){
		boolean active = false;

		SharedPreferences sp = context.getSharedPreferences("OURINFO", Context.MODE_PRIVATE);
		boolean main = sp.getBoolean("MainActive", false);
		boolean alarm = sp.getBoolean("AlarmActive", false);
		boolean alarmList = sp.getBoolean("AlarmlistActive", false);
		boolean warninglist = sp.getBoolean("WeatherWarninglistActive", false);
		if(main||alarm||alarmList||warninglist){//om n�gon av activity �r aktiva
			active=true;
		}
		return active;

	}

	public ForecastKeeper getKeeper() {
		return keeper;
	}

	public void setKeeper(ForecastKeeper keeper) {
		this.keeper = keeper;
	}

	public WeatherWarningKeeper getWarningKeeper() {
		if(warningKeeper==null){
			warningKeeper = WeatherWarningKeeper.readFromFile(context);
		}
		return warningKeeper;
	}

	public void setWarningKeeper(WeatherWarningKeeper warningKeeper) {
		this.warningKeeper = warningKeeper;
	}
}
