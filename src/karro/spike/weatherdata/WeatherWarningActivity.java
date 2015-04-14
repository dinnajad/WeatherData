package karro.spike.weatherdata;

import karro.spike.weatherdataspike.AlarmListFragment;
import karro.spike.weatherdataspike.WeatherWarningFragment;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;

public class WeatherWarningActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weather_warning);
		
		FragmentManager manager = getFragmentManager();
		Fragment fragment = manager.findFragmentById(R.id.warning_fragment_container);
		if (fragment == null) {
		fragment = new WeatherWarningFragment();
		manager.beginTransaction()
		.add(R.id.warning_fragment_container, fragment)
		.commit();
		}
	}
/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.weather_warning, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	*/
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
