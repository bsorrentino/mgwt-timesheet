package org.bsc.client;

import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.junit.client.GWTTestCase;

public class TimesheetTest extends GWTTestCase {


	@Override
	public String getModuleName() {
		return "org.bsc.timesheet";
	}

	
	public void testDateFormat() {
		
		
		DateTimeFormat dtf = DateTimeFormat.getFormat("MMMM");
		
		String format = dtf.format( new java.util.Date() );
		
		System.out.println("month = " + format);
		
	}
}
