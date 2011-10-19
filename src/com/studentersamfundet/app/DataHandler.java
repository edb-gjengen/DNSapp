package com.studentersamfundet.app;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class DataHandler implements Serializable {
	private static final long serialVersionUID = 6697543939807512231L;
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
		String[] result = new String[categories.size() + 1];
		result[0] = Event.ALL;
		
		int counter = 1;
		for (String c : categories) {
			result[counter++] = c;
		}
		
		return result;
	}
}
