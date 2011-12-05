package com.studentersamfundet.app;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.net.Uri;

public class Event implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String ALL = "Alle";
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	public int id;
	public String title;
	public String description;
	public Date date;
	public String location;
	public String text;
	public String category;
	
	public Event(
			int id, 
			String title, 
			String description, 
			String date, 
			String location,
			String text,
			String category) {
		this.id = id;
		this.title = title.trim();
		this.description = (description == null) ? "" : description.trim();
		this.location = (location == null) ? "" : location.trim();
		this.text = (text == null) ? "" : text.trim();
		this.category = (category == null) ? "" : category.trim();
		
		try {
			this.date = (date == null) ? null : dateFormat.parse(date.trim());
		} catch (ParseException e) {
			this.date = null;
		}
	}
	
	public String toString() {
		return this.title;
	}
	
	public Uri getUri() {
		if (this.id <= 0)
			return null;
		
		return Uri.parse("http://studentersamfundet.no/vis.php?ID=" + this.id);
	}
	
	public Uri getImageUri() {
		if (this.id <= 0) {
			return null;
		}
		
		String imageUri = "http://studentersamfundet.no/imageResize.php?pic=bilder/program/{id}.jpg&maxwidth=480";
		return Uri.parse(imageUri.replaceAll("{id}", Integer.toString(this.id)));
	}
}
