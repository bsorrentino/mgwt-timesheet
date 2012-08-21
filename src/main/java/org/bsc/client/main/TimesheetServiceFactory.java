package org.bsc.client.main;

import org.bsc.shared.MonthlyTimeSheet;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface TimesheetServiceFactory {

	public Request loadTimesheet( MonthlyReportPlace place, AsyncCallback<MonthlyTimeSheet> callback ) throws RequestException;

	Request saveDaylyActivities(DaylyReportPlace place,
			AsyncCallback<Void> callback) throws RequestException;

}
