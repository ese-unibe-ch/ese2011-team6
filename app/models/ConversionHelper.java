package models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Helper class for conversions between String and Dates
 */
public class ConversionHelper
{
	/**
	 * Expected format: "dd-MM-YYYY hh:mm"
	 * 
	 * @param stringDate
	 * @return
	 */
	// TODO does not handle any bad input!
	public static Date convertStringToDate(String stringDate)
	{

		SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy hh:mm");
		Date dateToReturn = null;
		try {
		dateToReturn =  df.parse(stringDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateToReturn;
	}

	public static String convertDateToString(Date date)
	{
		SimpleDateFormat sdfToString = new SimpleDateFormat("dd.MM.yyyy HH:mm");
		return sdfToString.format(date);
	}
}
