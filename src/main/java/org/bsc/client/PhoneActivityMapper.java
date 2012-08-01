
package org.bsc.client;

import org.bsc.client.activities.HomePlace;
import org.bsc.client.calendar.CalendarActivity;
import org.bsc.client.calendar.CalendarPlace;

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
		
		if( place instanceof CalendarPlace || place instanceof HomePlace) {
		
			result = new CalendarActivity( clientFactory );
		}
		
		return result;
	}
}
