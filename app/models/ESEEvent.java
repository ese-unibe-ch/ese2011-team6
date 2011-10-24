package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class ESEEvent extends Model {
	@ManyToOne
	public ESECalendar owner;
	public String name;
	public Date startDate;
	public Date endDate;
	public boolean isPublic;

	public ESEEvent(@Required String name, @Required String strStart,
			@Required String strEnd, @Required String strIsPublic) {
		this.name = name;
		this.startDate = ConversionHelper.convertStringToDate(strStart);
		this.endDate = ConversionHelper.convertStringToDate(strEnd);
		this.isPublic = Boolean.parseBoolean(strIsPublic);
	}

	public void renameEvent(@Required String newName) {
		this.name = newName;
	}

	public String getEventName() {
		return name;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public boolean isPublic() {
		return this.isPublic;
	}

	// TODO: Setters = editEvent
	// ? How does this look like?

}
