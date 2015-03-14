/**
 * 
 */
package karro.spike.weatherdata;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import karro.spike.weatherdataspike.model.Alarm;
import karro.spike.weatherdataspike.model.ForecastKeeper;
import karro.spike.weatherdataspike.model.IAlarm;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * @author Karro
 *
 */
public class AlarmListFragment extends ListFragment {
	private String filename;
	private ArrayList<IAlarm> mAlarms;//remember to save list to persistence if changes are made
	ForecastKeeper keeper;
	/***
	 * called from the OS when creating the fragment. 
	 * Initialise essentials here
	 */
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		Intent i =getActivity().getIntent();
		filename= i.getStringExtra("filename");
		try {
			keeper = ForecastKeeper.readFromFile(getActivity().getApplicationContext(), filename);
		} catch (FileNotFoundException e) {
			keeper =  new ForecastKeeper();
		}
		//hämta dataItems listan
		mAlarms = keeper.getAlarms();
		//skapa adapter
		//TODO skapa custom adapter med bättre vy
		ArrayAdapter<IAlarm> adapter = new ArrayAdapter<IAlarm>(getActivity(), android.R.layout.simple_list_item_1,mAlarms);
		setListAdapter(adapter);
		
	}
	/*
	private Alarm[] fejkDataItems() {
		
		Alarm ett= new Alarm("temperature","under","3");
		Alarm[] alarms = new Alarm[3];
		alarms[0] =ett;
		alarms[1] =ett;
		alarms[2] = ett;
		return alarms;
	}*/

	/***
	 * creates the view, connect listeners
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View v= inflater.inflate(R.layout.fragment_alarm_list, container,false);
		ListView list = (ListView) v.findViewById(R.id.myAlarmList);
	return v;
	}
}
