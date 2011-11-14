package models;

import java.util.*;
import javax.persistence.*;
import play.db.jpa.*;
import org.joda.time.DateTime;
import models.*;

@Entity
public class ModUser extends Model
{
	public String user;
	public String password;
	public String firstname;
	public String lastname;
	public Date birthday;

	@OneToMany(cascade=CascadeType.ALL)
	public List<ModCalendar> calendars;

	public ModUser (
		String user,
		String password,
		DateTime birthday
	) {
		this.user = user;
		this.password = password;
		this.firstname = "";
		this.lastname = "";
		this.birthday = birthday.toDate();
		calendars = new ArrayList<ModCalendar>();
		save();
	}

	public static ModUser addUser (
		String user,
		String password,
		DateTime birthday
	) {
		return new ModUser(user, password, birthday);
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

	public static ModUser getUserById (
		Long id
	) {
		return findById(id);
	}

	public String getName (
	) {
		return user;
	}

	public String getFirstname (
	) {
		return firstname;
	}

	public String getLastname (
	) {
		return lastname;
	}

	public DateTime getBirthday (
	) {
		return new DateTime(birthday);
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

	public Boolean checkPass (
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
