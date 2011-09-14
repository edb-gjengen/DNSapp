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
    
    public String toMonth(String m) {
    	int monthInt = Integer.parseInt(m);
    	String monthString;
    	
    	switch (monthInt) {
    		case 1:  monthString = "Jan";      break;
    		case 2:  monthString = "Feb";      break;
    		case 3:  monthString = "Mar";      break;
    		case 4:  monthString = "Apr";      break;
    		case 5:  monthString = "Mai";      break;
    		case 6:  monthString = "Jun";      break;
    		case 7:  monthString = "Jul";      break;
    		case 8:  monthString = "Aug";      break;
    		case 9:  monthString = "Sep";      break;
    		case 10: monthString = "Okt";      break;
    		case 11: monthString = "Nov";      break;
    		case 12: monthString = "Des";      break;
    		default: monthString = "NA"; 	   break;
    	}
    	return monthString;
    }
}
