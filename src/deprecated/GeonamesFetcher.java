/**
 * 
 */
package deprecated;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import karro.spike.weatherdataspike.DataFetcher;
import karro.spike.weatherdataspike.Geonames.GeonamesPosition;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.net.Uri;
import android.util.Log;

/**
 * @author Karro
 *
 */
public class GeonamesFetcher  extends DataFetcher{

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
	
	// spara koordinater att hämta från?
	
	public ArrayList<GeonamesPosition> fetchItems(){
		return fetchItems(64.8355f, 20.98453f); //TODO dont use hardcoded position KÅGE
		
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
			
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = factory.newPullParser();
			parser.setInput(new StringReader(xmlString));
			positions =parseItems(parser);
		}catch ( IOException ioe){
			Log.e(TAG, "Failed  to fetch items", ioe);
		} catch (XmlPullParserException xppe) {
			Log.e(TAG, "Failed  to parse items", xppe);
			//xppe.printStackTrace();
		}
		return positions;
	}
	
	/***
	 * parses items from XML to ArrayList<GeonamesPosition>
	 * @param parser
	 * @return
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	public ArrayList<GeonamesPosition> parseItems(XmlPullParser parser)
	throws XmlPullParserException, IOException{
		ArrayList<GeonamesPosition> positions = new ArrayList<GeonamesPosition>();
		int eventType = parser.next();		
		//TODO  ta bort fejkdata item
		GeonamesPosition fejkDataItem = new GeonamesPosition();
		fejkDataItem.setCountryName("SWEDEN fejk");
		fejkDataItem.setName(USERNAME);
		positions.add(fejkDataItem);
		
		while (eventType != XmlPullParser.END_DOCUMENT){
			
			String name = parser.getName();
			if(eventType == XmlPullParser.START_TAG && XML_START_TAG.equals(name)){
				
				//skapa nytt dataItem
				GeonamesPosition dataItem = parseADataItem(parser, eventType,name);
				
				//lägg till i listan
				positions.add(dataItem);
			}
			
			//eventType = parser.nextTag();//Här blir det fel vid END_Document behöver en funktion som tar nästa om det inte är END_Document
			eventType= skipTag(parser);
			
			name = parser.getName();
			Log.i(TAG, "hittat="  + name + " , skippar"+ " eventtype = "+ eventType);
		}
		
		
		return positions;
		
	}

	/**
	 * @param parser
	 * @param eventType
	 * @param name
	 * @return
	 * @throws IOException 
	 * @throws XmlPullParserException 
	 */
	private GeonamesPosition parseADataItem(XmlPullParser parser,
			int eventType, String name) throws XmlPullParserException, IOException {
		//skapa nytt dataItem
		GeonamesPosition dataItem = new GeonamesPosition();
		//fyll med data
		
		while (eventType != XmlPullParser.END_TAG && eventType !=XmlPullParser.END_DOCUMENT) {
			Log.i(TAG, ":" + name);
			if(eventType == XmlPullParser.START_TAG){
				switch(name){ //den stannar här blir aldrig annat än geoname
				case XML_START_TAG:
					Log.i(TAG, "case:" + name);
					skipTag(parser);
					break;
				case "toponymName":
					Log.i(TAG, "case:" + name);
					break;
					
				case XML_NAME://do something
					String positionName=  parser.getAttributeValue(null, XML_NAME);
					dataItem.setName("positionName");
					Log.i(TAG, "case:" + name+ "PositionName: "+ positionName);
					break;
					
			/*	case XML_LAT:
					String lat =  parser.getAttributeValue(null, XML_LAT);
					dataItem.setLat("lat");
					Log.i(TAG, "case:" + name);
					break;
					
				case XML_LNG:
					String lng = parser.getAttributeValue(null, XML_LNG);
					dataItem.setLng("lng");
					Log.i(TAG, "case:" + name);
					break;*/
					
				case XML_GEOID:
					String id = parser.getAttributeValue(null, XML_GEOID);
					dataItem.setGeonameId("id");
					Log.i(TAG, "case:" + name);
					break;
					
				case XML_COUNTRY:
					String country = parser.getAttributeValue(null, XML_COUNTRY);
					dataItem.setCountryName("country");
					Log.i(TAG, "case:" + name);
					break;
					
				case XML_REGION:
					String region = parser.getAttributeValue(null, XML_REGION);
					dataItem.setRegion("storregion");
					Log.i(TAG, "case:" + name);
					break;
				
				default: //Skip tag
					//eventType= skipTag(parser);
					break;									
				}
			}else
				Log.i(TAG, "ELSE:" + name);
			
			/*eventType = parser.nextTag();
			name = parser.getName();
			Log.i(TAG, "hittat="  + name + " , skipping"+ " eventtype = "+ eventType);
			eventType = parser.nextTag();
			name = parser.getName();
			Log.i(TAG, "hittat="  + name + " , skipping" + " eventtype = "+ eventType);*/
			
			eventType= skipTag(parser); //inte bra lösning hittade EOF
		}
		return dataItem;
	}
	
	private int skipTag(XmlPullParser parser) throws XmlPullParserException, IOException{
		
		int eventType = parser.next();
		int counter = 0;
		
		while(eventType != XmlPullParser.START_TAG){
			String name = parser.getName();	
			String text = parser.getText();
			Log.i(TAG, "default="  + name + " , skipping"+ " eventtype = "+ parser.TYPES[eventType] + ", Text: " + text + ", Count:"+ counter);
			eventType = parser.next();
			counter++;
			if(eventType == XmlPullParser.END_DOCUMENT)
			{
				Log.w(TAG, "HITTADE END_DOCUMENT");
				return eventType;
			}
		}
		
		Log.i(TAG,"EventType"+eventType+"   Count:"+counter );
		return eventType;
	}
	
	/***
	 * Converts latitude or longitude to decimalvalues
	 * @param deg
	 * @param min
	 * @param sec
	 * @return decimal degrees
	 */
	public float convertLatitude(int deg,int min, int sec){
		float result = deg +(min/60) +(sec/3600);
		return result;
				
	}
			
}
