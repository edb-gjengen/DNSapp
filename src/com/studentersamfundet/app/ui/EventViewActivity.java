package com.studentersamfundet.app.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
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
        
        title.setText(e.title);
        location.setText(e.location);
        datetime.setText(e.date);
        
        StringBuilder sb = new StringBuilder();
        if (e.description.length() > 0) {
        	sb.append("<b>");
        	sb.append(e.description);
        	sb.append("</b><br/><br/>");
        }
        if (e.text.length() > 0) {
        	sb.append(e.text);
        }
        description.setText(Html.fromHtml(sb.toString()));
        
        if (e.id > 0) { // if id exists and seems legit
        	link.setVisibility(View.VISIBLE);
        	link.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					String webAdress = "http://studentersamfundet.no/vis.php?ID=" + e.id;
					Uri webDestination = Uri.parse(webAdress);
					
					Intent webIntent = new Intent(Intent.ACTION_VIEW, webDestination);
					startActivity(webIntent);
				}
			});
        }
    }
}
