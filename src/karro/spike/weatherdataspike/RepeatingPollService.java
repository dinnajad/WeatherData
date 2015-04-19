/**
 * 
 */
package karro.spike.weatherdataspike;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * @author Karro
 *
 */
public class RepeatingPollService extends PollService {

	private static final String TAG = "RepeatingPollService";
	private static final long POLL_INTERVAL = 1000 * 60 * 60 * 24/4 ;// 4 ggr per dygn
	public RepeatingPollService(){
		super(TAG);	
	}

	/* sköts i PollService
	@Override
	protected void onHandleIntent(Intent intent) {
		super.onHandleIntent(intent);
		Log.i(TAG, "onHandleIntent i repetarn");
	}
*/
	/***
	 *  Method to start the pollservice reguraly
	 * @param context
	 * @param isOn
	 */
	public static void setServiceAlarm(Context context, boolean isOn){
		Log.i(TAG, "setServiceAlarm i repeatern");
		Intent i = new Intent(context, RepeatingPollService.class);
		PendingIntent pi= PendingIntent.getService(context, 0, i, 0);

		AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

		if(isOn){
			Log.i(TAG, "setServiceAlarm setting repeating alarm");
			manager.setRepeating(AlarmManager.RTC, System.currentTimeMillis(), POLL_INTERVAL, pi);
			//manager.set(AlarmManager.RTC, System.currentTimeMillis(), pi);
		}else{
			Log.i(TAG, "setServiceAlarm cancelling repeating alarm");
			manager.cancel(pi);
			pi.cancel();
			Log.i(TAG, "setServiceAlarm cancelled repeating alarm");
		}
	}

	/***
	 * returns true if an alarm exists 
	 * @param context
	 * @return
	 */
	public static boolean isServiceAlarmOn(Context context){
		Intent i = new Intent(context, RepeatingPollService.class);
		PendingIntent pi = PendingIntent.getService(context, 0, i, PendingIntent.FLAG_NO_CREATE);
		return pi !=null;
	}

}
