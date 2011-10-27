package modelTests;

import java.util.List;

import models.ESECalendar;
import models.ESEEvent;
import models.ESEFactory;
import models.ESEUser;

import org.junit.Before;
import org.junit.Test;

import play.test.UnitTest;

public class ESECalendarTest extends UnitTest {

	public ESECalendar testCalendar;

	@Before
	public void setUp() {
		testCalendar = ESEFactory.createCalendar("TestCalendarName", null);

		testCalendar.addEvent("TestEvent1", "20.10.2011 20:00",
				"20.10.2011 20:30", "true");
		testCalendar.addEvent("TestEvent2", "21.10.2011 20:00",
				"21.10.2011 20:30", "false");
		testCalendar.addEvent("TestEvent3", "22.10.2011 20:00",
				"22.10.2011 20:30", "true");
		
		assertEquals(3, testCalendar.getAllEventsAsList().size());
	}

	@Test
	public void shouldReturnCorrectName() {
		assertEquals(testCalendar.getName(), "TestCalendarName");
	}
	
	@Test
	public void shouldReturnCorrectOwner() {
		ESEUser testUser = ESEFactory.createUser("testUser", "pw");
		ESECalendar testCalendar2 = ESEFactory.createCalendar("TestCalendarName", testUser);
		assertEquals(testCalendar2.owner,testUser);
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
		ESEEvent e2 = ESEEvent.find("byEventName", "TestEvent2").first();
		ESEEvent e2b = ESEEvent.find("byEventName", "TestEvent2").first();
		ESEEvent e3 = ESEEvent.find("byEventName", "TestEvent3").first();
		
		/*
		TODO: Team Model: Problems with database or wrong specified test?
		System.out.println("e2: " + e2.id);
		System.out.println("e2b: " + e2b.id);
		System.out.println("e3: " + e3.id);
		System.out.println("Place 0 in List: " + testCalendar.eventList.get(0).id);
		System.out.println("Place 1 in List: " + testCalendar.eventList.get(1).id);
		System.out.println("Place 2 in List: " + testCalendar.eventList.get(2).id);
		*/
		
		testCalendar.removeEvent(e2.id);
		
		assertFalse(testCalendar.eventList.get(1).getName().equals("TestEvent2"));
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
