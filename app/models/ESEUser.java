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

	// STATIC METHODS:
	/**
	 * 
	 * @param username
	 *            of the current User
	 * @return
	 */
	public static List<ESEUser> getAllOtherUsers(String username) {
		List<ESEUser> allUsers = ESEUser.findAll();
		ESEUser user = ESEUser.find("byUsername", username).first();

		allUsers.remove(user);

		return allUsers;
	}

	public static List<ESEGroup> getGroupsOfUser(String username) {
		ESEUser user = ESEUser.find("byUsername", username).first();

		return user.getMyGroups();
	}

	public static ESEUser getUser(String user) {
		return find("byUsername", user).first();
	}

	// "THIS" GETTERS:

	public String getUsername() {
		return this.username;
	}

	public List<ESECalendar> getAllCalendars() {
		return this.calendarList;
	}

	public List<ESEGroup> getMyGroups() {
		return this.groupList;
	}

	public ESEGroup getGroup(String groupName) {
		for (ESEGroup group : this.groupList) {
			if (group.getGroupName().equals(groupName)) {
				return group;
			}
		}
		// TODO LK: Throw an exception
		return null;
	}

	public boolean validatePassword(String p) {
		return p.equals(password);
	}

	// --------------------- //
	// CREATE/REMOVE METHODS //
	// -------------------- //

	public void createCalendar(@Required String calendarName) {
		this.validateNewCalendar(calendarName);

		ESECalendar calendar = ESEFactory.createCalendar(calendarName, this);

		this.calendarList.add(calendar);
	}

	public void removeCalendar(@Required long calendarID) {
		ESECalendar calendar = ESECalendar.findById(calendarID); // LK @
																	// TEAM_TEST:
																	// test this
		this.calendarList.remove(calendar);
		calendar.delete(); // DB stuff
	}

	public void createGroup(@Required String groupName) {
		this.validateNewGroup(groupName);

		ESEGroup group = ESEFactory.createGroup(groupName, this);
		this.groupList.add(group);
	}

	public void removeGroup(@Required String groupName) {
		ESEGroup group = ESEGroup.find("byGroupNameAndUsername", groupName,
				username).first(); // TODO:
		// LK
		// @
		// TEAM_TEST:
		// Test
		// this!

		this.groupList.remove(group);
		group.delete();
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

	public void addUserToGroup(@Required String userName,
			@Required String groupName) {
		ESEGroup group = this.getGroup(groupName);
		group.addUser(userName);
	}

	// --------------- //
	// PRIVATE METHODS //
	// --------------- //

	private void initialize() {
		this.calendarList = new ArrayList<ESECalendar>();
		this.groupList = new ArrayList<ESEGroup>();

		ESEGroup groupFriends = ESEFactory.createGroup("Friends", this);
		System.out.println("----------OK BIS HIER--------");
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
