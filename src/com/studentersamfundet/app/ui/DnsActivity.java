package com.studentersamfundet.app.ui;

import java.util.Calendar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.studentersamfundet.app.R;

public class DnsActivity extends BaseDnsActivity {
	String[] openingHoursHouse = {
		"13.00 - 01.00",
		"13.00 - 01.00",
		"      - 01.30",
		"      - 03.00",
		"      - 03.00",
		"15.00 - 03.00",
		"      - 19.00"};
	
	String[] openingHoursBC = {
		"Stengt",
		"19.00 - 00.00",
		"19.00 - 00.00",
		"19.00 - 00.00",
		"19.00 - 03.00",
		"19.00 - 03.00",
		"20.00 - 03.00"};
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		/* Set opening hours: */
		TextView tvNeuf = (TextView)findViewById(R.id.main_menu_hours_neuf);
		TextView tvBC = (TextView)findViewById(R.id.main_menu_hours_bc);
		int day = (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) + 5) % 7;
		
		tvNeuf.setText(openingHoursHouse[day]);
		tvBC.setText(openingHoursBC[day]);

	}

	public void programButton(View v) {
		Intent intent = new Intent();

		intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
				| Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.setClass(this, ProgramListActivity.class);

		startActivity(intent);
	}

	public void ticketButton(View v) {
		Intent intent = new Intent();

		intent.setAction(Intent.ACTION_VIEW);
		intent.setData(Uri.parse("http://m.billettservice.no/ticket/search.do?articles=tmno&query=studentersamfundet&submit=S%C3%B8k"));

		startActivity(intent);
	}

	public void newsButton(View v) {
		Toast.makeText(this, "Not implemented (yet!)", Toast.LENGTH_SHORT).show();
	}

	public void joinButton(View v) {
		Intent intent = new Intent();
		
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.setClass(this, JoinUsActivity.class);
		
		startActivity(intent);

	}
}