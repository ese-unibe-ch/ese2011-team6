package controllers;

public class ESEDay
{
	public String head = ""; /* table header */
	public String cssc = ""; /* css class list */

	public void init_cssc (
	) {
		cssc = "";
	}
	public void append_cssc (
		String c
	) {
		cssc = cssc+" "+c;
	}
	public void set_head (
		String h
	) {
		head = h;
	}
	public String get_cssc (
	) {
		return cssc;
	}
	public String get_head (
	) {
		return head;
	}
}
