package com.studentersamfundet.app;

import java.io.Serializable;
import java.util.Date;

import android.net.Uri;

public class Event implements Serializable {
	private static final long serialVersionUID = -5222901012434000257L;
	
	public static final String ALL = "Alle";
	
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
	private String ticketUriStr;
	
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
			this.date = (date == null) ? null : new Date(1000 * Long.parseLong(date.trim()));
		} catch (NumberFormatException e) {
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
	
	public Uri getImageUri(int size) {
		if (this.id <= 0) {
			return null;
		}
		
		String imageUri = "http://studentersamfundet.no/imageResize.php?pic=bilder/program/{img}&maxwidth={width}&maxheight={height}&crop=1";
		imageUri = imageUri.replace("{img}", this.image);
		imageUri = imageUri.replace("{width}", Integer.toString(size));
		imageUri = imageUri.replace("{height}", Integer.toString(size));

		return Uri.parse(imageUri);
	}
	
	public String getPriceString() {
		if (this.regularPrice > 0 || this.memberPrice > 0)
			return "" +this.regularPrice +'/' +this.memberPrice +"kr";
	
		return "";
	}
	
	public Uri getTicketUri() {
		Uri ticketUri = null;
		
		if (ticketUriStr != null 
				&& ticketUriStr.startsWith("http://") 
				&& ticketUriStr.length() > 7) {
			ticketUri = Uri.parse(ticketUriStr);
		}
		
		return ticketUri;
	}
	
	public void setTicketsInfo(int regularPrice, int memberPrice, String ticketUri) {
		this.regularPrice = regularPrice;
		this.memberPrice = memberPrice;
		this.ticketUriStr = ticketUri;
	}
	
	public int hashCode() {
		return this.id;
	}
	
	public boolean equals(Object o2) {
		if (o2 instanceof Event) {
			Event e2 = (Event)o2;
			return this.id == e2.id;
		}
		return false;
	}
}
