package modelTests;

import java.util.List;

import models.ESECalendar;
import models.ESEEvent;

import org.junit.Before;
import org.junit.Test;

import play.test.UnitTest;

public class ESECalendarTest extends UnitTest {

	public ESECalendar testCalendar;

	@Before
	public void setUp() {
		testCalendar = new ESECalendar("TestCalendarName", null);

		testCalendar.addEvent("TestEvent1", "20.10.2011 20:00",
				"20.10.2011 20:30", "true");
		testCalendar.addEvent("TestEvent2", "21.10.2011 20:00",
				"21.10.2011 20:30", "false");
		testCalendar.addEvent("TestEvent3", "22.10.2011 20:00",
				"22.10.2011 20:30", "true");
	}

	@Test
	public void shouldReturnCorrectName() {
		assertEquals(testCalendar.getName(), "TestCalendarName");
	}

	@Test
	public void shouldComplainIfOverlappingEvents() {
		// TODO
	}

	@Test
	public void shouldAddAndReturnNewEvent() {
		testCalendar.addEvent("TestEvent4", "25.10.2011 20:00",
				"25.10.2011 20:30", "true");
		assertTrue(testCalendar.eventList.get(3).getName().equals("TestEvent4"));
		assertTrue(testCalendar.eventList.get(3).isPublic = true);
	}

	@Test
	public void shouldCorrectlyRenameCalendar() {
		testCalendar.renameCalendar("RenamedTestCalendar");
		assertTrue(testCalendar.getName().equals("RenamedTestCalendar"));
		assertFalse(testCalendar.getName().equals("TestCalendarName"));
	}

	@Test
	public void shouldRemoveCorrectEvent() {
		assertTrue(testCalendar.eventList.get(1).getName().equals("TestEvent2"));
		assertTrue(testCalendar.eventList.get(2).getName().equals("TestEvent3"));
		testCalendar.removeEvent("TestEvent2");
		assertFalse(testCalendar.eventList.get(1).getName()
				.equals("TestEvent2"));
		assertTrue(testCalendar.eventList.get(1).getName().equals("TestEvent3"));
	}

	@Test
	public void shouldReturnEventsOfCertainDay() {
		List<ESEEvent> testList = testCalendar
				.getListOfEventsRunningAtDay("20.10.2011 13:00");
		assertTrue(testList.size() == 1);

		testCalendar.addEvent("TestEvent5", "20.10.2011 14:00",
				"20.10.2011 15:00", "true");
		testList = testCalendar.getListOfEventsRunningAtDay("20.10.2011 13:00");
		assertTrue(testList.size() == 2);
		assertEquals(testList.get(0).getName(), "TestEvent1");
		assertEquals(testList.get(1).getName(), "TestEvent5");
	}

	@Test
	public void shouldReturnListOfAllEvents() {
		assertTrue(testCalendar.getAllEventsAsList().get(0).getName()
				.equals("TestEvent1"));
		assertTrue(testCalendar.getAllEventsAsList().get(1).getName()
				.equals("TestEvent2"));
		assertTrue(testCalendar.getAllEventsAsList().get(2).getName()
				.equals("TestEvent3"));
	}

	@Test
	public void shouldReturnListOfAllPublicEvents() {

		assertTrue(testCalendar.getPublicEventsAsList().size() == 2);

		assertTrue(testCalendar.getPublicEventsAsList().get(0).getName()
				.equals("TestEvent1"));
		assertTrue(testCalendar.getPublicEventsAsList().get(1).getName()
				.equals("TestEvent3"));
	}

	@Test
	public void shouldReturnIteratorOfAllEvents() {
		// TODO
	}

	@Test
	public void shouldReturnIteratorOfAllPublicEvents() {
		// TODO
	}

}
