package models;

import java.util.Date;

/**
 * ESECalendarUtil builds a wrapper around the <code> java.calendar</code>, so 
 * that the features provided by <code>java.calendar</code> may be used most 
 * efficiently.
 * 
 * @author lyriael
 *
 */
public class ESECalendarUtil {
	
	private java.util.Calendar cal = java.util.Calendar.getInstance();
//	private static ESECalendarUtil util;
	
	/**
	 * Private constructor. Use {@link #getInstanceToday()} to receive a valid
	 * instance.
	 */
	private ESECalendarUtil(){
	}
	
	/**
	 * Get an instance of <code>ESECalendarUtil</code>. By default
	 * it points at today.<p>
	 * {@link #getInstanceOfDate(Date date)} to set it with a <code>
	 * Date</code> object.<br>
	 * {@link #getInstanceOfStringDate(String date)} to set it with
	 * a <code>String</code> that is then converted to an <code>Date</code>
	 * object.
	 * 
	 * @return ESECalendarUtil
	 */
	public static ESECalendarUtil getInstanceToday(){
		ESECalendarUtil util = new ESECalendarUtil();
		return util;	
	}
	
	public static ESECalendarUtil getInstanceOfDate(Date date){
		//TODO
		return null;
	}
	
	public static ESECalendarUtil getInstanceOfStringDate(String date){
		//TODO
		return null;
	}
	
	/**
	 * Returns amount of days in the current month.
	 * 
	 * @see setAtDate(Date day)
	 * @see setMonth(int month)
	 * 
	 * @return int days
	 */
	public int getNumberOfDaysOfCurrentMonth(){
		return cal.getActualMaximum(cal.DATE);
	}
	
	/**
	 * Sets the calendar at this
	 * specific date.
	 * 
	 * @param day
	 */
	public void setAtDate(Date day){
		//TODO
	}
	/**
	 * Sets the calendar at this month within the
	 * same year. <br>
	 * Months are numbered starting from 1 (jan) to
	 * 12 (dec). <p>
	 * 
	 * Wrong input is not handled.
	 * 
	 * @param int month
	 */
	public void setMonth(int month){
		//TODO
	}
	
	/**
	 * Returns the weekday which this month starts with. <br>
	 * Weekdays are numbered from 1(monday) to 7(sunday).
	 * 
	 * @return int weekday
	 */
	public int getFirstMondayOfCurrentMonth(){
		//TODO
		return 0;
	}
	
	/**
	 * Returns the first weekday of the current month. <br>
	 * Weekdays are numbered from 1(monday) to 7(sunday).
	 * 
	 * @return int weekday
	 */
	public int getFirstWeekdayOfCurrentMonth(){
		//TODO
		return 0;
	}
	
	/**
	 * Sets the whole calendar to the next month.<br>
	 * All methods relating to "current" one month forth.
	 */
	public void setToNextMonth(){
		//TODO
	}
	/**
	 * Sets the whole calendar to the previous month.<br>
	 * All methods relating to "current" one month back.
	 */	
	public void setToPreviousMonth(){
		//TODO
	}
	/**
	 * Returns the day of month of the last Monday of the previous
	 * Month. <br>
	 * If the first weekday of the current month is a Monday, then <code>0</code>
	 * is returned. <br>
	 * 
	 * @return int day of month
	 */
	public int getLastMondayOfPreviousMonth(){
		//TODO
		return 0;
	}
	/**
	 * Returns the day of month of the first Sunday of the next
	 * Month. <br>
	 * If the last weekday of the current month is a Sunday, then <code>0</code>
	 * is returned. <br>
	 * 
	 * @return int day of month
	 */
	public int getFirstSundayOfNextMonth(){
		//TODO
		return 0;
	}
}
