/**
 * 
 */
package karro.spike.weatherdataspike.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import android.content.Context;
import android.util.Log;

/**Keeps the Alarms and saves/reads from file
 * @author Karro
 *
 */
@Root(strict=false)
public class AlarmKeeper {
	private static final String TAG ="AlarmKeeper";


	private static final String FILENAME = "Alarms.xml";

	@ElementList(required=false)
	private ArrayList<Alarm> alarms;

	public AlarmKeeper(){
		alarms = new ArrayList<Alarm>();
	}
	
	/***
	 * Add a new alarm if it not already exists
	 * @param alarm
	 */
	public void AddAlarm(Alarm alarm){
		if(!alarms.contains(alarm)){
			alarms.add(alarm);
		}
	}

	/***
	 * Removes an Alarm
	 * @param ialarm
	 */
	public void RemoveAlarm(IAlarm ialarm){
		Alarm alarm = ialarm.getAlarm();
		if(alarms.contains(alarm)){
			alarms.remove(alarm);
		}else{
			Log.e(TAG, "no item found in alarmList " +alarm.toString());
		}
	}

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
			Log.e(TAG+"serializer","the schema for the object is not valid" +e);
			e.printStackTrace();
		}
	}

	/*** Creates a AlarmKeeper loaded with the data from file
	 * @param context
	 * @return an instance of forecastKeeper or null if something went wrong in reading file
	 * @throws FileNotFoundException 
	 */
	public static AlarmKeeper readFromFile(Context context) {
		Serializer serializer= new Persister();
		FileInputStream fileIn;
		AlarmKeeper keeper =null;
		try {
			fileIn = context.openFileInput(FILENAME);
			keeper = serializer.read(AlarmKeeper.class, fileIn);
		} catch (FileNotFoundException fnfe) {
			Log.e(TAG, "Reading defaultFile instead" + fnfe.getMessage());
			keeper = new AlarmKeeper();
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
			keeper = new AlarmKeeper(); //TODO jag vet inte vad som skulle kunna orsaka det här så jag skapar en ny för att slippa tänka just nu
		}	

		return keeper;			
	}

	/**Returns the alarms and wrapps them in wrappers
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
}
