package com.studentersamfundet.app.ui;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;

import com.studentersamfundet.app.Event;

public class EventListTicketsActivity extends EventListActivity {
	
	class TicketListCreator extends ListCreator { 
		public TicketListCreator(String category, boolean forcedUpdate) {
			super(category, forcedUpdate);
		}

		@Override
		protected Event[] getEvents() {
			return dataHandler.getEventsWithTickets();
		}
	}
	
	@Override
	protected ListCreator getListCreator(String category, boolean forcedUpdate) {
    	return new TicketListCreator(category, forcedUpdate);
    }
	
	   protected OnClickListener getShortClickListener (final Event e) {
	    	return new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Uri webDestination = e.getTicketUri();
					
					Intent webIntent = new Intent(Intent.ACTION_VIEW, webDestination);
					startActivity(webIntent);
					
				}
			};
		}
	    
	    protected OnLongClickListener getLongClickListener (final Event e) {
	    	return new OnLongClickListener() {
				
				@Override
				public boolean onLongClick(View v) {
					return false;
				}
			};
	    }
}
