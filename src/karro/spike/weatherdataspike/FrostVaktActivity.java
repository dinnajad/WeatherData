package karro.spike.weatherdataspike;

import karro.spike.weatherdata.R;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class FrostVaktActivity extends Activity implements ConnectionCallbacks, OnConnectionFailedListener {

	private static final String TAG = "FrostVaktActivity";
	
	// Google Map
    private GoogleMap googleMap;
    protected  GoogleApiClient mGoogleApiClient;
	private Location mLastLocation;
	private LatLng mCurrentLocation;
	private Toast mLatitudeText;
	private Toast mLongitudeText;
	
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_frost_vakt);
		try {
            // Loading map
            initilizeMap();
 
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		buildGoogleApiClient();		 
    }

	protected synchronized void buildGoogleApiClient() {
	    mGoogleApiClient = new GoogleApiClient.Builder(this)
	        .addConnectionCallbacks(this)
	        .addOnConnectionFailedListener(this)
	        .addApi(LocationServices.API)
	        .build();
	}
	
	/**
	 * Moves camera ans zoomes in on a set position
	 */
	private void moveCameraToMyLocation() {
		CameraPosition cameraPosition = new CameraPosition.Builder().target(
                mCurrentLocation).zoom(11).build();
 
		googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
	}

	/**
	 * this Code sets a marker on the map
	 */
	private void setMarker() {
		//Code to set a marker
		// latitude and longitude
		double latitude = 46;
		double longitude = 21;
		
		if(mLastLocation!=null){
			latitude= mLastLocation.getLatitude();
			longitude = mLastLocation.getLongitude();
		}
		
		LatLng position = new LatLng(latitude, longitude);
		 mCurrentLocation = position;
		// create marker
		MarkerOptions marker = new MarkerOptions().position(position).title("Hello Maps ");
		 
		// adding marker
		googleMap.addMarker(marker);
	}
 
    /**
     * function to load map. If map is not created it will create it for you
     * */
    private void initilizeMap() {
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map)).getMap();
 
            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
 
    @Override
    protected void onResume() {
        super.onResume();
        initilizeMap();
        setMarker();
		
		moveCameraToMyLocation();
    }
	
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
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	 @Override
	    protected void onStart() {
	        super.onStart();
	        mGoogleApiClient.connect();
	        Log.i(TAG, "Connected to APIClient i onStart()");
	    }

	    @Override
	    protected void onStop() {
	        super.onStop();
	        if (mGoogleApiClient.isConnected()) {
	            mGoogleApiClient.disconnect();
	        }
	    }
	    
	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnected(Bundle arg0) {
		mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
           Log.i(TAG,"mLastLocation "+ mLatitudeText +":" +mLongitudeText);
        }
		
		setMarker();
		
		moveCameraToMyLocation();
	}

	@Override
	public void onConnectionSuspended(int arg0) {
		// TODO Auto-generated method stub
		
	}
}
