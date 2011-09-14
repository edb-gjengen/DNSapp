package com.studentersamfundet.app.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.studentersamfundet.app.R;

public class DnsActivity extends Activity {
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        View tmp = this.findViewById(R.id.join_us_textview);
        tmp.setVisibility(View.INVISIBLE);
    }
    
    public void clickHandler(View v) {
    	if (v.getId() == R.id.program_button) {
    		startActivity(new Intent(this, ProgramList.class));
    	} else if (v.getId() == R.id.bli_med_button) {
    		View j = this.findViewById(R.id.bli_med_button);
    		ImageView p = (ImageView)this.findViewById(R.id.paragraph);
    		View t = this.findViewById(R.id.join_us_textview);
    		
    		j.setClickable(false);
    		p.setVisibility(View.INVISIBLE);
    		t.setVisibility(View.VISIBLE);
    	}
    }
    
    @Override
    public void onBackPressed () {
    	View t = this.findViewById(R.id.join_us_textview);
    	if (t.getVisibility() == View.VISIBLE) {
    		View j = this.findViewById(R.id.bli_med_button);
    		ImageView p = (ImageView)this.findViewById(R.id.paragraph);
    		j.setClickable(true);
    		t.setVisibility(View.INVISIBLE);
    		p.setVisibility(View.VISIBLE);
    	} else {
    		finish();
    	}
    }
}