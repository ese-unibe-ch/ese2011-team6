package controllers;

import play.mvc.Controller;
import play.mvc.With;
import models.*;
import utils.*;

@With(Secure.class)
public class CtlCalendar extends Controller
{
	public static void home (
		String blob
	) {
		Message msg = new Message(params, blob, routeArgs);
		msg.addCalendar();
		msg.listCalendars();
		renderTemplate(msg.MASTER, msg);
	}

	public static void profile (
		String blob
	) {
		Message msg = new Message(params, blob, routeArgs);
		if(!msg.modUser()) {
			renderTemplate(msg.MASTER, msg);
		}
		master(msg.BLOB());
	}

	public static void master (
		String blob
	) {
		Message msg = new Message(params, blob, routeArgs);
		msg.addEvent();
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
