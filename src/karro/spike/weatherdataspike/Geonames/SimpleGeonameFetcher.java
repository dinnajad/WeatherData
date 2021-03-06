package karro.spike.weatherdataspike.Geonames;

import java.io.IOException;
import java.util.ArrayList;

import karro.spike.weatherdataspike.DataFetcher;
import karro.spike.weatherdataspike.model.IPosition;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.xmlpull.v1.XmlPullParserException;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

/***
 * fetches data from GeonamesDatabase
 * @author Karro
 *
 */
public class SimpleGeonameFetcher extends DataFetcher{
	private static final String TAG = "GeonamesFetcher";
	
	
	private static final String PLACE_STARTPOINT = "http://api.geonames.org/findNearbyPlaceName?";
	private static final String GEOID_STARTPOINT ="http://api.geonames.org/get?";
	
	private static final String USERNAME = "frostVakt";
	private ArrayList<GeonamesPosition> mPositionItems; 
	
	/***
	 * fetches data for a hardcoded position, intended for testing
	 * @return
	 */
	public ArrayList<GeonamesPosition> fetchItems(){
		return fetchItems(64.8355f, 20.98453f); // dont use hardcoded position K�GE		
		//return fetchItems(605428);
	}
	
	/***
	 * Fetches Data based on GPS coordinate
	 * @param latitude
	 * @param longitude
	 */
	public ArrayList<GeonamesPosition> fetchItems(float latitude, float longitude){
		ArrayList<GeonamesPosition> positions= new ArrayList<GeonamesPosition>();
		// convert float to string
		String lat = Float.toString(latitude);
		String lng = Float.toString(longitude);
		try{
			String url = Uri.parse(PLACE_STARTPOINT).buildUpon()
					.appendQueryParameter("lat", lat)
					.appendQueryParameter("lng", lng)
					.appendQueryParameter("username", USERNAME)
					.build().toString();
			Log.i(TAG,"querystring: " + url);
			String xmlString = getUrl(url);
			Log.i(TAG,"	recieved xml:" + xmlString);
			
			Serializer serializer = new Persister();
			
			positions =((Geonames) serializer.read(Geonames.class, xmlString)).toArrayList();
		}catch ( IOException ioe){
			Log.e(TAG, "Failed  to fetch items", ioe);
		} catch (XmlPullParserException xppe) {
			Log.e(TAG, "Failed  to parse items", xppe);
			
		} catch (Exception e) {
			
			Log.e(TAG, "Failed  to parse items, general exception", e);
		}
		
		return positions;
	}
	
	/***
	 * H�mtar positionsdata utifr�n geonameId
	 * @param geoId
	 * @return a positionObject if there is one otherwise null
	 */
	public GeonamesPosition fetchItems(int geoId){
		GeonamesPosition position= null;
		String id = Integer.toString(geoId);
		try{
			String url = Uri.parse(GEOID_STARTPOINT).buildUpon()
					.appendQueryParameter("geonameId", id)
					.appendQueryParameter("username", USERNAME)
					.build().toString();
			Log.i(TAG,"querystring: " + url);
			String xmlString = getUrl(url);
			Log.i(TAG,"	recieved xml:" + xmlString);
			
			Serializer serializer = new Persister();
			position= serializer.read(GeonamesPosition.class, xmlString);
			
		}catch ( IOException ioe){
			Log.e(TAG, "Failed  to fetch items", ioe);
		} catch (XmlPullParserException xppe) {
			Log.e(TAG, "Failed  to parse items", xppe);
		} catch (Exception e) {
			Log.e(TAG, "Failed  to parse items, general exception", e);
		}
		
		return position;
	}
	
	/***
	 * http://api.geonames.org/search?name_startsWith=K�G&maxRows=10&username=frostVakt
	 * H�mtar positionsdata utifr�n text
	 * @param geoId
	 * @return a positionObject if there is one otherwise null
	 */
	public Geonames fetchItemsforString(String name){
		ArrayList<GeonamesPosition> positions= new ArrayList<GeonamesPosition>();
		Geonames position= null;
		try{
			String url = Uri.parse("http://api.geonames.org/search?").buildUpon()
					.appendQueryParameter("name_startsWith", name)
					.appendQueryParameter("username", USERNAME)
					.build().toString();
			Log.i(TAG,"querystring: " + url);
			String xmlString = getUrl(url);
			Log.i(TAG,"	recieved xml:" + xmlString);
			
			Serializer serializer = new Persister();
			Geonames pos = (Geonames) serializer.read(Geonames.class, xmlString);
			positions =pos.toArrayList();
			
		}catch ( IOException ioe){
			Log.e(TAG, "Failed  to fetch items", ioe);
		} catch (XmlPullParserException xppe) {
			Log.e(TAG, "Failed  to parse items", xppe);
		} catch (Exception e) {
			Log.e(TAG, "Failed  to parse items, general exception", e);
		}
		
		return position; //allways returns null , code not finished
	}
	
