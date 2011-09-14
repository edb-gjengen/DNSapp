package com.studentersamfundet.app.ui;

import com.studentersamfundet.app.R;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

public abstract class BaseDnsActivity extends Activity {
    public void clickHandler(View v) {
    	Intent intent = new Intent();
    	
    	if (v.getId() == R.id.program_button) {
        	intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
        			| Intent.FLAG_ACTIVITY_SINGLE_TOP);
        	
    		intent.setClass(this, ProgramListActivity.class);
    		startActivity(intent);
    		
    	} else if (v.getId() == R.id.bli_med_button) {
        	intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY
        			| Intent.FLAG_ACTIVITY_SINGLE_TOP);
        	
    		intent.setClass(this, JoinUsActivity.class);
    		startActivity(intent);
    	}
    }
}
