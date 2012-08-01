package org.bsc.client.calendar;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class CalendarPlace extends Place {

	public static class Tokenizer implements PlaceTokenizer<CalendarPlace> {

		@Override
		public CalendarPlace getPlace(String token) {
			return new CalendarPlace();
		}

		@Override
		public String getToken(CalendarPlace place) {
			return "";
		}

	}
	
	public CalendarPlace() {
	}

}
