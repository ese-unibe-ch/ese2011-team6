package models;

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

	public static String convertDateToString(Date date)
	{
		SimpleDateFormat sdfToString = new SimpleDateFormat("dd.MM.yyyy HH:mm");
		return sdfToString.format(date);
	}
}
