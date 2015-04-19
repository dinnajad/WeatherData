/**
 * 
 */
package karro.spike.weatherdataspike;

import java.util.ArrayList;

import karro.spike.weatherdata.R;
import karro.spike.weatherdataspike.model.AlarmKeeper;
import karro.spike.weatherdataspike.model.IAlarm;
import karro.spike.weatherdataspike.model.WeatherWarning;
import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**Fragment that shows a list of the Alarms
 * @author Karro
 *
 */
public class AlarmListFragment extends ListFragment  {
	private ListView list;
	private ArrayList<IAlarm> mAlarms;//remember to save list to persistence if changes are made
	AlarmKeeper keeper;

	/***
	 * called from the OS when creating the fragment. 
	 * Initialise essentials here
	 */
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		keeper = AlarmKeeper.readFromFile(getActivity().getApplicationContext());

		if(keeper==null){
			keeper= new AlarmKeeper();
		}
		//hämta dataItems listan
		mAlarms = keeper.getAlarms();
		//skapa adapter
		//TODO skapa custom adapter med bättre vy och aktivera switch
		ArrayAdapter<IAlarm> adapter = new ArrayAdapter<IAlarm>(getActivity(), android.R.layout.simple_list_item_multiple_choice,mAlarms);
		setListAdapter(adapter);
	}

	/***
	 * adapter for {@link WeatherWarning} change to match Alarms instead
	 * @author Karro
	 *
	 *//*
	private class WeatherWarningAdapter extends ArrayAdapter<WeatherWarning>{

		public WeatherWarningAdapter(List<WeatherWarning> warnings) {
			super(getActivity(), 0, warnings);

		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent){
			if(convertView == null){
				convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_weather_warnings, null);
			}
			WeatherWarning warning = getItem(position);

			TextView messageTextView = (TextView)convertView.findViewById(R.id.message_textView_li);
			messageTextView.setText(warning.getMessage());

			TextView alarmTextView = (TextView)convertView.findViewById(R.id.alarm_textView_li);
			alarmTextView.setText(warning.getAlarm().toString());

			TextView time = (TextView)convertView.findViewById(R.id.time_textView_li);
			time.setText(warning.getOneDayWeatherData().getDayString());


			return convertView;

		}

	}*/
	
	@Override
	public void onResume(){
		super.onResume();

		keeper = AlarmKeeper.readFromFile(getActivity().getApplicationContext());

		if(keeper==null){
			keeper= new AlarmKeeper();
		}
		//hämta dataItems listan
		mAlarms = keeper.getAlarms();
		//skapa adapter
		//TODO skapa custom adapter med bättre vy och aktivera switch
		ArrayAdapter<IAlarm> adapter = new ArrayAdapter<IAlarm>(getActivity(), android.R.layout.simple_list_item_multiple_choice,mAlarms);
		setListAdapter(adapter);
	}
	
	/***
	 * creates the view, connect listeners
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View v= inflater.inflate(R.layout.fragment_alarm_list, container,false);
		list = (ListView) v.findViewById(R.id.myAlarmList);

		list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);// tar jag bort denna öppnas inte contextmenyn vid val
		list.setMultiChoiceModeListener(new MultiChoiceModeListener() {

			@Override
			public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
				// Here you can do something when items are selected/de-selected,
				// such as update the title in the CAB
				return false;
			}			

			@Override
			public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
				int itemId = item.getItemId();
				if (itemId == R.id.action_delete) {
					Log.v("DELETE","found delete case");
					deleteSelectedItems();
					mode.finish(); // Action picked, so close the CAB
					return true;
				} else {
					return false;
				}
			}

			@Override
			public void onItemCheckedStateChanged(ActionMode mode, int position,
					long id, boolean checked) {
				// Here you can perform updates to the CAB due to
				// an invalidate() request
			}

			@Override
			public void onDestroyActionMode(ActionMode mode) {
				// Here you can make any necessary updates to the activity when
				// the CAB is removed. By default, selected items are deselected/unchecked.
			}

			@Override
			public boolean onCreateActionMode(ActionMode mode, Menu menu) {
				// Inflate the menu for the CAB
				MenuInflater inflater = mode.getMenuInflater();
				inflater.inflate(R.menu.contextual, menu);
				return true;
			}
		});

		return v;
	}

	/*How to get hold of the selected items, example
	  String selected = "";

      int cntChoice = myList.getCount();

      SparseBooleanArray sparseBooleanArray = myList.getCheckedItemPositions();

      for(int i = 0; i < cntChoice; i++){

          if(sparseBooleanArray.get(i)) {

              selected += myList.getItemAtPosition(i).toString() + "\n";
          }

      }*/
	/***
	 * Helper function that deletes items that is selected
	 */
	private void deleteSelectedItems() {
		// Raderingen fungerade inte som den ska. Om jag har 3 alarm som "heter" 0, 1, 2 så ligger de på plats 0, 1, 2 i arrayen. 
		//Om jag markerar att radera 0, 1 så raderas först nr 0 och sedan raderas nr 1 i den nya array som uppstår när nr 0 har raderats. 
		//Jag raderar alltså fel position eftersom positionerna gäller ursprungsarrayen. Om jag markerar alla i listan kommer jag börja radera utanför arrayen efter ett tag. //
		//Börja med att radera högsta positionen istället för lägsta positionen så bör det inte vara något problem längre.
		Log.v("DELETE","entered deletefkn");

		int listCount = list.getCount();
		SparseBooleanArray sparseBooleanArray = list.getCheckedItemPositions();
		int alarmsRemoved = 0;
		for(int i=listCount-1; i>=0; i--)
		{
			if(sparseBooleanArray.get(i)) 
			{
				Log.v("DELETE","deleting: " +mAlarms.get(i).toString());
//				Toast.makeText(getActivity(),"removed" +i, Toast.LENGTH_SHORT).show();
				IAlarm alarm = mAlarms.remove(i);
				keeper.RemoveAlarm(alarm);// måste be keepern ta bort alarmet för att det ska märkas nästa gång vi öppnar appen
				alarmsRemoved++;
				Log.v("DELETE","deleted"+i);
			}
		}
		
		if(alarmsRemoved > 0){
			Toast.makeText(getActivity(),"removed " + alarmsRemoved + " alarms", Toast.LENGTH_SHORT).show();
		}

		keeper.saveToPersistence(getActivity().getApplicationContext());
	}
}
