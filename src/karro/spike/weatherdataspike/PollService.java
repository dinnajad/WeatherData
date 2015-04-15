/**
 * 
 */
package karro.spike.weatherdataspike;

import java.io.FileNotFoundException;

import karro.spike.weatherdataspike.YR.SimpleYrFetcher;
import karro.spike.weatherdataspike.YR.YrRootWeatherData;
import karro.spike.weatherdataspike.model.ActivityTracker;
import karro.spike.weatherdataspike.model.AlarmChecker;
import karro.spike.weatherdataspike.model.ForecastKeeper;
import karro.spike.weatherdataspike.model.IPosition;
import karro.spike.weatherdataspike.model.PositionKeeper;
import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;
import android.widget.Toast;

/**Pollservice that handles getting data from YR
 * @author Karro
 *
 */
public class PollService extends IntentService {

	private static final String TAG = "PollService";
	private static final long POLL_INTERVAL = 1000 * 60 * 60 * 24/4 ;// 4 ggr per dygn
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
			Toast.makeText(getApplicationContext(), "No Network Avaliable", Toast.LENGTH_SHORT).show();
			return;
		}
		
		try {
			storage = ForecastKeeper.readFromFile(getApplicationContext());
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
		
		YrRootWeatherData prediction;
		if( pos!=null){
			
			String s=pos.getCountryName()+"/"+ pos.getRegion()+"/"+pos.getName();
			prediction= new SimpleYrFetcher().fetchForecast(s);
		}else{
		prediction = new SimpleYrFetcher().fetchForecast();
		}
		storage.saveRootData(prediction);
		storage.saveForecast(prediction.getForeCast());
		storage.saveToPersistanse(getApplicationContext());
		storage.groupDataPerDay();
		
		alarmChecker = new AlarmChecker(getApplicationContext());
		alarmChecker.checkAlarms(storage.getDataPerDay());
		
		if(ActivityTracker.isAppActive(getApplicationContext())){// add check if app is active? yes Dont want to open app if its not open
		Log.i(TAG, "sending new MainActivityIntent");
		
		Intent mainActivity = new Intent(this,MainActivity.class);
		mainActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(mainActivity);
		Toast.makeText(getApplicationContext(), "Data hämtat", Toast.LENGTH_LONG).show();
		}
		Log.i(TAG, "OnHandleIntent ended " );
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
		manager.set(AlarmManager.RTC, System.currentTimeMillis(), pi);
		Log.v(TAG,"I setOneTimeServiceAlarm "+ isOn);
		if(isOn){
			
			manager.setRepeating(AlarmManager.RTC, System.currentTimeMillis(), POLL_INTERVAL, pi);
		}
	}
	
	/***
	 *  Method to start the pollservice reguraly
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
