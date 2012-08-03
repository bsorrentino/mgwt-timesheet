#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.client;

import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.junit.client.GWTTestCase;


public class TimesheetTest extends GWTTestCase {


	@Override
	public String getModuleName() {
		return "${package}.timesheet";
	}

	
	public void testDateFormat() {
		
		
		DateTimeFormat dtf = DateTimeFormat.getFormat("MMMM");
		
		String format = dtf.format( new java.util.Date() );
		
		System.out.println("month = " + format);
		
	}
}
