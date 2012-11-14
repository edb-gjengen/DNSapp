package com.studentersamfundet.app.ui;

import java.text.SimpleDateFormat;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.util.Linkify;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.studentersamfundet.app.Event;
import com.studentersamfundet.app.R;

public class EventViewActivity extends BaseDnsActivity {
	Event event;
	
   @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_view);
        
        Intent intent = getIntent();
        this.event = (Event)intent.getSerializableExtra("event");
        final Event e = this.event;
        
        if (e == null) {
        	throw new Error("This should NEVER happen");
        }
        
        TextView title = (TextView) findViewById(R.id.event_view_title);
        TextView description = (TextView) findViewById(R.id.event_view_description);
        TextView location = (TextView) findViewById(R.id.event_view_location);
        TextView datetime = (TextView) findViewById(R.id.event_view_datetime);
        Button link = (Button) findViewById(R.id.event_view_link);
        
        title.setText(Html.fromHtml(e.title));
        location.setText(Html.fromHtml(e.getLocation()));
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        datetime.setText(e.getDate() != null ? dateFormat.format(e.getDate()) : "");
        
        StringBuilder sb = new StringBuilder();
        
        if (e.getDescription().length() > 0) {
        	sb.append("<b>");
        	sb.append(e.getDescription());
        	sb.append("</b><br/><br/>");
        }
        
        if (e.getContent().length() > 0) {
        	sb.append(e.getContent());
        }
        
        description.setText(Html.fromHtml(sb.toString()));
        Linkify.addLinks(description, Linkify.ALL);
        
        final Uri ticketUri = e.getTicketUri();
        if (ticketUri != null) {
        	link.setVisibility(View.VISIBLE);
        	link.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {			
					Intent webIntent = new Intent(Intent.ACTION_VIEW, ticketUri);
					startActivity(webIntent);
				}
			});
        }
    }
   
   public void addToCalender(View v) {
	   if (this.event.getDate() == null)
			return;
		
		long startDate = this.event.getDate().getTime();
		
		try {
			Intent intent = new Intent(Intent.ACTION_EDIT);
			intent.setType("vnd.android.cursor.item/event");
			intent.putExtra("title", this.event.title);
			intent.putExtra("beginTime", startDate);
			intent.putExtra("endTime", startDate + (2*60*60*1000)); // Two hours
			intent.putExtra("allDay", false);
			intent.putExtra("eventLocation", this.event.getLocation());
			intent.putExtra("reminder", false);
		
			startActivity(intent);
		} catch (ActivityNotFoundException e) {
			Toast.makeText(this, R.string.calendar_not_found, Toast.LENGTH_SHORT).show();
		}
   }
}
