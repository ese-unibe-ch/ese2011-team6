package modelTests;

import java.util.List;

import models.ESECalendar;
import models.ESEFactory;
import models.ESEUser;
import net.sf.oval.constraint.AssertNullCheck;

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
		
		//check rest of initialization
		//TODO is that a problem? Does this problem only occure when date from .yml file is taken?
		// else the constructor would be used and the lists should be created, right?
		assertNotNull(bob.calendarList);
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
		assertEquals("Hausaufgaben", hansi.calendarList.get(0).name);
		
		hansi.createCalendar("Shopping Liste");
		assertEquals(2, hansi.calendarList.size());
		
		//TODO find out, if it can/should work
		//test with database/existing user
/*		ESEUser bob = ESEUser.find("byUsername", "bob").first();
		ESECalendar cal = new ESECalendar("AndereShoppingListe");
		assertNotNull(cal);
		bob.calendarList.add(cal);
*/	}
	
	@Test
	public void shouldRemoveCalendar(){
		//set up
		ESEUser hansi = new ESEUser("hansi", "sehrgeheim");
		hansi.createCalendar("Hausaufgaben");
		hansi.createCalendar("Shopping Liste");
		//TODO find better way to get calendars of user!
		ESECalendar husi = hansi.calendarList.get(0);
		ESECalendar shop = hansi.calendarList.get(1);
		
		//remove one
		hansi.removeCalendar(husi.id);
		assertEquals(1, hansi.calendarList.size());
		assertEquals(shop, hansi.calendarList.get(0));
		
		//remove last
		hansi.removeCalendar(shop.id);
		assertEquals(0, hansi.calendarList.size());
		
		//not tested it the case, when the list is empty or
		//when the id is wrong. This is not necessairy because
		//it is handled within the controller.
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
