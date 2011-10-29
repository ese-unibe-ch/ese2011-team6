package models;

import java.util.Date;
import javax.persistence.*;
import play.data.validation.Required;
import play.db.jpa.Model;

/**
 * Holds information about a certain ESEEvent: its name, start date, end date and
 * whether this event is public (i.e. everybody can see this ESEEvent) or private
 * (i.e. only the owner of the corresponding calendar can see this ESEEvent).
 * 
 * Provides methods to change all these attributes, i.e. name
 * start date, end date and visibility.
 */
@Entity
public class ESEEvent extends Model
{

	/**
	 * The {@link ESECalendar} which contains this ESEEvent
	 */
	@ManyToOne
	public ESECalendar correspondingCalendar;
	/**
	 * The name of this ESEEvent
	 */
	public String eventName;
	/**
	 * The start date of this ESEEvent
	 */
	public Date startDate;
	/**
	 * The end date of this ESEEvent
	 */
	public Date endDate;
	/**
	 * Whether this event is public (i.e. every {@link ESEUser} can see
	 * this event) of private (i.e. only the owner of the corresponding
	 * calendar can see this ESEEvent).
	 */
	public boolean isPublic;

	/**
	 * @deprecated Es soll stattdessen {@link #ESEEvent(String, String, String, ESECalendar, String)} verwendet werden
	 */
	public ESEEvent(String eventName, String strStart, String strEnd, String strIsPublic, ESECalendar correspondingCalendar)
	{
		new ESEEvent(eventName, strStart, strEnd, correspondingCalendar, strIsPublic);
	}

	public ESEEvent(@Required String eventName,
			@Required String strStart, @Required String strEnd,
			@Required ESECalendar correspondingCalendar, @Required String strIsPublic)
	{
		this.eventName = eventName;
		this.startDate = ESEConversionHelper.convertStringToDate(strStart);
		this.endDate = ESEConversionHelper.convertStringToDate(strEnd);
		// TODO: Is strStart <= strEnd?
		if (startDate.getTime() > endDate.getTime())
		{
			// TODO: Complain as these dates are !possible
		}
		this.correspondingCalendar = correspondingCalendar;
		this.correspondingCalendar.save();
		this.isPublic = Boolean.parseBoolean(strIsPublic);
	}

	/**
	 * @deprecated Es soll stattdessen {@link #editEventName(String)} verwendet werden
	 */
	public void renameEvent(@Required String newName)
	{
		editEventName(newName);
	}

	/**
	 * @deprecated Es soll stattdessen {@link #getEventName()} verwendet werden
	 */
	public String getName()
	{
		return this.getEventName();
	}
	/**
	 * Returns the name of this ESEEvent
	 * @return name of this ESEEvent
	 */
	public String getEventName()
	{
		return this.eventName;
	}
	
	/**
	 * Returns the start date of this ESEEvent
	 * @return start date of this ESEEvent
	 */
	public Date getStartDate()
	{
		return this.startDate;
	}

	/**
	 * Returns the end date of this ESEEvent
	 * @return end date of this ESEEvent
	 */
	public Date getEndDate()
	{
		return this.endDate;
	}

	/**
	 * Returns the visibility of this ESEEvent. "True" means
	 * that this event is public (i.e. every {@link ESEUser}
	 * can see this event). "False" means that this ESEEvent is
	 * private (i.e. only the owner of the corresponding
	 * calendar can see this ESEEvent).
	 * @return visibility of this ESEEvent
	 */
	public boolean isPublic()
	{
		return this.isPublic;
	}

	/**
	 * Changes the name of this ESEEvent
	 * @param newName
	 */
	// TODO: Setters = editEvents? How does this look like?
	public void editEventName(@Required String newName)
	{
		this.eventName = newName;
		this.save();
	}

	/**
	 * Changes the start date of this ESEEvent
	 * @param newStartDate
	 */
	public void editStartDate(@Required String newStartDate)
	{
		this.startDate = ESEConversionHelper.convertStringToDate(newStartDate);
		this.save();
	}

	/**
	 * Changes the end date of this ESEEvent
	 * @param newEndDate
	 */
	public void editEndDate(@Required String newEndDate)
	{
		this.endDate = ESEConversionHelper.convertStringToDate(newEndDate);
		this.save();
	}

	/**
	 * Changes the visibility of this ESEEvent
	 * @param publiclyViewable
	 */
	public void editVisibility(@Required String publiclyViewable)
	{
		this.isPublic = Boolean.parseBoolean(publiclyViewable);
		this.save();
	}
}
