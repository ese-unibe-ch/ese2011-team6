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
		List<ESEEvent> le = null;
		String authid = Secure.Security.connected();

		if ((c = ESECalendar.getCalendar(id)) != null) {
			le = ESEEvent.find("correspondingCalendar", c)
				.fetch();
		}
		render(le);
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
		Boolean err_date = false;
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
			pub = pub==null ?"false" :"true";
			ESEFactory.createEvent(name, beg, end, pub, c);
		}
		params.flash();
		validation.keep();
		add_evt(id);
	}
}
