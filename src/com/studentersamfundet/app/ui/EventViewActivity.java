package com.studentersamfundet.app.ui;

import java.text.SimpleDateFormat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.util.Linkify;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.studentersamfundet.app.Event;
import com.studentersamfundet.app.R;

public class EventViewActivity extends BaseDnsActivity {
   @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_view);
        
        Intent intent = getIntent();
        final Event e = (Event)intent.getSerializableExtra("event");
        
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
        
        if (e.id > 0) { // if id exists and seems legit
        	link.setVisibility(View.VISIBLE);
        	link.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					Uri webDestination = e.getUri();
					
					Intent webIntent = new Intent(Intent.ACTION_VIEW, webDestination);
					startActivity(webIntent);
				}
			});
        }
    }
}
