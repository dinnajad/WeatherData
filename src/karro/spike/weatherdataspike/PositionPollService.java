/**
 * 
 */
package karro.spike.weatherdataspike;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;

import karro.spike.weatherdataspike.Geonames.GeonamesPosition;
import karro.spike.weatherdataspike.Geonames.SimpleGeonameFetcher;

import karro.spike.weatherdataspike.model.ForecastKeeper;
import karro.spike.weatherdataspike.model.IPosition;
import karro.spike.weatherdataspike.model.PollService;
import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/**retrieves Positions from GEONAMES in a separate service And saves it to the XML file
 * @author Karro
 *
 */
public class PositionPollService extends IntentService implements ConnectionCallbacks, OnConnectionFailedListener {
	public static final String LNG = "lng";
	public static final String LAT = "lat";
	private static final String TAG = "PositionPollService";
	private ForecastKeeper storage;
	private GoogleApiClient mGoogleApiClient;
	private Location mLastLocation;
	public ArrayList<IPosition> mPositionItems;

	public PositionPollService(String name) {
		super(name);		
		mPositionItems = new ArrayList<IPosition>();
	}
	
	public PositionPollService(){
		super(null);
		mPositionItems = new ArrayList<IPosition>();
	}
	
	/* (non-Javadoc)
	 * @see android.app.IntentService#onHandleIntent(android.content.Intent)
	 */
	@Override
	protected void onHandleIntent(Intent intent) {
		Log.i(TAG, "recived the intent: "+ intent);
		
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

		if(!cm.getActiveNetworkInfo().isAvailable()){
			Log.e(TAG, "No network acess");
			return;
		}
		Log.v(TAG, "Network acess ready");

		buildGoogleApiClient();
		mGoogleApiClient.connect();
		
		//getPositionData());
	}

	/**
	 * @param intent
	 */
	private void getPositionData(double lat,double lng) {
		
		if(lat==0 || lng==0){return;}// if no real values no idea to go on
		
		float longitude = (float)lng;
		float latitude = (float) lat;
		
		/*
		SimpleGeonameFetcher fetcher = new SimpleGeonameFetcher();
		ArrayList<GeonamesPosition> geopositions = fetcher.fetchItems(latitude, longitude);*/
		//använda asyncTask istället
		FetchPositionTask background = (FetchPositionTask) new FetchPositionTask().execute();
		
	}

	/**
	 * 
	 */
	private void handleRecivedData() {
		// till att börja med tar vi första itemet i listan //TODO ask user what item to use
		Log.i(TAG,mPositionItems.get(0).toString());
		
		try {
			storage = ForecastKeeper.readFromFile(getApplicationContext(), ForecastKeeper.FORE_CAST_XML);
		} catch (FileNotFoundException e) {
			//ignore but handle later by creating new Forecastkeeper
			Log.i(TAG, "hittade inte filen");
		}	
		
		if(storage==null){
			storage = new ForecastKeeper();
		}
		
		storage.AddPosition(mPositionItems.get(0));
		storage.saveToPersistanse(getApplicationContext(), ForecastKeeper.FORE_CAST_XML);
	}
	
	protected synchronized void buildGoogleApiClient() {
		mGoogleApiClient = new GoogleApiClient.Builder(this)
		.addConnectionCallbacks(this)
		.addOnConnectionFailedListener(this)
		.addApi(LocationServices.API)
		.build();
	}

	@Override
	public void onConnected(Bundle connectionHint) {
		Log.i(TAG,"före hämta lastLocation");
		mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
				mGoogleApiClient);
		if (mLastLocation != null) {
			//DO something with the location
			Toast.makeText(getApplicationContext()	, "Latitude" + mLastLocation.getLatitude(), Toast.LENGTH_LONG).show();
			Log.i(TAG,"Latitude" + mLastLocation.getLatitude());
			getPositionData(mLastLocation.getLatitude(),mLastLocation.getLongitude()); //hit verkar den komma 
		}else{
			Log.e(TAG,"Hittade ingen Location");
			//TODO set some default position 
		}
		
		
	}
	
	/***
	 * Method to start the pollservice once, at once.  eg to get data now. a refresh 
	 * @param context
	 * @param isOn
	 */
	public static void setOneTimeServiceAlarm(Context context, boolean isOn){
		Intent i = new Intent(context, PositionPollService.class);
		PendingIntent pi= PendingIntent.getService(context, 0, i, 0);
		
		AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		//TODO currently this cancels the repeating alarm, how dont?
		if(isOn){
			manager.set(AlarmManager.RTC, System.currentTimeMillis(), pi);
		}else{
			manager.cancel(pi);
			pi.cancel();
		}
	}

	@Override
	public void onConnectionSuspended(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		
	}

	private class FetchPositionTask extends AsyncTask<Void,Void,ArrayList<GeonamesPosition>>{

		@Override
		protected ArrayList<GeonamesPosition> doInBackground(Void... params) {
			return new SimpleGeonameFetcher().fetchItems();
		}
		
		@Override
		protected void onPostExecute(ArrayList<GeonamesPosition> positions){
			mPositionItems.addAll( positions);
			handleRecivedData();
		}
		
	}

}
