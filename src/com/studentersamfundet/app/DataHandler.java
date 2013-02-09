package com.studentersamfundet.app;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;

import android.util.Log;

public class DataHandler implements Serializable {
	private static final long serialVersionUID = 6787541405689146002L;
	private static final String TAG = DataHandler.class.getName();
	
	private Collection<Event> events = new LinkedHashSet<Event>();
	private Collection<News> news = new LinkedList<News>();
	private Set<String> eventCategories = new TreeSet<String>();
	
	public void insertEvent(String id, 
			String title, 
			String description, 
			String date, 
			String location, 
			String text, 
			String category,
			String imageUri,
			String thumbUri,
			String priceReg,
			String priceMem,
			String ticketUriStr,
			String fbUriStr) {
		int intId = Integer.parseInt(id);
		
		Event event = new Event(intId, title, description, date, location, text, category);
		
		int regularPrice = 0;
		int memberPrice = 0;
		try {
			if (false == "".equals(regularPrice)) {
				regularPrice = Integer.parseInt(priceReg);
			}
			
			if (false == "".equals(memberPrice)) {
				memberPrice = Integer.parseInt(priceMem);
			}
		} catch (NumberFormatException e) { 
			/* Do nothing. */
			Log.w(TAG, "Couldn't parse price: " +e.toString());
		} 
		event.setFbUriStr(fbUriStr);
		event.setTicketsInfo(regularPrice, memberPrice, ticketUriStr);
		event.setImage(imageUri, thumbUri);
		
		events.add(event);
		eventCategories.add(category);
	}
	
	public void insertNews(String title, String description, String content, String link, String pubDate) {
		News n = new News(title, description, content, link, pubDate);
		this.news.add(n);
	}
	
	public Event getEvent(int id) {
		/* TODO: This has linear time complexity. Fix needed? */
		for (Event e : events) {
			if (e.id == id)
				return e;
		}
		return null;
	}
	
	public Event[] getEvents(String category) {
		if (category.equals(Event.ALL)) {
			return events.toArray(new Event[events.size()]);
		}
		
		LinkedList<Event> sorted = new LinkedList<Event>();

		for (Event event : events) {
			if (category.equals(event.getCategory()))
				sorted.add(event);
		}
		return sorted.toArray(new Event[sorted.size()]);
    }
	
	public Event[] getEventsWithTickets() {
		LinkedList<Event> list = new LinkedList<Event>();
		
		for (Event event : events) {
			if(event.getTicketUri() != null) {
				list.add(event);
			}
		}
		
		return list.toArray(new Event[list.size()]);
	}
	
	public News[] getNews() {
		return this.news.toArray(new News[this.news.size()]);
	}
	
	public String[] getEventCategories() {
		String[] result = new String[eventCategories.size() + 1];
		result[0] = Event.ALL;
		
		int counter = 1;
		for (String c : eventCategories) {
			result[counter++] = c;
		}
		
		return result;
	}
}
