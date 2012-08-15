package org.bsc.client.main;

import java.util.Date;

import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class MonthlyReportPlace extends Place {

	public static class Tokenizer implements PlaceTokenizer<MonthlyReportPlace> {

		private DateTimeFormat getDateFormat() {
			return DateTimeFormat.getFormat("MM-yyyy");
		}

		@Override
		public MonthlyReportPlace getPlace(String token) {
			
			return (token==null||token.length()==0) ? 
						new MonthlyReportPlace() : 
						new MonthlyReportPlace(getDateFormat().parse(token));
		}

		@Override
		public String getToken(MonthlyReportPlace place) {
			return getDateFormat().format(place.getDate());
		}

	}
	
	final java.util.Date date;

	public MonthlyReportPlace(Date date) {
		this.date = date;
	}

	public MonthlyReportPlace() {
		this( new java.util.Date());
	}
	
	public java.util.Date getDate() {
		return date;
	}
	
}
