/**
 * 
 */
package karro.spike.weatherdataspike;

import karro.spike.weatherdata.R;
import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * @author Karro
 *
 */
/*public class MainMenu {

	*//**
	 * @param item
	 *//*
	private void handleMenuClick(MenuItem item, Context context ) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		if(id==R.id.action_alarm){
			//Toast.makeText(getApplicationContext(), "nyttAlarm", Toast.LENGTH_LONG).show();
			//skapa ny aktivitet
			Intent alarm = new Intent(context ,AlarmActivity.class);
			startActivityForResult(alarm, 0);
			//startActivity(alarm);

		}else if(id==R.id.action_my_alarms){
			//Toast.makeText(getApplicationContext(), "Mina Alarm", Toast.LENGTH_LONG).show();
			//skapa ny aktivitet

			Intent alarms = new Intent(context,AlarmListActivity.class);
			startActivity(alarms);
		}else if(id==R.id.action_toggle_poll){

			boolean shouldStartAlarm = !PollService.isServiceAlarmOn(context);
			PollService.setServiceAlarm(context, shouldStartAlarm);
			Toast.makeText(context, "toggle poll " +shouldStartAlarm, Toast.LENGTH_LONG).show();
			invalidateOptionsMenu();

		}else if(id==R.id.action_refresh_data){			
			boolean shouldStartAlarm = PollService.isServiceAlarmOn(context);//differs from set repeting one above here we want to restart it if it should be on 
			PollService.setOneTimeServiceAlarm(context, shouldStartAlarm);
			Toast.makeText(context, "hämtar data nu", Toast.LENGTH_LONG).show();

		}else if(id==R.id.action_position){
			Toast.makeText(context, "Min position", Toast.LENGTH_SHORT).show();
			PositionPollService.setOneTimeServiceAlarm(context,true);
			
		}else if(id==R.id.action_weatherWarnings){
			Toast.makeText(context, "Warnings", Toast.LENGTH_SHORT).show();
			Intent warnings = new Intent(context,WeatherWarningActivity.class);			
			startActivity(warnings);
		
		}
		if (id == R.id.action_settings) {
		// proper settings Implementation
		Toast.makeText(getApplicationContext(), "Inställningar", Toast.LENGTH_LONG).show();
		return true;

	}else
	}
}*/
