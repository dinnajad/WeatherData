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
	private int lastnotificationNumber;//TODO make this a variable, stored in keeper? to not resend messages every time data updates?
	private int numberOfNotificationsSent;
	private ForecastKeeper keeper;
	
	
	public AlarmChecker(Context context){
		this.context= context;
		lastnotificationNumber = 001;
	}
	
	public void verifyAlarms(ArrayList<OneDayWeatherData> dataPerDay) {
		ArrayList<IAlarm> alarms = new ArrayList<IAlarm>();
		//börjar med hårdkodat alarm: om temperaturen är under 0 grader
		TemperatureAlarm tempAlarm = new TemperatureAlarm();
		Alarm alarm = new Alarm();
		alarm.setLimit("0");
		alarm.setLogicOperator("Under");
		alarm.setParameter("Temperaturen");
		tempAlarm.setAlarm(alarm);
		alarms.add(tempAlarm);
		
		//för varje dag
		for (OneDayWeatherData oneDayWeatherData : dataPerDay) {
			//kolla varje alarm
			
		for (IAlarm iAlarm : alarms) {		
			if(iAlarm.checkAlarm(oneDayWeatherData)){
				SendNotification(iAlarm.getAlarmMessage());
			}
		}
			/*
			if(oneDayWeatherData.getMintemperature()<0){// kan man använda assert?
				//ALARM RIIIIIING
				SendNotification(oneDayWeatherData.getMinTemperatureString());
			}*/
		}
	}

	protected void SendNotification(String msg){
		Intent i = new Intent(context, MainActivity.class);
		PendingIntent pi= PendingIntent.getActivity(context, 0, i,PendingIntent.FLAG_UPDATE_CURRENT);
		numberOfNotificationsSent++;

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
