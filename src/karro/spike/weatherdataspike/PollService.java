/**
 * 
 */
package karro.spike.weatherdataspike;

import java.io.FileNotFoundException;

import karro.spike.weatherdataspike.YR.SimpleYrFetcher;
import karro.spike.weatherdataspike.YR.YrRootWeatherData;
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
import android.net.NetworkInfo;
import android.util.Log;

/**Pollservice that handles getting data from YR
 * @author Karro
 *
 */
public class PollService extends IntentService {

	private static boolean isRepeatingAlarmActive = false;
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
		boolean networkAvaliable = true;

		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		if(cm==null){
			networkAvaliable = false;
		}else{
			NetworkInfo info = cm.getActiveNetworkInfo();
			if(info==null){// case flightmode
				networkAvaliable = false;
			}else{
				if(!info.isAvailable()){ 	
					networkAvaliable = false;
				}
			}
		}

		if(!networkAvaliable){
			if(MyLifecycleHandler.isApplicationVisible()){
				Intent mainActivity = new Intent(this,MainActivity.class);
				mainActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				mainActivity.putExtra("MESSAGE", "Inget nätverk tillgängligt");
				startActivity(mainActivity);
			}
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
			prediction = new SimpleYrFetcher().fetchForecast();//gets fallback on hardcoded position
		}
		storage.saveRootData(prediction);
		storage.saveForecast(prediction.getForeCast());
		storage.saveToPersistanse(getApplicationContext());
		storage.groupDataPerDay();

		alarmChecker = new AlarmChecker(getApplicationContext());
		alarmChecker.checkAlarms(storage.getDataPerDay());

		//if(!ActivityTracker.isAppActive(getApplicationContext())){// add check if app is active? yes Dont want to open app if its not open
		if(MyLifecycleHandler.isApplicationVisible()){
			/*Log.i(TAG, "sending new MainActivityIntent"); // egentligen den kod jag vill använda men eftersom det ännu inte finns nån indikator på hur många varningar jag har på förstasidan skickar jag till warningarna istället

		Intent mainActivity = new Intent(this,MainActivity.class);
		mainActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		mainActivity.putExtra("MESSAGE", "Data hämtat");
		startActivity(mainActivity);*/
			Log.i(TAG, "Sending warnings Intent");
			Intent mainActivity = new Intent(this,WeatherWarningActivity.class);
			mainActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);			
			startActivity(mainActivity);
			//Toast.makeText(getApplicationContext(), "Data hämtat", Toast.LENGTH_LONG).show(); //av nån anledning kan den här Toasten fastna på skärmen så att den lever kvar långt efter attt servicen är död
		}
		Log.i(TAG, "OnHandleIntent ended " );
	}


	/***
	 * Method to start the pollservice once, at once.  eg to get data now. a refresh 
	 * @param context
	 * @param shouldStartRepeatingAgain
	 */
	public static void setOneTimeServiceAlarm(Context context, boolean shouldStartRepeatingAgain){
		Intent i = new Intent(context, PollService.class);
		PendingIntent pi= PendingIntent.getService(context, 0, i, 0);

		AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		manager.set(AlarmManager.RTC, System.currentTimeMillis(), pi);
		Log.v(TAG,"I setOneTimeServiceAlarm plus repeterar "+ shouldStartRepeatingAgain);
		if(shouldStartRepeatingAgain){

			manager.setRepeating(AlarmManager.RTC, System.currentTimeMillis(), POLL_INTERVAL, pi);
			isRepeatingAlarmActive = true;
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
			isRepeatingAlarmActive = true;
		}else{
			manager.cancel(pi);
			pi.cancel();
			isRepeatingAlarmActive = false;
		}
	}

	/***
	 * returns true if an alarm exists 
	 * @param context
	 * @return
	 */
	public static boolean isServiceAlarmOn(Context context){
		return isRepeatingAlarmActive;
		/*
		Intent i = new Intent(context, PollService.class);
		PendingIntent pi = PendingIntent.getService(context, 0, i, PendingIntent.FLAG_NO_CREATE);
		return pi !=null;
		*/		 
	}

}
