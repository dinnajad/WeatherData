/**
 * 
 */
package karro.spike.weatherdataspike;

import java.util.Date;

import karro.spike.weatherdata.R;
import karro.spike.weatherdataspike.model.ForecastKeeper;
import karro.spike.weatherdataspike.model.OneDayWeatherData;
import android.app.Fragment;
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

	private ForecastKeeper storage;
	private TextView mPlaceTextview;
	private TextView mDayTextView;
	private TextView mMaxTempTextview;
	private TextView mMinTextView;

	private ImageView mImageView;

	private OneDayWeatherData mData; 
	private Boolean isExtended = true;

	/***
	 * called from the OS when creating the fragment. 
	 * Initialise essentials here
	 */
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		storage=((MainActivity) getActivity()).getStorage();
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
		if(mData == null){
			fejkData();
		}

		//TODO check that nothing is null
		String date= mData.getDayString();			
		String max= mData.getMaxTemperatureString();
		String min =mData.getMinTemperatureString();

		if(date==null)date=" ";		
		if(max==null) max= "";		
		if(min==null)min="";

		mDayTextView.setText(date);
		mMinTextView.setText(min);
		mMaxTempTextview.setText(max);

		if(isExtended){
			mPlaceTextview.setText(mData.getPlace());
			mPlaceTextview.setEnabled(true);
			mPlaceTextview.setVisibility(View.VISIBLE);
			//setPicture();
		}else{
			mPlaceTextview.setEnabled(false);
			mPlaceTextview.setVisibility(View.GONE);
			mImageView.setEnabled(false);
			mImageView.setVisibility(android.view.View.GONE);
		}
		//TODO if(orientation=landscape)

		return v;
	}

	/**
	 * 
	 */
	private void fejkData() {
		mData= new OneDayWeatherData();
		mData.setDay(new Date());
		mData.setMaxTemperature(100);
		mData.setMintemperature(-100);
		mData.setPlace("Himlen");
	}

	private void setPicture(){
		if(mData.getMintemperature()<=0){//TODO make limit flexible
			//mImageView.setImageResource(R.drawable.frost_pic);
			mImageView.setVisibility(android.view.View.VISIBLE);		
		}else 
			mImageView.setImageResource(R.drawable.sunflowers);
	}

	public OneDayWeatherData getData() {
		return mData;
	}

	public void setData(OneDayWeatherData data) {
		this.mData = data;
	}

}
