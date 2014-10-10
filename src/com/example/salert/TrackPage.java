package com.example.salert;

import java.util.ArrayList;



import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

public class TrackPage extends Fragment 
{
	ViewGroup rootView;
	ArrayList<Item> item_data;
	ListView trackView = null;
	ItemAdapter adapter;
	DatabaseHandler db = null;
	Activity mainActivity;
	LinearLayout linearLayout;

	static TrackPage singleton;
	private TrackPage()
	{	
	}
	
	public static TrackPage getTrackPage()
	{
		if(singleton == null)
		{
			singleton = new TrackPage();
		}
		
		return singleton;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	        Bundle savedInstanceState) 
	{
	    rootView = (ViewGroup) inflater.inflate(
	            R.layout.trackview, container, false);
	    db = new DatabaseHandler(getActivity());
	    
	    trackView = (ListView) rootView.findViewById(R.id.track_list);
	    item_data = (ArrayList<Item>) db.getAllItems();
	    int count = db.getItemCount();
	    Log.d("count is", Integer.toString(count));
	    
		adapter = new ItemAdapter(getActivity(), R.layout.track_item, item_data);
		adapter.notifyDataSetChanged();
		adapter.updateData(item_data);
		trackView.setAdapter(adapter);	
		
	    return rootView;
	}
	
	public void update()
	{
		item_data = db.getAllItems();
		adapter = new ItemAdapter(getActivity(), R.layout.track_item, item_data);
		trackView.setAdapter(adapter);
	}

}