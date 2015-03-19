/**
 * 
 */
package deprecated;

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

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/**
 * @author Karro
 *
 */
public class PositionFragment extends Fragment implements ConnectionCallbacks, OnConnectionFailedListener{
	
	private static final String TAG = "PositionFragment";
	private Object mGoogleApiClient;
	private GoogleMap googleMap;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
				
		try {
            // Loading map
            initilizeMap();
 
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		buildGoogleApiClient();		 
    }
	
	 @Override
	public void onResume() {
	        super.onResume();
	        initilizeMap();
	        setMarker();
			
			moveCameraToMyLocation();
	    }
	 
	protected synchronized void buildGoogleApiClient() {
	    mGoogleApiClient = new GoogleApiClient.Builder(this.getActivity())
	        .addConnectionCallbacks(this)
	        .addOnConnectionFailedListener(this)
	        .addApi(LocationServices.API)
	        .build();
	}
	
	/**
	 * Moves camera ans zoomes in on a set position
	 */
	private void moveCameraToMyLocation() {
		LatLng mCurrentLocation = null;
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
			
		
		LatLng position = new LatLng(latitude, longitude);
		
		// create marker
		MarkerOptions marker = new MarkerOptions().position(position).title("Hello Maps ");
		 
		// adding marker
		googleMap.addMarker(marker);
	}
 
    /**
     * function to load map. If map is not created it will create it for you
     * */
    private void initilizeMap() {
      /*  if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map)).getMap();
 
            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getActivity(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
        }*/
    }
    
	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnected(Bundle arg0) {
		
		
		setMarker();
		
		moveCameraToMyLocation();
	}

	@Override
	public void onConnectionSuspended(int arg0) {
		// TODO Auto-generated method stub
		
	}
	 @Override
	public void onStart() {
	        super.onStart();
	        ((GoogleApiClient) mGoogleApiClient).connect();
	        Log.i(TAG, "Connected to APIClient i onStart()");
	    }

	    @Override
		public void onStop() {
	        super.onStop();
	        if (((GoogleApiClient) mGoogleApiClient).isConnected()) {
	            ((GoogleApiClient) mGoogleApiClient).disconnect();
	        }
	    }

}
