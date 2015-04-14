/**
 * 
 */
package deprecated;

import karro.spike.weatherdataspike.model.IWeatherData;
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
public class PeriodWeatherFragment extends Fragment {
	
	private TextView mfromTimeTextView;
	private TextView mToTimeTextView;
	private TextView mTemperatureTextView;
	private TextView mPrecipitationTextView;
	private TextView mWindSpeedTextview;
	private TextView mWindDirTextView;
	private ImageView mSymbolImageView;
		
	private IWeatherData data;
	
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
		/*View v= inflater.inflate(R.layout.period_fragment, container,false);
		setUpTextViews(v);
		// setupSymbol();*/
		return container;
	}

	/***
	 * Sets up TextVievs  
	 * @param v
	 */
	private void setUpTextViews(View v) {
		/*mfromTimeTextView = (TextView) v.findViewById(R.id.fromTimeTextView);
		mToTimeTextView = (TextView) v.findViewById(R.id.toTimeTextView);
		mTemperatureTextView = (TextView) v.findViewById(R.id.tempTextView);
		mPrecipitationTextView = (TextView) v.findViewById(R.id.precipitationTextview);
		mWindDirTextView = (TextView) v.findViewById(R.id.windDirTextView);
		mWindSpeedTextview = (TextView) v.findViewById(R.id.windSpeedTextView);
		
		//mfromTimeTextView.setText(data.getTime());
		//mToTimeTextView.setText(data.getEndTime());
		mTemperatureTextView.setText(data.getTemperature().getTemperature());
		mPrecipitationTextView.setText(data.getPrecipitation().getValue());
		
		YrWindDirection dir= data.getWindDirection();
		mWindDirTextView.setText(dir.getCode()+" : "+ dir.getDegree());
		mWindSpeedTextview.setText(data.getWindSpeed().getMps());*/
	}
}
