/**
 * 
 */
package karro.spike.weatherdataspike.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

import org.simpleframework.xml.ElementArray;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import android.content.Context;
import android.util.Log;

/**Keeps the weatherwarnings
 * @author Karro
 *
 */
@Root(strict=false)
public class WeatherWarningKeeper {

	private static final String FILENAME = "WeatherWarnings.xml";
	private static final String TAG ="WeatherWarningKeeper";

	@ElementList
	private ArrayList<WeatherWarning> warnings;

	/***
	 * Constructor
	 */
	public WeatherWarningKeeper(){
		warnings = new ArrayList<WeatherWarning>();
	}

	/***
	 * creates a new WeatherWarningKeeper loaded with data from file
	 * @return WeatherWarningKeeper loaded with data from file or an empty one if file could not be found
	 */
	public static WeatherWarningKeeper readFromFile(Context context) {

		Serializer serializer = new Persister();
		FileInputStream fileIn;
		WeatherWarningKeeper keeper =null;
		try {
			fileIn = context.openFileInput(FILENAME);
			keeper = serializer.read(WeatherWarningKeeper.class, fileIn);
		} catch (FileNotFoundException fnfe) {
			Log.e(TAG, "Reading defaultFile instead"+fnfe.getMessage());
			keeper = new WeatherWarningKeeper();
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
			keeper = new WeatherWarningKeeper(); // jag vet inte vad som skulle kunna orsaka det här så jag skapar en ny
		}	

		return keeper;			
	}

	//TODO Remove code duplication by generate an abstract super class?
	/***
	 *  Persists this class to xml file
	 * @param context
	 */
	public void saveToPersistence(Context context){
		Serializer serializer= new Persister();
		FileOutputStream fileOut;
		try {
			fileOut = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);	
			serializer.write(this, fileOut);
			Log.v(TAG, "save complete");
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}catch (Exception e) {
			Log.e(TAG+"serializer","the schema for the object is not valid " +e);
			e.printStackTrace();
		}
	}

	/***
	 * gets the warnings
	 * @return the warnings
	 */
	public ArrayList<WeatherWarning> getWarnings() {

		return warnings;
	}

	/***
	 * Adds a warning
	 * @param warning
	 */
	public void addWarning(WeatherWarning warning) {
		if(!warnings.contains(warning)){
			warnings.add(warning);
		}
	}

	/***
	 * Clears the list of warnings
	 */
	public void clearWarnings() {
		warnings.clear();		
	}

}
