package controllers;

import models.*;
import utils.*;

public class CtlSecurity extends Secure.Security
{
	public static void register (
		String blob
	) {
		Message msg = new Message(params, blob, routeArgs);
		if (!msg.addUser()) {
			renderTemplate(msg.MASTER, msg);
		}
		CtlCalendar.home(null);
	}

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
