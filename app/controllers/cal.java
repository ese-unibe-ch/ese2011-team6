package controllers;

import java.util.List;
import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import play.mvc.*;
import play.data.validation.*;
import models.*;

@With(Secure.class)
public class cal extends Controller
{
	public static int[] monthdays = {
		31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

	public static String[] monthnames = {
		"January", "February", "March", "April", "May", "June",
		"July", "August", "September", "October", "November",
		"December" };

	public static String[] daysofweek = {	/* see dayofweek() */
		"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday",
		"Friday", "Saturday" };

	public static void ls_evts (
		String id,
		String year,
		String month,
		String day
	) {
		ESECalendar c;
		List<ESEEvent> le = null;
		int y, m, d, cy, cm, cd, days;
		String mname;
		String[] dow;

		if ((c = ESECalendar.getCalendar(id)) != null) {
			le = permitted(c)
				?c.getAllEventsAsList()
				:c.getPublicEventsAsList();
		}

		if (year == null || month == null || day == null) {
			y = cyear();
			m = cmonth();
			d = cday();
		}
		else {
			y = Integer.parseInt(year);
			m = Integer.parseInt(month);
			d = Integer.parseInt(day);
		}
		cy = cyear();
		cm = cmonth();
		cd = cday();
		days = monthdays[m-1];
		mname = monthnames[m-1];
		dow = daysofweek_to_dow(daysofweek, y, m, d);
		render(id, le, y, m, d, cy, cm, cd, days, mname, dow);
	}

	public static void ls_evts (
		String id
	) {
		cal.ls_evts(id, null, null, null);
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
			if (permitted(c)) {
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

	public static void rm_evt (
		String id,
		String eid
	) {
		ESECalendar c = ESECalendar.getCalendar(id);
		if (c != null && permitted(c)) {
			c.removeEvent(Long.parseLong(eid));
		}
		cal.ls_evts(id);
	}

	public static Boolean permitted (
		ESECalendar c
	) {
		String authid = Secure.Security.connected();
		String cal_owner = c.getOwner().getUsername();
		return authid.equals(cal_owner);
	}

	public static int cyear (
	) {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.YEAR);
	}

	public static int cmonth (
	) {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.MONTH);
	}

	public static int cday (
	) {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 *	derived from http://users.aol.com/s6sj7gt/mikecal.htm
	 *	output: 0 (sun) -> 6 (sat)
	 */
	public static int dayofweek (
		int y, int m, int d
	) {
		int z = 0;
		int adj = 0;

		if (m < 3) {
			z = y-1;
		}
		else {
			z = y;
			adj = 2;
		}
		return ((int)(23*m/9)+d+4+y+(int)(z/4)-(int)(z/100)+(int)(z/400)-adj)%7;
	}

	public static String[] daysofweek_to_dow (
		String[] daysofweek,
		int y, int m, int d
	) {
		String[] r = new String[7];
		int dow = dayofweek(y, m, d);
		for (int i=0; i<7; i++) {
			r[i] = daysofweek[(dow+i)%7];
		}
		return r;
	}
}
