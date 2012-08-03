#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )

package ${package};

import ${package}.activities.HomePlace;
import ${package}.calendar.CalendarActivity;
import ${package}.calendar.CalendarPlace;

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
