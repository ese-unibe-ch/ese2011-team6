package modelTests;

import java.util.Calendar;
import java.util.Date;

import models.ESEEvent;

import org.junit.Test;

import play.test.UnitTest;

/**
 * @author judith
 * 
 */
public class ESEEventTests extends UnitTest{
	
	Calendar cal = Calendar.getInstance();
	
	@Test
	public void constructorTest(){
		String start = "25:09:2011 08:15";
		String end = "25:09:2011 10:00";
		String isPublic ="false";
		ESEEvent event = new ESEEvent("Sitzung", start, end, isPublic);
		
		assertTrue(event.name.equals("Sitzung"));
		//TODO start and end date
	}
	
	@Test
	public void shouldDO(){
		//copy-past dummy
	}
	

}
