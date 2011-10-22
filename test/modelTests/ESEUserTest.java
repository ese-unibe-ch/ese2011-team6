package modelTests;

import java.util.List;

import models.ESEUser;

import org.junit.Test;

import play.db.jpa.GenericModel.JPAQuery;
import play.test.Fixtures;
import play.test.UnitTest;

/**
 * @author judith
 * 
 */
public class ESEUserTest extends UnitTest {

	
	@Test //"@Before" doesn't work! ... (but @Test does...)
	public void setup() {
		//set up database		
	    Fixtures.deleteDatabase();
	    Fixtures.loadModels("data.yml");
	}

	@Test
	public void shouldeUseDatabase(){
		//check if he's there
		assertEquals(1,ESEUser.count());
		
		//retrieve user from database
		ESEUser bob = ESEUser.find("byUsername", "bob").first();
		assertNotNull(bob);
		
		//check parameters
		assertEquals("bob", bob.username);
		assertEquals("secret", bob.password);
		assertEquals("Bob", bob.firstName);
		assertEquals("Bobber", bob.familyName);
	}
	
	
	@Test
	public void constructorWith2ParasTest(){
		//create a new user
		ESEUser nick = new ESEUser("nick", "sehrgeheim");
		
		//check username and password
		assertEquals("nick", nick.username);
		assertEquals("sehrgeheim", nick.password);
		
		//check initialization
		assertNotNull(nick.calendarList);
		assertNotNull(nick.groupList);
	}
	
	@Test
	public void constructorWith3ParasTest(){
		//create a new user
		ESEUser user = new ESEUser("hansi", "sehrgeheim", "Hans", "Müller");
		
		//check parameters
		assertTrue(user.username.equals("hansi"));
		assertTrue(user.familyName.equals("Müller"));
		assertTrue(user.firstName.equals("Hans"));
		assertTrue(user.password.equals("sehrgeheim"));
	}
	
	@Test
	public void shouldCreatCalendar(){
		//Test with new user
		ESEUser hansi = new ESEUser("hansi", "sehrgeheim");
		hansi.createCalendar("Hausaufgaben");
		
		assertNotNull(hansi.calendarList.get(0));
		//test with database user
		//TODO find out, if it can/should work
/*		ESEUser bob = ESEUser.find("byUsername", "bob").first();
		assertNotNull(bob);
		
		bob.createCalendar("ShoppingListe");
		assertNotNull(bob.calendarList);
*/	}
	
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
