package com.studentersamfundet.app;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class DataHandler {
	private List<Event> events = new LinkedList<Event>();
	private Set<String> categories = new TreeSet<String>();
	
	public void insertEvent(String id, String title, String description, String date, String location, String text, String category) {
		int intId = Integer.parseInt(id);
		if (getEvent(intId) != null)
			return;
		
		Event e = new Event(intId, title, description, date, location, text, category);
		events.add(e);
		categories.add(category);
	}
	
	public Event getEvent(int id) {
		for (Event e : events) {
			if (e.id == id)
				return e;
		}
		return null;
	}
	
	public Event[] populateEventList(String category) {
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
	
	public String[] getEventCategories() {
		String[] result = new String[categories.size() + 1];
		result[0] = Event.ALL;
		
		int counter = 1;
		for (String c : categories) {
			result[counter++] = c;
		}
		
		return result;
	}
}
