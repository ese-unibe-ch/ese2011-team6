package models;

import java.util.ArrayList;

public class User {
	private ArrayList<Calendar> calendarList;
	private String userName;
	private String password;

	private ArrayList<User> friendList;

	public User(String userName, String password) {
		this.userName = userName;
		this.password = password;

		this.calendarList = new ArrayList<Calendar>();
		this.friendList = new ArrayList<User>();
	}

	public ArrayList<User> getFriendList() {
		return this.getFriendList();
	}

	public ArrayList<Calendar> getCalendars() {
		return this.calendarList;
	}

	public String getName() {
		return this.userName;
	}

	public Object getPassword() {
		return this.password;
	}

}
