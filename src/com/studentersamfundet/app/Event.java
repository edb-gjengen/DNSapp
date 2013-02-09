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
	private String thumb;
	
	private int regularPrice;
	private int memberPrice;
	private String ticketUriStr;
	private String fbUriStr;
	
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
	
	public Uri getImageUri() {
		if (this.id <= 0) {
			return null;
		}
		return Uri.parse(this.image);
	}
	
	public Uri getThumbnail() {
		return Uri.parse(this.thumb);
	}
	
	public String getPriceString() {
		if (this.regularPrice > 0 || this.memberPrice > 0)
			return "" +this.regularPrice +'/' +this.memberPrice +"kr";
	
		return "";
	}
	
	public Uri getTicketUri() {
		Uri ticketUri = null;
		
		if (ticketUriStr != null 
				&& (ticketUriStr.startsWith("http://") 
					|| ticketUriStr.startsWith("https://"))
				&& ticketUriStr.length() > 7) {
			ticketUri = Uri.parse(ticketUriStr);
		}
		
		return ticketUri;
	}
	
	public void setImage(String imageUri, String thumbUri) {
		this.image = imageUri;
		this.thumb = thumbUri;
	}
	
	public void setTicketsInfo(int regularPrice, int memberPrice, String ticketUri) {
		this.regularPrice = regularPrice;
		this.memberPrice = memberPrice;
		this.ticketUriStr = ticketUri;
	}
	
	public void setFbUriStr(String uri) {
		this.fbUriStr = uri;
	}
	
	public String getFbUriStr() {
		return this.fbUriStr;
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
