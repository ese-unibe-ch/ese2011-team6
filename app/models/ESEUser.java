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
 * Has a user name, a password, a first name and a family name.
 * Possesses a list of {@link ESECalendar}s and a list of {@link ESEGroup}s.
 * 
 * Is able to change its personal information, to add/remove ESECalendars 
 * to/from its calendar list and to assign/remove other ESEUsers to/from 
 * its ESEGroups.
 * 
 * @see ESECalendar
 * @see ESEGroup
 * 
 */
@Entity
public class ESEUser extends Model {
	/**
	 * name of this ESEUser
	 */
	@Required
	public String username;
	/**
	 * password of this ESEUser
	 */
	@Required
	public String password;

	/**
	 * List of all {@link ESECalendar}s of this ESEUser
	 */
	@OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
	public List<ESECalendar> calendarList;
	/**
	 * List of all {@link ESEGroup}s of this ESEUser
	 */
	@OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
	public List<ESEGroup> groupList;

	/**
	 * Initialization of the first name of this ESEUser
	 */
	public String firstName = "";
	/**
	 * Initialization of the family name of this ESEUser
	 */
	public String familyName = "";

	/**
	 * Class constructor specifying user name, password, first name and family
	 * name of this ESEUser
	 */
	public ESEUser(String username, String password, String firstName,
			String familyName) {
		this.username = username;
		this.password = password;

		this.firstName = firstName;
		this.familyName = familyName;

		this.initialize();
	}

	/**
	 * Class constructor specifying only user name and password of this ESEUser
	 */
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
	 * TODO: Was macht die Methode genau? Team model?
	 */
	public static List<ESEUser> getAllOtherUsers(String username) {
		List<ESEUser> allUsers = ESEUser.findAll();
		ESEUser user = ESEUser.find("byUsername", username).first();

		allUsers.remove(user);

		return allUsers;
	}

	/**
	 * Returns a list of all {@link ESEGroup}s of a certain ESEUser
	 * @param username
	 * @return all ESEGroups of a certain ESEUser
	 * @see ESEGroup
	 */
	public static List<ESEGroup> getGroupsOfUser(String username) {
		ESEUser user = ESEUser.find("byUsername", username).first();

		return user.getMyGroups();
	}

	/**
	 * Searches a certain ESEUser by its name and returns him/her
	 * @param user
	 * @return returns a certain ESEUser
	 */
	public static ESEUser getUser(String user) {
		return find("byUsername", user).first();
	}

	// "THIS" GETTERS:

	/**
	 * Returns the user name of this ESEUser
	 * @return name of this ESEUser
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * Returns a list of all {@link ESECalendar}s of this
	 * ESEUser
	 * @return list of all ESECalendars this ESEUser possesses
	 * @see ESECalendar
	 */
	public List<ESECalendar> getAllCalendars() {
		return this.calendarList;
	}

	/**
	 * Returns a list of all {@link ESEGroup}s of this
	 * ESEUser
	 * @return list of all ESEGroups this ESEUser possesses
	 * @see ESEGroup
	 */
	public List<ESEGroup> getMyGroups() {
		return this.groupList;
	}

	/**
	 * Searches for an {@link ESEGroup} with a certain name
	 * in the list of all ESEGroups of this ESEUser. Returns
	 * that ESEGroup if it is in the ESEGroup list of this ESEUser
	 * @param groupName
	 * @return ESEGroup with a certain name if in the ESEGroup list of this ESEUser
	 */
	public ESEGroup getGroup(String groupName) {
		for (ESEGroup group : this.groupList) {
			if (group.getGroupName().equals(groupName)) {
				return group;
			}
		}
		// TODO LK: Throw an exception
		return null;
	}

	/**
	 * Returns if the password correct
	 * @param p
	 * @return if the password is correct
	 */
	public boolean validatePassword(String p) {
		return p.equals(password);
	}

	// --------------------- //
	// CREATE/REMOVE METHODS //
	// -------------------- //

	/**
	 * Adds an {@link ESEcalendar} to this ESEUser
	 * @param calendarName
	 * @see ESECalendar
	 */
	public void createCalendar(@Required String calendarName) {
		this.validateNewCalendar(calendarName);

		ESECalendar calendar = ESEFactory.createCalendar(calendarName, this);

		this.calendarList.add(calendar);
	}

	/**
	 * Removes a certain ESECalendar by its id from the list of ESECalendars
	 * of this ESEUser
	 * @param calendarID
	 * @see ESECalendar
	 */
	public void removeCalendar(@Required long calendarID) {
		ESECalendar calendar = ESECalendar.findById(calendarID);
		this.calendarList.remove(calendar);
		calendar.delete(); // DB stuff
	}

	/**
	 * Creates a new {@link ESEGroup} with this ESEUser as owner
	 * and adds it to the list of ESEGroups of this ESEUser
	 * @param groupName
	 */
	public void createGroup(@Required String groupName) {
		this.validateNewGroup(groupName);

		ESEGroup group = ESEFactory.createGroup(groupName, this);
		this.groupList.add(group);
	}

	/**
	 * Removes a certain ESEGroup  by its name from the list of ESEGroups of
	 * this ESEUser.
	 * @param groupName
	 */
	public void removeGroup(@Required String groupName) {
		ESEGroup group = ESEGroup.find("byGroupNameAndUsername", groupName,
				username).first(); // TODO:
		this.groupList.remove(group);
		group.delete();
	}

	// --------------- //
	// EDIT METHODS //
	// --------------- //

	/**
	 * Changes the password of this ESEUser
	 * @param password
	 */
	public void editPassword(@Required String password) {
		if (!password.equals("")) {
			this.password = password;
		} else {
			// TODO Throw an exception or do something similar
		}
	}

	/**
	 * Changes the family name of this ESEUser
	 * @param familyName
	 */
	public void editFamilyName(@Required String familyName) {
		if (!familyName.equals("")) {
			this.familyName = familyName;
		} else {
			// TODO Throw an exception or do something similar
		}
	}

	/**
	 * Changes the first name of this ESEUser
	 * @param firstName
	 */
	public void editFirstName(@Required String firstName) {
		if (!firstName.equals("")) {
			this.firstName = firstName;
		} else {
			// TODO Throw an exception or do something similar
		}
	}

	/**
	 * Adds this ESEUser to a certain {@link ESEGroup}
	 * @param userName
	 * @param groupName
	 * @see ESEGroup
	 */
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
