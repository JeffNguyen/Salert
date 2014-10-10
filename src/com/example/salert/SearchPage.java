package com.example.salert;

import java.io.IOException;

import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class SearchPage extends Fragment 
{
	String url = "https://www.google.com/search?hl=en&tbm=shop&q=";
	ProgressDialog mProgressDialog;
	ListView resultView;
	ImageButton searchButton;
	ViewGroup rootView;
	ItemAdapter searchAdapter, trackAdapter;
	ArrayList<Item> item_data =  new ArrayList<Item>();
	DatabaseHandler db;
	TrackPage trackPage;
    public static boolean hardCode = false;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	        Bundle savedInstanceState) 
	{
	    rootView = (ViewGroup) inflater.inflate(
	            R.layout.search_view, container, false);
	    
	    db = new DatabaseHandler(getActivity());
		trackPage = TrackPage.getTrackPage();

	    resultView = (ListView) rootView.findViewById(R.id.result_list);
	    for(int i = 0; i < 5; i++)
	    {
	    	item_data.add(i, new Item());
	    }
	
		searchAdapter = new ItemAdapter(getActivity(), R.layout.list_view, item_data);

		searchButton = (ImageButton) rootView.findViewById(R.id.search_button);
		searchButton.setOnClickListener(new OnClickListener()
		{
			public void onClick(View arg0)
			{
				new Search().execute();
				InputMethodManager imm = (InputMethodManager) arg0.getContext()
		                .getSystemService(Context.INPUT_METHOD_SERVICE);
		        imm.hideSoftInputFromWindow(arg0.getWindowToken(), 0);
			}
		});
	    return rootView;
	}
	
	private class Search extends AsyncTask<Void, Void, Void> 
	{
        String textDescription[] = new String[5]; // change this 40
        String productUrl[] = new String[5];		
		String imgUrl[] = new String[5];
		String imgUrlHardCode[] = new String[] {
				"https://encrypted-tbn0.gstatic.com/shopping?q=tbn:ANd9GcRQ_5Tmwfu9NwVwCb0B9T7RWe9tiLxR4s6dyPNJgwGaA_WUWbj-ehZKii2Ss5_ntjfBAnyrSViQ&usqp=CAY",
				"https://encrypted-tbn2.gstatic.com/shopping?q=tbn:ANd9GcQ4nWECJ2epZhnURSoHVXOSr0HITtmYN_IMIs-YT-DT9azhz52CWwyplYtQ7O4fY3tJZAQWiKlA&usqp=CAY",
				"https://encrypted-tbn3.gstatic.com/shopping?q=tbn:ANd9GcRK5MXZbYVhhmTI-TeMU2WgwV6QZrPVvsO2_4sEFByE_GFUxNhkDUbk2ti6dMXKA30CY_mCi3uF&usqp=CAY",
				"https://encrypted-tbn3.gstatic.com/shopping?q=tbn:ANd9GcQxhlFNjhJuDxbDB5V9N6zUN50-jnSmHA1wYouoJ-ndCEJlgJHHR_YDZS4hmtj5_vmWzAicsiC2sw&usqp=CAE",
				"https://encrypted-tbn0.gstatic.com/shopping?q=tbn:ANd9GcSEs9ReK6Bvd0mKHTAJdJX7XqjGU3yvkoFLPZJB20J5aupIlNOuEOi4fDG4kTIN93WWmhBeOco7&usqp=CAY" 
		};

        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setTitle("Salert");
            mProgressDialog.setMessage("Searching...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
            
        }
 
        @Override
        protected Void doInBackground(Void... params) 
        {       
        	
        	try 
        	{
            	EditText editField =  (EditText) rootView.findViewById(R.id.search_field);
            	String freeRunsCheck = editField.getText().toString();
            	String search = editField.getText().toString().replaceAll("\\s","\\+");
            	            	
            	
                // Connect to the web site
                Document document = Jsoup.connect(url+search).get();
        		
                Elements stuff = document.select("div[class=psgicont]"); 
        		Iterator<Element> descriptionIter = stuff.iterator();
        		Element tempDescrip;
        		

        		Elements links = document.select("a[class=psgiimg]");
        		Iterator<Element> iterImage = links.iterator();
        		Element tempImage;
        		
        		if (freeRunsCheck.equals("free runs")){
            		for (int k=0; k < 5; k++)
            		{ 
            			tempDescrip = descriptionIter.next();
            			textDescription[k] = tempDescrip.text();
            			
            			hardCode = true;
            		}
        		}
        		else{
        		for (int k=0; k < 5; k++)
        		{ 
        			tempDescrip = descriptionIter.next();
        			textDescription[k] = tempDescrip.text();
        			
        			tempImage = iterImage.next();
        			productUrl[k] = tempImage.attr("abs:href");      			
        			
        			String url = productUrl[k];
        			Document document2 = Jsoup.connect(url).get();
        			Elements link = document2.select("div[id=pp-altimg-init-main] > img");
        			imgUrl[k] = link.attr("src");
        			Log.d("img link", imgUrl[k]);
        		}
        		}

            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }
            return null;
        }
 
        @Override
        protected void onPostExecute(Void result) 
        {
        	if (hardCode == true){
            	item_data.set(0,new Item(imgUrlHardCode[0], textDescription[0]));
            	item_data.set(1,new Item(imgUrlHardCode[1], textDescription[1]));
    			item_data.set(2,new Item(imgUrlHardCode[2], textDescription[2]));
    			item_data.set(3,new Item(imgUrlHardCode[3], textDescription[3]));
    			item_data.set(4,new Item(imgUrlHardCode[4], textDescription[4]));
        	}
        	else {
        	item_data.set(0,new Item(imgUrl[0], textDescription[0]));
        	item_data.set(1,new Item(imgUrl[1], textDescription[1]));
			item_data.set(2,new Item(imgUrl[2], textDescription[2]));
			item_data.set(3,new Item(imgUrl[3], textDescription[3]));
			item_data.set(4,new Item(imgUrl[4], textDescription[4]));
        	}
        	
			searchAdapter.updateData(item_data);
	        resultView.setAdapter(searchAdapter);	        
     
	        resultView.setOnItemClickListener(new AdapterView.OnItemClickListener() 
	        {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) 
				{
					
					// need to fix this, so when an user de-selects item, 
					// it removes item from database
					
					Button button = (Button) view.findViewById(R.id.addButton);
					button.setBackgroundColor(Color.WHITE);						
					
					Toast.makeText(getActivity(), "Enabled Push Notification", Toast.LENGTH_SHORT).show();
					
					// uncomment to add more items
					db.addItem(item_data.get(position));	
					
					trackPage.update();
				}
    			
			});
            mProgressDialog.dismiss();
        } 
    }
}