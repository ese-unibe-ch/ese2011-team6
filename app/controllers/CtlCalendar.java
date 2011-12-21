package controllers;

import play.mvc.Controller;
import play.mvc.With;
import models.*;
import utils.*;

public class CtlCalendar extends Controller
{
	public static void master (
		String blob
	) {
		Message msg = new Message(params, blob, routeArgs);
		if (!msg.initUser()) {
			CtlSecurity.register(null);
		}
		msg.addCalendar();
		msg.delEvent();
		msg.modEvent();
		msg.addEvent();
		msg.modUser();
		msg.listCalendars();
		msg.listEvents();
		msg.listUsers();
		renderTemplate(msg.MASTER, msg);
	}
}
