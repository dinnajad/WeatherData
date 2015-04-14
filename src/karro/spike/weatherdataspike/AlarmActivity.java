/**
 * 
 */
package karro.spike.weatherdataspike;


import karro.spike.weatherdata.R;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


/**
 * visualizes the creation of an alarm
 * @author Karro
 *
 */
public class AlarmActivity extends Activity {
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarm);
		
		FragmentManager manager =getFragmentManager();
		Fragment fragment = manager.findFragmentById(R.id.alarm_fragment_container);
		if (fragment == null) {
		fragment =new AlarmFragment();
		manager.beginTransaction()
		.add(R.id.alarm_fragment_container, fragment)
		.commit();
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

		ed.putBoolean("AlarmActivityActive", true);
		ed.commit();
		Log.v("AlarmActivity", "AlarmActivity Active" );
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

		ed.putBoolean("AlarmActivityActive", false);
		ed.commit();
		Log.v("AlarmActivity", "AlarmActivity NOT active" );
	}
 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.alarm_list, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		
		return super.onOptionsItemSelected(item);
	}
}
