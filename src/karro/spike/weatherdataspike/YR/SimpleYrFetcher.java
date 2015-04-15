/**
 * 
 */
package karro.spike.weatherdataspike.YR;

import java.io.IOException;
import java.util.ArrayList;

import karro.spike.weatherdataspike.DataFetcher;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import android.net.Uri;
import android.util.Log;

/**fetches data from YR
 * b�r startas i en bakgrundstr�d
 * 
 * @author Karro
 *
 */
public class SimpleYrFetcher extends DataFetcher {

	private static final String TAG = "SimpleYrFetcher";
	private static final String K�GE = "http://www.yr.no/sted/Sverige/V�sterbotten/K�ge/varsel_time_for_time.xml";
	private static final String START = "http://www.yr.no/place/";
	private static final String HOUR_BY_HOUR ="varsel_time_for_time.xml";

	
	/***
	 * fetches forecast for a position
	 * @param landRegionStad
	 * @return
	 */
	public YrRootWeatherData fetchForecast(String landRegionStad){
		
		landRegionStad = landRegionStad.replace(' ', '_');
		
		Log.v(TAG,"landregionstad"+ landRegionStad);
		String url = Uri.parse(START).buildUpon()
				.appendEncodedPath(landRegionStad).appendEncodedPath(HOUR_BY_HOUR)
				.build().toString();				
		return getForecastFromYR(url);
	}
	
	/*** gets data for hardcodedposition warning!
	 * b�r startas i en bakgrundstr�d
	 * @return
	 */
	public YrRootWeatherData fetchForecast(){
		return getForecastFromYR(null);
	}
	
	/**Gets data from YR
	 * @return a {@link YrRootWeatherData}
	 */
	private YrRootWeatherData getForecastFromYR(String url) {

		YrRootWeatherData wdata=null;
		//YrForecast forecast=null;
		try{
			if(url==null){
			 url = Uri.parse(K�GE).buildUpon().build().toString();
			}
			Log.i(TAG,"URL: "+url);
			String xmlString =getUrl(url);
			//Log.i(TAG,"XMLstr�ng fr�n YR:"+ xmlString);
			//Log.i(TAG,"URL till yr: "+url);
			Log.i(TAG,"XMLstr�ng fr�n YR: h�mtad");

			Serializer serializer= new Persister();		
			wdata =serializer.read(YrRootWeatherData.class, xmlString);

		}catch ( IOException ioe){
			Log.e(TAG, "Failed  to fetch items", ioe);
		} /*catch (XmlPullParserException e) {
			Log.e(TAG, "Failed  to parse items", e);
		}*/ catch (Exception e) {
			Log.e(TAG, "Failed  to parse items", e);
		}

		return wdata;
	}
	
	/** for testing only
	 * @return
	 */
	private ArrayList<YrWetherData> fejkImplementation() {
		ArrayList<YrWetherData> items = new ArrayList<YrWetherData>();
		YrRootWeatherData wdata=null;
		YrForecast data =null;
		try{
			String xmlString = "\r\n" +"<weatherdata>"
					+		"<forecast>" 
					+		"<tabular>"
					+		"<time from=\"2015-03-03T12:00:00\" to=\"2015-03-03T18:00:00\" period=\"2\">" +"\r\n"
					+        "<!-- Valid from 2015-03-03T12:00:00 to 2015-03-03T18:00:00 -->"
					+        "<symbol number=\"4\" numberEx=\"4\" name=\"Skyet\" var=\"04\" />" + "\r\n"
					+        "<precipitation value=\"0.1\" minvalue=\"0\" maxvalue=\"0.1\" />" + "\r\n"
					+        "<!-- Valid at 2015-03-03T12:00:00 -->" + "\r\n"
					+        "<windDirection deg=\"167.4\" code=\"SSE\" name=\"S�r-s�r�st\" />" + "\r\n"
					+        "<windSpeed mps=\"2.5\" name=\"Svak vind\" />" + "\r\n"
					+        "<temperature unit=\"celsius\" value=\"2\" />" + "\r\n"
					+        "<pressure unit=\"hPa\" value=\"999.3\" />" + "\r\n"
					+      "</time>"
					+ "<time from=\"2015-03-03T12:00:00\" to=\"2015-03-03T18:00:00\" period=\"2\">" +"\r\n"
					+        "<!-- Valid from 2015-03-03T12:00:00 to 2015-03-03T18:00:00 -->"
					+        "<symbol number=\"4\" numberEx=\"4\" name=\"Skyet\" var=\"04\" />" + "\r\n"
					+        "<precipitation value=\"0.1\" minvalue=\"0\" maxvalue=\"0.1\" />" + "\r\n"
					+        "<!-- Valid at 2015-03-03T12:00:00 -->" + "\r\n"
					+        "<windDirection deg=\"167.4\" code=\"SSE\" name=\"S�r-s�r�st\" />" + "\r\n"
					+        "<windSpeed mps=\"2.5\" name=\"Svak vind\" />" + "\r\n"
					+        "<temperature unit=\"celsius\" value=\"2\" />" + "\r\n"
					+        "<pressure unit=\"hPa\" value=\"999.3\" />" + "\r\n"
					+      "</time>"
					+		"</tabular>" +"</forecast>" +"</weatherdata>";
			Log.i(TAG,"XMLstr�ng fr�n fejk:"+ xmlString);

			Serializer serializer= new Persister();

			wdata =serializer.read(YrRootWeatherData.class, xmlString);
			//items

		}catch ( IOException ioe){
			Log.e(TAG, "Failed  to fetch items", ioe);
		} /*catch (XmlPullParserException e) {
			Log.e(TAG, "Failed  to parse items", e);
		}*/ catch (Exception e) {
			Log.e(TAG, "Failed  to parse items", e);
		}

		Log.e(TAG,"parse Success : "+ wdata);
		items.addAll(wdata.getForeCast().getList());
		return items;
	}



}
