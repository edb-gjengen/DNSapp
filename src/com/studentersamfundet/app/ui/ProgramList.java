package com.studentersamfundet.app.ui;

import org.w3c.dom.NodeList;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.studentersamfundet.app.DataHandler;
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
        
        // View v = this.findViewById(R.id.program_button);
        // v.setClickable(false);
        
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
    	String[] vals = populateList();
    	ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, vals);
    	 
    	return adapter;
    }
    
    public String[] populateList() {
    	String[] val = new String[10];
    	val[0] = "test!";
    	val[1] = "test2";
    	val[2] = "test3";
    	val[3] = "test3";
    	val[4] = "test3";
    	val[5] = "test3";
    	val[6] = "test3";
    	val[7] = "test3";
    	val[8] = "test3";
    	val[9] = "test3";
    	return val;
    }
}
