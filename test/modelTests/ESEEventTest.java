package modelTests;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import models.ESEEvent;

import org.junit.Before;
import org.junit.Test;

import play.test.UnitTest;


public class ESEEventTest extends UnitTest{
		
	private ESEEvent testEvent1;
	private ESEEvent testEvent2;

	@Before
	public void setUp() {
		
		this.testEvent1 = new ESEEvent("testEvent1","25.10.2011 08:15","25.10.2011 10:00","false");
		this.testEvent2 = new ESEEvent("testEvent2","26.10.2011 08:15","26.10.2011 10:00","true");
	}
	
	@Test
	public void shouldReturnCorrectName(){
		assertEquals(testEvent1.name,"testEvent1");
	}

	@Test
	public void shouldReturnCorrectDates(){
		Calendar startDateToTest = new GregorianCalendar(2011, Calendar.OCTOBER, 25, 8, 15);
		Calendar endDateToTest = new GregorianCalendar(2011, Calendar.OCTOBER, 25, 10, 0);
		
		assertEquals(testEvent1.getStartDate().getTime(),startDateToTest.getTimeInMillis());
		assertEquals(testEvent1.getEndDate().getTime(),endDateToTest.getTimeInMillis());
	}
	
	@Test
	public void shouldReturnCorrectPrivacyStatus(){
		assertTrue(testEvent1.isPublic() == false);
		assertFalse(testEvent1.isPublic() == true);
		
		assertEquals(testEvent2.isPublic(),true);
	}
}
