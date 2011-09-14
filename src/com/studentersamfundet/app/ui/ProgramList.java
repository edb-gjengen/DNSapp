package com.studentersamfundet.app.ui;

import org.w3c.dom.NodeList;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.studentersamfundet.app.DataHandler;
import com.studentersamfundet.app.Event;
import com.studentersamfundet.app.FeedFetcher;
import com.studentersamfundet.app.R;
import com.studentersamfundet.app.XmlParser;

public class ProgramList extends ListActivity {
	public static final String feedURL = "http://folk.uio.no/larsjeng/test.xml";
	public static final String localURL = "/data/com.studentersamfundet.app/files/dns_events";
	public static FeedFetcher feed = new FeedFetcher(); 
	public static XmlParser parser = new XmlParser();
	public DataHandler dh;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_list);
        
        View v = this.findViewById(R.id.program_button);
        v.setClickable(false);
        
        // Do we have intarwebs?
        // YAY = Fetch the feed.
        // NAY = Inform about no connection.
        if (DnsActivity.checkConnection(this)) {
        	NodeList itemNodes = feed.fetch(feedURL, this, true);
        	dh = parser.parse(itemNodes);
        } else {
        	NodeList itemNodes = feed.fetch(localURL, this, false);
        	dh = parser.parse(itemNodes);

        	Toast toast = Toast.makeText(this, R.string.error_noconnection_noupdate, Toast.LENGTH_LONG);
        	toast.show();
        }
  
        ListAdapter adapter = createAdapter();
        setListAdapter(adapter);
    }
    
    protected ListAdapter createAdapter() {
    	Event[] events = dh.populateList();
    	String[] titles = new String[events.length];
    	
    	for (int i = 0; i < events.length; i++) {
    		Event e = events[i];
    		String date = e.date;
    		String month = date.substring(5, 7);
    		String day = date.substring(8, 10);
    		titles[i] = day + ". " + dh.toMonth(month) + ": " + e.title;
    	}
    	
    	// TODO: Lag en fin liste med litt mer informasjon og slik.
    	ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titles);
    	 
    	return adapter;
    	
    }
}
