package karro.spike.weatherdataspike;

import karro.spike.weatherdata.R;
import karro.spike.weatherdataspike.model.Alarm;
import karro.spike.weatherdataspike.model.AlarmKeeper;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class WeatherWarningActivity extends Activity {

	private static final String TAG = "WeatherWarningActivity";
	private Fragment mFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weather_warning);
		
		FragmentManager manager = getFragmentManager();
		mFragment = manager.findFragmentById(R.id.warning_fragment_container);
		if (mFragment == null) {
		mFragment = new WeatherWarningFragment();
		manager.beginTransaction()
		.add(R.id.warning_fragment_container, mFragment)
		.commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.weather_warning, menu);
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		handleMenuClick(item, getApplicationContext());
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * @param item
	 */
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
		}else if(id==R.id.action_refresh_data){			
			boolean shouldStartAlarm = PollService.isServiceAlarmOn(context);//differs from set repeting one above here we want to restart it if it should be on 
			PollService.setOneTimeServiceAlarm(context, shouldStartAlarm);
			Toast.makeText(context, "hämtar data nu", Toast.LENGTH_LONG).show();

		}else if(id==R.id.action_position){
			//Toast.makeText(context, "Min position", Toast.LENGTH_SHORT).show();
			PositionPollService.setOneTimeServiceAlarm(context,true);
			
		}else if(id==R.id.action_clear){
			//Toast.makeText(context, "Clear", Toast.LENGTH_SHORT).show();
			WeatherWarningFragment fragment = (WeatherWarningFragment) mFragment;
			fragment.clearWarings();	
		}else if(id==R.id.action_main){
			Intent main = new Intent(context,MainActivity.class);
			startActivity(main);
			}
		/*if (id == R.id.action_settings) {
		// proper settings Implementation
		Toast.makeText(getApplicationContext(), "Inställningar", Toast.LENGTH_LONG).show();
		return true;

	}else*/
	}
	
	@Override
	protected void onActivityResult(int requestCode,int resultCode, Intent data){
		if(data!=null){
			//get data
			String parameter = data.getStringExtra("parameter");
			String operator = data.getStringExtra("operator");
			Log.i(TAG,"operator=" + operator);
			String limit = data.getStringExtra("limit");
			Alarm alarm= new Alarm(parameter,operator,limit);

			String message = data.getStringExtra("message");
			alarm.setMessage(message);
			AlarmKeeper aKeeper;
			
			aKeeper = AlarmKeeper.readFromFile(getApplicationContext());

			aKeeper.AddAlarm(alarm);
			aKeeper.saveToPersistence(getApplicationContext());

			Intent alarms = new Intent(this,AlarmListActivity.class);
			
			startActivity(alarms);
		}

	}
	
	/***
	 * Saves the state in shared preferences so that the services can see if the app is active
	 */
	@Override
	protected void onStart() {
		super.onStart();

		// Store our shared preference
		SharedPreferences sp = getSharedPreferences("OURINFO", MODE_PRIVATE);
		Editor ed = sp.edit();
		ed.putBoolean("WeatherWarninglistActive", true);
		ed.commit();
		Log.v("WeatherWarninglist", "WeatherWarninglist Active" );
	}

	/***
	 * Saves the state in shared preferences so that the services can see if the app is active
	 */
	@Override
	protected void onStop() {
		super.onStop();

		// Store our shared preference
		SharedPreferences sp = getSharedPreferences("OURINFO", MODE_PRIVATE);
		Editor ed = sp.edit();
		ed.putBoolean("WeatherWarninglistActive", false);
		ed.commit();
		Log.v("WeatherWarninglist", "WeatherWarninglist NOT active" );
	}
}
