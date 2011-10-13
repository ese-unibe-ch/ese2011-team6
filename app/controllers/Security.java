package controllers;

import models.User;
import models.UserDatabase;

public class Security extends Secure.Security {
	static boolean authenticate(String username, String password) {
		UserDatabase userDB = UserDatabase.getInstance();

		User claimedUser = userDB.getUserByName(username);
		return (claimedUser != null)
				&& claimedUser.getPassword().equals(password);
	}
}
