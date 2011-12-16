package utils;

import play.jobs.Job;
import play.jobs.OnApplicationStart;
import org.joda.time.DateTime;
import models.*;

@OnApplicationStart
public class Bootstrap extends Job
{
	public void doJob (
	) {
if (ModUser.count() > 0)
	return;

		ModUser.addUser("guest", "", new DateTime());

		ModUser steve = ModUser.addUser("steve", "doocy",
			new DateTime(1954, 5, 13, 0, 0));
		ModUser gretchen = ModUser.addUser("gretchen", "carlson",
			new DateTime(1959, 7, 2, 0, 0));
		ModUser brian = ModUser.addUser("brian", "kilmeade",
			new DateTime(1957, 12, 27, 0, 0));
System.out.println("ModUser: "+ModUser.count());

		ModCalendar steve_w = steve.addCalendar("work");
		ModCalendar steve_p = steve.addCalendar("private");
		ModCalendar gretchen_w = gretchen.addCalendar("work");
		ModCalendar gretchen_p = gretchen.addCalendar("private");
		ModCalendar brian_w = brian.addCalendar("work");
		ModCalendar brian_p = brian.addCalendar("private");
System.out.println("ModCalendar: "+ModCalendar.count());

		steve_w.addEvent("event for steve #1",
			new DateTime(2011, 11, 4, 14, 0),
			new DateTime(2011, 11, 4, 18, 0), true);
		steve_w.addEvent("event for steve #2 (private)",
			new DateTime(2011, 11, 8, 10, 0),
			new DateTime(2011, 11, 8, 20, 0), false);
		steve_p.addEvent("event for steve #1",
			new DateTime(2011, 11, 4, 14, 0),
			new DateTime(2011, 11, 4, 18, 0), true);
		steve_p.addEvent("event for steve #2 (private)",
			new DateTime(2011, 11, 8, 10, 0),
			new DateTime(2011, 11, 8, 20, 0), false);

		gretchen_w.addEvent("event for gretchen #1",
			new DateTime(2011, 11, 4, 14, 0),
			new DateTime(2011, 11, 4, 18, 0), true);
		gretchen_w.addEvent("event for gretchen #2 (private)",
			new DateTime(2011, 11, 8, 10, 0),
			new DateTime(2011, 11, 8, 20, 0), false);
		gretchen_p.addEvent("event for gretchen #1",
			new DateTime(2011, 11, 4, 14, 0),
			new DateTime(2011, 11, 4, 18, 0), true);
		gretchen_p.addEvent("event for gretchen #2 (private)",
			new DateTime(2011, 11, 8, 10, 0),
			new DateTime(2011, 11, 8, 20, 0), false);

		brian_w.addEvent("event for brian #1",
			new DateTime(2011, 11, 4, 14, 0),
			new DateTime(2011, 11, 4, 18, 0), true);
		brian_w.addEvent("event for brian #2 (private)",
			new DateTime(2011, 11, 8, 10, 0),
			new DateTime(2011, 11, 8, 20, 0), false);
		brian_p.addEvent("event for brian #1",
			new DateTime(2011, 11, 4, 14, 0),
			new DateTime(2011, 11, 4, 18, 0), true);
		brian_p.addEvent("event for brian #2 (private)",
			new DateTime(2011, 11, 8, 10, 0),
			new DateTime(2011, 11, 8, 20, 0), false);
System.out.println("ModEvent: "+ModEvent.count());
	}
}
