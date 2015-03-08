package karro.spike.weatherdataspike;

import java.io.IOException;
import java.util.ArrayList;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.xmlpull.v1.XmlPullParserException;

import android.R.xml;
import android.net.Uri;
import android.util.Log;

public class SimpleGeonomeFetcher extends DataFetcher{
	private static final String TAG = "GeonamesFetcher";
	private static final String PLACE_STARTPOINT = "http://api.geonames.org/findNearbyPlaceName?";
	private static final String REGION_STARTPOINT = "http://api.geonames.org/countrySubdivision?";
	private static final String USERNAME = "frostVakt";
	
	
	private static final String XML_START_TAG = "geoname";
	
	private static final String XML_NAME = "name";
	private static final String XML_LAT = "lat";
	private static final String XML_LNG = "lng";
	private static final String XML_GEOID = "geonameId";
	private static final String XML_COUNTRY = "countryName";
	private static final String XML_REGION = "adminName1";
	
	public ArrayList<GeonamesPosition> fetchItems(){
		return fetchItems(64.8355f, 20.98453f); //TODO dont use hardcoded position KÅGE
		
	}
	
	/***
	 * 
	 * @param latitude
	 * @param longitude
	 */
	public ArrayList<GeonamesPosition> fetchItems(float latitude, float longitude){
		GeonamesPositionList positions= new GeonamesPositionList();
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
			
			
			//Reader reader = new StringReader(xmlString);// kanske inte nödvändigt finns en read dom tar string direkt
			Serializer serializer = new Persister();
			GeonamesPosition position= serializer.read(GeonamesPosition.class, xmlString);
			//positions.add();
			
		}catch ( IOException ioe){
			Log.e(TAG, "Failed  to fetch items", ioe);
		} catch (XmlPullParserException xppe) {
			Log.e(TAG, "Failed  to parse items", xppe);
			//xppe.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "Failed  to parse items, general exception", e);
		}
		
		return positions.toArrayList();
	}
	
	/***
	 * 
	 * @return
	 */
	public  ArrayList<GeonamesPosition> fetchPositions(){
		String xmlString ="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>"
				
				+ "<geoname>"
				+ "<toponymName>Kåge</toponymName>"
				+ "<name>Kåge</name>"
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
				+ "<adminName1>Västerbotten</adminName1>"
				//+ "<adminCode2>2482</adminCode2>"
				//+ "<adminName2>Skellefteå Kommun</adminName2>"
				//+ "<alternateName lang=\"link\">http://en.wikipedia.org/wiki/K%C3%A5ge</alternateName>"
				//+ "<timezone dstOffset=\"2.0\" gmtOffset=\"1.0\">Europe/Stockholm</timezone>"
				//+ "<bbox>"
				//+ "<west>20.97395</west>"
				//+ "<north>64.8402</north>"
				//+ "<east>20.99511</east>"
				//+ "<south>64.83121</south>"
				//+ "</bbox>"
				+ "</geoname>"
				;

		Log.i(TAG,"	recieved xml:" + xmlString);
		//GeonamesPositionList positions= new GeonamesPositionList();
		ArrayList<GeonamesPosition> positions = new ArrayList<GeonamesPosition>();
		GeonamesPosition position;
		Serializer serializer = new Persister();
		try{
		//positions =((GeonamesPositionList) serializer.read(GeonamesPositionList.class, xmlString)).toArrayList();
		position= serializer.read(GeonamesPosition.class, xmlString);
		positions.add(position);
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
