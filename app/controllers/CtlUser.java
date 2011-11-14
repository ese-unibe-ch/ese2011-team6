package controllers;

import play.mvc.Controller;
import play.mvc.With;
import models.*;
import utils.*;

@With(Secure.class)
public class CtlUser extends Controller
{
	public static void listCalendars (
		String blob
	) {
		Message msg = new Message(params, blob, routeArgs);
		msg.listCalendars();
		render(msg);
	}

	public static void listUsers (
		String blob
	) {
		Message msg = new Message(params, blob, routeArgs);
		msg.listUsers();
		render(msg);
	}

	public static void addUser (
		String blob
	) {
		Message msg = new Message(params, blob, routeArgs);
		msg.addUser();
		render(msg);
	}

	public static void addUserPost (
		String blob
	) {
		Message msg = new Message(params, blob, routeArgs);
		if (!msg.addUserPost()) {
			addUser(msg.BLOB());
		}
		listUsers(msg.BLOB());
	}

	public static void modUser (
		String blob
	) {
		Message msg = new Message(params, blob, routeArgs);
		if (!msg.modUser()) {
			listUsers(msg.BLOB());
		}
		addUser(msg.BLOB());
	}
}
