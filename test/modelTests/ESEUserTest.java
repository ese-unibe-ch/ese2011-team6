package modelTests;

import java.util.List;

import models.ESECalendar;
import models.ESEFactory;
import models.ESEGroup;
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

	//TODO use Factory!!
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
	public void createUserWithFacotry(){
		//TODO Factory problem!
		ESEUser hansi = ESEFactory.createUser("hansi", "geheim");
		assertNotNull(hansi);
		assertNotNull(hansi.calendarList);
		assertNotNull(hansi.groupList);
	}
		
	@Test
	public void shouldCreatCalendar(){
		//Test with new user
		ESEUser hansi = ESEFactory.createUser("hansi", "sehrgeheim", "Hans", "Müller");
		
		//add by using method
		hansi.createCalendar("Hausaufgaben");
		assertNotNull(hansi.calendarList.get(0));		
		assertEquals("Hausaufgaben", hansi.calendarList.get(0).name);
		assertEquals(1, hansi.calendarList.size());
			
		//add by using factory
		hansi.calendarList.add(ESEFactory.createCalendar("Wichtige Sachen"));
		assertEquals(3, hansi.calendarList.size());
		
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
		ESEUser hansi = ESEFactory.createUser("hansi", "sehrgeheim", "Hans", "Müller");
		hansi.createCalendar("Hausaufgaben");
		hansi.createCalendar("Shopping Liste");
		assertEquals(2,hansi.calendarList.size());
		
		ESECalendar husi = ESECalendar.find("byName", "Hausaufgaben").first();
		ESECalendar husi2 = hansi.calendarList.get(0);
		assertEquals(husi.id, husi2.id);
		//TODO fix this mess! why does ESECalendar.find(...) not work that way?
		
		ESECalendar shop = ESECalendar.find("byName", "Shopping Liste").first();
		assertNotNull(husi);
		assertNotNull(shop);
		assertEquals(ESECalendar.find("byName", "Hausaufgaben").first().getClass(), husi.getClass());
		
		//remove one
		hansi.removeCalendar(husi.id);
		assertEquals(1, hansi.calendarList.size());
		assertEquals(shop, hansi.calendarList.get(0));
		
		//remove last
		hansi.removeCalendar(shop.id);
		assertEquals(0, hansi.calendarList.size());
		
		//not tested is the case, when the list is empty or
		//when the id is wrong. This is not necessairy because
		//it is handled within the controller.
	}
	
	@Test
	public void shouldCreateGroup(){
		ESEUser hansi = ESEFactory.createUser("hansi", "sehrgeheim", "Hans", "Müller");
		
		assertEquals(1,hansi.groupList.size()); //jeder user hat eine 
		//default gruppe "friends"
		
		//add by using method
		hansi.createGroup("studies");
		assertEquals(2, hansi.groupList.size());
		//TODO groupe is not properly added.
		
		//add by using Factory
		hansi.groupList.add(ESEFactory.createGroup("best buddies"));	
		assertEquals(3,hansi.groupList.size());
		//add by using constructor
		hansi.groupList.add(new ESEGroup("verwandte"));
		assertEquals(4,hansi.groupList.size());
	}
	
	@Test
	public void shouldRemoveGroupe(){
		//set up
		ESEUser hansi = ESEFactory.createUser("hansi", "sehrgeheim", "Hans", "Müller");
		hansi.groupList.add(ESEFactory.createGroup("studies"));	
		hansi.groupList.add(ESEFactory.createGroup("best buddies"));	
		
		assertEquals(3, hansi.groupList.size());
		ESEGroup studies = ESEGroup.find("byGroupName", "studies").first();
		assertEquals("studies", studies.groupName);

	}
	
	@Test
	public void shouldTestEdits(){
		ESEUser hansi = ESEFactory.createUser("hansi", "sehrgeheim", "Hans", "Müller");
		
		hansi.editPassword("nochvielgeheimer");
		assertEquals("nochvielgeheimer", hansi.password);
		
		hansi.editFamilyName("Müller");
		assertEquals("Müller", hansi.familyName);
		
		hansi.editFirstName("Hans");
		assertEquals("Hans", hansi.firstName);
		
	}

}
