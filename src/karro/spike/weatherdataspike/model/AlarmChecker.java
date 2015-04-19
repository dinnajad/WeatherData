/**
 * 
 */
package karro.spike.weatherdataspike.model;

import java.util.ArrayList;

import karro.spike.weatherdata.R;
import karro.spike.weatherdataspike.MainActivity;
import karro.spike.weatherdataspike.MyLifecycleHandler;
import karro.spike.weatherdataspike.WeatherWarningActivity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

/**checks alarms and issues warnings as a result.
 * @author Karro
 *
 */
public class AlarmChecker {
	private static final String TAG = "AlarmChecker";

	private Context context;
	private int lastnotificationNumber;
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
	public void checkAlarms(ArrayList<OneDayWeatherData> dataPerDay) {
		AlarmKeeper keeper = AlarmKeeper.readFromFile(context);

		ArrayList<IAlarm> alarms = keeper.getAlarms();
		//Log.i(TAG,"Antal alarm: " + alarms.size());

		warningKeeper = WeatherWarningKeeper.readFromFile(context);
		warningKeeper.clearWarnings();
		//för varje dag
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

//		if(!ActivityTracker.isAppActive(context)){
//			SendNotification(iAlarm.getAlarmMessage());
//		}	
		if(!MyLifecycleHandler.isApplicationVisible()){
			SendNotification(iAlarm.getAlarmMessage());}
	}

	/***
	 * Sends a Notification with a custom message
	 * @param msg
	 */
	protected void SendNotification(String msg){
		Intent i = new Intent(context, WeatherWarningActivity.class);
		//Intent i = new Intent(context, MainActivity.class);
//		TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
//		// Adds the back stack
//		stackBuilder.addParentStack(MainActivity.class);
//		// Adds the Intent to the top of the stack
//		stackBuilder.addNextIntent(i);
//		// Gets a PendingIntent containing the entire back stack
//		PendingIntent pi =
//		        stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
		PendingIntent pi= PendingIntent.getActivity(context, 0, i,PendingIntent.FLAG_UPDATE_CURRENT);
		numberOfNotificationsSent++;
		if(msg==null){
			msg=context.getString(karro.spike.weatherdata.R.string.frost);
		}

		NotificationCompat.Builder mBuilder= new NotificationCompat.Builder(context);

		mBuilder.setContentTitle("FrostVarning").setSmallIcon(R.drawable.thermometer_snowflake)
		.setContentText("Du har "+ numberOfNotificationsSent + " vädervarningar "+ msg)
		/*
		 * Sets the big view "big text" style and supplies the
		 * text that will be displayed
		 * in the detail area of the expanded notification.
		 * These calls are ignored by the support library for
		 * pre-4.1 devices.
		 */
		.setStyle(new NotificationCompat.BigTextStyle()
		.bigText(" Du har "+ numberOfNotificationsSent + " vädervarningar"+ msg))
				.setAutoCancel(true)
				.setContentIntent(pi);
		NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

		manager.notify(lastnotificationNumber, mBuilder.build());		
	}

	
	
	/***
	 * getter for ForcastKeeper
	 * @return
	 */
	public ForecastKeeper getKeeper() {
		return keeper;
	}

	/****
	 * Setter fro ForcastKeeper
	 * @param keeper
	 */
	public void setKeeper(ForecastKeeper keeper) {
		this.keeper = keeper;
	}

	/***
	 * getter for WarningKeeper
	 * @return
	 */
	public WeatherWarningKeeper getWarningKeeper() {
		if(warningKeeper==null){
			warningKeeper = WeatherWarningKeeper.readFromFile(context);
		}
		return warningKeeper;
	}

	/***
	 * Setter for WarningKeeper
	 * @param warningKeeper
	 */
	public void setWarningKeeper(WeatherWarningKeeper warningKeeper) {
		this.warningKeeper = warningKeeper;
	}
}
