package karro.spike.weatherdataspike;


import java.io.IOException;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

public class WeatherFrameFragment extends Fragment {

	GridView mGridView;
	/***
	 * called from the OS when creating the fragment. 
	 * Initialise essentials here
	 */
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		setRetainInstance(true);
		new FetchItemsTask().execute();
	}
	
	/***
	 * creates the view, connect listeners
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View v= inflater.inflate(R.layout.fragment_main, container,false);
		
		mGridView = (GridView) v.findViewById(R.id.gridView);
		
	return v;
	}

	private class FetchItemsTask extends AsyncTask<Void,Void,Void>{

		@Override
		protected Void doInBackground(Void... params) {
			
			new YrFetcher().fetchItems();
		
			/*
		try{
			String result = new YrFetcher().getUrl("http://www.google.com");
			Log.i("WWFragment " , "Fetched contents of URL:" +result);
		} catch (IOException ioe){
			Log.e("WWfragment ", "Failed to fetch URL: ", ioe);
		}*/
		return null;
		}
		
	}
}
