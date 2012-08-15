package org.bsc.client.main;

import java.util.Date;

import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class DaylyReportPlace extends Place {

	
	private final java.util.Date date;
	
	public DaylyReportPlace(Date date) {
		super();
		this.date = date;
	}

	public java.util.Date getDate() {
		return date;
	}

	public static class Tokenizer implements PlaceTokenizer<DaylyReportPlace> {

		private DateTimeFormat getDateFormat() {
			return DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_SHORT);
		}
		
		@Override
		public DaylyReportPlace getPlace(String token) {
			return new DaylyReportPlace( getDateFormat().parse(token) );
		}

		@Override
		public String getToken(DaylyReportPlace place) {
			return getDateFormat().format(place.getDate());
		}
		
	}
}
