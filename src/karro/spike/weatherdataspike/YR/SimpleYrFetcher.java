/**
 * 
 */
package karro.spike.weatherdataspike.YR;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.Text;
import org.simpleframework.xml.core.Persister;

import android.net.Uri;
import android.util.Log;
import karro.spike.weatherdataspike.DataFetcher;

/**bör startas i en bakgrundstråd
 * 
 * @author Karro
 *
 */
public class SimpleYrFetcher extends DataFetcher {

	private static final String TAG = "YrFetcher";
	private static final String KÅGE = "http://www.yr.no/sted/Sverige/Västerbotten/Kåge/varsel_time_for_time.xml";

	/***
	 * bör startas i en bakgrundstråd
	 * @return
	 */
	public Forecast fetchForecast(){
		return realImplementation();
	}
	/***
	 * bör startas i en bakgrundstråd
	 * @return
	 */
	public ArrayList<YrWetherData> fetchItems(){
		return realImplementation1();
	}

	private ArrayList<YrWetherData> realImplementation1() {
		ArrayList<YrWetherData> items = new ArrayList<YrWetherData>();
		items.addAll( realImplementation().getList());
		return items;
	}

	/**
	 * @return
	 */
	private Forecast realImplementation() {

		WeatherData wdata=null;
		//Forecast forecast=null;
		try{
			String url = Uri.parse(KÅGE).buildUpon().build().toString();//TODO use savedPosition instead of hardcoded

			String xmlString =getUrl(url);
			Log.i(TAG,"XMLsträng från YR:"+ xmlString);
			//Log.i(TAG,"XMLsträng från YR: hämtad");

			Serializer serializer= new Persister();		
			wdata =serializer.read(WeatherData.class, xmlString);

		}catch ( IOException ioe){
			Log.e(TAG, "Failed  to fetch items", ioe);
		} /*catch (XmlPullParserException e) {
			Log.e(TAG, "Failed  to parse items", e);
		}*/ catch (Exception e) {
			Log.e(TAG, "Failed  to parse items", e);
		}

		return wdata.getForeCast();
	}

	/**
	 * @return
	 */
	private ArrayList<YrWetherData> fejkImplementation() {
		ArrayList<YrWetherData> items = new ArrayList<YrWetherData>();
		WeatherData wdata=null;
		Forecast data =null;
		try{
			String xmlString = "\r\n" +"<weatherdata>"
					+		"<forecast>" 
					+		"<tabular>"
					+		"<time from=\"2015-03-03T12:00:00\" to=\"2015-03-03T18:00:00\" period=\"2\">" +"\r\n"
					+        "<!-- Valid from 2015-03-03T12:00:00 to 2015-03-03T18:00:00 -->"
					+        "<symbol number=\"4\" numberEx=\"4\" name=\"Skyet\" var=\"04\" />" + "\r\n"
					+        "<precipitation value=\"0.1\" minvalue=\"0\" maxvalue=\"0.1\" />" + "\r\n"
					+        "<!-- Valid at 2015-03-03T12:00:00 -->" + "\r\n"
					+        "<windDirection deg=\"167.4\" code=\"SSE\" name=\"Sør-sørøst\" />" + "\r\n"
					+        "<windSpeed mps=\"2.5\" name=\"Svak vind\" />" + "\r\n"
					+        "<temperature unit=\"celsius\" value=\"2\" />" + "\r\n"
					+        "<pressure unit=\"hPa\" value=\"999.3\" />" + "\r\n"
					+      "</time>"
					+ "<time from=\"2015-03-03T12:00:00\" to=\"2015-03-03T18:00:00\" period=\"2\">" +"\r\n"
					+        "<!-- Valid from 2015-03-03T12:00:00 to 2015-03-03T18:00:00 -->"
					+        "<symbol number=\"4\" numberEx=\"4\" name=\"Skyet\" var=\"04\" />" + "\r\n"
					+        "<precipitation value=\"0.1\" minvalue=\"0\" maxvalue=\"0.1\" />" + "\r\n"
					+        "<!-- Valid at 2015-03-03T12:00:00 -->" + "\r\n"
					+        "<windDirection deg=\"167.4\" code=\"SSE\" name=\"Sør-sørøst\" />" + "\r\n"
					+        "<windSpeed mps=\"2.5\" name=\"Svak vind\" />" + "\r\n"
					+        "<temperature unit=\"celsius\" value=\"2\" />" + "\r\n"
					+        "<pressure unit=\"hPa\" value=\"999.3\" />" + "\r\n"
					+      "</time>"
					+		"</tabular>" +"</forecast>" +"</weatherdata>";
			Log.i(TAG,"XMLsträng från fejk:"+ xmlString);

			Serializer serializer= new Persister();

			wdata =serializer.read(WeatherData.class, xmlString);
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
