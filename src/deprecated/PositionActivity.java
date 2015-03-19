package deprecated;

import karro.spike.weatherdata.R;
import karro.spike.weatherdataspike.Geonames.SimpleGeonameFetcher;

import com.google.android.gms.common.api.GoogleApiClient;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class PositionActivity extends Activity {

	protected  GoogleApiClient mGoogleApiClient;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*setContentView(R.layout.position_fragment);
		
		FragmentManager manager =getFragmentManager();
		Fragment fragment = manager.findFragmentById(R.id.position_fragment_container);
		if (fragment == null) {
		fragment =new PositionFragment();
		manager.beginTransaction()
		.add(R.id.position_fragment_container, fragment)
		.commit();
		}*/
	}

	
	public void fetchPositions(){
	
	
	SimpleGeonameFetcher fetcher =  new SimpleGeonameFetcher();
	fetcher.fetchTextPos("Ume");
	fetcher.mPositionItems.get(0).toString();
	}
	
	/* add this to manifest if this activity should be used again
	 <activity
            android:name="deprecated.PositionActivity"
            android:label="@string/app_name"
            android:parentActivityName="karro.spike.weatherdataspike.MainActivity" >
            <meta-data
                android:name="android.support.PARENT_ACTIVITY"
                android:value="karro.spike.weatherdataspike.MainActivity" />
        </activity>
        */

	
 
   
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.frost_vakt, menu);
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		
		return super.onOptionsItemSelected(item);
	}
	
}
