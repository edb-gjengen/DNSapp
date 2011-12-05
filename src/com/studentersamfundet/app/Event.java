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
	
	public final int id;
	public final String title;
	private String description;
	private Date date;
	private String location;
	private String text;
	private String category;
	private String image;
	
	private int regularPrice;
	private int memberPrice;
	private Uri ticketUri;
	
	public Event(
			int id, 
			String title, 
			String description, 
			String date, 
			String location,
			String text,
			String category,
			String image) {
		this.id = id;
		this.title = title.trim();
		this.description = (description == null) ? "" : description.trim();
		this.location = (location == null) ? "" : location.trim();
		this.text = (text == null) ? "" : text.trim();
		this.category = (category == null) ? "" : category.trim();
		this.image = image;
		
		try {
			this.date = (date == null) ? null : dateFormat.parse(date.trim());
		} catch (ParseException e) {
			this.date = null;
		}
	}
	
	public String toString() {
		return this.title;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public String getContent() {
		return this.text;
	}
	
	public String getCategory() {
		return this.category;
	}
	
	public Date getDate() {
		return this.date;
	}
	
	public String getLocation() {
		return this.location;
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
		
		String imageUri = "http://studentersamfundet.no/imageResize.php?pic=bilder/program/{image}&maxwidth=480";
		return Uri.parse(imageUri.replaceAll("{image}", this.image));
	}
	
	public String getPriceString() {
		return "" +this.regularPrice +'/' +this.memberPrice;
	}
	
	public Uri getTicketUri() {
		return this.ticketUri;
	}
	
	public void setTicketsInfo(int regularPrice, int memberPrice, Uri ticketUri) {
		this.regularPrice = regularPrice;
		this.memberPrice = memberPrice;
		this.ticketUri = ticketUri;
	}
}