	public void fetchTextPos(String name){ 
	FetchTextPositionTask task = (FetchTextPositionTask) new FetchTextPositionTask().execute(name);	
	}
	
	/***
	 * Asynk class that fetches position based on a string, result is loaded into mPositionItems
	 * @author Karro
	 *
	 */
	private class FetchTextPositionTask extends AsyncTask<String,Void,Geonames>{

		@Override
		protected Geonames doInBackground(String... params) {
			String name = params[0];			
			return fetchItemsforString(name);
		}

		@Override
		protected void onPostExecute(Geonames positions){
			mPositionItems.addAll( positions.getList());
		
		}

	}
	
	/***
	 * method to test methods witout internetconnection, uses hardcoded string instead. only for TESTING
	 * @return
	 */
	public  ArrayList<GeonamesPosition> fetchPositions(){
		/*String xmlString ="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>"
				
				+ "<geoname>"
				+ "<toponymName>K�ge</toponymName>"
				+ "<name>K�ge</name>"
				+ "<lat>64.83571</lat>"
				+ "<lng>20.98453</lng>"
				+ "<geonameId>605428</geonameId>"
				+ "<countryCode>SE</countryCode>"
				+ "<countryName>Sweden</countryName>"
				+ "<fcl>P</fcl>"
				+ "<fcode>PPL</fcode>"
				//+ "<fclName>city, village,...</fclName>"
				//+ "<fcodeName>populated place</fcodeName>"
				//+ "<population>2205</population>"
				//+ "<asciiName>Kage</asciiName>"
				//+ "<alternateNames/>"
				//+ "<elevation/>"
				//+ "<srtm3>-32768</srtm3>"
				//+ "<continentCode>EU</continentCode>"
				//+ "<adminCode1 ISO3166-2=\"AC\">23</adminCode1>"
				+ "<adminName1>V�sterbotten</adminName1>"
				//+ "<adminCode2>2482</adminCode2>"
				//+ "<adminName2>Skellefte� Kommun</adminName2>"
				//+ "<alternateName lang=\"link\">http://en.wikipedia.org/wiki/K%C3%A5ge</alternateName>"
				//+ "<timezone dstOffset=\"2.0\" gmtOffset=\"1.0\">Europe/Stockholm</timezone>"
				//+ "<bbox>"
				//+ "<west>20.97395</west>"
				//+ "<north>64.8402</north>"
				//+ "<east>20.99511</east>"
				//+ "<south>64.83121</south>"
				//+ "</bbox>"
				+ "</geoname>"
				;*/

		String xmlString ="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>"
				+ "<geonames>"
				+ "<geoname>"
				+ "<toponymName>K�ge</toponymName>"
				+ "<name>K�ge</name>"
				+ "<lat>64.83571</lat>"
				+ "<lng>20.98453</lng>"
				+ "<geonameId>605428</geonameId>"
				+ "<countryCode>SE</countryCode>"
				+ "<countryName>Sweden</countryName>"
				+ "<fcl>P</fcl>"
				+ "<fcode>PPL</fcode>"
				+ "<adminName1>V�sterbotten</adminName1>"
				+ "</geoname>"
				
				+ "<geoname>"
				+ "<toponymName>K�ge</toponymName>"
				+ "<name>K�ge</name>"
				+ "<lat>64.83571</lat>"
				+ "<lng>20.98453</lng>"
				+ "<geonameId>605428</geonameId>"
				+ "<countryCode>SE</countryCode>"
				+ "<countryName>Sweden</countryName>"
				+ "<fcl>P</fcl>"
				+ "<fcode>PPL</fcode>"
				+ "<adminName1>V�sterbotten</adminName1>"
				+ "</geoname>"
				+ "</geonames>";
		
		Log.i(TAG,"	recieved xml:" + xmlString);
		//GeonamesPositionList positions= new GeonamesPositionList();
		ArrayList<GeonamesPosition> positions = new ArrayList<GeonamesPosition>();
		IPosition position;
		Serializer serializer = new Persister();
		try{
		positions =((Geonames) serializer.read(Geonames.class, xmlString)).toArrayList();
		//position= serializer.read(GeonamesPosition.class, xmlString);
		//positions.add(position);
	}catch ( IOException ioe){
		Log.e(TAG, "Failed  to fetch items", ioe);
	} catch (XmlPullParserException xppe) {
		Log.e(TAG, "Failed  to parse items", xppe);
		
	} catch (Exception e) {
		
		Log.e(TAG, "Failed  to parse items, general exception", e);
	}

		return positions;		
	}

	/**
	 * @return the positionItems
	 */
	public ArrayList<GeonamesPosition> getPositionItems() {
		return mPositionItems;
	}

	/**
	 * @param positionItems the positionItems to set
	 */
	public void setPositionItems(ArrayList<GeonamesPosition> positionItems) {
		mPositionItems = positionItems;
	}
}
