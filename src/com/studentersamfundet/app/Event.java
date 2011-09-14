package com.studentersamfundet.app;

public class Event {
	public int id;
	public String title;
	public String description;
	public String date;
	public String location;
	public String category;
	
	public Event(int id, String title, String description, String date, String location, String category) {
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
}
