package com.studentersamfundet.app.ui;

import java.util.Calendar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import com.studentersamfundet.app.FeedFetcher;
import com.studentersamfundet.app.R;
import com.studentersamfundet.app.RSSParserNews;
import com.studentersamfundet.app.RSSParserProgram;
import com.studentersamfundet.app.ui.lists.EventListActivity;
import com.studentersamfundet.app.ui.lists.EventListTicketsActivity;
import com.studentersamfundet.app.ui.lists.NewsListActivity;

public class DnsActivity extends BaseDnsActivity {
	public static final String eventFeedURL = "http://studentersamfundet.no/rss/robert_program_feed.php";
	public static final String newsFeedURL = "http://studentersamfundet.no/rss/nyheter_feed.php";
	
	private FeedFetcher eventFeedFetcher;
	private FeedFetcher newsFeedFetcher;
	
	private String[] openingHoursHouse = {
		"10.00 - 01.00",
		"10.00 - 01.00",
		"10.00 - 01.00",
		"10.00 - 03.00",
		"10.00 - 03.00",
		"12.00 - 03.00",
		"12.00 - 20.00"};
	
	private String[] openingHoursGB = {
		"13.00 - 01.00",
		"13.00 - 01.00",
		"13.00 - 01.00",
		"13.00 - 03.00",
		"13.00 - 03.00",
		"16.00 - 03.00",
		"Stengt"};	
	
	private String[] openingHoursBC = {
		"19.00 - 00.00",
		"19.00 - 00.00",
		"19.00 - 00.00",
		"19.00 - 03.00",
		"19.00 - 03.00",
		"20.00 - 03.00",
		"Stengt"};
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		/* Initialize necessary variables: */
		eventFeedFetcher = new FeedFetcher(eventFeedURL, "local_events");
		newsFeedFetcher = new FeedFetcher(newsFeedURL, "local_news");
		
		/* Set opening hours: */
		TextView tvNeuf = (TextView)findViewById(R.id.main_menu_hours_neuf);
		TextView tvBC = (TextView)findViewById(R.id.main_menu_hours_bc);
		TextView tvGB = (TextView)findViewById(R.id.main_menu_hours_gb);
		int day = (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) + 5) % 7;
		
		tvNeuf.setText(openingHoursHouse[day]);
		tvBC.setText(openingHoursBC[day]);
		tvGB.setText(openingHoursGB[day]);
		
		setupFocus();
	}

	public void setupFocus() {
		final View[] buttons = {
			findViewById(R.id.main_menu_program),
			findViewById(R.id.main_menu_tickets),
			findViewById(R.id.main_menu_join),
			findViewById(R.id.main_menu_news),
		};
		
		for (int i = 0; i < buttons.length; i++) {
			final int index = i;
			
			buttons[index].setOnTouchListener(new OnTouchListener() {
				boolean pointerMoved = false;
				
				public boolean onTouch(View v, MotionEvent event) {
					if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
						for (int j = 0; j < buttons.length; j++) {
							if (index != j) {								
								Animation animation = new AlphaAnimation(1.0f, 0.6f);
								animation.setDuration(100);
								animation.setFillAfter(true);

								buttons[j].startAnimation(animation);
								
							}
						}
						
						this.pointerMoved = false;
						return true;
					} 
					
					if (event.getActionMasked() == MotionEvent.ACTION_MOVE) {
						if (this.pointerMoved)
							return true;
						
						float x = event.getRawX();
						float y = event.getRawY();
						
						int[] location = new int[2];
						v.getLocationOnScreen(location);
						
						int width = v.getMeasuredWidth();
						int height = v.getMeasuredHeight();
						
						if (x < location[0]
							    || x > location[0]+width
							    || y < location[1]
							    || y > location[1]+height) {
							this.pointerMoved = true;
						}
					}
					
					if (event.getActionMasked() == MotionEvent.ACTION_UP) {
						/* Define the animation that makes elements opaque: */
						Animation animation = new AlphaAnimation(0.6f, 1.0f);
						animation.setDuration(50);
						animation.setFillAfter(true);
						
						if (!this.pointerMoved) {		
							animation.setDuration(0);
							v.performClick();
						}
						
						for (int j = 0; j < buttons.length; j++) {
							buttons[j].startAnimation(animation);
						}
						
						return true;
					}
					
					return false;
				}
			});
		}
	}
	
	public void programButton(View v) {
		Intent intent = setupNewIntent(EventListActivity.class);
		intent.putExtra("feed", eventFeedFetcher);
		intent.putExtra("parser", new RSSParserProgram());
		startActivity(intent);
	}

	public void ticketButton(View v) {
		Intent intent = setupNewIntent(EventListTicketsActivity.class);
		intent.putExtra("feed", eventFeedFetcher);
		intent.putExtra("parser", new RSSParserProgram());
		startActivity(intent);
	}

	public void newsButton(View v) {
		Intent intent = setupNewIntent(NewsListActivity.class);
		intent.putExtra("feed", newsFeedFetcher);
		intent.putExtra("parser", new RSSParserNews());
		startActivity(intent);
	}

	public void joinButton(View v) {
		Intent intent = setupNewIntent(JoinUsActivity.class);
		startActivity(intent);
	}
}