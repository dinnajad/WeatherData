/**
 * 
 */
package karro.spike.weatherdata;

import karro.spike.weatherdataspike.Alarm;
import android.app.ListFragment;
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

	private Alarm[] mAlarms;

	/***
	 * called from the OS when creating the fragment. 
	 * Initialise essentials here
	 */
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		//sätta titeln?
		//hämta dataItems listan
		mAlarms = fejkDataItems();
		//skapa adapter
		//TODO skapa custom adapter med bättre vy
		ArrayAdapter<Alarm> adapter = new ArrayAdapter<Alarm>(getActivity(), android.R.layout.simple_list_item_1,mAlarms);
		setListAdapter(adapter);
	}
	
	private Alarm[] fejkDataItems() {
		
		Alarm ett= new Alarm("temperature","under","3","-11");
		Alarm[] alarms = new Alarm[3];
		alarms[0] =ett;
		alarms[1] =ett;
		alarms[2] = ett;
		return alarms;
	}

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
