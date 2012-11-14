package com.studentersamfundet.app.ui.lists;

import java.text.SimpleDateFormat;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.studentersamfundet.app.Event;
import com.studentersamfundet.app.R;
import com.studentersamfundet.app.network.ImageLoader;
import com.studentersamfundet.app.ui.ChooseCategoryDialog;
import com.studentersamfundet.app.ui.EventViewActivity;
import com.studentersamfundet.app.ui.ChooseCategoryDialog.Callback;

public class EventListActivity extends BaseListActivity {
	public static final int imageSize = 80;
	
	private String currentCategory = Event.ALL; 
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_list);
        
		ListCreator<?> lc = getListCreator(this, R.id.event_list, R.id.event_list_progress_bar);
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
            ListCreator<?> lc = getListCreator(this, R.id.event_list, R.id.event_list_progress_bar);
            lc.setForcedUpdate(true);
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
    		ListCreator<?> lc = getListCreator(this, R.id.event_list, R.id.event_list_progress_bar);
    		lc.execute();
    	}
    }
        
    protected <T> ListAdapter createAdapter(T[] objects) {
    	Event[] events = (Event[])objects;
    	
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
    			
    			/* Set images: */
    			ImageLoader loader = new ImageLoader(parent, position, R.id.event_list_row_image);
				loader.execute(e.getImageUri(imageSize).toString());
    			
    			
    			/* Set text: */
    			TextView titleView = (TextView) row.findViewById(R.id.event_list_row_text);
    			titleView.setText(Html.fromHtml(e.title));
    	 
    			SimpleDateFormat dateFormat = new SimpleDateFormat("d. MMMM");
    			TextView dateView = (TextView) row.findViewById(R.id.event_list_row_date);
    			dateView.setText(dateFormat.format(e.getDate()));
    			
    			TextView priceView = (TextView) row.findViewById(R.id.event_list_row_price);
    			priceView.setText(e.getPriceString());
    			
    			/* Apply styles: */
    			if (position % 2 == 0) {
    				row.setBackgroundResource(R.color.ListItemBackgroundEven);
    			} else {
    				row.setBackgroundResource(R.color.ListItemBackgroundOdd);
    			}
				titleView.setTextAppearance(EventListActivity.this, R.style.ListItemTextTitle);
				dateView.setTextAppearance(EventListActivity.this, R.style.ListItemTextDate);
				priceView.setTextAppearance(EventListActivity.this, R.style.ListItemTextDate);
    			
    			/* Add onClickListeners: */
    			row.setOnClickListener(getShortClickListener(e));
    			
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
				
				currentCategory = message[0];
				ListCreator<?> lc = getListCreator(EventListActivity.this, R.id.event_list, R.id.event_list_progress_bar);
				lc.setCategory(message[0]);
				lc.execute();
			}
		};
		
    	if (getDataHandler() != null) {
	    	Dialog dialog = new ChooseCategoryDialog(this, callback, getDataHandler().getEventCategories());
	    	dialog.show();
    	} else {
    		Toast.makeText(this, R.string.error_loading, Toast.LENGTH_SHORT).show();
    	}
    }

 
    
    protected ListCreator<Event> getListCreator(Context context, int idList, int idProgressBar) {
    	return new ListCreator<Event>(context, idList, idProgressBar) {

			@Override
			protected Event[] getObjects() {
				return getDataHandler().getEvents(currentCategory);
			}
    	};
    }
    
    protected OnClickListener getShortClickListener (final Event e) {
    	return new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(EventListActivity.this, EventViewActivity.class);
				intent.putExtra("event", e);
				
				startActivity(intent);
			}
		};
	}
}
