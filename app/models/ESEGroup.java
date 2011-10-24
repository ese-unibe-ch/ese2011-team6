/**
 * 
 */
package models;

import java.util.ArrayList;
import java.util.Arrays;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.data.validation.Required;
import play.db.jpa.Model;

/**
 * @author lukas
 * 
 */
@Entity
public class ESEGroup extends Model {
	@ManyToOne
	public ESEUser owner;
	@Required
	public String groupName;
	public ArrayList<ESEUser> userList;

	public ESEGroup(String groupName, ESEUser owner) {
		this.groupName = groupName;
		this.owner = owner;

		this.initialize();
	}

	private void initialize() {
		this.userList = new ArrayList<ESEUser>();
	}

	public void editGroupName(@Required String groupName) {
		// TODO: check string is not empty
		this.groupName = groupName;
	}

	public void addUser(@Required long userID) {
		ESEUser user = ESEUser.findById(userList);

		boolean valid = !this.userList.contains(user);

		if (valid) {
			this.userList.add(user);
		} else {
			// TODO Throw an exception or do something similar, because user is
			// already in this group
		}
	}

	public void addUser(@Required String userName) {
		ESEUser user = ESEUser.find("byUsername", userName).first(); // LK @
																		// TEAM_TEST
																		// test
																		// this!

		boolean valid = !this.userList.contains(user);

		if (valid) {
			this.userList.add(user);
		} else {
			// TODO Throw an exception or do something similar, because user is
			// already in this group
		}
	}

	public void removeUser(@Required String userName) {
		ESEUser user = ESEUser.find("byUsername", userName).first();

		this.userList.remove(user); // TODO (Maybe) Throw an exception or do
									// something similar, if the user does not
									// exist in the list
	}

	public void removeUser(@Required long userID) {
		ESEUser user = ESEUser.findById(userID);

		this.userList.remove(user); // TODO (Maybe) Throw an exception or do
									// something similar, if the user does not
									// exist in the list
	}

	public ArrayList<ESEUser> getAllUser() {
		return this.userList;
	}

	public boolean isUserInGroup(@Required long userID) {
		ESEUser user = ESEUser.findById(userID);
		return this.userList.contains(user);
	}

	public boolean isUserInGroup(@Required String userName) {
		ESEUser user = ESEUser.find("byUsername", userName).first();
		return this.userList.contains(user);
	}

	@Override
	public String toString() { // TODO: LK: correct?
		String strName = "Group " + this.groupName;
		String[] arrayUsers = (String[]) this.userList.toArray();
		String strUsers = "Users: " + Arrays.toString(arrayUsers);

		return strName + " " + strUsers;
	}

	public String getGroupName() {
		return this.groupName;
	}
}
