package modelTests;

import static org.junit.Assert.*;

import java.util.List;

import models.ESECalendar;
import models.ESEFactory;
import models.ESEGroup;
import models.ESEUser;
import net.sf.oval.constraint.AssertNullCheck;

import org.junit.Before;
import org.junit.Test;

import play.db.jpa.GenericModel.JPAQuery;
import play.test.Fixtures;
import play.test.UnitTest;


public class ESEDataBaseTest {
	
	@Before
	public void setup() {
		//set up database		
	    Fixtures.deleteDatabase();
	    Fixtures.loadModels("data.yml");
	}

	@Test
	public void shouldeUseDatabase(){
		//check for Bob, Bill and Jack from data.yml
		assertEquals(3,ESEUser.count());
		
		//retrieve user from database
		ESEUser bob = ESEUser.find("byUsername", "bob").first();
		assertNotNull(bob);
		ESEUser bill = ESEUser.find("byFirstname", "Bill").first();
		assertNotNull(bill);
		ESEUser jack = ESEUser.find("byFamilyName", "Sparrow").first();
		assertNotNull(jack);
		
		//check parameters only for Bob
		assertEquals("bob", bob.username);
		assertEquals("secret", bob.password);
		assertEquals("Bob", bob.firstName);
		assertEquals("Bobber", bob.familyName);
		
		//check
				
	}

}
