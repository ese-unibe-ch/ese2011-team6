package models;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.Entity;
import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class ESEEvent extends Model
{

	public String name;
	public Date startDate;
	public Date endDate;
	public boolean isPublic;

	public ESEEvent(String name, String strStart, String strEnd, String strIsPublic)
	{
		this.name = name;
		this.startDate = convertStringToDate(strStart);
		this.endDate = convertStringToDate(strEnd);
		this.isPublic = Boolean.parseBoolean(strIsPublic);
	}

	public void renameEventName(@Required String newName)
	{
		this.name = newName;
	}

	public String getEventName()
	{
		return name;
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
	/**
	 * Expected format: "dd-MM-YYYY hh:mm"
	 * 
	 * @param stringDate
	 * @return
	 */
	// TODO does not handle any bad input!
	private Date convertStringToDate(String stringDate)
	{

		Calendar cal = Calendar.getInstance();

		int day = Integer.parseInt(stringDate.substring(0, 2));
		int month = Integer.parseInt(stringDate.substring(3, 5)) - 1; // java.Calendar ranges from 0 to 11
		int year = Integer.parseInt(stringDate.substring(6, 10));
		int hourOfDay = Integer.parseInt(stringDate.substring(11, 13));
		int minute = Integer.parseInt(stringDate.substring(14, 16));

		cal.set(year, month, day, hourOfDay, minute, 0);
		Date date = cal.getTime();

		return date;
	}

	private String convertDateToString(Date date)
	{
		SimpleDateFormat sdfToString = new SimpleDateFormat("dd.MM.yyyy HH:mm");
		return sdfToString.format(date);
	}
	
	/* Only for testing purposes */
	public Date testStringToDateConverting(String stringDate){
		return this.convertStringToDate(stringDate);
	}
	

}
