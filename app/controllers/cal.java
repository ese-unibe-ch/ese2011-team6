package controllers;

import java.util.List;
import play.mvc.*;
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
}
