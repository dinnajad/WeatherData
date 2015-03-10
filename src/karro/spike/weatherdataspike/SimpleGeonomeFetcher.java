package karro.spike.weatherdataspike;

import java.io.IOException;
import java.util.ArrayList;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.xmlpull.v1.XmlPullParserException;

import android.net.Uri;
import android.util.Log;

public class SimpleGeonomeFetcher extends DataFetcher{
	private static final String TAG = "GeonamesFetcher";
	
	private static final String PLACE_STARTPOINT = "http://api.geonames.org/findNearbyPlaceName?";
	private static final String GEOID_STARTPOINT ="http://api.geonames.org/get?";
	
	private static final String USERNAME = "frostVakt";
	
	public ArrayList<GeonamesPosition> fetchItems(){
		//return fetchItems(64.8355f, 20.98453f); //TODO dont use hardcoded position K�GE		
		return fetchItems(605428);
	}
	
	/***
	 * 
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
			
			
			//Reader reader = new StringReader(xmlString);// kanske inte n�dv�ndigt finns en read dom tar string direkt
			Serializer serializer = new Persister();
			//GeonamesPosition position= serializer.read(GeonamesPosition.class, xmlString);
			//positions.add();
			positions =((Geonames) serializer.read(Geonames.class, xmlString)).toArrayList();
		}catch ( IOException ioe){
			Log.e(TAG, "Failed  to fetch items", ioe);
		} catch (XmlPullParserException xppe) {
			Log.e(TAG, "Failed  to parse items", xppe);
			//xppe.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "Failed  to parse items, general exception", e);
		}
		
		return positions;
	}
	
	/***
	 * H�mtar positionsdata utifr�n geonameId
	 * @param geoId
	 * @return
	 */
	public ArrayList<GeonamesPosition> fetchItems(int geoId){
		ArrayList<GeonamesPosition> positions= new ArrayList<GeonamesPosition>();
				
		try{
			String url = Uri.parse(GEOID_STARTPOINT).buildUpon()
					.appendQueryParameter("geonameId", "605428")
					.appendQueryParameter("username", USERNAME)
					.build().toString();
			Log.i(TAG,"querystring: " + url);
			String xmlString = getUrl(url);
			Log.i(TAG,"	recieved xml:" + xmlString);
			
			Serializer serializer = new Persister();
			//positions =((Geonames) serializer.read(Geonames.class, xmlString)).toArrayList();
			GeonamesPosition position= serializer.read(GeonamesPosition.class, xmlString);
			positions.add(position);
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
	 * 
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
		GeonamesPosition position;
		Serializer serializer = new Persister();
		try{
		positions =((Geonames) serializer.read(Geonames.class, xmlString)).toArrayList();
		//position= serializer.read(GeonamesPosition.class, xmlString);
		//positions.add(position);
	}catch ( IOException ioe){
		Log.e(TAG, "Failed  to fetch items", ioe);
	} catch (XmlPullParserException xppe) {
		Log.e(TAG, "Failed  to parse items", xppe);
		//xppe.printStackTrace();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		Log.e(TAG, "Failed  to parse items, general exception", e);
	}

		return positions;		
	}
}