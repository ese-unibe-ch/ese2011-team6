package utils;

import java.util.Map;
import java.util.List;
import java.util.LinkedList;
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
	public List<String> curOverlaps;
	public LinkedList<String> paramStack;

	public Message (
		Params params,
		String blob,
		RouteArgs routeArgs
	) {
		initParams(params, blob);
		initRouteArgs(routeArgs);
		paramStack = new LinkedList<String>();
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
			if (paramsBlob.get(key) == null) {
				continue;
			}
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

	public void pruneErrors (
	) {
		for (String key :params.allSimple().keySet()) {
			if (!key.startsWith("uri_err_")) {
				continue;
			}
			DEL(key);
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

	public Boolean initUser (
	) {
		String sel, cur;
		sel = GET("uri_user");
		cur = CtlSecurity.authUser();
		selUser = ModUser.getUser(sel);
		curUser = ModUser.getUser(cur);
		if (curUser == null) {
			setFlag("anonymous");
			curUser = ModUser.getUser("anonymous");
		}
		if (selUser == null || hasFlag("usermod")) {
			if (hasFlag("anonymous")) {
				return false;
			}
			selUser = curUser;
		}
		if (selUser != curUser) {
			setFlag("readonly");
		}
		else {
			delFlag("readonly");
		}
		return true;
	}

	public void initCalendar (
	) {
		String c = GET("uri_cal");
		curCalendar = selUser.getCalendar(c);
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
				cssCalData[i] += "day-selected ";
			}
			if (d.equals(curDate.withTimeAtStartOfDay())) {
				cssCalData[i] += "day-today ";
			}
			le = curCalendar.getEventsAt(d, curUser);
			if (le.size() > 0) {
				cssCalData[i] += "day-event ";
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

	public int selDateLastDow (
	) {
		return selDate
			.withDayOfMonth(selDateDays()).getDayOfWeek();
	}

	public void PUT (
		String key,
		String val
	) {
		String curCaller;
		try {
			throw new Exception();
		}
		catch (Exception e) {
			curCaller = e.getStackTrace()[1].getMethodName();
		}
		//System.out.println(curCaller+": "+
		//	key+": ["+ GET(key)+"]->["+val+"]");
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
		//System.out.println(curCaller+": "+
		//	key+": ["+ GET(key)+"]->DELETE!");
		params.remove(key);
	}

	public void PUSH (
		String key,
		String val
	) {
		String tmpkey = "_TMPKEY_"+key;
		String tmpval = "_TMPVAL_"+key;
		PUT(tmpkey, key);
		PUT(tmpval, GET(key));
		paramStack.add(key);
		PUT(key, val);
	}

	public void PUSH (
		String key
	) {
		PUSH(key, "true");
	}

	public void POP (
	) {
		String key = paramStack.removeLast();
		String tmpkey = "_TMPKEY_"+key;
		String tmpval = "_TMPVAL_"+key;
		if (GET(tmpval) == null) {
			DEL(GET(tmpkey));
		}
		else {
			PUT(GET(tmpkey), GET(tmpval));
		}
		DEL(tmpkey);
		DEL(tmpval);
	}

	public String BLOB (
	) {
		return getParamsBlob();
	}

	public void setFlag (
		String name
	) {
		String flag = "uri_"+name;
		PUT(flag);
	}

	public void delFlag (
		String name
	) {
		String flag = "uri_"+name;
		DEL(flag);
	}

	public Boolean hasFlag (
		String name
	) {
		String flag = "uri_"+name;
		if (GET(flag) == null) {
			return false;
		}
		return true;
	}

	public Boolean getFlag (
		String name
	) {
		Boolean r = hasFlag(name);
		delFlag(name);
		return r;
	}

	public Boolean isShort (
		String s,
		int n
	) {
		if (s == null) {
			return true;
		}
		if (s.length() < n) {
			return true;
		}
		return false;
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
		if (!getFlag("eventdel")) {
			return;
		}
		id = GET("uri_eventid");
		DEL("uri_eventid");
		if (id == null || curCalendar == null) {
			return;
		}
		if (!curCalendar.isOwner(curUser)) {
			return;
		}
		curCalendar.delEvent(Long.parseLong(id));
	}

	public void addEvent (
	) {
		String name;
		DateTime beg;
		DateTime end;
		Boolean pub;
		String id;
		ModEvent event;

		initUser();
		initCalendar();
		id = GET("uri_eventid");
		if (!getFlag("form_event")) {
			return;
		}
		if (curCalendar == null) {
			return;
		}
		if (!curCalendar.isOwner(curUser)) {
			return;
		}
		name = GET("uri_eventname");
		if (isShort(name, 3)) {
			PUT("uri_err_eventname");
			return;
		}
		try {
			beg = fmt.parseDateTime(GET("uri_datebeg"));
			end = fmt.parseDateTime(GET("uri_dateend"));
		}
		catch (Exception e) {
			PUT("uri_err_date");
			return;
		}
		pub = GET("uri_eventpub")==null ?false :true;
		event = curCalendar.addEvent(name, beg, end, pub);
		if (event == null) {
			PUT("uri_err_date");
			return;
		}
		if (id != null) {
			curCalendar.delEvent(Long.parseLong(id));
		}
		curOverlaps = curCalendar.getOverlaps(event, fmt);
		DEL("uri_eventname");
		DEL("uri_datebeg");
		DEL("uri_dateend");
		DEL("uri_eventpub");
		DEL("uri_eventid");
		pruneErrors();
	}

	public void modEvent (
	) {
		String id;

		initUser();
		initCalendar();
		if (!getFlag("eventmod")) {
			return;
		}
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

	public void addCalendar (
	) {
		String name;

		initUser();
		if (!getFlag("form_calendar")) {
			return;
		}
		name = GET("uri_newcal");
		if (isShort(name, 3)) {
			PUT("uri_err_newcal");
			return;
		}
		if (curUser.addCalendar(name) == null) {
			PUT("uri_err_newcal");
			return;
		}
		pruneErrors();
	}

	public void listCalendars (
	) {
		initUser();
		calendars = selUser.getCalendars();
	}

	public Boolean addUser (
	) {
		String username;
		String password;
		String passwordc;

		pruneErrors();
		if (!getFlag("form_register")) {
			return false;
		}
		username = GET("uri_username");
		if (isShort(username, 3)) {
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
		passwordc = GET("uri_passwordc");
		if (password.length() == 0 ||
		    !password.equals(passwordc)) {
			PUT("uri_err_passwordc");
			return false;
		}
		ModUser.addUser(username, password);
		DEL("uri_username");
		DEL("uri_password");
		DEL("uri_passwordc");
		return true;
	}

	public void modUser (
	) {
		ModUser user;
		DateTime birthday;

		initUser();
		user = ModUser.getUser(GET("uri_user"));
		if (user == null || user != curUser) {
			return;
		}
		if (!getFlag("form_user")) {
			if (!hasFlag("usermod")) {
				return;
			}
			PUT("uri_firstname", user.getFirstname());
			PUT("uri_lastname", user.getLastname());
			PUT("uri_birthday",
				user.getBirthday().toString(fmt_short));
			return;
		}
		try {
			birthday = fmt_short
				.parseDateTime(GET("uri_birthday"));
		}
		catch (Exception e) {
			birthday = null;
		}
		user.setPassword(GET("uri_password"));
		user.setFirstname(GET("uri_firstname"));
		user.setLastname(GET("uri_lastname"));
		user.setBirthday(birthday);
		DEL("uri_password");
		DEL("uri_firstname");
		DEL("uri_lastname");
		DEL("uri_birthday");
		getFlag("usermod");
		pruneErrors();
	}

	public void listUsers (
	) {
		String pattern;

		if (!getFlag("form_find")) {
			return;
		}
		pattern = GET("uri_finduser");
		if (isShort(pattern, 4)) {
			PUT("uri_err_finduser");
			return;
		}
		users = ModUser.getUsers(pattern);
		pruneErrors();
	}
}
