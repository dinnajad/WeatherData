/**
 * 
 */
package karro.spike.weatherdataspike;


import karro.spike.weatherdata.R;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


/**
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
			Toast.makeText(getApplicationContext(), "Inst�llningar", Toast.LENGTH_LONG).show();
			return true;
		}else if(id==R.id.action_alarm){
			Toast.makeText(getApplicationContext(), "nyttAlarm", Toast.LENGTH_LONG).show();
		}else if(id==R.id.action_search){
			Toast.makeText(getApplicationContext(), "Search", Toast.LENGTH_LONG).show();
		}
		return super.onOptionsItemSelected(item);
	}
}