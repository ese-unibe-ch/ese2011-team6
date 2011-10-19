package modelTests;

import play.mvc.Before;
import play.test.Fixtures;
import play.test.UnitTest;

import java.util.Calendar;
import java.util.Date;

import models.ESEUser;

import org.junit.*;
import play.test.*;

/**
 * @author judith
 * 
 */
public class ESEUserTest extends UnitTest {

	@Before
	public void setUp() {
	    Fixtures.deleteDatabase();
	    Fixtures.loadModels("data.yml");
	}
	
	@Test
	public void tryAnything(){
		assertEquals(1,ESEUser.count()); //database doesn't seam to work to way I understood it
	}
	
	
	@Test
	public void constructorWith2ParasTest(){
//		ESEUser user = new ESEUser("nick", "sehrgeheim");
		//TODO
	}
	
	@Test
	public void constructorWith3ParasTest(){
//		ESEUser user = new ESEUser("nick", "sehrgeheim", "Hans", "Müller");
//		assertTrue(user.username.equals("nick"));
//		assertTrue(user.familyName.equals("Müller"));
//		assertTrue(user.firstName.equals("Hans"));
//		assertTrue(user.password.equals("sehrgeheim"));
	}
	
	@Test
	public void shouldInitializeTest(){
		//TODO
	}
	
	@Test
	public void shouldCreatCalendar(){
		
	}
	
	@Test
	public void shouldRemoveCalendar(){
		//TODO
	}
	
	@Test
	public void shouldCreateGroup(){
		//TODO
	}
	
	@Test
	public void shouldRemoveGroupe(){
		//TODO
	}
	
	@Test
	public void shouldTestEdits(){
		//TODO
	}

}
