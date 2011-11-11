package controllers;

import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;
import play.mvc.*;
import play.data.validation.*;
import models.*;
import utils.*;

@With(Secure.class)
public class CtlCalendar extends Controller
{
	private static String auth_user = Secure.Security.connected();

	public static void lsEvents (
		String uri_user,
		String uri_cal,
		String uri_yy,
		String uri_mm,
		String uri_dd
	) {
		String user = uri_user==null ?auth_user :uri_user;
		String cal = uri_cal;

		ModUser u;
		ModCalendar c;
		Message msg = null;
		List<ModEvent> le = null;

		if ((u = ModUser.getUser(user)) == null) {
			user = auth_user;
			u = ModUser.getUser(user);
		}
		if ((c = u.getCalendar(cal)) != null) {
			msg = new Message();
			msg.lsEvents(uri_yy, uri_mm, uri_dd, c);
			le = c.isOwner(u)
				?c.getEventsAt(msg.date, false)
				:c.getEventsAt(msg.date, true);
		}
		render(user, cal, le, msg);
	}

	public static void lsEvents (
		String uri_user,
		String uri_cal
	) {
		String user = uri_user==null ?auth_user :uri_user;
		String cal = uri_cal;

		CtlCalendar.lsEvents(user, cal, null, null, null);
	}

	public static void addEvent (
		String uri_user,
		String uri_cal,
		String eid,
		String ename,
		String ebeg,
		String eend,
		String epub,
		Boolean err_date
	) {
		String user = uri_user==null ?auth_user :uri_user;
		String cal = uri_cal;

		render(user, cal, eid, ename, ebeg, eend, epub, err_date);
	}

	public static void addEventPost (
		String uri_user,
		String uri_cal,
		String eid,
		@Required String ename,
		@Required String ebeg,
		@Required String eend,
		String epub
	) {
		String user = uri_user==null ?auth_user :uri_user;
		String cal = uri_cal;

		ModUser u;
		ModCalendar c;
		Date dbeg = null;
		Date dend = null;
		Boolean err_date = false;
		SimpleDateFormat sdf = new SimpleDateFormat(
			"dd.MM.yyyy HH:mm");

		try {
			dbeg = sdf.parse(ebeg);
			dend = sdf.parse(eend);
			if (dend.compareTo(dbeg) < 0) {
				throw new Exception();
			}
		} catch (Exception e) {
			err_date = true;
		}
		if ((u = ModUser.getUser(user)) == null) {
			user = auth_user;
			u = ModUser.getUser(user);
		}
		if (!validation.hasErrors() && !err_date &&
		   (c = u.getCalendar(cal)) != null) {
			if (c.isOwner(u)) {
				epub = epub==null ?"false" :"true";
				if (eid != null) {
					c.delEvent(Long.parseLong(eid));
				}
				c.addEvent(ename, dbeg, dend, /* XXX: ugly */
					Boolean.parseBoolean(epub));
			}
			CtlCalendar.lsEvents(user, cal);
		}
		params.flash();
		validation.keep();
		CtlCalendar.addEvent(user, cal, eid, ename, ebeg,
			eend, epub, err_date);
	}

	public static void modEvent (
		String uri_user,
		String uri_cal,
		String eid
	) {
		String user = uri_user==null ?auth_user :uri_user;
		String cal = uri_cal;

		ModUser u;
		ModCalendar c;
		ModEvent e = null;	/* XXX: needs handling */
		if ((u = ModUser.getUser(user)) == null) {
			user = auth_user;
			u = ModUser.getUser(user);
		}
		if ((c = u.getCalendar(cal)) != null && c.isOwner(u)) {
			e = c.getEvent(Long.parseLong(eid));
		}
		c.addEvent(e.getName(), e.getBeg(), e.getEnd(), e.isPublic());
	}

	public static void delEvent (
		String uri_user,
		String uri_cal,
		String eid
	) {
		String user = uri_user==null ?auth_user :uri_user;
		String cal = uri_cal;

		ModUser u;
		ModCalendar c;
		if ((u = ModUser.getUser(user)) == null) {
			user = auth_user;
			u = ModUser.getUser(user);
		}
		if ((c = u.getCalendar(cal)) != null && c.isOwner(u)) {
			c.delEvent(Long.parseLong(eid));
		}
		CtlCalendar.lsEvents(user, cal);
	}
}
