package org.bsc.client.main;

import org.bsc.shared.DaylyReport;
import org.bsc.shared.MonthlyTimeSheet;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class DaylyReportPlace extends Place {

	private MonthlyTimeSheet timesheet;
	private final int day;
	
	public DaylyReportPlace(int day) {
		this.day = day;
	}

	public DaylyReportPlace(MonthlyTimeSheet timesheet, int day) {
		this(day);
		this.timesheet = timesheet;
	}

	public int getDay() {
		return day;
	}

	public java.util.Date getDate() {
		return (timesheet!=null) ? timesheet.getDate() : null;
	}
	
	public DaylyReport getDaylyReport() {
		if( timesheet != null ) {
			return timesheet.getDayList().get(day);
		}
		return null;
	}

	public java.util.List<String> getActivityNames() {
		return ( timesheet != null ) ?
				timesheet.getActivityNames() :
				new java.util.ArrayList<String>(0);
	}
	public static class Tokenizer implements PlaceTokenizer<DaylyReportPlace> {

		@Override
		public DaylyReportPlace getPlace(String token) {
			return new DaylyReportPlace( Integer.valueOf(token) );
		}

		@Override
		public String getToken(DaylyReportPlace place) {
			return String.valueOf( place.day );
		}
		
	}
}
