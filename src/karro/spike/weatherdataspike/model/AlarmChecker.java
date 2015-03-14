/**
 * 
 */
package karro.spike.weatherdataspike.model;

import java.util.ArrayList;

import karro.spike.weatherdataspike.MainActivity;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

/**
 * @author Karro
 *
 */
public class AlarmChecker {
	private Context context;
	private int lastnotificationNumber;//TODO make this a variable, stored in keeper? to not resend messages every time data updates?
	private int numberOfNotificationsSent;
	
	
	public AlarmChecker(Context context){
		this.context= context;
		lastnotificationNumber = 001;
	}
	public void verifyAlarms(ArrayList<OneDayWeatherData> dataPerDay) {
		//börjar med hårdkodat alarm: om temperaturen är under 0 grader
		//TODO generalisera 
		for (OneDayWeatherData oneDayWeatherData : dataPerDay) {

			if(oneDayWeatherData.getMintemperature()<0){// kan man använda assert?
				//ALARM RIIIIIING
				SendNotification(oneDayWeatherData.getMinTemperatureString());
			}

		}

	}

	protected void SendNotification(String msg){
		Intent i = new Intent(context, MainActivity.class);
		PendingIntent pi= PendingIntent.getActivity(context, 0, i,PendingIntent.FLAG_UPDATE_CURRENT);
		numberOfNotificationsSent++;

		NotificationCompat.Builder mBuilder= new NotificationCompat.Builder(context);

		mBuilder.setContentTitle("FrostVarning").setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
		.setContentText(msg+" .."+ " Inatt sjunker temperaturen under 0 grader, skydda alla känsliga växter!"
		+ "/n"+ "se övriga "+ numberOfNotificationsSent + "vädervarningar")
		.setContentIntent(pi);
		NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

		manager.notify(lastnotificationNumber, mBuilder.build());

	}
}
