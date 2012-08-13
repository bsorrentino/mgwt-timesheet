package org.bsc.client;

import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.junit.client.GWTTestCase;


public class TimesheetTest extends GWTTestCase {


	@Override
	public String getModuleName() {
		return "org.bsc.timesheet";
	}

	
	public void testDayForMonth() {
		
		java.util.Date v = new java.util.Date();
		
		final int currentMonth = v.getMonth();
		
		// yyyy-MM-dd'T'HH:mm:ss.SSSZZZ
		final DateTimeFormat dtf = DateTimeFormat.getFormat("MMMM EEEE");

		v.setDate(1);
		
		for( int date = 1; v.getMonth() == currentMonth; ++date ) {
			
			String format = dtf.format( v );
			
			System.out.println(format);
			
			v.setDate( date );
			
			
		}

	}
	public void testDateFormat() {
		
		
		DateTimeFormat dtf = DateTimeFormat.getFormat("MMMM");
		
		String format = dtf.format( new java.util.Date() );
		
		System.out.println("month = " + format);
		
	}
}
