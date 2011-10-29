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
	
	private static java.util.Calendar cal;
	
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
		cal = java.util.Calendar.getInstance();
		return new ESECalendarUtil();	
	}
	
	/**
	 * Sets the calendar at this
	 * specific date.
	 * 
	 * @param day
	 */
	public void setAtDate(Date date){
		cal.setTime(date);
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
		assert(month >0);
		assert(month <=12);
		cal.set(cal.MONTH, month-1);
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
	 * Returns the weekday which this month starts with. <br>
	 * Weekdays are numbered from 1(monday) to 7(sunday).
	 * 
	 * @return int weekday
	 */
	public int getFirstMondayOfCurrentMonth(){

		Date temp = cal.getTime(); //save original date		
		cal.set(cal.DATE, 1);
		int weekday = cal.get(cal.DAY_OF_WEEK);
		int monday = ((8-weekday)%7)+2;//calculate date from weekday		
		cal.setTime(temp); //setting pointer back
		
		return monday;
	}
	
	/**
	 * Returns the first weekday of the current month. <br>
	 * Weekdays are numbered from 1(monday) to 7(sunday).
	 * 
	 * @return int weekday
	 */
	public int getFirstWeekdayOfCurrentMonth(){
		
		Date temp = cal.getTime(); //save original date
		cal.set(cal.DATE, 1);
		int weekday = cal.get(cal.DAY_OF_WEEK);	
		System.out.println("weekday: "+weekday);
		cal.setTime(temp); //setting pointer back
		weekday = ((weekday + 5)%7)+1;
		return weekday;
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
