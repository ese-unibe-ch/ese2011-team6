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

	public void createUser(String userName, String password) {
		// TODO Check if a user with userName already exist and throw an
		// exception
		User user = new User(userName, password);
		userList.add(user);
	}

	public void deleteUser(String userName, String password) {
		// TODO
	}

	public User getUserByName(String username) {
		// TODO Auto-generated method stub
		return null;
	}

}
