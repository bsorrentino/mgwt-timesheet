
package org.bsc.client;

import org.bsc.client.activities.HomePlace;
import org.bsc.client.calendar.CalendarActivity;
import org.bsc.client.calendar.CalendarPlace;
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
		
		String placeClass = place.getClass().getName();
		
		if( place instanceof HomePlace || place instanceof MonthlyReportPlace ) {
			
			result = new MonthlyReportActivity( clientFactory );
		}
		else if( place instanceof CalendarPlace) {
		
			result = new CalendarActivity( clientFactory );
		}
		
		return result;
	}
}
