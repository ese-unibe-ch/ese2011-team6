package controllers;

import java.util.List;
import play.mvc.*;
import play.data.validation.*;
import models.*;

@With(Secure.class)
public class CtlUser extends Controller
{
	private static String auth_user = Secure.Security.connected();

	public static void lsCalendars (
		String uri_user
	) {
		ModUser u;
		List<ModCalendar> lc;
		String user = uri_user==null ?auth_user :uri_user;

		if ((u = ModUser.getUser(user)) == null) {
			user = auth_user;
			u = ModUser.getUser(user);
		}
		lc = u.getCalendars();
		render(user, lc);
	}

	public static void lsUsers (
	) {
		List<ModUser> lu;
		lu = ModUser.getUsers();
		render(lu);
	}

	public static void addUser (
		String uname
	) {
		render(uname);
	}

	public static void addUserPost (
		@Required String uname,
		@Required String upass,
		String ufname,
		String ulname
	) {
		ModUser u = null;
		if (!validation.hasErrors()) {
			if ((u = ModUser.getUser(uname)) != null) {
				u.setPassword(upass);
				u.setFirstname(ufname);
				u.setLastname(ulname);
			}
			else {
				ModUser.addUser(uname, upass);
			}
			CtlUser.lsUsers();
		}
		params.flash();
		validation.keep();
		CtlUser.addUser(uname);
	}

	public static void delUser (
		String uname
	) {
		ModUser u = ModUser.getUser(uname);
		if (u != null) {
			/**
			 *	XXX: ModUser.removeUser() should exist
			 *	and not only delete the user but also
			 *	all his/her Calendars and/or Events..
			 */
			u.delete();
		}
		CtlUser.lsUsers();
	}
}
