/**
 * 
 */
package karro.spike.weatherdataspike;

import java.util.ArrayList;

import karro.spike.weatherdataspike.model.WeatherWarning;
import karro.spike.weatherdataspike.model.WeatherWarningKeeper;
import android.app.ListFragment;
import android.os.Bundle;
import android.widget.ArrayAdapter;

/**
 * @author Karro
 *
 */
public class WeatherWarningFragment extends ListFragment{
	private WeatherWarningKeeper keeper;
	private ArrayList<WeatherWarning> warnings;

	/***
	 * called from the OS when creating the fragment. 
	 * Initialise essentials here
	 */
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		keeper = WeatherWarningKeeper.readFromFile(getActivity().getApplicationContext());

		if(keeper==null){
			keeper= new WeatherWarningKeeper();
		}
		//hämta dataItems listan
		warnings = keeper.getWarnings();// varför är den tom?
		//skapa adapter
		//TODO skapa custom adapter med bättre vy
		ArrayAdapter<WeatherWarning> adapter = new ArrayAdapter<WeatherWarning>(getActivity().getApplicationContext(), 
				android.R.layout.simple_list_item_multiple_choice, warnings);
		setListAdapter(adapter);

	}

	/***
	 * creates the view, connect listeners
	 *//*
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View v= inflater.inflate(R.layout.fragment_alarm_list, container,false);
		return v;
		}*/
}
