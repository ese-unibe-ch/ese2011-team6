package controllers;

import java.util.List;
import play.mvc.*;
import play.data.validation.*;
import models.*;

@With(Secure.class)
public class usr extends Controller
{
	public static void ls_cals (
		String id
	) {
		ESEUser u;
		List<ESECalendar> lc;
		String authid = Secure.Security.connected();
		String user = id==null ?authid :id;

		if ((u = ESEUser.getUser(user)) == null) {
			user = authid;
			u = ESEUser.getUser(user);
		}
		lc = u.getAllCalendars();
		render(lc);
	}

	public static void ls (
	) {
		List<ESEUser> lu;
		String authid = Secure.Security.connected();

		lu = ESEUser.getAllOtherUsers(authid);
		render(lu);
	}

	public static void add_usr (
		@Required String uname
	) {
		render(uname);
	}

	public static void add_usr_post (
		@Required String uname,
		@Required String pass,
		String fname,
		String lname
	) {
		ESEUser u = null;
		if (!validation.hasErrors()) {
			if ((u = ESEUser.getUser(uname)) != null) {
				u.editPassword(pass);
				u.editFirstName(pass);
				u.editFamilyName(pass);
			}
			else {
				ESEFactory.createUser(uname, pass, fname, lname);
			}
			usr.ls();
		}
		params.flash();
		validation.keep();
		add_usr(uname);
	}

	public static void rm_usr (
		String uname
	) {
		ESEUser u = ESEUser.find("byUsername", uname).first();
		u.delete();
		usr.ls();
	}
}
