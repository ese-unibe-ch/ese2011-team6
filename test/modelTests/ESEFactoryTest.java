package modelTests;

import models.ESECalendar;
import models.ESEEvent;
import models.ESEFactory;
import models.ESEGroup;
import models.ESEUser;

import org.junit.Before;
import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;

public class ESEFactoryTest extends UnitTest {

	@Before
	public void setUp() {
		// no setup needed so far
	}

	@Test
	public void testsCorrectWorkingOfDatabase() {
		Fixtures.loadModels("data.yml");
		assertEquals(ESEUser.count(), 2);
		Fixtures.deleteDatabase();
		assertEquals(ESEUser.count(), 0);
	}

	@Test
	public void shouldAddCalendarToDataBase() {
		Fixtures.deleteDatabase();
		assertEquals(ESECalendar.count(), 0);
		ESEFactory.createCalendar("Testcalendar1");
		assertEquals(ESECalendar.count(), 1);
	}

	@Test
	public void shouldAddEventToDataBase() {
		Fixtures.deleteDatabase();
		assertEquals(ESEEvent.count(), 0);
		ESEFactory.createEvent("AnotherTestevent", "13.04.2011 13:00",
				"14.04.2011 14:00", "true");
		assertEquals(ESEEvent.count(), 1);
	}

	@Test
	public void shouldAddGroupToDataBase() {
		Fixtures.deleteDatabase();
		assertEquals(ESEGroup.count(), 0);
		ESEFactory.createGroup("TestGroupName"); // LK @ TEAM_TEST: Need the
													// owner
													// (ESEUser object) of
													// the group
		assertEquals(ESEGroup.count(), 1);
	}

	@Test
	public void shouldAddUserToDataBase() {
		Fixtures.deleteDatabase();
		assertEquals(ESEUser.count(), 0);
		ESEFactory.createUser("testUser1", "pw1", "firstName1", "familyName1");
		assertEquals(ESEUser.count(), 1);
	}

	@Test
	public void shouldAddUserWithoutFirstAndFamilyNameToDataBase() {
		Fixtures.deleteDatabase();
		assertEquals(ESEUser.count(), 0);
		ESEFactory.createUser("testUser2", "pw2");
		assertEquals(ESEUser.count(), 1);
	}
}
