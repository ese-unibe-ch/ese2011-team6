package modelTests;

import models.*;

import org.junit.*;

import play.test.Fixtures;
import play.test.UnitTest;

public class ESEFactoryTest extends UnitTest {

	@Before
	public void setUp() {
		Fixtures.deleteDatabase();
		Fixtures.loadModels("data.yml");
	}

	@Test
	public void testsCorrectWorkingOfDatabase() {

		assertEquals(ESEUser.count(), 3);
		Fixtures.deleteDatabase();
		assertEquals(ESEUser.count(), 0);
		ESEUser testUser = ESEFactory.createUser("User", "Pass");
		ESECalendar testCalendar = ESEFactory.createCalendar("Testcalendar1", testUser);
		ESEEvent testEvent = ESEFactory.createEvent("Event",
				"28.10.2011 12:00", "28.10.2011 12:00", "true", testCalendar);

		assertEquals(testUser, (ESEUser) ESEUser.find("byUsername", "User").first());
		assertEquals(testCalendar, (ESECalendar) ESECalendar.find("byCalendarName", "Testcalendar1").first());
		assertEquals(testEvent, (ESEEvent) ESEEvent.find("byEventName", "Event").first());
	}

	@Test
	public void shouldAddCalendarToDataBase() {
		Fixtures.deleteDatabase();
		assertEquals(ESECalendar.count(), 0);
		ESEFactory.createCalendar("Testcalendar1", ESEFactory.createUser("User", "Pass"));
		assertEquals(ESECalendar.count(), 1);
	}

	@Test
	public void shouldAddEventToDataBase() {
		// LK @ TEAM_TEST: FIX THIS!
		Fixtures.deleteDatabase();
		assertEquals(ESEEvent.count(), 0);
		ESEFactory.createEvent("AnotherTestevent", "13.04.2011 13:00",
		"14.04.2011 14:00", "true",
		ESEFactory.createCalendar("Testcalendar1", ESEFactory.createUser("User", "Pass")) );
		assertEquals(ESEEvent.count(), 1);
	}

	@Test
	public void shouldAddGroupToDataBase() {
		Fixtures.deleteDatabase();
		assertEquals(ESEGroup.count(), 0);
		ESEFactory.createGroup("TestGroupName", null).save();
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
