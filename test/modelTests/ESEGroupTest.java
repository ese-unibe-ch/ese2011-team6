package modelTests;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import models.ESEEvent;
import models.ESEGroup;
import models.ESEUser;

import org.junit.Before;
import org.junit.Test;

import ch.unibe.jexample.Given;

import play.data.validation.Required;
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
	
	@Test
	public void shouldEditGroupNameCorrectly(){
		testGroup.editGroupName("TestGroupName1NewName");
		assertEquals(testGroup.groupName,"TestGroupName1NewName");
		
		//TODO: What happens if empty string? Will this be handled in the model or in the view?
	}
	
	@Test
	public void shouldAddUsers(){
		//TODO
	}
	
	@Given("shouldAddUsers")
	public void shouldReturnListOfAllUsers(){
		
		//TODO
	}
	
}
