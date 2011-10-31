package com.studentersamfundet.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.studentersamfundet.app.R;

public class DnsActivity extends BaseDnsActivity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		/* Set opening hours: */
		TextView openingHours = (TextView)findViewById(R.id.main_menu_openinghours);
		openingHours.setText(
				   "Huset      13.00-01.00"
				+"\nKjøkkenet       -19.00"
				+"\nBokCaféen  19.00-00.00");

	}

	public void programButton(View v) {
		Intent intent = new Intent();

		intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
				| Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.setClass(this, ProgramListActivity.class);

		startActivity(intent);
	}

	public void ticketButton(View v) {
		Toast.makeText(this, "Not implemented (yet!)", Toast.LENGTH_SHORT).show();
	}

	public void streamButton(View v) {
		Toast.makeText(this, "Not implemented (yet!)", Toast.LENGTH_SHORT).show();
	}

	public void joinButton(View v) {
		Intent intent = new Intent();
		
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.setClass(this, JoinUsActivity.class);
		
		startActivity(intent);

	}
}