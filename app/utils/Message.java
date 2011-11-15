package utils;

import java.util.Map;
import java.util.List;
import play.libs.Codec;
import play.mvc.Scope.Params;
import play.mvc.Scope.RouteArgs;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import controllers.*;
import models.*;

public class Message
{
	public final String MASTER = "master.html";
	public final String REGISTRATION = "registration.html";

	public Params params;
	public RouteArgs routeArgs;
	public ModUser curUser;
	public ModUser selUser;
	public ModCalendar curCalendar;
	public DateTime curDate;
	public DateTime selDate;
	public String[] cssCalData;
	public List<ModEvent> events;
	public List<ModCalendar> calendars;
	public List<ModUser> users;
	public DateTimeFormatter fmt;
	public DateTimeFormatter fmt_short;
	public String curTitle = "XXX: title";
	public String curCaller;

	public Message (
		Params params,
		String blob,
		RouteArgs routeArgs
	) {
		initParams(params, blob);
		initRouteArgs(routeArgs);
		fmt = DateTimeFormat.forPattern("dd.MM.yyyy HH:mm");
		fmt_short = DateTimeFormat.forPattern("dd.MM.yyyy");
		try {
			throw new Exception();
		}
		catch (Exception e) {
			curCaller = e.getStackTrace()[1].getMethodName();
		}
	}

	public void initParams (
		Params params,
		String blob
	) {
		JSONParser parser;
		JSONObject paramsBlob;
		this.params = params;
		try {
			parser = new JSONParser();
			blob = new String(Codec.decodeBASE64(blob));
			paramsBlob = (JSONObject)parser.parse(blob);
		}
		catch (Exception e) {
			return;
		}
		for (Object key :paramsBlob.keySet()) {
			if (!key.toString().startsWith("uri_")) {
				continue;
			}
			if (GET(key.toString()) != null) {
				continue;
			}
			PUT(key.toString(),
				paramsBlob.get(key).toString());
		}
	}

	public void pruneParams (
	) {
		for (String key :params.allSimple().keySet()) {
			if (!key.startsWith("uri_err_")) {
				continue;
			}
			params.remove(key);
		}
	}

	public String printParams (
	) {
		String s = String.format("params:\n");
		for (String key :params.allSimple().keySet()) {
			s += String.format("\t%-20s: %s\n", key, GET(key));
		}
		return s;
	}

	public String getParamsBlob (
	) {
		JSONObject obj = new JSONObject();
		for (String key :params.allSimple().keySet()) {
			if (!key.startsWith("uri_")) {
				continue;
			}
			obj.put(key, GET(key));
		}
		return Codec.encodeBASE64(obj.toString());
	}

	public void initRouteArgs (
		RouteArgs routeArgs
	) {
		this.routeArgs = routeArgs;
		for (String key :params.allSimple().keySet()) {
			if (!key.startsWith("uri_")) {
				continue;
			}
			routeArgs.put(key, GET(key));
		}
	}

	public void initUser (
	) {
		String sel, cur;
		sel = GET("uri_user");
		cur = CtlSecurity.authUser();
		selUser = ModUser.getUser(sel);
		curUser = ModUser.getUser(cur);
		if (selUser == null) {
			selUser = curUser;
		}
	}

	public void initCalendar (
	) {
		String c = GET("uri_cal");
		curCalendar = selUser.getCalendar(c);
		if (curCalendar.isOwner(curUser)) {
			return;
		}
		PUT("uri_cal_readonly");
	}

	public void initDate (
	) {
		String yy = GET("uri_yy");
		String mm = GET("uri_mm");
		String dd = GET("uri_dd");
		curDate = new DateTime();
		if (yy == null || mm == null || dd == null) {
			selDate = curDate;
			return;
		}
		selDate = new DateTime(
			Integer.parseInt(yy),
			Integer.parseInt(mm),
			Integer.parseInt(dd),
			0, 0);
	}

	public void initCssCal (
	) {
		List<ModEvent> le;
		int daysweek = 7;
		int days = selDateDays();
		DateTime d = selDate.withDayOfMonth(1);
		cssCalData = new String[days];

		for (int i=0; i<days; i++) {
			if (cssCalData[i] == null) {
				cssCalData[i] = "";
			}
			cssCalData[i] += "day ";
			if (d.equals(selDate)) {
				cssCalData[i] += "selected ";
			}
			if (d.equals(curDate)) {
				cssCalData[i] += "today ";
			}
			le = curCalendar.getEventsAt(d, curUser);
			if (le.size() > 0) {
				cssCalData[i] += "event ";
			}
			d = d.plusDays(1);
		}
	}

	public int selDateDays (
	) {
		return selDate.dayOfMonth().getMaximumValue();
	}

