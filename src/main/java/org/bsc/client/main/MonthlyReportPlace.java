package org.bsc.client.main;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class MonthlyReportPlace extends Place {

	public static class Tokenizer implements PlaceTokenizer<MonthlyReportPlace> {

		@Override
		public MonthlyReportPlace getPlace(String token) {
			return new MonthlyReportPlace();
		}

		@Override
		public String getToken(MonthlyReportPlace place) {
			return "";
		}

	}
}
