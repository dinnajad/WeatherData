/**
 * 
 */
package karro.spike.weatherdataspike.model;

import karro.spike.weatherdataspike.YR.SimpleYrFetcher;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;

/**
 * @author Karro
 *
 */
public class PollService extends IntentService {

	private static final String TAG = "PollService";
	
	public PollService(){
	 super(TAG);	
	}
	
	/* (non-Javadoc)
	 * @see android.app.IntentService#onHandleIntent(android.content.Intent)
	 */
	@Override
	protected void onHandleIntent(Intent intent) {
		Log.i(TAG, "recived an intent: "+ intent);
		
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		if(!cm.getActiveNetworkInfo().isAvailable()){ 		
			return;
		}
		
		//hämta väderdata //TODO lägg till för alla sparade positioner
		new SimpleYrFetcher().fetchItems();
	}

}
