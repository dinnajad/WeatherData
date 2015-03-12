package karro.spike.weatherdataspike;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.os.Build;
import karro.spike.weatherdata.AlarmListActivity;
import karro.spike.weatherdata.R;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		FragmentManager manager =getFragmentManager();
		Fragment fragment = manager.findFragmentById(R.id.fragmentContainer);
		if (fragment == null) {
		//fragment = new WeatherFrameFragment();
		fragment =new WeatherDayFragment();
		manager.beginTransaction()
		.add(R.id.fragmentContainer, fragment)
		.commit();
		}
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
		if (id == R.id.action_settings) {
			//TODO proper settings Implementation
			Toast.makeText(getApplicationContext(), "Inställningar", Toast.LENGTH_LONG).show();
			return true;
			
		}else if(id==R.id.action_alarm){
			Toast.makeText(getApplicationContext(), "nyttAlarm", Toast.LENGTH_LONG).show();
			//skapa ny aktivitet
			Intent alarm = new Intent(this,AlarmActivity.class);
			startActivity(alarm);
		}else if(id==R.id.action_my_alarms){
			Toast.makeText(getApplicationContext(), "Mina Alarm", Toast.LENGTH_LONG).show();
			//skapa ny aktivitet
			Intent alarm = new Intent(this,AlarmListActivity.class);
			startActivity(alarm);
			
			
		}else if(id==R.id.action_search){
			Toast.makeText(getApplicationContext(), "Search", Toast.LENGTH_LONG).show();
			//TODO proper Search implementation
		}
		return super.onOptionsItemSelected(item);
	}
}	

