package modelTests;

import java.util.Calendar;
import java.util.GregorianCalendar;

import models.ESECalendar;
import models.ESEConversionHelper;
import models.ESEEvent;
import models.ESEFactory;
import models.ESEUser;

import org.junit.Before;
import org.junit.Test;

import play.test.UnitTest;

public class ESEEventTest extends UnitTest {

	private ESEEvent testEvent1;
	private ESEEvent testEvent2;

	@Before
	public void setUp() {
		this.testEvent1 = ESEFactory.createEvent("testEvent1", "25.10.2011 08:15",
				"25.10.2011 10:00", "false", null);
		this.testEvent2 = ESEFactory.createEvent("testEvent2", "26.10.2011 08:15",
				"26.10.2011 10:00", "true", null);
	}

	@Test
	public void shouldReturnCorrectName() {
		assertEquals(testEvent1.getName(), "testEvent1");
	}

	@Test
	public void shouldReturnCorrectDates() {
		Calendar startDateToTest = new GregorianCalendar(2011,
				Calendar.OCTOBER, 25, 8, 15);
		Calendar endDateToTest = new GregorianCalendar(2011, Calendar.OCTOBER,
				25, 10, 0);

		assertEquals(testEvent1.getStartDate().getTime(),
				startDateToTest.getTimeInMillis());
		assertEquals(testEvent1.getEndDate().getTime(),
				endDateToTest.getTimeInMillis());
	}

	@Test
	public void shouldReturnCorrectPrivacyStatus() {
		assertTrue(testEvent1.isPublic() == false);
		assertFalse(testEvent1.isPublic() == true);

		assertEquals(testEvent2.isPublic(), true);
	}
	
	@Test
	public void shouldModifyEvent()
	{
		ESEFactory.createUser("User", "Pass");
		ESEUser testUser = ESEUser.find("byUsername", "User").first();
		
		ESECalendar testCal = ESEFactory.createCalendar("testCal", testUser);
		
		ESEEvent eventToModify = ESEFactory.createEvent("unmodified", 
		"25.10.2011 00:00", "26.10.2011 00:00", "1", testCal);
		
		assertEquals("unmodified", eventToModify.getEventName());
		assertEquals("25.10.2011 00:00", 
		ESEConversionHelper.convertDateToString(eventToModify.getStartDate()));
		assertEquals("26.10.2011 00:00", 
		ESEConversionHelper.convertDateToString(eventToModify.getEndDate()));
		assertTrue(eventToModify.isPublic());
		
		eventToModify.editEventName("modified event");
		eventToModify.editStartDate("25.10.2011 12:00");
		eventToModify.editEndDate("26.10.2011 12:00");
		eventToModify.editVisibility("0");
	
		assertEquals("modified event", eventToModify.getEventName());
		assertEquals("25.10.2011 12:00", 
		ESEConversionHelper.convertDateToString(eventToModify.getStartDate()));
		assertEquals("26.10.2011 12:00", 
		ESEConversionHelper.convertDateToString(eventToModify.getEndDate()));
		assertFalse(eventToModify.isPublic());
	}
}