	public int selDateDow (
	) {
		return selDate.withDayOfMonth(1).getDayOfWeek();
	}

	public void PUT (
		String key,
		String val
	) {
		System.out.println(curCaller+": "+
			key+": ["+ GET(key)+"]->["+val+"]");
		params.put(key, val);
	}

	public void PUT (
		String key
	) {
		PUT(key, "true");
	}

	public void PIF (
		String key,
		String val
	) {
		if (GET(key) != null) {
			return;
		}
		PUT(key, val);
	}

	public String GET (
		String key
	) {
		return params.get(key);
	}

	public void DEL (
		String key
	) {
		params.remove(key);
	}

	public void PUSH (
		String key
	) {
		PUT("_tmp_key", key);
		PUT("_tmp_val", GET(key));
	}

	public void POP (
	) {
		PUT(GET("_tmp_key"), GET("_tmp_val"));
		DEL("_tmp_key");
		DEL("_tmp_val");
	}

	public String BLOB (
	) {
		return getParamsBlob();
	}

	public void listEvents (
	) {
		initUser();
		initCalendar();
		initDate();
		initCssCal();
		if (curCalendar == null) {
			return;
		}
		events = curCalendar.getEventsAt(selDate, curUser);
		PIF("uri_datebeg", selDate.toString(fmt));
		PIF("uri_dateend", selDate.toString(fmt));
	}

	public void delEvent (
	) {
		String id;

		initUser();
		initCalendar();
		id = GET("uri_eventid");
		if (id == null || curCalendar == null) {
			return;
		}
		if (!curCalendar.isOwner(curUser)) {
			return;
		}
		curCalendar.delEvent(Long.parseLong(id));
	}

	public Boolean addEventPost (
	) {
		String name;
		DateTime beg;
		DateTime end;
		Boolean pub;
		String id;

		pruneParams();
		initUser();
		initCalendar();
		if (curCalendar == null) {
			return false;
		}
		if (!curCalendar.isOwner(curUser)) {
			return false;
		}
		name = GET("uri_eventname");
		if (name.length() == 0) {
			PUT("uri_err_eventname");
			return false;
		}
		try {
			beg = fmt.parseDateTime(GET("uri_datebeg"));
			end = fmt.parseDateTime(GET("uri_dateend"));
		}
		catch (Exception e) {
			PUT("uri_err_date");
			return false;
		}
		pub = GET("uri_eventpub")==null ?false :true;
		if (!curCalendar.addEvent(name, beg, end, pub)) {
			return false;
		}
		id = GET("uri_eventid");
		if (id != null) {
			curCalendar.delEvent(Long.parseLong(id));
		}
		return true;
	}

	public void modEvent (
	) {
		String id;

		initUser();
		initCalendar();
		id = GET("uri_eventid");
		if (id == null || curCalendar == null) {
			return;
		}
		if (!curCalendar.isOwner(curUser)) {
			return;
		}
		ModEvent e = curCalendar.getEvent(Long.parseLong(id));
		PUT("uri_eventname", e.getName());
		PUT("uri_datebeg", e.getBeg().toString(fmt));
		PUT("uri_dateend", e.getEnd().toString(fmt));
		PUT("uri_eventpub", e.isPublic().toString());
	}

	public void listCalendars (
	) {
		initUser();
		calendars = selUser.getCalendars();
	}

	public void listUsers (
	) {
		users = ModUser.getUsers();
	}

	public void addUser (
	) {
		return;
	}

	public Boolean addUserPost (
	) {
		String username;
		String password;
		String firstname;
		String lastname;
		DateTime birthday;

		pruneParams();
		username = GET("uri_username");
		if (username.length() == 0) {
			PUT("uri_err_username");
			return false;
		}
		if (ModUser.getUser(username) != null) {
			PUT("uri_err_username_exists");
			return false;
		}
		password = GET("uri_password");
		if (password.length() == 0) {
			PUT("uri_err_password");
			return false;
		}
		try {
			birthday = fmt_short
				.parseDateTime(GET("uri_birthday"));
		}
		catch (Exception e) {
			PUT("uri_err_birthday");
			return false;
		}
		ModUser.addUser(username, password, birthday);
		return true;
	}

	public Boolean modUser (
	) {
		String id;
		ModUser user;

		initUser();
		id = GET("uri_userid");
		user = ModUser.getUserById(Long.parseLong(id));
		if (user == null || user != curUser) {
			return false;
		}
		PUT("uri_firstname", user.getFirstname());
		PUT("uri_lastname", user.getLastname());
		PUT("uri_birthday", user.getBirthday().toString(fmt_short));
		return true;
	}
}
