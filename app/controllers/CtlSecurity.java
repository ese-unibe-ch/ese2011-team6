package controllers;

import models.*;

public class CtlSecurity extends Secure.Security
{
	static boolean authenticate (
		String username,
		String password
	) {
		ModUser u = ModUser.getUser(username);
		if (u == null) {
			return false;
		}
		return u.validatePassword(password);
	}
}
