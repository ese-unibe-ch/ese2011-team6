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
		this.startDate = ConversionHelper.convertStringToDate(strStart);
		this.endDate = ConversionHelper.convertStringToDate(strEnd);
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
	 * @deprecated Use static ConversionHelper.convertStringToDate(String) instead
	 */
	@Deprecated
	public Date testStringToDateConverting(String stringDate){
		return ConversionHelper.convertStringToDate(stringDate);
	}
}
