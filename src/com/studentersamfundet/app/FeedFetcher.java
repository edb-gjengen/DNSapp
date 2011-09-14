package com.studentersamfundet.app;

import java.io.FileInputStream;
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
	
	private InputStream OpenHttpConnection(String URL) throws IOException {
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
	
	public NodeList fetch(String URL, Context c, boolean con) {
	       InputStream in = null;
	       NodeList itemNodes = null;
	       
	        try {
	        	if (con) {
	        		in = OpenHttpConnection(URL);
	            	saveFile(in, c);
	            	in = OpenHttpConnection(URL);
	        	} else
	        		in = new FileInputStream(URL);
	            Document doc = null;
	            DocumentBuilderFactory dbf = 
	                DocumentBuilderFactory.newInstance();
	            DocumentBuilder db;
	            
	            try {
	                db = dbf.newDocumentBuilder();
	                doc = db.parse(in);
	            } catch (ParserConfigurationException e) {
	            } catch (SAXException e) {}        
	            
	            doc.getDocumentElement().normalize(); 
	            
	            // Retrieve all the <item> nodes.
	            itemNodes = doc.getElementsByTagName("item");
	            in.close();
	        } catch (IOException e) {}
	        return itemNodes;
	}
	
	void saveFile(InputStream in, Context c) throws IOException, FileNotFoundException {
		final String FILENAME = "dns_events";
		final int BUFFER_SIZE = 2000;
		
		InputStreamReader isr = new InputStreamReader(in);
		
		char[] inputBuffer = new char[BUFFER_SIZE];
		StringBuilder outputStr = new StringBuilder();

		int charRead;
		while ((charRead = isr.read(inputBuffer)) > 0) {
			outputStr.append(charRead);
			inputBuffer = new char[BUFFER_SIZE];
		}
		in.close();

		FileOutputStream fos = c.openFileOutput(FILENAME, Context.MODE_PRIVATE);
		fos.write(outputStr.toString().getBytes());
		fos.close();
	}
}
