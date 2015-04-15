package karro.spike.weatherdataspike;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/***
 * ParentClass that can fetch data via url.
 * @author Karro
 *
 */
public class DataFetcher {

	public DataFetcher() {
		super();
	}

	/***
	 * getBytearray of data from url. use @getUrl(String urlSpec) to get string instead
	 * @param urlSpec
	 * @return
	 * @throws IOException
	 */
	protected byte[] getUrlBytes(String urlSpec) throws IOException {

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

	/***
	 * gets the data string from an url
	 * @param urlSpec
	 * @return
	 * @throws IOException
	 */
	public String getUrl(String urlSpec) throws IOException {

		return new String(getUrlBytes(urlSpec));		
	}

}