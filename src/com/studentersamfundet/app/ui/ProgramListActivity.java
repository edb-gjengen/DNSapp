package com.studentersamfundet.app.ui;

import java.io.IOException;

import org.w3c.dom.NodeList;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
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
import com.studentersamfundet.app.XmlParser;

public class ProgramListActivity extends BaseDnsActivity {
	public static final String feedURL = "http://studentersamfundet.no/rss/lars_program_feed.php";
	
	private FeedFetcher feed;
	private XmlParser parser;
	private DataHandler dataHandler;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_list);
        
        this.feed = new FeedFetcher(this, feedURL);
        this.parser = new XmlParser();
        
        // Do we have intarwebs?
        // YAY = Fetch the feed.
        // NAY = Inform about no connection.
        try {
	        NodeList itemNodes = feed.fetch();
	        dataHandler = parser.parse(itemNodes);
	        
        } catch (IOException e) {
        	Toast toast = Toast.makeText(this, R.string.error_noconnection_noupdate, Toast.LENGTH_LONG);
        	toast.show();
        	return;
        }
  
        ListView list = (ListView)findViewById(R.id.event_list);
        
        ListAdapter adapter = createAdapter();
        list.setAdapter(adapter);
    }
    
    protected ListAdapter createAdapter() {
    	Event[] events = dataHandler.populateList();
    	
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
    			
    			final Event e = getItem(position);
    			
    			TextView titleView = (TextView) row.findViewById(R.id.event_list_row_text);
    			titleView.setText(Html.fromHtml(e.title));
    	 
    			TextView dateView = (TextView) row.findViewById(R.id.event_list_row_date);
    			dateView.setText(Html.fromHtml(e.getDateString()));
    			
    			row.setOnClickListener(new OnClickListener() {
					
					public void onClick(View v) {
						Intent intent = new Intent(ProgramListActivity.this, EventViewActivity.class);
						intent.putExtra("event", e);
						
						startActivity(intent);
					}
				});
    			
    			/* Apply styles: */
    			if (position % 2 == 0) {
    				row.setBackgroundResource(R.color.ListItemBackgroundEven);
    				titleView.setTextAppearance(ProgramListActivity.this, R.style.ListItemTextEven);
    				dateView.setTextAppearance(ProgramListActivity.this, R.style.ListItemTextEven);
    			} else {
    				row.setBackgroundResource(R.color.ListItemBackgroundOdd);
    				titleView.setTextAppearance(ProgramListActivity.this, R.style.ListItemTextOdd);
    				dateView.setTextAppearance(ProgramListActivity.this, R.style.ListItemTextOdd);
    			}
    			
    			return row;
    		}
    	};
    	 
    	return adapter;
    	
    }
}
