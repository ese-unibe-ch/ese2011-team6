/**
 * 
 */
package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.data.validation.Required;
import play.db.jpa.Model;

/**
 * Holds information about its owner, its name and about a list
 * of {@link ESEUser}s assigned to this group by its owner.
 * 
 * Provides methods that change its name, add/remove ESEUsers to
 * the user list an check whether a certain ESEUser is in this
 * list already
 * 
 * @see ESEUser
 * 
 */
@Entity
public class ESEGroup extends Model {
	/**
	 * The owner ({@link ESEUser}) of this ESEGroup
	 */
	@ManyToOne
	public ESEUser owner;
	/**
	 * The name of this ESEGroup
	 */
	@Required
	public String groupName;
	/**
	 * A list of {@link ESEUser}s that were added to this ESEGroup
	 * by its owner
	 */
	public ArrayList<ESEUser> userList;

	public ESEGroup(String groupName, ESEUser owner) {
		this.initialize();
		this.groupName = groupName;
		this.owner = owner;

	}

	private void initialize() {
		this.userList = new ArrayList<ESEUser>();
	}

	/**
	 * Changes the name of this ESEGroup
	 * @param groupName
	 */
	public void editGroupName(@Required String groupName) {
		// TODO: check string is not empty
		this.groupName = groupName;
	}

	/**
	 * Searches an {@link ESEUser} by its id 
	 * and adds him/her to this ESEGroup
	 * @param userID
	 * @see ESEUser
	 */
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

	/**
	 * Searches an {@link ESEUser} by its user name
	 * and adds him/her to this ESEGroup
	 * @param userName
	 * @see ESEUser
	 */
	public void addUser(@Required String userName) {
		ESEUser user = ESEUser.find("byUsername", userName).first(); 

		boolean valid = !this.userList.contains(user);

		if (valid) {
			this.userList.add(user);
		} else {
			// TODO Throw an exception or do something similar, because user is
			// already in this group
		}
	}

	/**
	 * Searches an {@link ESEUser} by its user name
	 * and removes him/her from this ESEGroup
	 * @param userName
	 */
	public void removeUser(@Required String userName) {
		ESEUser user = ESEUser.find("byUsername", userName).first();

		this.userList.remove(user); // TODO (Maybe) Throw an exception or do
									// something similar, if the user does not
									// exist in the list
	}

	/**
	 * Searches an {@link ESEUser} by its user name
	 * and removes him/her from this ESEGroup
	 * @param userID
	 */
	public void removeUser(@Required long userID) {
		ESEUser user = ESEUser.findById(userID);

		this.userList.remove(user); // TODO (Maybe) Throw an exception or do
									// something similar, if the user does not
									// exist in the list
	}

	/**
	 * Returns all ESEUsers in this ESEGroup
	 * @return all users in this ESEGroup
	 * @see ESEUser
	 */
	public List<ESEUser> getAllUser() {
		return this.userList;
	}

	/**
	 * Checks if a {@link ESEUser} with a certain id is 
	 * in this ESEGroup
	 * @param userID
	 * @return true if the ESEUser with a certain id is in this ESEGroup, false elsewhere
	 */
	public boolean isUserInGroup(@Required long userID) {
		ESEUser user = ESEUser.findById(userID);
		return this.userList.contains(user);
	}

	/**
	 * Checks if a {@link ESEUser} with a certain user name is 
	 * in this ESEGroup
	 * @param userID
	 * @return true if the ESEUser with a certain user name is in this ESEGroup, false elsewhere
	 */
	public boolean isUserInGroup(@Required String userName) {
		ESEUser user = ESEUser.find("byUsername", userName).first();
		return this.userList.contains(user);
	}

	// @Override
	// public String toString() { // TODO: LK: correct?
	// String strName = "Group " + this.groupName;
	// String[] arrayUsers = (String[]) this.userList.toArray();
	// String strUsers = "Users: " + Arrays.toString(arrayUsers);
	//
	// return strName + " " + strUsers;
	// }

	/**
	 * Returns the name of this ESEGroup
	 * @return name of this ESEGroup
	 */
	public String getGroupName() {
		return this.groupName;
	}
}
