package modelTests;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import models.ConversionHelper;
import models.ESEEvent;

import org.junit.Before;
import org.junit.Test;

import play.test.UnitTest;

public class ConversionHelperTest extends UnitTest{

	String stringDate;
	Date dateToTest;
	Date compareDate;
	
	@Before
	public void setUp() {
	
		this.stringDate = "14.05.2015 15:00";
		Calendar compareDateCalendar = new GregorianCalendar(2015, Calendar.MAY, 14, 15, 00);
		compareDate = compareDateCalendar.getTime();
		
		Calendar dateToTestCalendar = new GregorianCalendar(2011, Calendar.FEBRUARY, 13, 14, 00);
		dateToTest = dateToTestCalendar.getTime();
	}
	
	@Test
	public void shouldParseStringToDate(){
		assertEquals(ConversionHelper.convertStringToDate(stringDate),compareDate);
	}
	
	@Test
	public void shouldParseDateToString(){
		assertEquals(ConversionHelper.convertDateToString(dateToTest),"13.02.2011 14:00");
	}
	
	@Test
	public void WhatToDoWithBadImput(){
		//TODO: What happens in the case of bad imput? Or is all controlled by the view?
		// Two cases of bad imput: 1) 13.04.1fss 14:00 oder auch: 35.10.2011 13:00
	}
	
}