package com.studentersamfundet.app.ui;

import com.studentersamfundet.app.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.view.View;

public abstract class BaseDnsActivity extends Activity {
	protected Handler handler = new Handler();
	
    public void clickHandler(View v) {
    	Intent intent = new Intent();
    	
    	/* Program button was pushed: */
    	if (v.getId() == R.id.program_button) {
        	intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
        			| Intent.FLAG_ACTIVITY_SINGLE_TOP);
        	
    		intent.setClass(this, EventListActivity.class);
    		startActivity(intent);
    		
    	/* Join button was pushed: */
    	} else if (v.getId() == R.id.bli_med_button) {
        	intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY
        			| Intent.FLAG_ACTIVITY_SINGLE_TOP);
        	
    		intent.setClass(this, JoinUsActivity.class);
    		startActivity(intent);
    		
    	/* The header was pushed: */
    	} else if (v.getId() == R.id.header) {
        	intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
        			| Intent.FLAG_ACTIVITY_SINGLE_TOP);
        	
    		intent.setClass(this, DnsActivity.class);
    		startActivity(intent);
    	}
    }
    
    public void doNothing(View v) {
    	/* As promised. */
    }
}
