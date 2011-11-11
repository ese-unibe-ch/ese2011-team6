package utils;

import java.util.Date;
import play.jobs.*;
import play.test.*;
import models.*;

@OnApplicationStart
public class Bootstrap extends Job
{
	public void doJob (
	) {
		/**
		 *	disable fixtures for now..
		 *
		 *
		Fixtures.deleteAll();
		Fixtures.load("../test/data.yml");
		 *
		 */
if (ModUser.count() > 0)
	return;

		ModUser steve = ModUser.addUser("steve", "doocy");
		ModUser gretchen = ModUser.addUser("gretchen", "carlson");
		ModUser brian = ModUser.addUser("brian", "kilmeade");
System.out.println("ModUser: "+ModUser.count());

		ModCalendar steve_w = steve.addCalendar("work");
		ModCalendar steve_p = steve.addCalendar("private");
		ModCalendar gretchen_w = gretchen.addCalendar("work");
		ModCalendar gretchen_p = gretchen.addCalendar("private");
		ModCalendar brian_w = brian.addCalendar("work");
		ModCalendar brian_p = brian.addCalendar("private");
System.out.println("ModCalendar: "+ModCalendar.count());

		steve_w.addEvent("event for steve #1",
			new Date("04/11/2011 14:00"),
			new Date("04/11/2011 18:00"), true);
		steve_w.addEvent("event for steve #2 (private)",
			new Date("08/11/2011 10:00"),
			new Date("08/11/2011 20:00"), false);
		steve_p.addEvent("event for steve #1",
			new Date("04/11/2011 14:00"),
			new Date("04/11/2011 18:00"), true);
		steve_p.addEvent("event for steve #2 (private)",
			new Date("08/11/2011 10:00"),
			new Date("08/11/2011 20:00"), false);

		gretchen_w.addEvent("event for gretchen #1",
			new Date("04/11/2011 14:00"),
			new Date("04/11/2011 18:00"), true);
		gretchen_w.addEvent("event for gretchen #2 (private)",
			new Date("08/11/2011 10:00"),
			new Date("08/11/2011 20:00"), false);
		gretchen_p.addEvent("event for gretchen #1",
			new Date("04/11/2011 14:00"),
			new Date("04/11/2011 18:00"), true);
		gretchen_p.addEvent("event for gretchen #2 (private)",
			new Date("08/11/2011 10:00"),
			new Date("08/11/2011 20:00"), false);

		brian_w.addEvent("event for brian #1",
			new Date("04/11/2011 14:00"),
			new Date("04/11/2011 18:00"), true);
		brian_w.addEvent("event for brian #2 (private)",
			new Date("08/11/2011 10:00"),
			new Date("08/11/2011 20:00"), false);
		brian_p.addEvent("event for brian #1",
			new Date("04/11/2011 14:00"),
			new Date("04/11/2011 18:00"), true);
		brian_p.addEvent("event for brian #2 (private)",
			new Date("08/11/2011 10:00"),
			new Date("08/11/2011 20:00"), false);
System.out.println("ModEvent: "+ModEvent.count());
	}
}
