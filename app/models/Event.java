package models;

import java.util.Date;

public class Event {
	
	private Date start;
	private Date end;
	private boolean isPublic;
	private String name;
	
	public Event(String name, boolean isPublic, Date start, Date end){
		//TODO throw exception if input is not valid ?
		this.name = name;
		this.isPublic = isPublic;
		this.start = start;
		this.end = end;
	}
	
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getStart() {
		return start;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	public Date getEnd() {
		return end;
	}
	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}
	public boolean isPublic() {
		return isPublic;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	

}
