package models;

import java.util.ArrayList;
import java.util.List;

public class Calendar {

	private List<Event> events;
	private String name;

	public Calendar(String name) {
		events = new ArrayList<Event>();
		this.name = name;
	}
	
	
	public List<Event> getEventsOfDay(String date){
		//TODO
		return null;
	}

	public void addEvent(Event e) {
		events.add(e);
	}

	public String getName() {
		return name;
	}

}
