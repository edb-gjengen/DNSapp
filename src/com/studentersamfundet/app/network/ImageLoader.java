package com.studentersamfundet.app.network;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidParameterException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ImageLoader extends AsyncTask<String, Void, Drawable> {
	private final static ConcurrentMap<String, Drawable> cache = new ConcurrentHashMap<String, Drawable>();
	private final ViewGroup parent;
	private final int position;
	private final int imageId;
	private int progressbarId = -1;
	
	public ImageLoader(ViewGroup parent, int position, int imageId)  {
		this.parent = parent;
		this.position = position;
		this.imageId = imageId;
	}
		
	@Override
	protected Drawable doInBackground(String... params) {
		if (params.length > 1)
			throw new InvalidParameterException("Too many arguments to the function!");
		
		Drawable d = cache.get(params[0]);
		if (d != null)
			return d;
		
		try {
			HttpURLConnection con = (HttpURLConnection)new URL(params[0]).openConnection();
		    InputStream is = con.getInputStream();
		    		
			d = Drawable.createFromStream(is, params[0].toString());
			cache.put(params[0], d);
		} catch (IOException e) {
			Log.e("DNSapp", "An exception has occured while downloading images!", e);
		}
		return d;
	}
	
	@Override
	protected void onPostExecute(Drawable result) {
		super.onPostExecute(result);
		
		if (result != null && parent != null) {
			ViewGroup row = (ViewGroup)parent.getChildAt(position);
			
			if (row != null) {
				if (progressbarId > 0) {
					View pb = row.findViewById(progressbarId);
					pb.setVisibility(View.GONE);
				}
				ImageView image = (ImageView)row.findViewById(imageId);
				image.setImageDrawable(result);
				image.setVisibility(View.VISIBLE);
			}
		}
	}
	
	public void setProgressbar(int id) {
		this.progressbarId = id;
	}
}
