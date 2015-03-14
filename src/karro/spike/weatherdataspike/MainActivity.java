package karro.spike.weatherdataspike;

import java.io.FileNotFoundException;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.os.Build;
import karro.spike.weatherdata.AlarmListActivity;
import karro.spike.weatherdata.R;
import karro.spike.weatherdataspike.YR.SimpleYrFetcher;
import karro.spike.weatherdataspike.model.Alarm;
import karro.spike.weatherdataspike.model.ForecastKeeper;
import karro.spike.weatherdataspike.model.OneDayWeatherData;
import karro.spike.weatherdataspike.model.PollService;

public class MainActivity extends Activity {
	protected static final String FORE_CAST_XML = "ForeCast.xml";
	private ForecastKeeper storage;
	
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
		//Läs in data från fil
		try {
			setStorage(ForecastKeeper.readFromFile(getApplicationContext(), FORE_CAST_XML));
		} catch (FileNotFoundException e) {
						
		}
		if(storage==null){
			//starta Pollservice och låt den köra hämtningen en gång
				Intent i = new Intent(getApplicationContext(), PollService.class);
				try {
					setStorage(ForecastKeeper.readFromFile(getApplicationContext(), FORE_CAST_XML));
				} catch (FileNotFoundException fe) {
					Log.e(FORE_CAST_XML, "hittar ingen fil andra försöket, skapar ny keeper istället");
					if(storage==null){
					storage = new ForecastKeeper();
					}
				}
			}else{
		OneDayWeatherData data = storage.getTodaysWeather();
		//Ladda fragmentet med data
		WeatherDayFragment odwd = (WeatherDayFragment)fragment;
		odwd.setData(data);
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
		if (id == R.id.action_settings) {//TODO kopiera den här koden till övriga activities och modifiera när nödvändigt
			//TODO proper settings Implementation
			Toast.makeText(getApplicationContext(), "Inställningar", Toast.LENGTH_LONG).show();
			return true;
			
		}else if(id==R.id.action_alarm){
			Toast.makeText(getApplicationContext(), "nyttAlarm", Toast.LENGTH_LONG).show();
			//skapa ny aktivitet
			Intent alarm = new Intent(this,AlarmActivity.class);
			startActivityForResult(alarm, 0);
			//startActivity(alarm);
			
		}else if(id==R.id.action_my_alarms){
			Toast.makeText(getApplicationContext(), "Mina Alarm", Toast.LENGTH_LONG).show();
			//skapa ny aktivitet
			
			Intent alarms = new Intent(this,AlarmListActivity.class);
			alarms.putExtra("filename", FORE_CAST_XML);
			startActivity(alarms);
		}else if(id==R.id.action_toggle_poll){
			
			boolean shouldStartAlarm = !PollService.isServiceAlarmOn(this);
			PollService.setServiceAlarm(this, shouldStartAlarm);
			Toast.makeText(getApplicationContext(), "toggle poll " +shouldStartAlarm, Toast.LENGTH_LONG).show();
		//TODO eventually make item update text see page 477 
			
		}else if(id==R.id.action_refresh_data){			
			boolean shouldStartAlarm = !PollService.isServiceAlarmOn(this);
			PollService.setOneTimeServiceAlarm(this, shouldStartAlarm);
			Toast.makeText(getApplicationContext(), "hämtar data nu", Toast.LENGTH_LONG).show();
			}
		else if(id==R.id.action_search){
			Toast.makeText(getApplicationContext(), "Search", Toast.LENGTH_LONG).show();
			//TODO proper Search implementation
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onActivityResult(int requestCode,int resultCode, Intent data){
		if(data!=null){
			//get data
			String parameter = data.getStringExtra("parameter");
			String operator = data.getStringExtra("operator");
			String limit = data.getStringExtra("limit");
			
			
			Alarm alarm= new Alarm(parameter,operator,limit);
		storage.AddAlarm(alarm);	
		storage.saveToPersistanse(getApplicationContext(), FORE_CAST_XML);
		}
		
	}
	
	public ForecastKeeper getStorage() {
		return storage;
	}

	public void setStorage(ForecastKeeper storage) {
		this.storage = storage;
	}
}	

