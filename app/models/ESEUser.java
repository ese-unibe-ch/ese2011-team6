/**
 * 
 */
package models;

import java.util.ArrayList;

import javax.persistence.Entity;

import play.data.validation.Required;
import play.db.jpa.Model;

/**
 * @author lukas
 * 
 */
@Entity
public class ESEUser extends Model {
	@Required
	public String username;
	@Required
	public String password;
	public ArrayList<ESECalendar> calendarList;
	public ArrayList<ESEGroup> groupList;

	public String firstName;
	public String familyName;

	public ESEUser(String username, String password, String firstName,
			String familyName) {
		this.username = username;
		this.password = password;

		this.firstName = firstName;
		this.familyName = familyName;

		this.initialize();
	}

	public ESEUser(String username, String password) {
		this.username = username;
		this.password = password;

		this.initialize();
	}

	// --------------------- //
	// CREATE/REMOVE METHODS //
	// -------------------- //

	public void createCalendar(@Required String calendarName) {
		this.validateNewCalendar(calendarName);

		ESECalendar calendar = new ESECalendar(calendarName);

		this.calendarList.add(calendar);
	}

	public void removeCalendar(@Required long calendarID) {
		ESECalendar calendar = ESECalendar.findById(calendarID); // TODO: test
																	// this!
		this.calendarList.remove(calendar);
		calendar.delete(); // DB stuff
	}

	public void createGroup(@Required String groupName) {
		this.validateNewGroup(groupName);

		ESEGroup group = new ESEGroup(groupName);
	}

	// --------------- //
	// EDIT METHODS //
	// --------------- //

	public void editPassword(@Required String password) {
		if (!password.equals("")) {
			this.password = password;
		} else {
			// TODO Throw an exception or do something similar
		}
	}

	public void editFamilyName(@Required String familyName) {
		if (!familyName.equals("")) {
			this.familyName = familyName;
		} else {
			// TODO Throw an exception or do something similar
		}
	}

	public void editFirstName(@Required String firstName) {
		if (!firstName.equals("")) {
			this.firstName = firstName;
		} else {
			// TODO Throw an exception or do something similar
		}
	}

	// --------------- //
	// PRIVATE METHODS //
	// --------------- //

	private void initialize() {
		this.calendarList = new ArrayList<ESECalendar>();
		this.groupList = new ArrayList<ESEGroup>();

		ESEGroup groupFriends = new ESEGroup("Friends");
		this.groupList.add(groupFriends);

//		this.save();
	}

	private void validateNewCalendar(String calendarName) {
		// TODO check if a calendar with the name calendarName already
		// exists
	}

	private void validateNewGroup(String groupName) {
		// TODO check if a group with the name groupName already
		// exists
	}
}
