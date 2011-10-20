package modelTests;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import models.ESECalendar;
import models.ESEEvent;

import org.junit.Before;
import org.junit.Test;

import play.test.UnitTest;


public class ESECalendarTest extends UnitTest{
		
	public ESECalendar testCalendar;

	@Before
	public void setUp() {
		testCalendar = new ESECalendar("TestCalendarName");
	}
	
	@Test
	public void shouldReturnCorrectName(){
		assertEquals(testCalendar.name,"TestCalendarName");
	}
	
	@Test
	public void shouldComplainIfOverlappingEvents(){
		//TODO
	}
	
	@Test
	public void shouldAddAndReturnNewEvent(){
		testCalendar.addEvent("TestEvent", "20.10.2011 20:00", "20.10.2011 20:30", "true");
		assertTrue(testCalendar.eventList.get(0).name.equals("TestEvent"));
		assertTrue(testCalendar.eventList.get(0).isPublic = true);
	}

	@Test
	public void shouldCorrectlyRenameCalendar(){
		testCalendar.renameCalendar("RenamedTestCalendar");
		assertTrue(testCalendar.name.equals("RenamedTestCalendar"));
		assertFalse(testCalendar.name.equals("TestCalendarName"));
	}
	
	@Test
	public void shouldRemoveCorrectEvent(){
		testCalendar.addEvent("TestEvent1", "20.10.2011 20:00", "20.10.2011 20:30", "true");
		testCalendar.addEvent("TestEvent2", "21.10.2011 20:00", "21.10.2011 20:30", "true");
		testCalendar.addEvent("TestEvent3", "22.10.2011 20:00", "22.10.2011 20:30", "true");
		
		assertTrue(testCalendar.eventList.get(1).name.equals("TestEvent2"));
		assertTrue(testCalendar.eventList.get(2).name.equals("TestEvent3"));
		testCalendar.removeEvent("TestEvent2");
		assertFalse(testCalendar.eventList.get(1).name.equals("TestEvent2"));
		assertTrue(testCalendar.eventList.get(1).name.equals("TestEvent3"));
	}
	
	@Test
	public void shouldReturnListOfAllEvents(){
		testCalendar.addEvent("TestEvent1", "20.10.2011 20:00", "20.10.2011 20:30", "true");
		testCalendar.addEvent("TestEvent2", "21.10.2011 20:00", "21.10.2011 20:30", "true");
		testCalendar.addEvent("TestEvent3", "22.10.2011 20:00", "22.10.2011 20:30", "true");
		
		assertTrue(testCalendar.getAllEventsAsList().get(0).name.equals("TestEvent1"));
		assertTrue(testCalendar.getAllEventsAsList().get(1).name.equals("TestEvent2"));
		assertTrue(testCalendar.getAllEventsAsList().get(2).name.equals("TestEvent3"));
	}
	
	@Test
	public void shouldReturnListOfAllPublicEvents(){
		testCalendar.addEvent("TestEvent1", "20.10.2011 20:00", "20.10.2011 20:30", "false");
		testCalendar.addEvent("TestEvent2", "21.10.2011 20:00", "21.10.2011 20:30", "true");
		testCalendar.addEvent("TestEvent3", "22.10.2011 20:00", "22.10.2011 20:30", "false");
		testCalendar.addEvent("TestEvent4", "22.10.2011 20:00", "22.10.2011 20:30", "true");
		
		assertTrue(testCalendar.getPublicEventsAsList().size() == 2);
		
		assertTrue(testCalendar.getPublicEventsAsList().get(0).name.equals("TestEvent2"));
		assertTrue(testCalendar.getPublicEventsAsList().get(1).name.equals("TestEvent4"));
	}
	
	@Test
	public void shouldReturnIteratorOfAllEvents(){
		testCalendar.addEvent("TestEvent1", "20.10.2011 20:00", "20.10.2011 20:30", "true");
		testCalendar.addEvent("TestEvent2", "21.10.2011 20:00", "21.10.2011 20:30", "true");
		testCalendar.addEvent("TestEvent3", "22.10.2011 20:00", "22.10.2011 20:30", "true");
		
		//TODO
	}
	
	@Test
	public void shouldReturnIteratorOfAllPublicEvents(){
		testCalendar.addEvent("TestEvent1", "20.10.2011 20:00", "20.10.2011 20:30", "false");
		testCalendar.addEvent("TestEvent2", "21.10.2011 20:00", "21.10.2011 20:30", "true");
		testCalendar.addEvent("TestEvent3", "22.10.2011 20:00", "22.10.2011 20:30", "false");
		testCalendar.addEvent("TestEvent4", "22.10.2011 20:00", "22.10.2011 20:30", "true");
		
		//TODO
	}
	
	
}
