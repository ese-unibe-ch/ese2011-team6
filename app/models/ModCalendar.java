package models;

import java.util.*;
import javax.persistence.*;
import play.db.jpa.*;
import org.joda.time.*;
import models.*;

@Entity
public class ModCalendar extends Model
{
	public String name;

	@ManyToOne
	public ModUser owner;
	@OneToMany(cascade=CascadeType.ALL)
	public List<ModEvent> events;

	public ModCalendar (
		ModUser owner,
		String name
	) {
		this.owner = owner;
		this.name = name;
		events = new ArrayList<ModEvent>();
		save();
	}

	public String getName (
	) {
		return name;
	}

	public ModUser getOwner (
	) {
		return owner;
	}

	public Boolean addEvent (
		String name,
		DateTime beg,
		DateTime end,
		Boolean pub
	) {
		ModEvent e;
		try {
			e = new ModEvent(this, name, beg, end, pub);
		}
		catch (Exception ex) {
			return false;
		}
		events.add(e);
		save();
		return true;
	}

	public void delEvent (
		Long id
	) {
		ModEvent e;
		if ((e = getEvent(id)) == null) {
			return;
		}
		events.remove(e);
		save();
		e.delete();
	}

	public ModEvent getEvent (
		Long id
	) {
		for (ModEvent e :events)  {
			if (!e.getId().equals(id)) {
				continue;
			}
			return e;
		}
		return null;
	}

	public List<ModEvent> getEventsAt (
		DateTime date,
		ModUser user
	) {
		DateTime beg, end;
		DateTime a = date.withTimeAtStartOfDay();
		DateTime b = date.withTimeAtStartOfDay();
		List<ModEvent> le = new ArrayList<ModEvent>();
		for (ModEvent e :events)  {
			beg = e.getBeg().withTimeAtStartOfDay();
			end = e.getEnd().withTimeAtStartOfDay();
			if (!(beg.compareTo(a) <= 0 &&
			      end.compareTo(b) >= 0)) {
				continue;
			}
			if (!isOwner(user) && !e.isPublic()) {
				continue;
			}
			le.add(e);
		}
		return le;
	}

	public Boolean isOwner (
		ModUser user
	) {
		return user.getId().equals(owner.getId());
	}
}
