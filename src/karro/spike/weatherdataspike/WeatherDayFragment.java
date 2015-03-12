/**
 * 
 */
package karro.spike.weatherdataspike;

import java.util.Calendar;
import java.util.Date;

import karro.spike.weatherdata.R;
import karro.spike.weatherdataspike.YR.YrWetherData;
import karro.spike.weatherdataspike.model.OneDayWeatherData;
import karro.spike.weatherdataspike.model.PollService;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author Karro
 *
 */
public class WeatherDayFragment extends Fragment {
	@SuppressWarnings("unused")//for use in any log messages
	private static final String TAG= "WeatherDayFragment";

	private TextView mPlaceTextview;
	private TextView mDayTextView;
	private TextView mMaxTempTextview;
	private TextView mMinTextView;

	private ImageView mImageView;

	private OneDayWeatherData data; 
	private Boolean isExtended =true;

	/***
	 * called from the OS when creating the fragment. 
	 * Initialise essentials here
	 */
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		Intent i = new Intent(getActivity(),PollService.class);
		getActivity().startService(i);
	}

	/***
	 * creates the view, connect listeners
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View v= inflater.inflate(R.layout.weather_day_fragment, container,false);

		//get references to the textViews
		mPlaceTextview = (TextView) v.findViewById(R.id.placeTextView);
		mDayTextView = (TextView) v.findViewById(R.id.dayTextView);
		mMaxTempTextview = (TextView) v.findViewById(R.id.maxTempTextView);
		mMinTextView = (TextView) v.findViewById(R.id.minTempTextView);

		mImageView = (ImageView) v.findViewById(R.id.imageView);
		if(data == null){
			data= new OneDayWeatherData();
			data.setDay(new Date());
			data.setMaxTemperature(100);
			data.setMintemperature(-100);
			data.setPlace("Himlen");
		}


		mDayTextView.setText(data.getDay().toString());//TODO use Calendar to get Date
		mMaxTempTextview.setText(data.getMaxTemperatureString());
		mMinTextView.setText(data.getMinTemperatureString());

		if(isExtended){
			mPlaceTextview.setText(data.getPlace());
			mPlaceTextview.setEnabled(true);
			mPlaceTextview.setVisibility(View.VISIBLE);
			setPicture();
			}else{
				mPlaceTextview.setEnabled(false);
				mPlaceTextview.setVisibility(View.GONE);
				mImageView.setEnabled(false);
				mImageView.setVisibility(android.view.View.GONE);
			}
		//TODO if(orientation=landscape)
		
		return v;
	}

	private void setPicture(){
		if(data.getMintemperature()<=0){//TODO make limit flexible
			//mImageView.setImageResource(R.drawable.frost_pic);
			mImageView.setVisibility(android.view.View.VISIBLE);		
		}else 
			mImageView.setImageResource(R.drawable.sunflowers);
	}

	public OneDayWeatherData getData() {
		return data;
	}

	public void setData(OneDayWeatherData data) {
		this.data = data;
	}

}
