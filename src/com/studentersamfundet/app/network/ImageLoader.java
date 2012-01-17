package com.studentersamfundet.app.network;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

public class ImageLoader extends AsyncTask<String, Void, Drawable> {
	public final static Map<String, Drawable> cache = new HashMap<String, Drawable>();
	private final ImageView view;
	
	public ImageLoader(ImageView... views)  {
		if (views.length > 1)
			throw new InvalidParameterException("Too many arguments to the function!");
		
		this.view = views[0];
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
		
		if (result != null && view != null) {
			view.setImageDrawable(result);
		}
	}
}
