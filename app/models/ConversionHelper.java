package models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Helper class for conversions between String and Date
 */
public class ConversionHelper
{
	private static final String inputFormat = "dd.MM.yyyy HH:mm";

	public static Date convertStringToDate(String userDateString)
	{
		DateTimeFormatter dateTimeConvert = DateTimeFormat.forPattern(inputFormat);
		DateTime dateTimeParser = dateTimeConvert.parseDateTime(userDateString);
		return dateTimeParser.toDate();
	}

	public static String convertDateToString(Date date)
	{
		SimpleDateFormat simpleDateConverter = new SimpleDateFormat(inputFormat);
		return simpleDateConverter.format(date);
	}
}
