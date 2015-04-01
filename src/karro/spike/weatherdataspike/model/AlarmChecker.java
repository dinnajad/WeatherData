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
		ArrayList<IAlarm> alarms = new ArrayList<IAlarm>();
		//börjar med hårdkodat alarm: om temperaturen är under 0 grader
		//TODO använda de alarm användaren skapat!!!
		TemperatureAlarm tempAlarm = new TemperatureAlarm();
		Alarm alarm = new Alarm();
		alarm.setLimit("-10");
		alarm.setLogicOperator("Över");
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
		
		alarms.add(tempAlarm2);
		
		//för varje dag
		for (OneDayWeatherData oneDayWeatherData : dataPerDay) {
			//kolla varje alarm

			for (IAlarm iAlarm : alarms) {		
				if(iAlarm.checkAlarm(oneDayWeatherData)){
					//ALARM RIIIIIING
					SendNotification(iAlarm.getAlarmMessage());
				}
			}

		}
	}

	/***
	 * Sends a Notification with a custom message
	 * @param msg
	 */
	protected void SendNotification(String msg){
		Intent i = new Intent(context, MainActivity.class);
		PendingIntent pi= PendingIntent.getActivity(context, 0, i,PendingIntent.FLAG_UPDATE_CURRENT);
		numberOfNotificationsSent++;
		if(msg==null){msg="";}
		NotificationCompat.Builder mBuilder= new NotificationCompat.Builder(context);

		mBuilder.setContentTitle("FrostVarning").setSmallIcon(R.drawable.thermometer_snowflake)
		.setContentText("Du har "+ numberOfNotificationsSent + " vädervarningar "+ context.getString(karro.spike.weatherdata.R.string.frost))//TODO hur få ut texten och inte int värdet
		/*
		 * Sets the big view "big text" style and supplies the
		 * text that will be displayed
		 * in the detail area of the expanded notification.
		 * These calls are ignored by the support library for
		 * pre-4.1 devices.
		 */
		.setStyle(new NotificationCompat.BigTextStyle()
		.bigText(msg+" .."+ context.getString(karro.spike.weatherdata.R.string.frost_warning_big)
				+ " Du har "+ numberOfNotificationsSent + " vädervarningar"))
				.setAutoCancel(true)
				.setContentIntent(pi);
		NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

		manager.notify(lastnotificationNumber, mBuilder.build());
		//TODO samla notifieringarna och visa i min app också
	}
}
