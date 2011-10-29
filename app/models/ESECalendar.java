package models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.*;
import play.data.validation.Required;
import play.db.jpa.JPABase;
import play.db.jpa.Model;

/**
 * Every  ESECalendar has a name, an owner and a
 * list of{@link ESEEvent}s.
 * This class has functionalities to add/remove events and
 * provides lists of events at certain days, of certain 
 * visibility etc.
 * 
 * @see ESEEvent
 */
@Entity
public class ESECalendar extends Model
{
	/**
	 * Name of this ESECalendar
	 */
	public String calendarName;
	/**
	 * List of ESEEvents in this ESECalendar
	 */
	@OneToMany(mappedBy = "correspondingCalendar", cascade = CascadeType.ALL)
	public List<ESEEvent> eventList;
	/**
	 * Owner ({@link ESEUser}) of this ESECalendar
	 */
	@ManyToOne
	public ESEUser owner;

	public ESECalendar(String calendarName, ESEUser owner)
	{
		this.calendarName = calendarName;
		this.eventList = new ArrayList<ESEEvent>();
		this.owner = owner;
	}

	/**
	 * Adds an {@link ESEEvent} to this ESECalendar
	 * 
	 * @param eventName
	 * @param startDate
	 * @param endDate
	 * @param isPublic
	 */
	public void addEvent(@Required String eventName, @Required String startDate,
						 @Required String endDate, @Required String isPublic)
	{
		ESEEvent newEvent = ESEFactory.createEvent(eventName, startDate, endDate, this, isPublic);
		for (ESEEvent existingEvent : this.eventList)
		{
			if (checkEventOverlaps(existingEvent, newEvent))
			{
				// TODO: Complain as new event overlaps with other event
			}
		}
		this.eventList.add(newEvent);
		this.save();
	}

	private boolean checkEventOverlaps(ESEEvent existingEvent, ESEEvent newEvent)
	{
		boolean startDateOverlaps = startDateLiesInBetweenExistingEvent(existingEvent, newEvent);
		boolean endDateOverlaps = endDateLiesInBetweenExistingEvent(existingEvent, newEvent);
		boolean embracesEvent = eventIsSubsetOfExistingEvent(existingEvent, newEvent);
		boolean isInEvent = eventContainsExistingEvent(existingEvent, newEvent);
		return startDateOverlaps || endDateOverlaps || embracesEvent || isInEvent;
	}

	private boolean startDateLiesInBetweenExistingEvent(ESEEvent existingEvent, ESEEvent newEvent)
	{
		long existingStartTime = existingEvent.getStartDate().getTime();
		long newStartTime = newEvent.getStartDate().getTime();
		long existingEndTime = existingEvent.getEndDate().getTime();
		return existingStartTime <= newStartTime && newStartTime <= existingEndTime;
	}

	private boolean endDateLiesInBetweenExistingEvent(ESEEvent existingEvent, ESEEvent newEvent)
	{
		long existingStartTime = existingEvent.getStartDate().getTime();
		long newEndTime = newEvent.getEndDate().getTime();
		long existingEndTime = existingEvent.getEndDate().getTime();
		return existingStartTime <= newEndTime && newEndTime <= existingEndTime;
	}

	private boolean eventIsSubsetOfExistingEvent(ESEEvent existingEvent, ESEEvent newEvent)
	{
		long existingStartTime = existingEvent.getStartDate().getTime();
		long newStartTime = newEvent.getStartDate().getTime();
		long newEndTime = newEvent.getEndDate().getTime();
		long existingEndTime = existingEvent.getEndDate().getTime();
		return existingStartTime <= newStartTime && newEndTime <= existingEndTime;
	}

	private boolean eventContainsExistingEvent(ESEEvent existingEvent, ESEEvent newEvent)
	{
		long newStartTime = newEvent.getStartDate().getTime();
		long existingStartTime = existingEvent.getStartDate().getTime();
		long existingEndTime = existingEvent.getEndDate().getTime();
		long newEndTime = newEvent.getEndDate().getTime();
		return newStartTime <= existingStartTime && existingEndTime <= newEndTime;
	}

	/**
	 * Returns this ESECalendar
	 * 
	 * @param id
	 * @return this  ESECalendar
	 */
	public static ESECalendar getCalendar(String id) {
		long cid = Long.parseLong(id);
		return findById(cid);
	}

	/**
	 * @deprecated Es soll stattdessen {@link #getCalendarName()} verwendet werden
	 */
	public String getName()
	{
		return this.getCalendarName();
	}

	/**
	 * Returns the name of this  ESECalendar
	 * @return name of ESECalendar
	 */
	public String getCalendarName()
	{
		return this.calendarName;
	}

	 /**
	  * Returns the owner ({@link ESEUser}) of this ESECalendar
	  * @return the owner of this ESECalendar
	  */
	public ESEUser getOwner()
	{
		return this.owner;
	}

