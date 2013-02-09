package com.studentersamfundet.app;

import java.io.IOException;
import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JSONParserProgram implements IParser {
	private static final long serialVersionUID = 3364287016121595334L;
	private final DataHandler dh;

	public JSONParserProgram() {
		this(null);
	}

	public JSONParserProgram(DataHandler dh) {
		if (dh == null) {
			this.dh = new DataHandler();
		} else {
			this.dh = dh;
		}
	}

	public DataHandler parse(InputStream in) {
		String id = "";
		String title = "";
		String description = "";
		String text = "";
		String image = "";
		String thumb = "";
		
		String date = "";
		String location = "";
		String category = "";
		String priceReg = "";
		String priceMem = "";
		String ticketUrl = "";
		String fbUrl = "";
		
		try {
			String jString = Utils.convertStreamToString(in);
			in.close();
			JSONObject jObject = new JSONObject(jString);
			JSONObject tempJSON;
			JSONArray eventArray = jObject.getJSONArray("events");
			int count = jObject.getInt("count");
			
			for (int i = 0;i < count;i++) { 
				// Variables directly from the JSONObject
				try {
					tempJSON = eventArray.getJSONObject(i);
					id = tempJSON.getString("id");
					title = tempJSON.getString("title");
					description = tempJSON.getString("excerpt");
					date = tempJSON.getString("date");
					text = tempJSON.getString("content");
					category = tempJSON.getString("event");
					
					JSONObject custom = tempJSON.getJSONObject("custom_fields");
					ticketUrl = custom.getJSONArray("_neuf_events_bs_url").getString(0);					
					fbUrl = custom.getJSONArray("_neuf_events_fb_url").getString(0);
					location = custom.getJSONArray("_neuf_events_venue").getString(0);
					priceReg = custom.getJSONArray("_neuf_events_price_regular").getString(0);
					priceMem = custom.getJSONArray("_neuf_events_price_member").getString(0);
					date = custom.getJSONArray("_neuf_events_starttime").getString(0);
					
					JSONArray attachments = tempJSON.getJSONArray("attachments");
					JSONObject images = attachments.getJSONObject(0).getJSONObject("images");
					
					image = images.getJSONObject("full").getString("url");
					thumb = images.getJSONObject("thumbnail").getString("url");
					
					// This is as ugly as it gets. Refactor?
					dh.insertEvent(id, 
							title, 
							description, 
							date,
							location, 
							text, 
							category, 
							image,
							thumb,
							priceReg,
							priceMem,
							ticketUrl,
							fbUrl);
				} catch (JSONException e) {
					Log.e("json-parser", e.getMessage());
					continue;
				} 
			}
		} catch (IOException e) {
			Log.e("json-parser", e.getMessage());
		} catch (JSONException e) {
			Log.e("json-parser", e.getMessage());
		}
		return dh;
	}
}
