package org.bsc.client;

import org.bsc.shared.EntityFactory;
import org.bsc.shared.DaylyReport;
import org.bsc.shared.MonthlyTimeSheet;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;


public class TimesheetTest extends GWTTestCase {


	@Override
	public String getModuleName() {
		return "org.bsc.timesheet";
	}

	
	public void testAutoBean() {
		
		EntityFactory ef = GWT.create(EntityFactory.class);
		
		AutoBean<MonthlyTimeSheet> ts = ef.makeTimeSheet();
		
		ts.as().setDate( new java.util.Date() );
		
		java.util.List<DaylyReport> dayList = new java.util.ArrayList<DaylyReport>();
		
		AutoBean<DaylyReport> mr = ef.makeDaylyReport();
		mr.as().setDay( 1 );
		mr.as().setHours(8);
		
		dayList.add( mr.as() );
		
		ts.as().setDayList(dayList);
		
		String payload = AutoBeanCodex.encode(ts).getPayload();
		
		System.out.println( "payload " + payload );
	}
	
	@SuppressWarnings("deprecation")
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
