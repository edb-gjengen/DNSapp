package com.studentersamfundet.app.ui.lists;

import java.io.IOException;
import java.io.InputStream;

import com.studentersamfundet.app.DataHandler;
import com.studentersamfundet.app.Event;
import com.studentersamfundet.app.FeedFetcher;
import com.studentersamfundet.app.IParser;
import com.studentersamfundet.app.R;
import com.studentersamfundet.app.ui.BaseDnsActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

public abstract class BaseListActivity extends BaseDnsActivity {
	private DataHandler datahandler;
	private FeedFetcher feed;
	private IParser parser;
	
	private boolean isUpdated = false;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/* Fetch the feed fetcher: */
		Intent i = getIntent();
		this.feed = (FeedFetcher)i.getExtras().get("feed");
		if (this.feed == null)
			throw new RuntimeException("This activity must be fed a feed feeder.");

		/* Create the parser: */
		this.parser = (IParser)i.getExtras().get("parser");
		if (this.parser == null)
			throw new RuntimeException("This activity requires feed parser");

		/* DataHandler will be set later. */
	}

	protected final DataHandler getDataHandler() {
		if (!isUpdated && datahandler == null)
			throw new RuntimeException("You have to create and run list creator first!");
		
		return this.datahandler;
	}
	
	/**
	 * Creates list adapter used by listview. 
	 * @param objects
	 * @return
	 */
	protected abstract <T> ListAdapter createAdapter(T[] objects);
	
	protected abstract class ListCreator<T> extends AsyncTask<Void, Void, Boolean> {
		private String category = Event.ALL;
		private boolean forcedUpdate = false;

		private final Context context;
		private final int idList;
		private final int idProgressBar;

		public ListCreator(Context context, int idList, int idProgressBar) {
			this.context = context;
			this.idList = idList;
			this.idProgressBar = idProgressBar;
		}

		protected String getCategory() {
			return this.category;
		}

		protected void setCategory(String category) {
			this.category = category;
		}

		protected boolean getForcedUpdate() {
			return this.forcedUpdate;
		}

		protected void setForcedUpdate(boolean forcedUpdate) {
			this.forcedUpdate = forcedUpdate;
		}

		@Override 
		protected void onPreExecute() {
			if (forcedUpdate) {
				datahandler = null;
			}

			ListView list = (ListView)findViewById(idList);
			list.setVisibility(View.GONE);

			ProgressBar pb = (ProgressBar)findViewById(idProgressBar);
			if (pb != null)
				pb.setVisibility(View.VISIBLE);
		}

		@Override
		protected Boolean doInBackground(Void ... v) {
					// Do we have intarwebs?
					// YAY = Fetch the feed.
					// NAY = Inform about no connection.
					try {
						if (datahandler == null) {
							InputStream in = feed.fetch(BaseListActivity.this, this.forcedUpdate);
							datahandler = parser.parse(in);
						}
						return true;

					} catch (IOException e) {
						return false;
					}
		}

		@Override
		protected void onPostExecute(Boolean result) {
			isUpdated = true;
			
			if (result) {
				ListView list = (ListView)findViewById(idList);
				T[] objects = getObjects();

				ProgressBar pb = (ProgressBar)findViewById(idProgressBar);
				if (pb != null)
					pb.setVisibility(View.GONE);
				
				Log.i("DNSapp", "Showing " + objects.length +" objects.");
				ListAdapter adapter = createAdapter(objects);
				if (adapter == null) {
					return;
				}
				
				list.setAdapter(adapter);
				list.setVisibility(View.VISIBLE);

			} else {
				Toast toast = Toast.makeText(context, R.string.error_noconnection_noupdate, Toast.LENGTH_LONG);
				toast.show();
			}
		}

		abstract protected T[] getObjects();
	}
}
