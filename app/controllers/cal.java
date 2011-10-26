package controllers;

import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;
import play.mvc.*;
import play.data.validation.*;
import models.*;

@With(Secure.class)
public class cal extends Controller
{
	public static void ls_evts (
		String id
	) {
		ESECalendar c;
		String cal_owner;
		List<ESEEvent> le = null;
		String authid = Secure.Security.connected();

		if ((c = ESECalendar.getCalendar(id)) != null) {
			le = c.getAllEventsAsList();
		}
		cal_owner = c.getOwner().getUsername();
		if (!authid.equals(cal_owner)) {
			le = c.getPublicEventsAsList();
		}
		render(id, le);
	}

	public static void add_evt (
		String id
	) {
		render(id);
	}

	public static void add_evt_post (
		String id,
		@Required String name,
		@Required String beg,
		@Required String end,
		String pub
	) {
		Date dbeg, dend;
		String cal_owner;
		Boolean err_date = false;
		String authid = Secure.Security.connected();
		ESECalendar c = ESECalendar.getCalendar(id);
		SimpleDateFormat sdf = new SimpleDateFormat(
			"dd.MM.yyyy HH:mm");

		try {
			dbeg = sdf.parse(beg);
			dend = sdf.parse(end);
			if (dend.compareTo(dbeg) < 0) {
				throw new Exception();
			}
		} catch (Exception e) {
			err_date = true;
		}
		if (!validation.hasErrors() && !err_date && c != null) {
			cal_owner = c.getOwner().getUsername();
			if (authid.equals(cal_owner)) {
				pub = pub==null ?"false" :"true";
				ESEFactory.createEvent(
					name, beg, end, pub, c);
			}
			cal.ls_evts(id);
		}
		params.flash();
		validation.keep();
		add_evt(id);
	}
}
