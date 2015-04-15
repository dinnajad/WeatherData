/**
 * 
 */
package karro.spike.weatherdataspike.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

import karro.spike.weatherdataspike.YR.YrForecast;
import karro.spike.weatherdataspike.YR.YrRootWeatherData;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import android.content.Context;
import android.util.Log;

/**Keeps the forecasts and reads /ssaves to file
 * @author Karro
 *
 */
@Root(strict=false)
public class ForecastKeeper {
	private static final String TAG = "ForecastKeeper";
	public static final String FORE_CAST_XML = "ForeCast.xml";

	@Element(required=false)
	private  YrForecast currentForecast;

	@Element(required=false) 
	private YrRootWeatherData mRootData;

	@ElementList
	private ArrayList<Alarm> alarms;

	private ArrayList<OneDayWeatherData> dataPerDay ;

	public ForecastKeeper(){
		alarms = new ArrayList<Alarm>();

	}

	/***
	 * adds the forecast
	 * @param fc
	 * @return
	 */
	public boolean saveForecast(YrForecast fc){
		if(fc == null)return false;

		currentForecast = fc;
		Log.v(TAG, "forecast saved"+ currentForecast);

		return true;				
	}

	/***
	 * gets a forecast of todays weather or null if there is none
	 * @return
	 */
	public OneDayWeatherData getTodaysWeather() {
		if(currentForecast==null){
			// what do? if no currentForecast
			return null;
		}
		return ForecastTransformer.getTodaysWeather(currentForecast);		
	}

	/***
	 * groups forecastdata in 24h periods
	 */
	public void groupDataPerDay() {
		ArrayList<IWeatherData> lista = new ArrayList<IWeatherData>( currentForecast.getList());
		dataPerDay= ForecastTransformer.groupWeatherDataFor24h(lista);		
	}	

	/***
	 * Persists this class to xml file
	 * @param context
	 * @param fileName
	 */
	public void saveToPersistanse(Context context){
		//think about using http://stackoverflow.com/questions/4118751/how-do-i-serialize-an-object-and-save-it-to-a-file-in-android instead
		//https://androidresearch.wordpress.com/2013/04/07/caching-objects-in-android-internal-storage/
		Serializer serializer= new Persister();
		FileOutputStream fileOut;
		try {
			fileOut = context.openFileOutput(FORE_CAST_XML, Context.MODE_PRIVATE);	
			serializer.write(this, fileOut);
			Log.v(TAG, "save complete");
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}catch (Exception e) {
			Log.e(TAG+"serializer","the schema for the object is not valid" +e);
			e.printStackTrace();
		}
	}

	/***
	 * @param context
	 * @param fileName
	 * @return an instance of forecastKeeper or null if something went wrong in reading file
	 * @throws FileNotFoundException 
	 */
	public static ForecastKeeper readFromFile(Context context) throws FileNotFoundException{
		Serializer serializer= new Persister();
		FileInputStream fileIn;
		ForecastKeeper keeper =null;
		try {
			fileIn = context.openFileInput(FORE_CAST_XML);
			keeper = serializer.read(ForecastKeeper.class, fileIn);
		} catch (FileNotFoundException fnfe) {
			Log.e(TAG, "Reading defaultFile instead"+fnfe.getMessage());
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}	

		return keeper;			
	}

	/**
	 * @return the currentForecast
	 */
	public YrForecast getCurrentForecast() {
		return currentForecast;
	}


	/**
	 * @param currentForecast the currentForecast to set
	 */
	public void setCurrentForecast(YrForecast currentForecast) {
		this.currentForecast = currentForecast;
	}


	public ArrayList<OneDayWeatherData> getDataPerDay() {
		return dataPerDay;
	}

	public void setDataPerDay(ArrayList<OneDayWeatherData> dataPerDay) {
		this.dataPerDay = dataPerDay;
	}

	public void saveRootData(YrRootWeatherData prediction) {
		mRootData = prediction;		
	}

	public YrRootWeatherData getRootWeatherData(){
		return mRootData;
	}

}
