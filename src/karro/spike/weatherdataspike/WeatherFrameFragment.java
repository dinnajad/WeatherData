package karro.spike.weatherdataspike;


import java.util.ArrayList;

import karro.spike.weatherdataspike.YR.SimpleYrFetcher;
import karro.spike.weatherdataspike.YR.YrWetherData;
import Geonames.GeonamesPosition;
import Geonames.SimpleGeonomeFetcher;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;

public class WeatherFrameFragment extends Fragment {
	//EditText mEditText;
	GridView mTopGridView;
	GridView mGridView;
	ArrayList<GeonamesPosition> mPositionItems;
	ArrayList<YrWetherData> mItems;
	
	/***
	 * called from the OS when creating the fragment. 
	 * Initialise essentials here
	 */
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		setRetainInstance(true);
		new FetchItemsTask().execute();
		FetchPositionTask background = (FetchPositionTask) new FetchPositionTask().execute();
		//new FetchPositionTask.execute();
		
		//shortcut som hoppar över trådning och hämtning av data från webservice
		//SimpleGeonomeFetcher fetcher = new SimpleGeonomeFetcher();
		//mPositionItems = fetcher.fetchPositions();
	}
	
	/***
	 * creates the view, connect listeners
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View v= inflater.inflate(R.layout.fragment_main, container,false);
		//mEditText = (EditText) v.findViewById(R.id.editText1);
		mTopGridView= (GridView) v.findViewById(R.id.gridView_top);
		mGridView = (GridView) v.findViewById(R.id.gridView_bottom);
		setUpAdapter();
		setUpAdapterTop();
	return v;
	}

	private void setUpAdapter() {
		if ( getActivity() == null || mGridView == null )return;
		if(mItems!=null){
			mGridView.setAdapter(new ArrayAdapter<YrWetherData>(getActivity(), android.R.layout.simple_gallery_item,mItems));
			
		}else{
			mGridView.setAdapter(null);
		}
		
	}
	
	private void setUpAdapterTop() {
		if ( getActivity() == null || mTopGridView == null )return;
		if(mPositionItems!=null){
			mTopGridView.setAdapter(new ArrayAdapter<GeonamesPosition>(getActivity(), android.R.layout.simple_gallery_item,mPositionItems));
			
		}else{
			mTopGridView.setAdapter(null);
		}
		
	}
	
	
	private class FetchPositionTask extends AsyncTask<Void,Void,ArrayList<GeonamesPosition>>{

		@Override
		protected ArrayList<GeonamesPosition> doInBackground(Void... params) {
			return new SimpleGeonomeFetcher().fetchItems();
		}
		
		@Override
		protected void onPostExecute(ArrayList<GeonamesPosition> positions){
			mPositionItems = positions;
			setUpAdapterTop();
		}
		
	}

		

	private class FetchItemsTask extends AsyncTask<Void,Void,ArrayList<YrWetherData>>{

		@Override
		protected ArrayList<YrWetherData> doInBackground(Void... params) {
			//new GeonamesFetcher().fetchItems(); 
			return new SimpleYrFetcher().fetchItems();		
		}
		
		@Override
		protected void onPostExecute(ArrayList<YrWetherData> items){
			
			mItems = items;
			setUpAdapter();
		}
		
	}
}
