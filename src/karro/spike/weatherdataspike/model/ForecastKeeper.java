/**
 * 
 */
package karro.spike.weatherdataspike.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

import karro.spike.weatherdataspike.YR.Forecast;
import karro.spike.weatherdataspike.YR.IWeatherData;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import android.content.Context;
import android.util.Log;

/**
 * @author Karro
 *
 */
@Root(strict=false)
public class ForecastKeeper {
	private static final String TAG = "ForecastKeeper";

	@Element
	private  Forecast currentForecast;
	@ElementList
	private ArrayList<Alarm> alarms;

	private ArrayList<OneDayWeatherData> dataPerDay ;

	public ForecastKeeper(){
		alarms = new ArrayList<Alarm>();

	}

	public boolean saveForecast(Forecast fc){
		if(fc == null)return false;

		//forecasts.add(fc);
		currentForecast = fc;
		Log.v(TAG, "forecast saved"+ currentForecast);

		return true;				
	}

	/***
	 * 
	 * @param alarm
	 */
	public void AddAlarm(Alarm alarm){
		if(!alarms.contains(alarm)){
			alarms.add(alarm);
		}
	}

	public OneDayWeatherData getTodaysWeather() {
		if(currentForecast==null){
			//TODO what do? if no currentForecast	
		}
		return ForecastTransformer.getTodaysWeather(currentForecast);		
	}

	public void groupDataPerDay() {
		ArrayList<IWeatherData> lista = new ArrayList<IWeatherData>( currentForecast.getList());
		dataPerDay= ForecastTransformer.groupWeatherDataForDate(lista);		
	}	

	/***
	 * Persists this class to xml file
	 * @param context
	 * @param fileName
	 */
	public void saveToPersistanse(Context context, String fileName){
		//TODO think about using http://stackoverflow.com/questions/4118751/how-do-i-serialize-an-object-and-save-it-to-a-file-in-android instead
		//https://androidresearch.wordpress.com/2013/04/07/caching-objects-in-android-internal-storage/
		Serializer serializer= new Persister();
		FileOutputStream fileOut;
		try {
			fileOut = context.openFileOutput(fileName, Context.MODE_PRIVATE);	
			serializer.write(this, fileOut);
			Log.v(TAG, "save complete");
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}catch (Exception e) {
			Log.e("serializer","the schema for the object is not valid" +e);
			e.printStackTrace();
		}



	}
	/***
	 * 
	 * @param context
	 * @param fileName
	 * @return an instance of forecastKeeper or null if something went wrong in reading file
	 * @throws FileNotFoundException 
	 */
	public static ForecastKeeper readFromFile(Context context, String fileName) throws FileNotFoundException{
		Serializer serializer= new Persister();
		FileInputStream fileIn;
		ForecastKeeper keeper =null;
		try {
			fileIn = context.openFileInput(fileName);
			keeper = serializer.read(ForecastKeeper.class, fileIn);
		} catch (FileNotFoundException fnfe) {
			Log.e(TAG, fnfe.getMessage());
			throw  fnfe;
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}	

		return keeper;			
	}

	/**
	 * @return the currentForecast
	 */
	public Forecast getCurrentForecast() {
		return currentForecast;
	}


	/**
	 * @param currentForecast the currentForecast to set
	 */
	public void setCurrentForecast(Forecast currentForecast) {
		this.currentForecast = currentForecast;
	}


	/**
	 * @return the alarms
	 */
	public ArrayList<IAlarm> getAlarms() {
		ArrayList<IAlarm> iAlarms = new ArrayList<IAlarm>();
		for (Alarm alarm : alarms) {
			if(alarm.getParameter().equals("Temperaturen")){
				TemperatureAlarm temp= new TemperatureAlarm();
				temp.setAlarm(alarm);
				iAlarms.add(temp);
			}
		}
		return iAlarms;
	}


	/**
	 * @param alarms the alarms to set
	 */
	public void setAlarms(ArrayList<Alarm> alarms) {
		this.alarms = alarms;
	}

	private ArrayList< Alarm> fejkDataItems() {

		Alarm ett= new Alarm("temperature","under","3");
		ArrayList<Alarm> alarms = new ArrayList<Alarm>();
		alarms.add(ett);

		return alarms;
	}

	public ArrayList<OneDayWeatherData> getDataPerDay() {
		return dataPerDay;
	}

	public void setDataPerDay(ArrayList<OneDayWeatherData> dataPerDay) {
		this.dataPerDay = dataPerDay;
	}


}
