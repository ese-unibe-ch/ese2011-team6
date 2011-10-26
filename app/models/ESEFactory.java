/**
 * 
 */
package models;

/**
 * @author lukas
 * 
 */
public abstract class ESEFactory {
	public static ESECalendar createCalendar(String calendarName, ESEUser owner) {
		ESECalendar calendar = new ESECalendar(calendarName, owner);
		calendar.save();

		return calendar;
	}

	public static ESEEvent createEvent(String eventName, String strStart,
			String strEnd, String strIsPublic, ESECalendar correspondingCalendar) {
		ESEEvent event = new ESEEvent(eventName, strStart, strEnd, strIsPublic,
				correspondingCalendar);
		event.save();

		return event;
	}

	public static ESEGroup createGroup(String groupName, ESEUser owner) {
		ESEGroup group = new ESEGroup(groupName, owner);
		// group.save();
		// TODO: @ team-test: Warum ist das group.save() hier auskommentiert? 
		// -> ESEFactoryTest, #shouldAddGroupToDatabase() fails

		return group;
	}

	public static ESEUser createUser(String username, String password,
			String firstName, String familyName) {
		ESEUser user = new ESEUser(username, password, firstName, familyName);
		user.save();

		return user;
	}

	public static ESEUser createUser(String username, String password) {
		ESEUser user = new ESEUser(username, password);
		user.save();

		return user;
	}
}
