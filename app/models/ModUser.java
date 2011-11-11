package models;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import javax.persistence.*;
import play.db.jpa.*;
import models.*;

@Entity
public class ModUser extends Model
{
	public String user;
	public String password;
	public String firstname;
	public String lastname;

	@OneToMany(cascade=CascadeType.ALL)
	public List<ModCalendar> calendars;

	public ModUser (
		String user,
		String password
	) {
		this.user = user;
		this.password = password;
		this.firstname = "";
		this.lastname = "";
		calendars = new ArrayList<ModCalendar>();
		save();
	}

	public static ModUser addUser (
		String user,
		String password
	) {
		return new ModUser(user, password);
	}

	public static List<ModUser> getUsers (
	) {
		return findAll();
	}

	public static ModUser getUser (
		String user
	) {
		return find("byUser", user).first();
	}

	public String getName (
	) {
		return user;
	}

	public void setPassword (
		String password
	) {
		this.password = password;
	}

	public void setFirstname (
		String firstname
	) {
		this.firstname = firstname;
	}

	public void setLastname (
		String lastname
	) {
		this.lastname = lastname;
	}

	public Boolean validatePassword (
		String password
	) {
		return password.equals(this.password);
	}

	public ModCalendar addCalendar (
		String name
	) {
		ModCalendar c = new ModCalendar(this, name);
		calendars.add(c);
		save();
		return c;
	}

	public List<ModCalendar> getCalendars (
	) {
		return calendars;
	}

	public ModCalendar getCalendar (
		String name
	) {
		List<ModCalendar> lc = new ArrayList<ModCalendar>();
		for (ModCalendar c :calendars)  {
			if (c.getName().equals(name)) {
				return c;
			}
		}
		return null;
	}
}
