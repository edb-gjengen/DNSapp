package com.studentersamfundet.app;

import java.io.IOException;
import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JSONParserNews implements IParser {
	private static final long serialVersionUID = -2654591623794449527L;
	private final DataHandler dh;
	
	public JSONParserNews() {
		 this(null);
	}
	
	public JSONParserNews(DataHandler dh) {
		if (dh == null) {
			this.dh = new DataHandler();
		} else {
			this.dh = dh;
		}
	}

	public DataHandler parse(InputStream in) {
        String title = "";
        String description = "";
        String link = "";
        String content = "";
        String pubDate = "";
        
        try {
        	String jString = Utils.convertStreamToString(in);
        	in.close();
        	JSONObject jObject = new JSONObject(jString);
        	JSONObject tempJSON;
        	JSONArray eventArray = jObject.getJSONArray("posts");
	        int count = jObject.getInt("count");
	        
	        for (int i = 0;i < count;i++) { 
	        	tempJSON = eventArray.getJSONObject(i);
	        	title = tempJSON.getString("title");
	        	description = tempJSON.getString("excerpt");
	        	link = tempJSON.getString("url");
	        	content = tempJSON.getString("content");
	        	pubDate = tempJSON.getString("date");
	        	
	        	dh.insertNews(title, description, content, link, pubDate);           
	        }

	        
        } catch (IOException e) {
        	Log.e("json-parser", e.getMessage());
        	return dh;
        } catch (JSONException e) {
        	Log.e("json-parser", e.getMessage());
        	return dh;
        }
        
        return dh;
	}
}
