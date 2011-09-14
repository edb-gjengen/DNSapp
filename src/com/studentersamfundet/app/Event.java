package com.studentersamfundet.app;

public class Event {
	int id;
	String title;
	String description;
	String date;
	String location;
	String category;
	Event next;
	
	Event(int id, String title, String description, String date, String location, String category) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.date = date;
		this.location = location;
		this.category = category;
	}
}
