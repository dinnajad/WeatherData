/**
 * 
 */
package karro.spike.weatherdataspike.model;

import karro.spike.weatherdataspike.YR.SimpleYrFetcher;
import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;

/**
 * @author Karro
 *
 */
public class PollService extends IntentService {

	private static final String TAG = "PollService";
	private static final long POLL_INTERVAL = 1000 * 15 ; //15 sec
	private ForecastKeeper storage; 
	private String fileName = "ForeCast.xml";
	
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
		
		storage = ForecastKeeper.readFromFile(getApplicationContext(), fileName);
		//hämta väderdata //TODO lägg till för alla sparade positioner och spara datat nånstans
		
		if(storage==null){
			storage = new ForecastKeeper();
			}
		
		storage.saveForecast(new SimpleYrFetcher().fetchForecast());
		storage.saveToPersistanse(getApplicationContext(), fileName);
	}
	
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
