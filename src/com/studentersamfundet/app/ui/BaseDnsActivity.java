package com.studentersamfundet.app.ui;

import com.studentersamfundet.app.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.view.View;

public abstract class BaseDnsActivity extends Activity {
	protected Handler handler = new Handler();
	
    public void clickHandler(View v) {
    	Intent intent = null;
    	
    	/* Program button was pushed: */
    	if (v.getId() == R.id.main_menu_program) {
        	intent = setupNewIntent(EventListActivity.class);
    		startActivity(intent);
    		
    	/* Join button was pushed: */
    	} else if (v.getId() == R.id.main_menu_join) {
        	intent = setupNewIntent(JoinUsActivity.class);
    		startActivity(intent);
    		
    	/* The header was pushed: */
    	} else if (v.getId() == R.id.header) {
        	intent = setupNewIntent(DnsActivity.class);
    		startActivity(intent);
    	}
    }
    
    public void doNothing(View v) {
    	/* As promised. */
    }
    
    protected Intent setupNewIntent(Class<?> cl) {
    	Intent intent = new Intent();
    	intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
    			| Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.setClass(this, cl);
		
		return intent;
    }
}
