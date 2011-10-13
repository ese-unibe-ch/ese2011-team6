package controllers;

import models.User;
import models.UserDatabase;

public class Security {
	
    static boolean authenticate(String username, String password) {
        UserDatabase um = UserDatabase.getInstance();
        User claimedUser = um.getUserByName(username);
        return (claimedUser != null ) && claimedUser.getPassword().equals(password);
    }
}
