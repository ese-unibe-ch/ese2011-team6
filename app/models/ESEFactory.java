/**
 * 
 */
package models;

/**
 * @author lukas
 * 
 */
public abstract class ESEFactory {
	public static ESECalendar createCalendar(String calendarName) {
		ESECalendar calendar = new ESECalendar(calendarName);
		calendar.save();

		return calendar;
	}

	public static ESEEvent createEvent(String eventName, String strStart,
			String strEnd, String strIsPublic) {
		ESEEvent event = new ESEEvent(eventName, strStart, strEnd, strIsPublic);
		event.save();

		return event;
	}

	public static ESEGroup createGroup(String groupName) {
		ESEGroup group = new ESEGroup(groupName);
		group.save();

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
