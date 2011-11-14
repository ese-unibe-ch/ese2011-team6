package controllers;

import models.*;

public class CtlSecurity extends Secure.Security
{
	static boolean authenticate (
		String username,
		String password
	) {
		ModUser u = ModUser.getUser(username);
		if (u == null || !u.checkPass(password)) {
			return false;
		}
		return true;
	}

	public static String authUser (
	) {
		return CtlSecurity.connected();
	}
}
