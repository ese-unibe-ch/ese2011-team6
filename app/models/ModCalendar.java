package models;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import javax.persistence.*;
import play.db.jpa.*;
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

	public ModEvent addEvent (
		String name,
		Date beg,
		Date end,
		Boolean pub
	) {
		ModEvent e = new ModEvent(this, name, beg, end, pub);
		events.add(e);
		save();
		return e;
	}

	public void delEvent (
		Long id
	) {
		for (ModEvent e :events)  {
			if (!e.getId().equals(id)) {
				continue;
			}
			e.delete();
		}
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
		Date date,
		Boolean pub
	) {
		Date beg, end;
		Date a = (Date)date.clone();
		Date b = (Date)date.clone();
		List<ModEvent> le = new ArrayList<ModEvent>();
		for (ModEvent e :events)  {
			beg = e.getBeg();
			end = e.getEnd();
			a.setHours(beg.getHours());	/* XXX: ugly */
			a.setMinutes(beg.getMinutes());
			b.setHours(end.getHours());
			b.setMinutes(end.getMinutes());
			if (!(beg.compareTo(a) <= 0 &&
			      end.compareTo(b) >= 0)) {
				continue;
			}
			if (pub && !e.isPublic()) {
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
