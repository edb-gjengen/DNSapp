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
				tempJSON = eventArray.getJSONObject(i);
				id = tempJSON.getString("id");
				title = tempJSON.getString("title");
				description = tempJSON.getString("excerpt");
				date = tempJSON.getString("date");
				text = tempJSON.getString("content");
				image = tempJSON.getString("thumbnail");
				
				tempJSON = tempJSON.getJSONObject("custom_fields");
				ticketUrl = tempJSON.getJSONArray("_neuf_events_bs_url").getString(0);
				fbUrl = tempJSON.getJSONArray("_neuf_events_fb_url").getString(0);
				location = tempJSON.getJSONArray("_neuf_events_venue").getString(0);
				priceReg = tempJSON.getJSONArray("_neuf_events_price_regular").getString(0);
				priceMem = tempJSON.getJSONArray("_neuf_events_price_member").getString(0);
				date = tempJSON.getJSONArray("_neuf_events_starttime").getString(0);
				
				
				// This is as ugly as it gets. Refactor?
				dh.insertEvent(id, 
						title, 
						description, 
						date,
						location, 
						text, 
						category, 
						image,
						priceReg,
						priceMem,
						ticketUrl,
						fbUrl);
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
