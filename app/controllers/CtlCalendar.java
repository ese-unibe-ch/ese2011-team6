package controllers;

import play.mvc.Controller;
import play.mvc.With;
import models.*;
import utils.*;

@With(Secure.class)
public class CtlCalendar extends Controller
{
	public static void master (
		String blob
	) {
		Message msg = new Message(params, blob, routeArgs);
		msg.addCalendar();
		msg.addEvent();
		msg.modUser();
		msg.listCalendars();
		msg.listEvents();
		msg.listUsers();
		renderTemplate(msg.MASTER, msg);
	}

	public static void delEvent (
		String blob
	) {
		Message msg = new Message(params, blob, routeArgs);
		msg.delEvent();
		master(msg.BLOB());
	}

	public static void modEvent (
		String blob
	) {
		Message msg = new Message(params, blob, routeArgs);
		msg.modEvent();
		master(msg.BLOB());
	}
}
