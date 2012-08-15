
package org.bsc.client;

import org.bsc.client.activities.HomePlace;
import org.bsc.client.calendar.CalendarActivity;
import org.bsc.client.calendar.CalendarPlace;
import org.bsc.client.main.DaylyReportActivity;
import org.bsc.client.main.DaylyReportPlace;
import org.bsc.client.main.MonthlyReportActivity;
import org.bsc.client.main.MonthlyReportPlace;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;


/**
 * 
 */
public class PhoneActivityMapper implements ActivityMapper {

	private final ClientFactory clientFactory;

	public PhoneActivityMapper(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	@Override
	public Activity getActivity(Place place) {

		Activity result = null;
		
		if( place instanceof HomePlace  ) {
			
			result = new MonthlyReportActivity( clientFactory, new MonthlyReportPlace() );
		}

		else if( place instanceof MonthlyReportPlace) {
			
			result = new MonthlyReportActivity( clientFactory, (MonthlyReportPlace) place );
		}
		
		else if( place instanceof DaylyReportPlace) {
		
			result = new DaylyReportActivity( clientFactory, (DaylyReportPlace)place );
		}
		
		else if( place instanceof CalendarPlace) {
		
			result = new CalendarActivity( clientFactory );
		}
		
		return result;
	}
}
