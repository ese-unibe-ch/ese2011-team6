package models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.*;
import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class ESECalendar extends Model
{
	public String calendarName;
	@OneToMany(mappedBy = "correspondingCalendar", cascade = CascadeType.ALL)
	public List<ESEEvent> eventList;
	@ManyToOne
	public ESEUser owner;

	public ESECalendar(String calendarName, ESEUser owner)
	{
		this.calendarName = calendarName;
		this.eventList = new ArrayList<ESEEvent>();
		this.owner = owner;
	}

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
	 * @deprecated Es soll stattdessen {@link #getCalendarName()} verwendet werden
	 */
	public String getName()
	{
		return this.getCalendarName();
	}

	public String getCalendarName()
	{
		return this.calendarName;
	}

	public ESEUser getOwner()
	{
		return this.owner;
	}

	public void renameCalendar(@Required String newName)
	{
		this.calendarName = newName;
		this.save();
	}

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

	public void removeEvent(@Required Long eventId)
	{
		for (ESEEvent e : this.eventList)
		{
			if (e.getId().equals(eventId))
			{
				this.eventList.remove(e);
				e.delete(); // DB stuff
				this.save();
				break;
			}
		}
		//TODO: Complain as this event is not in the list
	}

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

	public Iterator<ESEEvent> getIteratorOfEventsRunningAtDay(@Required String calendarDay)
	{
		return this.getListOfEventsRunningAtDay(calendarDay).iterator();
	}

	public ArrayList<ESEEvent> getAllEventsAsList()
	{
		return new ArrayList<ESEEvent>(eventList);
	}

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

	public Iterator<ESEEvent> getAllEventsAsIterator()
	{
		return this.getPublicEventsAsList().iterator();
	}

	public Iterator<ESEEvent> getPublicEventsAsIterator()
	{
		return this.getAllEventsAsList().iterator();
	}
}
