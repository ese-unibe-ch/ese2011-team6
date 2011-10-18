package models;

import java.util.ArrayList;
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
		ESEEvent newEvent = new ESEEvent(eventName, startDate, endDate,
				isPublic);
		eventList.add(newEvent);
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
}
