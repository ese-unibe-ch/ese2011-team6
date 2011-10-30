package models;

/**
 * Creates instances of ESECalendar, ESEEvent, ESEGroup and ESEUser
 * 
 * @see ESECalendar
 * @see ESEEvent
 * @see ESEGroup
 * @see ESEUser
 */
public abstract class ESEFactory
{
	public static ESECalendar createCalendar(String calendarName, ESEUser owner)
	{
		ESECalendar calendar = new ESECalendar(calendarName, owner);
		calendar.save();

		return calendar;
	}

	/**
	 * @deprecated Es soll stattdessen {@link #createEvent(String, String, String, ESECalendar, String)} verwendet werden
	 */
	public static ESEEvent createEvent(String eventName, String strStart,
			String strEnd, String strIsPublic, ESECalendar correspondingCalendar)
	{
		return ESEFactory.createEvent(eventName, strStart, strEnd,
				correspondingCalendar, strIsPublic);
	}

	/**
	 * Creates an {@link ESEEvent}
	 * @param eventName
	 * @param strStart
	 * @param strEnd
	 * @param correspondingCalendar
	 * @param strIsPublic
	 * @return newly created ESEEvent
	 * @see ESEEvent
	 */
	public static ESEEvent createEvent(String eventName, String strStart, String strEnd,
			ESECalendar correspondingCalendar, String strIsPublic)
	{
		ESEEvent event = new ESEEvent(eventName, strStart, strEnd,
				correspondingCalendar, strIsPublic);
		correspondingCalendar.save();
		event.save();

		return event;
	}

	/**
	 * Creates a new {@link ESEGroup}
	 * @param groupName
	 * @param owner
	 * @return newly created ESEGroup
	 * @see ESEGroup
	 */
	public static ESEGroup createGroup(String groupName, ESEUser owner)
	{
		ESEGroup group = new ESEGroup(groupName, owner);
		// group.save();

		return group;
	}

	/**
	 * Creates a new {@link ESEUser} with the minimum information 
	 * needed to create an ESEUser.
	 * @param username
	 * @param password
	 * @return newly created ESEUser
	 * @see ESEUser
	 */
	public static ESEUser createUser(String username, String password)
	{
		ESEUser user = new ESEUser(username, password);
		user.save();

		return user;
	}

	/**
	 * Creates a new {@link ESEUser} with all possible specifications
	 * to create an ESEUser.
	 * @param username
	 * @param password
	 * @param firstName
	 * @param familyName
	 * @return newly created ESEUser
	 * @see ESEUser
	 */
	public static ESEUser createUser(String username, String password,
			String firstName, String familyName)
	{
		ESEUser user = new ESEUser(username, password, firstName, familyName);
		user.save();

		return user;
	}
}
