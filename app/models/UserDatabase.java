package models;

import java.util.ArrayList;

public class UserDatabase {
	private ArrayList<User> userList;
	private static UserDatabase database;

	private UserDatabase() {
		this.userList = new ArrayList<User>();
	}

	public static UserDatabase getInstance() {
		if (database == null) {
			database = new UserDatabase();
		}

		return database;
	}

	public void createUser(String username, String password) {

		if (this.getUserByName(username) != null) {
			// TODO: throw an exception: user already exist
		} else {
			User user = new User(username, password);
			userList.add(user);
		}
	}

	public void deleteUser(String username, String password) {
		// TODO: Throw the exception of getUserByName if user is null;
		User user = this.getUserByName(username);

		if (user.getPassword().equals(password)) {
			userList.remove(user);
		} else {
			// TODO throw an exception: password wrong
		}
	}

	public User getUserByName(String username) {
		for (User user : userList) {
			if (user.getName().equals(username)) {
				return user;
			}
		}

		// TODO Throw an exception: noUserFound
		return null;
	}

}
