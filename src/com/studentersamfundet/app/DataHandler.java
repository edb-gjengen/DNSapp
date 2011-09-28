package com.studentersamfundet.app;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class DataHandler {
	private List<Event> events = new LinkedList<Event>();
	private Set<String> categories = new TreeSet<String>();
	
	public void insert(String id, String title, String description, String date, String location, String text, String category) {
		int intId = Integer.parseInt(id);
		if (get(intId) != null)
			return;
		
		Event e = new Event(intId, title, description, date, location, text, category);
		events.add(e);
		categories.add(category);
	}
	
	public Event get(int id) {
		for (Event e : events) {
			if (e.id == id)
				return e;
		}
		return null;
	}
	
	public Event[] populateList(String category) {
		LinkedList<Event> sorted = new LinkedList<Event>();

		if (category.equals(Event.ALL)) {
			return events.toArray(new Event[events.size()]);
		}

		for (Event event : events) {
			if (category.equals(event.category))
				sorted.add(event);
		}
		return sorted.toArray(new Event[sorted.size()]);
    }
	
	public String[] getCategories() {
		return categories.toArray(new String[categories.size()]);
	}
}
