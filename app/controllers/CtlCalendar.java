package controllers;

import play.mvc.Controller;
import play.mvc.With;
import models.*;
import utils.*;

@With(Secure.class)
public class CtlCalendar extends Controller
{
	public static void listEvents (
		String blob
	) {
		Message msg = new Message(params, blob, routeArgs);
		msg.listEvents();
		render(msg);
	}

	public static void delEvent (
		String blob
	) {
		Message msg = new Message(params, blob, routeArgs);
		msg.delEvent();
		listEvents(msg.BLOB());
	}

	public static void addEvent (
		String blob
	) {
		Message msg = new Message(params, blob, routeArgs);
		msg.addEvent();
		render(msg);
	}

	public static void addEventPost (
		String blob
	) {
		Message msg = new Message(params, blob, routeArgs);
		if (!msg.addEventPost()) {
			addEvent(msg.BLOB());
		}
		listEvents(msg.BLOB());
	}

	public static void modEvent (
		String blob
	) {
		Message msg = new Message(params, blob, routeArgs);
		msg.modEvent();
		addEvent(msg.BLOB());
	}
}
