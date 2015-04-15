/**
 * 
 */
package karro.spike.weatherdataspike;

import karro.spike.weatherdataspike.model.IPosition;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;

/**Takes care of position things for mainActivity
 * @author Karro
 *
 */
public class PositionHandler implements ConnectionCallbacks, OnConnectionFailedListener {
	private static final String TAG = "PositionHandler";
	private Context context;
	private IPosition position;
	private GoogleApiClient mGoogleApiClient;
	private Location mLastLocation;

	public PositionHandler(Context context){
		this.context = context;
		buildGoogleApiClient();
		mGoogleApiClient.connect();
	}

	/***
	 * Builds the GoogleApiClient
	 */
	protected synchronized void buildGoogleApiClient() {
		mGoogleApiClient = new GoogleApiClient.Builder(this.context)
		.addConnectionCallbacks(this)
		.addOnConnectionFailedListener(this)
		.addApi(LocationServices.API)
		.build();
	}

	@Override
	public void onConnected(Bundle connectionHint) {
		mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
				mGoogleApiClient);
		if (mLastLocation != null) {
			//DO something with the location
			Intent i = new Intent(context, PositionPollService.class);
			i.putExtra(PositionPollService.LAT, mLastLocation.getLatitude());
			i.putExtra(PositionPollService.LNG, mLastLocation.getLongitude());

			Log.i(TAG, "position recieved "+  mLastLocation.getLatitude() +" , "+ mLastLocation.getLongitude());
			PendingIntent pi= PendingIntent.getService(context, 0, i, 0);

			AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
			manager.set(AlarmManager.RTC, System.currentTimeMillis(), pi);
		}
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {


	}
	@Override
	public void onConnectionSuspended(int arg0) {

	}
}
