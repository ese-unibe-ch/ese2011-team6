package models;

import java.util.*;
import javax.persistence.*;
import play.db.jpa.*;
import org.joda.time.*;
import models.*;

@Entity
public class ModEvent extends Model
{
	public String name;
	public Date beg;
	public Date end;
	public Boolean pub;

	@ManyToOne
	public ModCalendar calendar;

	public ModEvent (
		ModCalendar calendar,
		String name,
		DateTime beg,
		DateTime end,
		Boolean pub
	) {
		this.calendar = calendar;
		this.name = name;
		this.beg = beg.toDate();
		this.end = end.toDate();
		this.pub = pub;
		save();
	}

	public String getName (
	) {
		return name;
	}

	public DateTime getBeg (
	) {
		return new DateTime(beg);
	}

	public DateTime getEnd (
	) {
		return new DateTime(end);
	}

	public Boolean isPublic (
	) {
		return pub;
	}
}
