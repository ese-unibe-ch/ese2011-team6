package models;

import java.text.ParseException;
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
	 * @throws ParseException 
	 */
	private Date convertStringToDate(String stringDate)
	{
		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm");
		Date dateToReturn = null;
		try {
			dateToReturn =  df.parse(stringDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateToReturn;
	}

	private String convertDateToString(Date date)
	{
		SimpleDateFormat sdfToString = new SimpleDateFormat("dd.MM.yyyy HH:mm");
		return sdfToString.format(date);
	}
	
	/* Only for testing purposes */
	public Date testStringToDateConverting(String stringDate) throws ParseException{
		return this.convertStringToDate(stringDate);
	}
	

}
