package models;

import java.util.ArrayList;
import java.util.Iterator;
import javax.persistence.Entity;
import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class ESECalendar extends Model
{

	public String name;
	public ArrayList<ESEEvent> eventList;

	public ESECalendar(String name)
	{
		this.name = name;
		eventList = new ArrayList<ESEEvent>();
		this.save();
	}

	public void addEvent(@Required String eventName,
			@Required String startDate, @Required String endDate,
			@Required String isPublic)
	{
		ESEEvent newEvent = new ESEEvent(eventName, startDate, endDate,	isPublic);
		for (ESEEvent existingEvent : eventList)
		{
			if (checkEventOverlaps(existingEvent, newEvent))
			{
				// TODO: Complain as new event overlaps with other event
			}
		}
		eventList.add(newEvent);
	}

	private boolean checkEventOverlaps(ESEEvent existingEvent, ESEEvent newEvent)
	{
		return (startDateLiesInBetweenExistingEvent(existingEvent, newEvent)
				|| endDateLiesInBetweenExistingEvent(existingEvent, newEvent)
				|| eventIsSubsetOfExistingEvent(existingEvent, newEvent)
				|| eventContainsExistingEvent(existingEvent, newEvent));
	}

	private boolean startDateLiesInBetweenExistingEvent(ESEEvent existingEvent, ESEEvent newEvent)
	{
		return existingEvent.getStartDate().getTime() <= newEvent.getStartDate().getTime()
												&& newEvent.getStartDate().getTime() <= existingEvent.getEndDate().getTime();
	}

	private boolean endDateLiesInBetweenExistingEvent(ESEEvent existingEvent, ESEEvent newEvent)
	{
		return existingEvent.getStartDate().getTime() <= newEvent.getEndDate().getTime()
												&& newEvent.getEndDate().getTime() <= existingEvent.getEndDate().getTime();
	}

	private boolean eventIsSubsetOfExistingEvent(ESEEvent existingEvent, ESEEvent newEvent)
	{
		return existingEvent.getStartDate().getTime() <= newEvent.getStartDate().getTime()
												&& newEvent.getEndDate().getTime() <= existingEvent.getEndDate().getTime();
	}

	private boolean eventContainsExistingEvent(ESEEvent existingEvent, ESEEvent newEvent)
	{
		return newEvent.getStartDate().getTime() <= existingEvent.getStartDate().getTime()
										   && existingEvent.getEndDate().getTime() <= newEvent.getEndDate().getTime();
	}

	public String getName()
	{
		return name;
	}

	public void renameCalendar(@Required String newName)
	{
		this.name = newName;
	}

	public void removeEvent(@Required String eventName)
	{
		for (ESEEvent e : eventList)
		{
			if (e.getEventName().equals(eventName))
			{
				eventList.remove(e);
				break;
			}
		}
	}

	public ArrayList<ESEEvent> getAllEventsAsList()
	{
		return eventList;
	}

	public ArrayList<ESEEvent> getPublicEventsAsList()
	{
		ArrayList<ESEEvent> publicEventList = new ArrayList<ESEEvent>();
		for (ESEEvent e : eventList)
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
		ArrayList<ESEEvent> publicEventIterator = new ArrayList<ESEEvent>();
		for (ESEEvent e : eventList)
		{
			if (e.isPublic())
			{
				publicEventIterator.add(e);
			}
		}
		return publicEventIterator.iterator();
	}

	public Iterator<ESEEvent> getPublicEventsAsIterator()
	{
		return eventList.iterator();
	}
}
