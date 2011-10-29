package models;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Helper class for conversions between String and Date
 */
public class ESEConversionHelper
{
	private static final String inputFormat = "dd.MM.yyyy HH:mm";

	/**
	 * Converts Strings of the form "dd.MM.yyy HH:mm" to {@link Date} objects
	 * @param userDateString
	 * @see Date
	 */
	public static Date convertStringToDate(String userDateString)
	{
		DateTimeFormatter dateTimeConvert = DateTimeFormat.forPattern(inputFormat);
		DateTime dateTimeParser = dateTimeConvert.parseDateTime(userDateString);
		return dateTimeParser.toDate();
	}

	/**
	 * Converts {@link Date} objects to Strings of the form "dd.MM.yyy HH:mm"
	 * @param date
	 * @see Date
	 */
	public static String convertDateToString(Date date)
	{
		SimpleDateFormat simpleDateConverter = new SimpleDateFormat(inputFormat);
		return simpleDateConverter.format(date);
	}
}
