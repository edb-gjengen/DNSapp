package com.studentersamfundet.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.studentersamfundet.app.Event;
import com.studentersamfundet.app.R;

public class EventViewActivity extends BaseDnsActivity {
   @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_view);
        
        Intent intent = getIntent();
        Event e = (Event)intent.getSerializableExtra("event");
        
        if (e == null) {
        	throw new Error("This should NEVER happen");
        }
        
        TextView title = (TextView) findViewById(R.id.event_view_title);
        TextView desc = (TextView) findViewById(R.id.event_view_decription);
        
        title.setText(e.title);
        desc.setText(e.description);
    }
}
