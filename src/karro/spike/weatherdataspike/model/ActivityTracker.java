/**
 * 
 */
package karro.spike.weatherdataspike.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/** Tracks if any activity in this app is active
 * @author Karro
 *
 */
public class ActivityTracker {

	/***
	 * checks if the app is active
	 * @return
	 */
	public static boolean isAppActive(Context context){
		boolean active = false;

		SharedPreferences sp = context.getSharedPreferences("OURINFO", Context.MODE_PRIVATE);
		boolean main = sp.getBoolean("MainActive", false);
		boolean alarm = sp.getBoolean("AlarmActive", false);
		boolean alarmList = sp.getBoolean("AlarmlistActive", false);
		boolean warninglist = sp.getBoolean("WeatherWarninglistActive", false);
		Log.i("Activitytracker", main+": "+alarm+": "+ alarmList+": "+warninglist);
		if(main||alarm||alarmList||warninglist){//om någon av activity är aktiva
			active=true;
		}
		return active;

	}
}
