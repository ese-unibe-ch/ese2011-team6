package models;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import javax.persistence.*;
import play.db.jpa.*;
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
		Date beg,
		Date end,
		Boolean pub
	) {
		this.calendar = calendar;
		this.name = name;
		this.beg = beg;
		this.end = end;
		this.pub = pub;
		save();
	}

	public String getName (
	) {
		return name;
	}

	public Date getBeg (
	) {
		return beg;
	}

	public Date getEnd (
	) {
		return end;
	}

	public Boolean isPublic (
	) {
		return pub;
	}
}
