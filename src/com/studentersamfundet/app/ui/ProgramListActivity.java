package com.studentersamfundet.app.ui;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.w3c.dom.NodeList;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
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
import com.studentersamfundet.app.ui.ChooseCategoryDialog.Callback;

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
        
        createList(Event.ALL, false);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.layout.menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
        case R.id.menu_refresh:
            this.createList(Event.ALL, true);
            return true;
        
        case R.id.menu_categories:
        	createCategoryDialog();
        	return true;
            
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
    protected void createList(String category, boolean forcedUpdate) {
    	// Do we have intarwebs?
        // YAY = Fetch the feed.
        // NAY = Inform about no connection.
        try {
	        NodeList itemNodes = feed.fetch(forcedUpdate);
	        dataHandler = parser.parse(itemNodes);
	        
        } catch (IOException e) {
        	Toast toast = Toast.makeText(this, R.string.error_noconnection_noupdate, Toast.LENGTH_LONG);
        	toast.show();
        	return;
        }
  
        ListView list = (ListView)findViewById(R.id.event_list);
        
        ListAdapter adapter = createAdapter(category);
        list.setAdapter(adapter);
    }
    
    protected ListAdapter createAdapter(String category) {
    	Event[] events = dataHandler.populateList(category);
    	
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
    			
    			row.setOnLongClickListener(new OnLongClickListener() {
					
					@Override
					public boolean onLongClick(View v) {
						long startDate = 0;
						try {
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
							startDate = sdf.parse(e.date).getTime();
						} catch (ParseException e) {
							return false;
						}
						
						Intent intent = new Intent(Intent.ACTION_EDIT);
						intent.setType("vnd.android.cursor.item/event");
						intent.putExtra("title", e.title);
						intent.putExtra("beginTime", startDate);
						intent.putExtra("endTime", startDate + (2*60*60*1000)); // Two hours
						intent.putExtra("allDay", false);
						intent.putExtra("eventLocation", e.location);
						intent.putExtra("reminder", false);
						startActivity(intent);
						return true;
					}
				});
    			
    			/* Apply styles: */
    			if (position % 2 == 0) {
    				row.setBackgroundResource(R.color.ListItemBackgroundEven);
    				titleView.setTextAppearance(ProgramListActivity.this, R.style.ListItemTextTitle);
    				dateView.setTextAppearance(ProgramListActivity.this, R.style.ListItemTextDate);
    			} else {
    				row.setBackgroundResource(R.color.ListItemBackgroundOdd);
    				titleView.setTextAppearance(ProgramListActivity.this, R.style.ListItemTextTitle);
    				dateView.setTextAppearance(ProgramListActivity.this, R.style.ListItemTextDate);
    			}
    			
    			return row;
    		}
    	};
    	 
    	return adapter;
    	
    }
    
    protected void createCategoryDialog() {
    	Callback callback = new Callback() {
			public void run(String... message) {
				if (message.length < 1) 
					message = new String[] { Event.ALL };
				
				ProgramListActivity.this.createList(message[0], false);
			}
		};
    	
    	Dialog dialog = new ChooseCategoryDialog(this, callback, dataHandler.getCategories());
    	dialog.show();
    }
}