	/**
	 * Renames this ESECalendar
	 * @param newName
	 */
	public void renameCalendar(@Required String newName)
	{
		this.calendarName = newName;
		this.save();
	}

	/**
	 * Looks for an {@link ESEEvent} with a certain name and
	 * removes it from this calendar
	 * @param eventName
	 */
	public void removeEvent(@Required String eventName)
	{
		for (ESEEvent e : this.eventList)
		{
			if (e.getEventName().equals(eventName))
			{
				this.eventList.remove(e);
				e.delete(); // DB stuff
				this.save();
				break;
			}
		}
		//TODO: Complain as this event is not in the list
	}

	/**
	 * Removes an {@link ESEEvent} by its unique id
	 * @param eventId
	 */
	public void removeEvent(@Required Long eventId)
	{
		this.removeEvent(((ESEEvent) ESEEvent.findById(eventId)).getEventName());
	}

	/**
	 * Returns a list of all {@link ESEEvent}s at a certain day
	 * which have the visibility "public" (i.e. can be seen by
	 * every {@link ESEUser}, not only by their owners.
	 * 
	 * @param calendarDay
	 * @return list of all public events at a certain day
	 * @see ESEEvent
	 * @see ESEUser
	 */
	public ArrayList<ESEEvent> getListOfPubEventsRunningAtDay(@Required String calendarDay)
	{
		ArrayList<ESEEvent> eventsFormDate = new ArrayList<ESEEvent>();
		// Create pseudo event that
		// starts at "calendarDay" 00:00 and
		// ends at "calendarDay" 23:59
		ESEEvent pseudoEvent = new ESEEvent("CompareHelperEvent",
				calendarDay.substring(0, 10) + " 00:00",
				calendarDay.substring(0, 10) + " 23:59", this, "1");
		for (ESEEvent e : this.eventList)
		{
			if (checkEventOverlaps(e, pseudoEvent))
			{
				if (!e.isPublic()) {
					continue;
				}
				eventsFormDate.add(e);
			}
		}
		return eventsFormDate;
	}

	/**
	 * Returns a list of all {@link ESEEvent}s at a certain day
	 * 
	 * @param calendarDay
	 * @return list of all events at a certain day
	 * @see ESEEvent
	 */
	public ArrayList<ESEEvent> getListOfEventsRunningAtDay(@Required String calendarDay)
	{
		ArrayList<ESEEvent> eventsFormDate = new ArrayList<ESEEvent>();
		// Create pseudo event that
		// starts at "calendarDay" 00:00 and
		// ends at "calendarDay" 23:59
		ESEEvent pseudoEvent = new ESEEvent("CompareHelperEvent",
				calendarDay.substring(0, 10) + " 00:00",
				calendarDay.substring(0, 10) + " 23:59", this, "1");
		for (ESEEvent e : this.eventList)
		{
			if (checkEventOverlaps(e, pseudoEvent))
			{
				eventsFormDate.add(e);
			}
		}
		return eventsFormDate;
	}

	/**
	 * Returns an iterator of all {@link ESEEvent}s at a certain day
	 * 
	 * @param calendarDay
	 * @return list of all events at a certain day
	 * @see ESEEvent
	 */
	public Iterator<ESEEvent> getIteratorOfEventsRunningAtDay(@Required String calendarDay)
	{
		return this.getListOfEventsRunningAtDay(calendarDay).iterator();
	}

	/**
	 * Returns an ArrayList of all {@link ESEEvent}s in this ESECalendar
	 * @return all events in this calendar
	 */
	public ArrayList<ESEEvent> getAllEventsAsList()
	{
		return new ArrayList<ESEEvent>(eventList);
	}

	/**
	 * Returns an ArrayList of all {@link ESEEvent}s in this ESECalendar
	 * which have the visibility "public" (i.e. can be seen by
	 * every {@link ESEUser}, not only by their owners.
	 * 
	 * @return a list of all public events
	 * @see ESEEvent
	 * @see ESEUser
	 */
	public ArrayList<ESEEvent> getPublicEventsAsList()
	{
		ArrayList<ESEEvent> publicEventList = new ArrayList<ESEEvent>();
		for (ESEEvent e : this.eventList)
		{
			if (e.isPublic())
			{
				publicEventList.add(e);
			}
		}

		return publicEventList;
	}
	
	/**
	 * Returns an Iterator of all {@link ESEEvent}s in this ESECalendar
	 * @return all events in this calendar
	 */
	public Iterator<ESEEvent> getAllEventsAsIterator()
	{
		return this.getPublicEventsAsList().iterator();
	}

	/**
	 * Returns an Iterator of all {@link ESEEvent}s in this ESECalendar
	 * which have the visibility "public" (i.e. can be seen by
	 * every {@link ESEUser}, not only by their owners.
	 * 
	 * @return a list of all public events
	 * @see ESEEvent
	 * @see ESEUser
	 */
	public Iterator<ESEEvent> getPublicEventsAsIterator()
	{
		return this.getAllEventsAsList().iterator();
	}
}
