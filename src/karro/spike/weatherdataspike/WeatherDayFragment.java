/**
 * 
 */
package karro.spike.weatherdataspike;

import java.io.FileNotFoundException;
import java.util.Date;

import karro.spike.weatherdata.R;
import karro.spike.weatherdataspike.YR.YrLocation;
import karro.spike.weatherdataspike.YR.YrRootWeatherData;
import karro.spike.weatherdataspike.model.ForecastKeeper;
import karro.spike.weatherdataspike.model.OneDayWeatherData;
import android.app.Fragment;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**Fragment that  shows one days weatherdata
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
	private TextView mCreditsTextView;
	private TextView mMessageTextview;

	private ImageView mImageView;

	private OneDayWeatherData mData; 
	private Boolean isExtended = true;

	private String mMessage;


	/***
	 * called from the OS when creating the fragment. 
	 * Initialise essentials here
	 */
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		mMessage = getActivity().getIntent().getStringExtra("MESSAGE");
		
		//Läs in data från fil
		try {
			storage= ForecastKeeper.readFromFile(getActivity());
		} catch (FileNotFoundException e) {

		}
		if(storage==null){
			//starta Pollservice och låt den köra hämtningen en gång till
			//Intent i = new Intent(getApplicationContext(), PollService.class);
			getForecastKeeper();
			storage.saveToPersistanse(getActivity());
		}
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
		mCreditsTextView = (TextView) v.findViewById(R.id.yr);
		mMessageTextview = (TextView) v.findViewById(R.id.MessageTextView);
		
		mImageView = (ImageView) v.findViewById(R.id.imageView);
		return v;
	}

	/***
	 * called after on Start and everytime the fragment is brought to the front 
	 * good place to make sure that all data is up to date
	 */
	@Override
	public void onResume(){
		super.onResume();
		getForecastKeeper();//för att datat ska vara aktuellt
		updateView();
	}

	/**
	 * gets a current ForecastKeeper
	 */
	private void getForecastKeeper() {
		try {
			storage =ForecastKeeper.readFromFile(getActivity());
		} catch (FileNotFoundException fe) {
			Log.e(TAG, "hittar ingen fil , skapar ny keeper istället");
		}
		if(storage==null){
			storage = new ForecastKeeper();
		}
	}

	/**
	 * Updates all components with current data
	 */
	private void updateView() {
		mData = storage.getTodaysWeather();

		if(mData == null){
			fejkData();//just to show something
			mMessageTextview.setText(getActivity().getString(R.string.first_time_instruction));
			mMessageTextview.setVisibility(View.VISIBLE);
			mMessageTextview.setEnabled(true);
			mMessageTextview.setBackgroundColor(Color.LTGRAY);
			return;
		}

		// check that nothing is null
		String date= mData.getDayString();			
		String max= mData.getMaxTemperatureString();
		String min =mData.getMinTemperatureString();

		String place=null;
		String yr=null;
		if(storage!=null){
			YrRootWeatherData root=	storage.getRootWeatherData();
			if(root!=null){
				yr =root.getCredit().toString();
				YrLocation location= root.getLocation();
				if(location!=null){
					place = location.toString();
				}
			}
		}
		if(date==null)date=" ";		
		if(max==null) max= "";		
		if(min==null)min="";
		if(yr==null)yr="";
		if(place==null)place="";
		

		mDayTextView.setText(date);
		mMinTextView.setText(min);
		mMaxTempTextview.setText(max);
		mCreditsTextView.setText(yr);

		
		if(mMessage!= null){
			mMessageTextview.setBackgroundColor(Color.LTGRAY);
			mMessageTextview.setText(mMessage);
			mMessageTextview.setEnabled(true);
			mMessageTextview.setVisibility(View.VISIBLE);
		}else{
			mMessageTextview.setVisibility(View.GONE);
			mMessageTextview.setEnabled(false);
		}
		
		if(isExtended){
			mPlaceTextview.setText(place);
			mPlaceTextview.setEnabled(true);
			mPlaceTextview.setVisibility(View.VISIBLE);
			setPicture();
		}else{
			mPlaceTextview.setEnabled(false);
			mPlaceTextview.setVisibility(View.GONE);
			mImageView.setEnabled(false);
			mImageView.setVisibility(android.view.View.GONE);
		}
	}

	/**
	 * Fejks some data
	 */
	private void fejkData() {
		mData= new OneDayWeatherData();
		mData.setDay(new Date());
		mData.setMaxTemperature(100);
		mData.setMintemperature(-100);
		mData.setPlace("Himlen");
	}

	/***
	 * Sets the Pic based on temperature
	 */
	private void setPicture(){
		if(getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE){

			if(mData.getMintemperature()<=0){
				mImageView.setImageResource(R.drawable.frost_pic);
				mImageView.setVisibility(android.view.View.VISIBLE);		
			}else {
				mImageView.setImageResource(R.drawable.sunflowers);
				mImageView.setVisibility(android.view.View.VISIBLE);
			}
		}
	}
}
