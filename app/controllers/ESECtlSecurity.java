package controllers;

import models.*;

public class sec extends Secure.Security
{
	static boolean authenticate (
		String user,
		String pass
	) {
		ESEUser u = ESEUser.getUser(user);
		if (u == null) {
			return false;
		}
		return u.validatePassword(pass);
	}
}
