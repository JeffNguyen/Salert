package com.example.salert;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;


public class MainActivity extends ActionBarActivity 
{
    ViewPager mPager;
    ScreenSlidePagerAdapter mAdapter;
    final static int NUM_PAGES = 2;
    DatabaseHandler db;

    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Remove title bar
	    this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_main);
		
		mPager = (ViewPager) findViewById(R.id.pager);
	    mAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
	    mPager.setAdapter(mAdapter);
	    
	    new Notify().execute();

		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
	}
	
	public void onClick(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
	
	private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
	    public ScreenSlidePagerAdapter(FragmentManager fm) {
	        super(fm);
	    }

	    @Override
	    public Fragment getItem(int i) {

	        Fragment frag = null;

	        switch (i) {
	        case 0:
	            frag = new SearchPage();
	            break;
	        case 1:
	            frag = TrackPage.getTrackPage();//new TrackPage();
	            break;
	        default:
	        	frag = new SearchPage();
	        	break;
	        }
	        return frag;
	    }

	    @Override
	    public int getCount() {
	        return NUM_PAGES;
	    }
	}
	
	private class Notify extends AsyncTask<Void, Void, Void> 
	{
	 
	    @Override
	    protected Void doInBackground(Void... params) 
	    {   
		     try 
		     {
		    	 Thread.sleep(5000);
		     } 
		     
		     catch (InterruptedException e) 
		     {
		    	 e.printStackTrace();
		     }
		         
		     return null;
    	}
	 
        @Override
        protected void onPostExecute(Void result) 
        {
        	Intent intent = new Intent(Intent.ACTION_VIEW);
        	intent.setData(Uri.parse("http://shop.nordstrom.com/s/burberry-check-stamped-bracelet-watch-42mm/3762989?origin=category-personalizedsort&contextualcategoryid=0&fashionColor=&resultback=291&cm_sp=personalizedsort-_-browseresults-_-1_1_B"));
        		
            PendingIntent pIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, Intent.FLAG_ACTIVITY_NEW_TASK);           
            
            android.support.v4.app.NotificationCompat.Builder n = new NotificationCompat.Builder(getBaseContext());

            n.setContentTitle("An item has gone on sale!")
	           .setContentText("Burberry Check Stamped Bracelet Watch, 42mm Silver/ Black")
	           .setSmallIcon(R.drawable.watch1)
	           .setContentIntent(pIntent)
	           .setAutoCancel(true);
        
            NotificationManager notificationManager = 
                  (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            notificationManager.notify(0, n.build()); 
        } 
    }
}