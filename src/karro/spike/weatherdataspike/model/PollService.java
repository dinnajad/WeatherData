/**
 * 
 */
package karro.spike.weatherdataspike.model;

import java.io.FileNotFoundException;

import karro.spike.weatherdataspike.MainActivity;
import karro.spike.weatherdataspike.YR.Forecast;
import karro.spike.weatherdataspike.YR.SimpleYrFetcher;
import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * @author Karro
 *
 */
public class PollService extends IntentService {

	private static final String TAG = "PollService";
	private static final long POLL_INTERVAL = 1000 * 15 *60; //15 sec*60
	private ForecastKeeper storage; 
	private PositionKeeper storedPositions;
	
	private String fileName = "ForeCast.xml";
	private AlarmChecker alarmChecker;
	public PollService(){
	 super(TAG);	
	}
	
	/* (non-Javadoc)
	 * @see android.app.IntentService#onHandleIntent(android.content.Intent)
	 */
	@Override
	protected void onHandleIntent(Intent intent) {
		Log.i(TAG, "recived an intent: "+ intent);
		
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		if(!cm.getActiveNetworkInfo().isAvailable()){ 		
			return;
		}
		
		try {
			storage = ForecastKeeper.readFromFile(getApplicationContext(), fileName);
			storedPositions = PositionKeeper.readFromFile(getApplicationContext());
		} catch (FileNotFoundException e) {
			//ignore but handle later by creating new Forecastkeeper
		}	
		
		if(storage==null){
			storage = new ForecastKeeper();
		}
		if(storedPositions == null){
			storedPositions = new PositionKeeper();
		}
		
		IPosition pos = storedPositions.getFavouritePosition();
		Forecast prediction;
		if( pos!=null){
			
			String s= pos.getRegion()+"/"+pos.getName();
			prediction= new SimpleYrFetcher().fetchForecast(s);
		}else{
		prediction = new SimpleYrFetcher().fetchForecast();
		}
		storage.saveForecast(prediction);
		storage.saveToPersistanse(getApplicationContext(), fileName);
		storage.groupDataPerDay();
		
		alarmChecker = new AlarmChecker(getApplicationContext());
		alarmChecker.verifyAlarms(storage.getDataPerDay());
		//SendNotification();
	}
	
	protected void SendNotification(){
		Intent i = new Intent(getApplicationContext(), MainActivity.class);
		PendingIntent pi= PendingIntent.getService(getApplicationContext(), 0, i,PendingIntent.FLAG_UPDATE_CURRENT);
		
		int lastnotificationNumber = 001;
		NotificationCompat.Builder mBuilder= new NotificationCompat.Builder(getApplicationContext());
		
		mBuilder.setContentTitle("FrostVarning").setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
			.setContentText("Inatt sjunker temperaturen under 0 grader, skydda alla känsliga växter!")
		.setContentIntent(pi);
		NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		
		manager.notify(lastnotificationNumber++, mBuilder.build());
		
	}
	
	/***
	 * Method to start the pollservice once, at once.  eg to get data now. a refresh 
	 * @param context
	 * @param isOn
	 */
	public static void setOneTimeServiceAlarm(Context context, boolean isOn){
		Intent i = new Intent(context, PollService.class);
		PendingIntent pi= PendingIntent.getService(context, 0, i, 0);
		
		AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		//TODO currently this cancels the repeating alarm, how dont?
		if(isOn){
			manager.set(AlarmManager.RTC, System.currentTimeMillis(), pi);
		}else{
			manager.cancel(pi);
			pi.cancel();
		}
	}
	
	/***
	 * 
	 * @param context
	 * @param isOn
	 */
	public static void setServiceAlarm(Context context, boolean isOn){
		Intent i = new Intent(context, PollService.class);
		PendingIntent pi= PendingIntent.getService(context, 0, i, 0);
		
		AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		
		if(isOn){
			manager.setRepeating(AlarmManager.RTC, System.currentTimeMillis(), POLL_INTERVAL, pi);
		}else{
			manager.cancel(pi);
			pi.cancel();
		}
	}
	
	/***
	 * returns true if an alarm exists 
	 * @param context
	 * @return
	 */
	 public static boolean isServiceAlarmOn(Context context){
		Intent i = new Intent(context, PollService.class);
		PendingIntent pi = PendingIntent.getService(context, 0, i, PendingIntent.FLAG_NO_CREATE);
		return pi !=null;		 
	 }

}
