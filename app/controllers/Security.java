package controllers;

import models.User;

public class Security {
	
    static boolean authenticate(String username, String password) {
        UserDatabase um = UserDatabase.getIsntance();
        User claimedUser = um.getUserByName(username);
        return (claimedUser != null ) && claimedUser.getPassword().equals(password);
    }
}
