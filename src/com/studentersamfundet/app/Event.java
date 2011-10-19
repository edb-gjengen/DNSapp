package com.studentersamfundet.app;

import java.io.Serializable;

public class Event implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String ALL = "Alle";
	
	public int id;
	public String title;
	public String description;
	public String date;
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
		this.date = (date == null) ? "" : date.trim();
		this.location = (location == null) ? "" : location.trim();
		this.text = (text == null) ? "" : text.trim();
		this.category = (category == null) ? "" : category.trim();
	}
	
	public String toString() {
		return this.title;
	}
	
	public String getDateString() {
		String date = this.date;
		
		String month = date.substring(5, 7);
		String day = date.substring(8, 10);
		return Utils.stripNullFromDay(day) + ". " + Utils.intToMonthStr(Integer.parseInt(month));
	}
}
