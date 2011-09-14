package com.studentersamfundet.app;

import org.w3c.dom.NodeList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class DnsActivity extends Activity {
	public static final String PREFS_NAME = "DnsPrefs";
	public static final String feedURL = "http://folk.uio.no/larsjeng/test.xml";
	public static final String localURL = "/data/com.studentersamfundet.app/files/dns_events";
	public static FeedFetcher feed = new FeedFetcher(); 
	public static XmlParser parser = new XmlParser();
	public DataHandler dh;
	Context context = null;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        
        View tmp = this.findViewById(R.id.join_us_textview);
        tmp.setVisibility(View.INVISIBLE);
        
		context = getApplicationContext();
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart", false);
        
        // Is it the first time the app is started?
        if (!firstStart) {
        	// Do we have intarwebs?
        	// YAY = Fetch the feed.
        	// NAY = Inform about no connection.
        	if (checkConnection()) {
        		NodeList itemNodes = feed.fetch(feedURL, this, true);
        		dh = parser.parse(itemNodes);
        	} else {
        		Toast toast = Toast.makeText(context, R.string.error_noconnection_firstart, Toast.LENGTH_LONG);
        		toast.show();
        	}
        } else {     	
        	if (checkConnection()) {
        		NodeList itemNodes = feed.fetch(feedURL, this, true);
        		dh = parser.parse(itemNodes);
        	} else {
        		NodeList itemNodes = feed.fetch(localURL, this, false);
        		dh = parser.parse(itemNodes);

        		Toast toast = Toast.makeText(context, R.string.error_noconnection_noupdate, Toast.LENGTH_LONG);
        		toast.show();
        	}
        }
    }
    
    public void clickHandler(View v) {
    	if (v.getId() == R.id.program_button) {
    		startActivity(new Intent(context, ProgramList.class));
    	} else if (v.getId() == R.id.bli_med_button) {
    		View j = this.findViewById(R.id.bli_med_button);
    		ImageView p = (ImageView)this.findViewById(R.id.paragraph);
    		View t = this.findViewById(R.id.join_us_textview);
    		
    		j.setClickable(false);
    		p.setVisibility(View.INVISIBLE);
    		t.setVisibility(View.VISIBLE);
    		
    		// FIX ON BACK PRESSED!
    	}
    }
    
    @Override
    public void onBackPressed () {
    	View t = this.findViewById(R.id.join_us_textview);
    	if (t.getVisibility() == View.VISIBLE) {
    		View j = this.findViewById(R.id.bli_med_button);
    		ImageView p = (ImageView)this.findViewById(R.id.paragraph);
    		j.setClickable(true);
    		t.setVisibility(View.INVISIBLE);
    		p.setVisibility(View.VISIBLE);
    	} else {
    		finish();
    	}
    	
    }
    
    /* Check for Internet connection. */
    boolean checkConnection() {
    	ConnectivityManager conMan
    	= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    	NetworkInfo netInfo = conMan.getActiveNetworkInfo();
    	return netInfo != null;
    }
}