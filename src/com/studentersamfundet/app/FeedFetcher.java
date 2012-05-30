package com.studentersamfundet.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import android.content.Context;

public class FeedFetcher implements Serializable {
	private static final long serialVersionUID = 3408406671996232103L;
	private final String localFilename;
	private String url;
	
	public FeedFetcher(String url, String localFilename) {
		this.url = url;
		this.localFilename = localFilename;
	}
	
	private InputStream openHttpConnection() throws IOException {
		InputStream in = null;
		int response = -1;
		URL url = new URL(this.url);
		URLConnection con = url.openConnection();
		
		if (!(con instanceof HttpURLConnection))       
			throw new IOException("Not an HTTP connection!");
		
		HttpURLConnection httpCon = (HttpURLConnection) con;
		httpCon.setAllowUserInteraction(false);
		httpCon.setInstanceFollowRedirects(true);
		httpCon.setRequestMethod("GET");
		httpCon.connect();
		
		response = httpCon.getResponseCode();
		
		if (response == HttpURLConnection.HTTP_OK)
			in = httpCon.getInputStream();
		
		return in;
	}
	
	/**
	 * Fetches list of events from the web.
	 * @param forcedUpdate specifies if we HAVE TO update local file, or let 
	 * the function decide.
	 * @return Returns list of events as a XML NodeList
	 * @throws IOException Is thrown if the app cannot download the file from
	 * the internet and there is no local copy.
	 */
	public InputStream fetch(Context context, boolean forcedUpdate) throws IOException {
		InputStream in = null;
		   
			/* Update the local file if possible. */
		try {
			if (forcedUpdate || doesFileNeedUpdate(context)) {
				in = openHttpConnection();
		    	saveFile(in, context);
			}
			
		} catch(IOException e) {
			/* OK, so we failed to update it. It doesn't matter yet, because we can try to... */
		} finally {
			if (in != null) 
				in.close();
		}
		
		/* ...load local file: */
		in = loadFile(context); // if we fail here, we fail ultimately. Throw the Exception!
		
		return in;
	}
	
	public InputStream loadLocalData(Context c) throws IOException {
		InputStream is = loadFile(c);
		return is;
	}
	
	
	private boolean doesFileNeedUpdate(Context c) throws IOException {
		/** Maximum amount of time before the file needs updating, in ms: */
		final long maxInterval = 1000 * 60 * 60; // one hour
			
		File file = c.getFileStreamPath(localFilename);
		if (! file.exists()) return true;
		
		long now = System.currentTimeMillis();
		long lastMod = file.lastModified();
		
		return now - lastMod > maxInterval;
	}
	
	private void saveFile(InputStream in, Context c) throws IOException, FileNotFoundException {
		final int BUFFER_SIZE = 128;
		
		OutputStream os = c.openFileOutput(localFilename, Context.MODE_PRIVATE);
		byte[] inputBuffer = new byte[BUFFER_SIZE];
		
		int bytes = 0;
		while ((bytes = in.read(inputBuffer)) > 0) {
			os.write(inputBuffer, 0, bytes);
			inputBuffer = new byte[BUFFER_SIZE];
		}
		os.close();
	}
	
	private InputStream loadFile(Context c) throws FileNotFoundException {
		return c.openFileInput(localFilename);
	}
}
