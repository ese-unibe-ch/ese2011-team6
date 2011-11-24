package models;

import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;
import org.joda.time.DateTime;
import models.*;

@Entity
public class ModUser extends Model
{
	/* jokr you can use jpa and play validation annotations to describe the instance variables more clearly
	 * for example use the annotation @Required to define the variables which aren't nullable
	 * or the jpa annotation @Column(unique=true) to make the username unique (you then should also write
	 * a play validation check otherwise it just throws a exception
	 */
	public String user;
	public String password;
	public String firstname;
	public String lastname;
	public Date birthday;

	@OneToMany(cascade=CascadeType.ALL)
	public List<ModCalendar> calendars;

	public ModUser (
		String user,
		String password
	) {
		this.user = user;
		this.password = password;
		calendars = new ArrayList<ModCalendar>();
		save();
	}

	public static ModUser addUser (
		String user,
		String password
	) {
		return new ModUser(user, password);
	}
	
	/* jokr it seems a bit random what kind of constructors extist to create users
	 * whith jpa it is better to create one constructor for all not nullable attributes
	 * and set the others as simple instance variable references. For more details
	 * look for the comments in the controller
	 */
	public static ModUser addUser (
		String user,
		String password,
		DateTime birthday
	) {
		ModUser u = new ModUser(user, password);
		u.setBirthday(birthday);
		return u;
	}
	
	// jokr it isn't clear which kind of pattern you're expecting
	public static List<ModUser> getUsers (
		String pattern
	) {
		String query;
		if (pattern.length() <= 3) {
			return null;
		}
		query = "user like '%"+pattern+"%'";
		return find(query).fetch();
	}

	public static ModUser getUser (
		String user
	) {
		return find("byUser", user).first();
	}
	
	// jokr this method is not necassary. Just call ModUser.findById(id)
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
		save();
	}

	public void setFirstname (
		String firstname
	) {
		this.firstname = firstname;
		save();
	}

	public void setLastname (
		String lastname
	) {
		this.lastname = lastname;
		save();
	}

	public void setBirthday (
		DateTime birthday
	) {
		this.birthday = birthday.toDate();
		save();
	}

	public Boolean checkPass (
		String password
	) {
		return password.equals(this.password);
	}

	public ModCalendar addCalendar (
		String name
	) {
		ModCalendar c;
		try {
			c = new ModCalendar(this, name);
		}
		catch (Exception ex) {
			return null;
		}
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
		// jokr the object lc is never used
		List<ModCalendar> lc = new ArrayList<ModCalendar>();
		for (ModCalendar c :calendars)  {
			if (c.getName().equals(name)) {
				return c;
			}
		}
		return null;
	}
}