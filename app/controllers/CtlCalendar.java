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
		renderTemplate(msg.MASTER, msg);
	}

	public static void delEvent (
		String blob
	) {
		Message msg = new Message(params, blob, routeArgs);
		msg.delEvent();
		listEvents(msg.BLOB());
	}

	public static void addEventPost (
		String blob
	) {
		Message msg = new Message(params, blob, routeArgs);
		msg.addEventPost();
		listEvents(msg.BLOB());
	}

	public static void modEvent (
		String blob
	) {
		Message msg = new Message(params, blob, routeArgs);
		msg.modEvent();
		listEvents(msg.BLOB());
	}
}
