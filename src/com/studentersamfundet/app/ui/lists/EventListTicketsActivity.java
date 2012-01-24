package com.studentersamfundet.app.ui.lists;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;

import com.studentersamfundet.app.Event;

public class EventListTicketsActivity extends EventListActivity {
	@Override
	protected ListCreator<Event> getListCreator(Context context, int idList, int idProgressBar) {
		return new ListCreator<Event>(context, idList, idProgressBar) {
			@Override
			protected Event[] getObjects() {
				return getDataHandler().getEventsWithTickets();
			}	
		};
	}

	@Override
	protected OnClickListener getShortClickListener (final Event e) {
		return new OnClickListener() {
			public void onClick(View v) {
				Uri webDestination = e.getTicketUri();

				Intent webIntent = new Intent(Intent.ACTION_VIEW, webDestination);
				startActivity(webIntent);

			}
		};
	}
}
