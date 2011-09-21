package com.studentersamfundet.app;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.content.Context;

public class FeedFetcher {
	private final String LOCAL_FILENAME = "dns_events";
	
	private InputStream openHttpConnection(String URL) throws IOException {
		InputStream in = null;
		int response = -1;
		URL url = new URL(URL);
		URLConnection con = url.openConnection();
		
		if (!(con instanceof HttpURLConnection))       
			throw new IOException("Not an HTTP connection!");
		
		try {
			HttpURLConnection httpCon = (HttpURLConnection) con;
			httpCon.setAllowUserInteraction(false);
			httpCon.setInstanceFollowRedirects(true);
			httpCon.setRequestMethod("GET");
			httpCon.connect();
			
			response = httpCon.getResponseCode();
			
			if (response == HttpURLConnection.HTTP_OK)
				in = httpCon.getInputStream();
		} catch (Exception e) {
			throw new IOException("Error connecting.");
		}
		
		return in;
	}
	
	/**
	 * Fetches list of events from the web.
	 * @param URL String containing address to the rss-feed.
	 * @param c Context from which this function is called.
	 * @return Returns list of events as a XML NodeList
	 * @throws IOException Is thrown if the app cannot download the file from
	 * the internet and there is no local copy.
	 */
	public NodeList fetch(String URL, Context c) throws IOException {
	       InputStream in = null;
	       NodeList itemNodes = null;
	       
        	/* Update the local file if possible. */
        	try {
            	saveFile(openHttpConnection(URL), c);
            	
        	} catch(IOException e) {
        		/* OK, so we failed to update it. It doesn't matter yet, because we can try to... */
        	}
        	
    		/* ...load local file: */
        	in = loadFile(c); // if we fail here, we fail ultimately. Throw the Exception! 
    	
        	
            Document doc = null;
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db;
            
            try {
                db = dbf.newDocumentBuilder();
                doc = db.parse(in);
            } catch (ParserConfigurationException e) {
            	/* This should never happen. */
            } catch (SAXException e) {
            	/* This shouldn't happen unless the feed returned by the server
            	 * is somehow incompatible with the what the application expects.
            	 * If it happens, the best course of action is to pretend we failed
            	 * to download the event list (it's close enough and it simplifies
            	 * exception-handling). */
            	throw new IOException("Bad response from server.");
            }        
            
            doc.getDocumentElement().normalize(); 
            
            // Retrieve all the <item> nodes.
            itemNodes = doc.getElementsByTagName("item");
            in.close();

	        return itemNodes;
	}
	
	private void saveFile(InputStream in, Context c) throws IOException, FileNotFoundException {
		final int BUFFER_SIZE = 2000;
		
		InputStreamReader isr = new InputStreamReader(in);
		
		char[] inputBuffer = new char[BUFFER_SIZE];
		StringBuilder outputStr = new StringBuilder();

		while (isr.read(inputBuffer) > 0) {
			outputStr.append(inputBuffer);
			inputBuffer = new char[BUFFER_SIZE];
		}
		in.close();

		FileOutputStream fos = c.openFileOutput(LOCAL_FILENAME, Context.MODE_PRIVATE);
		fos.write(outputStr.toString().getBytes());
		fos.close();
	}
	
	private InputStream loadFile(Context c) throws FileNotFoundException {
		return c.openFileInput(LOCAL_FILENAME);
	}
}
