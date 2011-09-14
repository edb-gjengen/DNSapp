package com.studentersamfundet.app.ui;

import com.studentersamfundet.app.R;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

public abstract class BaseDnsActivity extends Activity {
    public void clickHandler(View v) {
    	Intent intent = new Intent();
    	intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION 
    			| Intent.FLAG_ACTIVITY_NO_HISTORY
    			| Intent.FLAG_ACTIVITY_SINGLE_TOP);
    	
    	if (v.getId() == R.id.program_button) {
    		intent.setClass(this, ProgramListActivity.class);
    		startActivity(intent);
    		
    	} else if (v.getId() == R.id.bli_med_button) {
    		intent.setClass(this, JoinUsActivity.class);
    		startActivity(intent);
    	}
    }
}
