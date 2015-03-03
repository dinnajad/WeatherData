package karro.spike.weatherdataspike;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.net.Uri;
import android.util.Log;

public class YrFetcher {

	private static final String  KÅGE = "http://www.yr.no/sted/Sverige/Västerbotten/Kåge/forecast.xml";
	
	public void fetchItems(){
		try{
			String url = Uri.parse(KÅGE).buildUpon().build().toString();
		
		String xmlString =getUrl(url);
		Log.i("YrFetcher","	recieved xml:" + xmlString);
		}catch ( IOException ioe){
			Log.e("YrFetcher", "Failed  to fetch items", ioe);
		}
	}
	
	byte[] getUrlBytes(String urlSpec) throws IOException{

		URL url = new URL(urlSpec);
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();

		try{
			ByteArrayOutputStream out= new ByteArrayOutputStream();                                                             
			InputStream in = connection.getInputStream();

			if(connection.getResponseCode() != HttpURLConnection.HTTP_OK){
				return null;
			}

			int bytesRead = 0;
			byte[] buffer = new byte[1024];
			
			while((bytesRead = in.read(buffer))>0){
				out.write(buffer, 0, bytesRead);
			}
			
			out.close();
			return out.toByteArray();

		}finally{
			connection.disconnect();
		}	
	}
	
	public String getUrl(String urlSpec) throws IOException{
			
		return new String(getUrlBytes(urlSpec));		
	}
	
	
}
