package models;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.*;
import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class ESEEvent extends Model
{

	@ManyToOne
	public ESECalendar correspondingCalendar;
	public String eventName;
	public Date startDate;
	public Date endDate;
	public boolean isPublic;

	public ESEEvent(String eventName,
			String strStart, String strEnd, ESECalendar correspondingCalendar, String strIsPublic)
	{
		this.eventName = eventName;
		this.startDate = ConversionHelper.convertStringToDate(strStart);
		this.endDate = ConversionHelper.convertStringToDate(strEnd);
		this.correspondingCalendar = correspondingCalendar;
		this.isPublic = Boolean.parseBoolean(strIsPublic);
	}

	/**
	 * @deprecated Es soll stattdessen {@link #editEventName(String)} verwendet werden
	 */
	public void renameEvent(@Required String newName)
	{
		editEventName(newName);
	}

	public String getEventName()
	{
		return this.eventName;
	}

	public Date getStartDate()
	{
		return this.startDate;
	}

	public Date getEndDate()
	{
		return this.endDate;
	}

	public boolean isPublic()
	{
		return this.isPublic;
	}

	//TODO: Setters = editEvents? How does this look like?
	public void editEventName(@Required String newName)
	{
		this.eventName = newName;
	}

	public void editStartDate(@Required String newStartDate)
	{
		this.startDate = ConversionHelper.convertStringToDate(newStartDate);
	}

	public void editEndDate(@Required String newEndDate)
	{
		this.endDate = ConversionHelper.convertStringToDate(newEndDate);
	}

	public void editVisibility(@Required String publicViewable)
	{
		this.isPublic = Boolean.parseBoolean(publicViewable);
	}

}
