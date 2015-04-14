package karro.spike.weatherdataspike;

import java.io.FileNotFoundException;

import karro.spike.weatherdata.R;
import karro.spike.weatherdata.WeatherWarningActivity;
import karro.spike.weatherdataspike.model.Alarm;
import karro.spike.weatherdataspike.model.AlarmKeeper;
import karro.spike.weatherdataspike.model.ForecastKeeper;
import karro.spike.weatherdataspike.model.OneDayWeatherData;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity {
	protected static final String TAG = "MainActivity";
	protected static final String FORE_CAST_XML = "ForeCast.xml";
	private ForecastKeeper storage;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		FragmentManager manager =getFragmentManager();
		Fragment fragment = manager.findFragmentById(R.id.fragmentContainer);
		if (fragment == null) {

			fragment =new WeatherDayFragment();
			manager.beginTransaction()
			.add(R.id.fragmentContainer, fragment)
			.commit();
		}
		//Läs in data från fil
		try {
			setStorage(ForecastKeeper.readFromFile(getApplicationContext(), FORE_CAST_XML));
		} catch (FileNotFoundException e) {

		}
		if(storage==null){
			//starta Pollservice och låt den köra hämtningen en gång till
			//Intent i = new Intent(getApplicationContext(), PollService.class);
			getForecastKeeper();
			storage.saveToPersistanse(getApplicationContext(), FORE_CAST_XML);
		}else{
			OneDayWeatherData data = storage.getTodaysWeather();
			//Ladda fragmentet med data
			WeatherDayFragment odwd = (WeatherDayFragment)fragment;
			odwd.setData(data);
		}
	}


	/**
	 * 
	 */
	private void getForecastKeeper() {
		try {
			setStorage(ForecastKeeper.readFromFile(getApplicationContext(), FORE_CAST_XML));
		} catch (FileNotFoundException fe) {
			Log.e(FORE_CAST_XML, "hittar ingen fil andra försöket, skapar ny keeper istället");
		}
		if(storage==null){
			storage = new ForecastKeeper();
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
		ed.putBoolean("active", true);
		ed.commit();
		Log.v(TAG, "Main Active" );
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

		ed.putBoolean("MainActive", false);
		ed.commit();
		Log.v(TAG, "Main NOT active" );
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_activity_actions, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		if(id==R.id.action_alarm){
			//Toast.makeText(getApplicationContext(), "nyttAlarm", Toast.LENGTH_LONG).show();
			//skapa ny aktivitet
			Intent alarm = new Intent(this,AlarmActivity.class);
			startActivityForResult(alarm, 0);
			//startActivity(alarm);

		}else if(id==R.id.action_my_alarms){
			//Toast.makeText(getApplicationContext(), "Mina Alarm", Toast.LENGTH_LONG).show();
			//skapa ny aktivitet

			Intent alarms = new Intent(this,AlarmListActivity.class);
			alarms.putExtra("filename", FORE_CAST_XML);
			startActivity(alarms);
		}else if(id==R.id.action_toggle_poll){

			boolean shouldStartAlarm = !PollService.isServiceAlarmOn(this);
			PollService.setServiceAlarm(this, shouldStartAlarm);
			Toast.makeText(getApplicationContext(), "toggle poll " +shouldStartAlarm, Toast.LENGTH_LONG).show();
			invalidateOptionsMenu();

		}else if(id==R.id.action_refresh_data){			
			boolean shouldStartAlarm = PollService.isServiceAlarmOn(this);//differs from set repeting one above here we want to restart it if it should be on 
			PollService.setOneTimeServiceAlarm(this, shouldStartAlarm);
			Toast.makeText(getApplicationContext(), "hämtar data nu", Toast.LENGTH_LONG).show();

			//TODO better update of view
		}else if(id==R.id.action_position){
			Toast.makeText(getApplicationContext(), "Min position", Toast.LENGTH_SHORT).show();
			PositionPollService.setOneTimeServiceAlarm(this,true);
			
		}else if(id==R.id.action_weatherWarnings){
			Toast.makeText(getApplicationContext(), "Warnings", Toast.LENGTH_SHORT).show();
			Intent warnings = new Intent(this,WeatherWarningActivity.class);			
			startActivity(warnings);
		
		}
		/*if (id == R.id.action_settings) {
		// proper settings Implementation
		Toast.makeText(getApplicationContext(), "Inställningar", Toast.LENGTH_LONG).show();
		return true;

	}else*/	
		return super.onOptionsItemSelected(item);
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
			/*
			getForecastKeeper();

			storage.AddAlarm(alarm);	
			storage.saveToPersistanse(getApplicationContext(), FORE_CAST_XML);
			 */


			aKeeper = AlarmKeeper.readFromFile(getApplicationContext());

			aKeeper.AddAlarm(alarm);
			aKeeper.saveToPersistence(getApplicationContext());

			Intent alarms = new Intent(this,AlarmListActivity.class);
			alarms.putExtra("filename", FORE_CAST_XML);
			startActivity(alarms);
		}

	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu){
		super.onPrepareOptionsMenu(menu);
		MenuItem toggleItem = menu.findItem(R.id.action_toggle_poll);
		if(PollService.isServiceAlarmOn(getApplicationContext())){
			toggleItem.setTitle(R.string.action_toggle_poll_turn_off);
		}else{
			toggleItem.setTitle(R.string.action_toggle_poll);
		}

		return true;


	}
	/***
	 * gets the ForecastKeeper that stores the data
	 * @return
	 */
	public ForecastKeeper getStorage() {
		return storage;
	}

	/***
	 * Sets the ForecastKeeper that will store data
	 * @param storage
	 */
	public void setStorage(ForecastKeeper storage) {
		this.storage = storage;
	}
}	

