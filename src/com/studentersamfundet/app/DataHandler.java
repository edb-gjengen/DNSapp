package com.studentersamfundet.app;

import java.util.LinkedList;

public class DataHandler {
	private LinkedList<Event> events = new LinkedList<Event>();

	public void insert(String id, String title, String description, String date, String location, String category) {
		int intId = Integer.parseInt(id);
		if (get(intId) != null)
			return;
		
		Event e = new Event(intId, title, description, date, location, category);
		events.add(e);
	}
	
	public Event get(int id) {
		for (Event e : events) {
			if (e.id == id)
				return e;
		}
		return null;
	}
	
    public Event[] populateList() {
    	return events.toArray(new Event[events.size()]);
    }
}
