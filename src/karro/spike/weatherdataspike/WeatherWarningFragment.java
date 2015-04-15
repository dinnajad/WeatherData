/**
 * 
 */
package karro.spike.weatherdataspike;

import java.util.ArrayList;
import java.util.List;

import karro.spike.weatherdata.R;
import karro.spike.weatherdataspike.model.WeatherWarning;
import karro.spike.weatherdataspike.model.WeatherWarningKeeper;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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
		warnings = keeper.getWarnings();
		//skapa adapter
		WeatherWarningAdapter adapter = new WeatherWarningAdapter(warnings);
		setListAdapter(adapter);

	}


	/***
	 * adapter for {@link WeatherWarning}
	 * @author Karro
	 *
	 */
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
		
	}
}
