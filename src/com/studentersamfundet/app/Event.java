package com.studentersamfundet.app;

import java.io.Serializable;

public class Event implements Serializable {
	private static final long serialVersionUID = 1L;
	public int id;
	public String title;
	public String description;
	public String date;
	public String location;
	public String category;
	
	public Event(
			int id, 
			String title, 
			String description, 
			String date, 
			String location, 
			String category) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.date = date;
		this.location = location;
		this.category = category;
	}
	
	public String toString() {
		return this.title;
	}
	
	public String getDateString() {
		String date = this.date;
		
		String month = date.substring(5, 7);
		String day = date.substring(8, 10);
		return day + ". " + Utils.intToMonthStr(Integer.parseInt(month));
	}
}
