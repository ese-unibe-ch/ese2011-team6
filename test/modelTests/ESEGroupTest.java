package modelTests;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import models.ESEEvent;
import models.ESEGroup;

import org.junit.Before;
import org.junit.Test;

import play.test.UnitTest;

/**
 * @author judith
 * 
 */
public class ESEGroupTest extends UnitTest{
	
	private ESEGroup testGroup;

	@Before
	public void setUp() {
		this.testGroup = new ESEGroup("TestGroupName1");
	}
	
	@Test
	public void shouldReturnCorrectGroupName(){
		assertEquals(testGroup.groupName,"TestGroupName1");
	}
	
	// More tests: TODO

}
