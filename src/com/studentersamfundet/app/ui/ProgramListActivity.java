package com.studentersamfundet.app.ui;

import org.w3c.dom.NodeList;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.studentersamfundet.app.DataHandler;
import com.studentersamfundet.app.Event;
import com.studentersamfundet.app.FeedFetcher;
import com.studentersamfundet.app.R;
import com.studentersamfundet.app.Utils;
import com.studentersamfundet.app.XmlParser;

public class ProgramListActivity extends BaseDnsActivity {
	public static final String feedURL = "http://folk.uio.no/larsjeng/test.xml";
	public static final String localURL = "/data/com.studentersamfundet.app/files/dns_events";
	public static FeedFetcher feed = new FeedFetcher(); 
	public static XmlParser parser = new XmlParser();
	public DataHandler dh;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_list);
        
        // Do we have intarwebs?
        // YAY = Fetch the feed.
        // NAY = Inform about no connection.
        if (Utils.checkConnection(this)) {
        	NodeList itemNodes = feed.fetch(feedURL, this, true);
        	dh = parser.parse(itemNodes);
        } else {
        	NodeList itemNodes = feed.fetch(localURL, this, false);
        	dh = parser.parse(itemNodes);

        	Toast toast = Toast.makeText(this, R.string.error_noconnection_noupdate, Toast.LENGTH_LONG);
        	toast.show();
        }
  
        ListView list = (ListView)findViewById(R.id.event_list);
        
        ListAdapter adapter = createAdapter();
        list.setAdapter(adapter);
    }
    
    protected ListAdapter createAdapter() {
    	Event[] events = dh.populateList();
    	
    	ListAdapter adapter = new ArrayAdapter<Event>(this, R.layout.event_list_row, R.id.event_list_row_text, events) {
    		@Override
    		public View getView(int position, View convertView, ViewGroup parent) {
    			View row;
    			
    			if (null == convertView) {
    				LayoutInflater inflater = getLayoutInflater();
    				row = inflater.inflate(R.layout.event_list_row, null);
    			} else {
    				row = convertView;
    			}
    	 
    			/* Apply styles: */
    			if (position % 2 == 0) 
    				row.setBackgroundResource(R.color.ListItemEven);
    			else
    				row.setBackgroundResource(R.color.ListItemOdd);
    			
    			final Event e = getItem(position);
    			
    			TextView titleView = (TextView) row.findViewById(R.id.event_list_row_text);
    			titleView.setText(e.title);
    	 
    			TextView dateView = (TextView) row.findViewById(R.id.event_list_row_date);
    			dateView.setText(e.getDateString());
    			
    			row.setOnClickListener(new OnClickListener() {
					
					public void onClick(View v) {
						Intent intent = new Intent(ProgramListActivity.this, EventViewActivity.class);
						intent.putExtra("event", e);
						
						startActivity(intent);
					}
				});
    			
    			return row;
    		}
    	};
    	 
    	return adapter;
    	
    }
}
