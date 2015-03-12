/**
 * 
 */
package karro.spike.weatherdataspike;

import karro.spike.weatherdata.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author Karro
 *
 */
public class AlarmFragment extends Fragment {
	
	/***
	 * called from the OS when creating the fragment. 
	 * Initialise essentials here
	 */
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
	}
	
	/***
	 * creates the view, connect listeners
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View v= inflater.inflate(R.layout.alarm_fragment, container,false);
	return v;
	}
}
