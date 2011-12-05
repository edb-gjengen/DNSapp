package com.studentersamfundet.app.ui;

import java.io.IOException;
import java.text.SimpleDateFormat;

import org.w3c.dom.NodeList;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.AsyncTask;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.studentersamfundet.app.DataHandler;
import com.studentersamfundet.app.Event;
import com.studentersamfundet.app.FeedFetcher;
import com.studentersamfundet.app.R;
import com.studentersamfundet.app.RSSParserProgram;
import com.studentersamfundet.app.ui.ChooseCategoryDialog.Callback;

public class ProgramListActivity extends BaseDnsActivity {
	public static final String feedURL = "http://studentersamfundet.no/rss/lars_program_feed.php";
	
	private FeedFetcher feed;
	private RSSParserProgram parser;
	private DataHandler dataHandler;
	
	private String currentCategory = Event.ALL; 
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_list);
        
        this.feed = new FeedFetcher(this, feedURL);
        this.parser = new RSSParserProgram();
        
        ListCreator lc = new ListCreator(Event.ALL, false);
        lc.execute();
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
            ListCreator lc = new ListCreator(Event.ALL, true);
            lc.execute();
            return true;
        
        case R.id.menu_categories:
        	createCategoryDialog();
        	return true;
            
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
    @Override
    public void onBackPressed() {
    	if (currentCategory.equals(Event.ALL)) {
    		super.onBackPressed();
    	} else {
    		ListCreator lc = new ListCreator(Event.ALL, false);
    		lc.execute();
    	}
    }
    
    
    protected ListAdapter createAdapter(String category) {
    	Event[] events = dataHandler.getEvents(category);
    	
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
    	 
    			SimpleDateFormat dateFormat = new SimpleDateFormat("d. MMMM");
    			TextView dateView = (TextView) row.findViewById(R.id.event_list_row_date);
    			dateView.setText(dateFormat.format(e.getDate()));
    			
    			row.setOnClickListener(new OnClickListener() {
					
					public void onClick(View v) {
						Intent intent = new Intent(ProgramListActivity.this, EventViewActivity.class);
						intent.putExtra("event", e);
						
						startActivity(intent);
					}
				});
    			
    			row.setOnLongClickListener(new OnLongClickListener() {
					
					public boolean onLongClick(View v) {
						if (e.getDate() == null)
							return false;
						
						long startDate = e.getDate().getTime();
						
						try {
							Intent intent = new Intent(Intent.ACTION_EDIT);
							intent.setType("vnd.android.cursor.item/event");
							intent.putExtra("title", e.title);
							intent.putExtra("beginTime", startDate);
							intent.putExtra("endTime", startDate + (2*60*60*1000)); // Two hours
							intent.putExtra("allDay", false);
							intent.putExtra("eventLocation", e.getLocation());
							intent.putExtra("reminder", false);
						
							startActivity(intent);
							return true;
						} catch (ActivityNotFoundException e) {
							return false;
						}
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
				
				ListCreator lc = new ListCreator(message[0], false);
				lc.execute();
			}
		};
		
    	if (dataHandler != null) {
	    	Dialog dialog = new ChooseCategoryDialog(this, callback, dataHandler.getEventCategories());
	    	dialog.show();
    	} else {
    		Toast.makeText(this, R.string.error_loading, Toast.LENGTH_SHORT);
    	}
    }

    protected class ListCreator extends AsyncTask<Void, Void, Boolean> {
    	protected String category;
    	protected boolean forcedUpdate;
    	
    	public ListCreator(String category, boolean forcedUpdate) {
    		this.category = category;
    		this.forcedUpdate = forcedUpdate;
    	}
    	
    	@Override 
    	protected void onPreExecute() {
    		if (forcedUpdate) {
    			dataHandler = null;
    		}
    	
    		ListView list = (ListView)findViewById(R.id.event_list);
	        list.setVisibility(View.GONE);
	        
	        ProgressBar pb = (ProgressBar)findViewById(R.id.event_list_progress_bar);
	        pb.setVisibility(View.VISIBLE);
    	}
    	
		@Override
		protected Boolean doInBackground(Void ... v) {
			// Do we have intarwebs?
	        // YAY = Fetch the feed.
	        // NAY = Inform about no connection.
	        try {
	        	if (dataHandler == null) {
			        NodeList itemNodes = feed.fetch(this.forcedUpdate);
			        dataHandler = parser.parse(itemNodes);
	        	}
			    return true;
		        
	        } catch (IOException e) {
	        	return false;
	        }
		}
    	
		@Override
		protected void onPostExecute(Boolean result) {
			if (result) {
				ListView list = (ListView)findViewById(R.id.event_list);
		        ListAdapter adapter = createAdapter(this.category);
		        list.setAdapter(adapter);
		        list.setVisibility(View.VISIBLE);
		        
		        ProgressBar pb = (ProgressBar)findViewById(R.id.event_list_progress_bar);
		        pb.setVisibility(View.GONE);
		        
		        currentCategory = category;
			} else {
				Toast toast = Toast.makeText(ProgramListActivity.this, R.string.error_noconnection_noupdate, Toast.LENGTH_LONG);
	        	toast.show();
			}
		}
    }
}
