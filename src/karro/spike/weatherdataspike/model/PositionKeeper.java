/**
 * 
 */
package karro.spike.weatherdataspike.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

import karro.spike.weatherdataspike.Geonames.GeonamesPosition;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import android.content.Context;
import android.util.Log;

/**
 * @author Karro
 *
 */
public class PositionKeeper {
	private static final String TAG = "PositionKeeper";
	private static final String FILENAME = "Positions.xml";

	@ElementList(required=false)
	private ArrayList<IPosition> savedPositions;
	@Element(required=false)
	private IPosition favouritePosition;
	@ElementList(required=false)
	private ArrayList<IPosition> textSearchedPositions;

	public  PositionKeeper(){
		savedPositions = new ArrayList<IPosition>();
		textSearchedPositions = new ArrayList<IPosition>();

		favouritePosition = new GeonamesPosition();//Have to have a default...
		favouritePosition.setCountryName("France");
		favouritePosition.setRegion("Île-de-France");
		favouritePosition.setId(2988507);
		favouritePosition.setName("Paris");

	}

	/***
	 * 
	 * @param position
	 */
	public void AddPosition(IPosition position){
		if(!savedPositions.contains(position)){
			savedPositions.add(position);

		}
	}

	/***
	 * 
	 * @param position
	 */
	public void SetFavouritePosition(IPosition position){
		favouritePosition = position;
		Log.w(TAG,"Favourite Position Set");
	}

	/***
	 * Persists this class to xml file
	 * @param context
	 * @param fileName
	 */
	public void saveToPersistanse(Context context){
		//TODO think about using http://stackoverflow.com/questions/4118751/how-do-i-serialize-an-object-and-save-it-to-a-file-in-android instead
		//https://androidresearch.wordpress.com/2013/04/07/caching-objects-in-android-internal-storage/
		Serializer serializer= new Persister();
		FileOutputStream fileOut;
		try {
			fileOut = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);	
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
	public static PositionKeeper readFromFile(Context context) throws FileNotFoundException{
		Serializer serializer= new Persister();
		FileInputStream fileIn;
		PositionKeeper keeper =null;
		try {
			fileIn = context.openFileInput(FILENAME);
			keeper = serializer.read(PositionKeeper.class, fileIn);
		} catch (FileNotFoundException fnfe) {
			Log.e(TAG, fnfe.getMessage());
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}	
		if(keeper==null){
			keeper=new PositionKeeper();
			keeper.saveToPersistanse(context);
		}
		return keeper;			
	}
	/**
	 * @return the savedPositions
	 */
	public ArrayList<IPosition> getSavedPositions() {
		return savedPositions;
	}

	/**
	 * @param savedPositions the savedPositions to set
	 */
	public void setSavedPositions(ArrayList<IPosition> savedPositions) {
		this.savedPositions = savedPositions;
	}

	/**
	 * @return the favouritePosition
	 */
	public IPosition getFavouritePosition() {
		return favouritePosition;
	}

	/**
	 * @return the textSearchedPositions
	 */
	public ArrayList<IPosition> getTextSearchedPositions() {
		return textSearchedPositions;
	}

	/**
	 * @param textSearchedPositions the textSearchedPositions to set
	 */
	public void setTextSearchedPositions(ArrayList<IPosition> textSearchedPositions) {
		this.textSearchedPositions = textSearchedPositions;
	}



}
