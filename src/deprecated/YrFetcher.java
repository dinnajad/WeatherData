package deprecated;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import karro.spike.weatherdataspike.DataFetcher;
import karro.spike.weatherdataspike.YR.YrWetherData;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.net.Uri;
import android.util.Log;
@Deprecated
public class YrFetcher extends DataFetcher {
	private static final String TAG = "YrFetcher";
	private static final String KÅGE = "http://www.yr.no/sted/Sverige/Västerbotten/Kåge/forecast.xml";
	private static final String XML_TAG = "time";
	private static final String XML_TEMP = "temperature";
	private static final String XML_SYMBOL = "symbol";
	private static final String XML_PRECIPITATION = "precipitation";
	private static final String XML_WIND_DIR = "windDirection";
	private static final String XML_WINDSPEED = "windSpeed";
	private static final String XML_PRESSURE = "pressure";
	
	public ArrayList<YrWetherData> fetchItems(){
		ArrayList<YrWetherData> items = new ArrayList<YrWetherData>();
		try{
			String url = Uri.parse(KÅGE).buildUpon().build().toString();
		
		String xmlString =getUrl(url);
		//Log.i("YrFetcher","	recieved xml:" + xmlString);
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		XmlPullParser parser = factory.newPullParser();
		parser.setInput(new StringReader(xmlString));
		parseItems(items,parser);
		
		}catch ( IOException ioe){
			Log.e(TAG, "Failed  to fetch items", ioe);
		} catch (XmlPullParserException e) {
			Log.e(TAG, "Failed  to parse items", e);
		}
		return items;
	}
	
	void parseItems(ArrayList<YrWetherData> items, XmlPullParser parser)
	throws XmlPullParserException, IOException{
		int eventType = parser.next();
		
		
		while (eventType != XmlPullParser.END_DOCUMENT){
			
			String name = parser.getName();
			if(eventType == XmlPullParser.START_TAG && XML_TAG.equals(name)){
				//skapa nytt dataItem
				YrWetherData dataItem = new YrWetherData();
				
				//läs ut time som är i första taggen på varje tidpunkt				
				parseTime(parser, dataItem);
				
				eventType = parser.nextTag();
				name = parser.getName();
				//Log.i(TAG, "***********innan while: " + name);
				
				while (eventType != XmlPullParser.END_TAG ) {
					Log.i(TAG, ":" + name);
					if(XML_SYMBOL.equals(name)){
						parseSymbol(parser, dataItem);
						//Log.i(TAG, "hittat symbol: " + name);
					}else if(XML_PRECIPITATION.equals(name)){
						//Log.i(TAG, "hittat regn: " + name);
					}else if( XML_TEMP.equals(name)){
						 parseTemperature(parser, dataItem);
						 //Log.i(TAG, "hittat temperatur" + eventType);
					}else if(XML_WIND_DIR.equals(name)){
						//Log.i(TAG, "hittat vindriktning: " + name);
					}else if(XML_WINDSPEED.equals(name)){
						//Log.i(TAG, "hittat vindhastighet: " + name);
					}else if(XML_PRESSURE.equals(name)){
						//Log.i(TAG, "hittat lufttryck: " + name);
						//TODO Parsa ut mer data om behov finns
					}
					
					eventType = parser.nextTag();
					name = parser.getName();
					Log.i(TAG, "hittat="  + name + " , skipping"+ " eventtype = "+ eventType);
					eventType = parser.nextTag();
					name = parser.getName();
					Log.i(TAG, "hittat="  + name + " , skipping" + " eventtype = "+ eventType);
					// fundera ut varför man skriver skipTag två gånger för att det ska 
					//funka först hittar den end taggen sen nästa tagg
				//pressure
				 }
				
				
				
				items.add(dataItem);//och itemet in i listan
			}

			eventType = parser.next();
			
		}
		
	}

	private void parseSymbol(XmlPullParser parser, YrWetherData dataItem) {
		String symbol = parser.getAttributeValue(null, "numberEx");
		//dataItem.setSymbol(symbol);
	}

	/**
	 * @param parser
	 * @param dataItem
	 */
	private void parseTemperature(XmlPullParser parser, YrWetherData dataItem) {
		String temperature= parser.getAttributeValue(null, "value");
		
		//stoppa in strängarna i ett dataItem
		//dataItem.setTemperature(temperature);
		Log.i(TAG, "temperature parsed:" + temperature);
	}

	/**
	 * Parses out the from time
	 * @param parser
	 * @return
	 */
	private String parseTime(XmlPullParser parser ,YrWetherData dataItem) {
		String time = parser.getAttributeValue(null,"from");
		String timeTo = parser.getAttributeValue(null,"to");
		//dataItem.setTime(time);
		//dataItem.setEndTime(timeTo);
		return time;
	}
	
	private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
	    if (parser.getEventType() != XmlPullParser.START_TAG) {
	        throw new IllegalStateException();
	    }
	    int depth = 1;
	    while (depth != 0) {
	        switch (parser.next()) {
	        case XmlPullParser.END_TAG:
	            depth--;
	            break;
	        case XmlPullParser.START_TAG:
	            depth++;
	            break;
	        }
	    }
	 }
	
	
}
