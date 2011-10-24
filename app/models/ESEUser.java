/**
 * 
 */
package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

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

	@OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
	public List<ESECalendar> calendarList;
	@OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
	public List<ESEGroup> groupList;

	public String firstName = "";
	public String familyName = "";

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
	// GETTERS METHODS //
	// -------------------- //
	/**
	 * 
	 * @param username
	 *            of the current User
	 * @return
	 */
	public static List<ESEUser> getAllOtherUsers(String username) {
		List<ESEUser> allUsers = ESEUser.findAll();
		ESEUser currentUser = ESEUser.find("byUsername", username).first();

		allUsers.remove(currentUser);

		return allUsers;
	}

	// --------------------- //
	// CREATE/REMOVE METHODS //
	// -------------------- //

	public void createCalendar(@Required String calendarName) {
		System.out.println("CREATE CALENDAR: " + calendarName);
		this.validateNewCalendar(calendarName);

		ESECalendar calendar = ESEFactory.createCalendar(calendarName, this);

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

		ESEGroup group = ESEFactory.createGroup(groupName, this);
		this.groupList.add(group);
	}

	// TODO GET CALENDAR

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

		ESEGroup groupFriends = new ESEGroup("Friends", this);
		this.groupList.add(groupFriends);
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
