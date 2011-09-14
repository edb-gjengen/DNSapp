package com.studentersamfundet.app;

public class DataHandler {
	Event events;

	void insert(String id, String title, String description, String date, String location, String category) {
		int intId = Integer.parseInt(id);
		if (get(intId) != null)
			return;
		
		Event e = new Event(intId, title, description, date, location, category);
		
		if (events == null)
			events = e;
		else {
			Event tmp = events;
			while (tmp.next != null)
				tmp = tmp.next;
			tmp.next = e;
		}
	}
	
	Event get(int id) {
		Event e = events;
		while (e != null && e.id != id)
			e = e.next;
		return e;
	}
}
