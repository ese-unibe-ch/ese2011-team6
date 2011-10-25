package controllers;

import java.util.List;
import play.mvc.*;
import models.*;

@With(Secure.class)
public class usr extends Controller
{
	public static void ls_cal (
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
}
