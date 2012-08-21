package org.bsc.client.main;

import java.util.Arrays;

import org.bsc.shared.ActivityReport;
import org.bsc.shared.DaylyReport;
import org.bsc.shared.EntityFactory;
import org.bsc.shared.MonthlyTimeSheet;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;

public class TimesheetServiceFactoryImpl implements TimesheetServiceFactory {

	static final boolean DEVELOPMENT = false;
	
	final EntityFactory ef;
	
	enum ContentType {
		APPLICATION_JSON("application/json"),
		TEXT_PLAIN("text/plain"),
		FORM_URLENCODED("application/x-www-form-urlencoded")
		;
		
		private final String value;
		
		ContentType(String value) {
			this.value = value;
		}
		
		public String value() { return value; }
		
	}

	public TimesheetServiceFactoryImpl(EntityFactory ef) {
		super();
		this.ef = ef;
	}

	private String getServiceUrl(String service) {
		
		return new StringBuilder()
				.append( (DEVELOPMENT) ? GWT.getModuleBaseURL() : "http://sabina-soul.appspot.com/sabina/")
				.append( service )
				.toString();
	}

	private com.google.gwt.storage.client.Storage ls = 
			com.google.gwt.storage.client.Storage.getLocalStorageIfSupported();


	public com.google.gwt.storage.client.Storage getLocalStorage() {
		return ls;
	}

	final MonthlyReportPlace.Tokenizer t = new MonthlyReportPlace.Tokenizer();

	/**
	 * 
	 * @param place
	 * @return
	 */
	private String getStorageKey( MonthlyReportPlace place ) {
		return MonthlyTimeSheet.class.getName() + ":" + t.getToken(place);
	}
	
	/**
	 * 
	 * @return
	 */
	public java.util.List<String> getDefaultActivityNames() {
		
		return Arrays.asList("FERIE","ROL", "VARIE");
	}
	
	/**
	 * 
	 * @param place
	 * @param callback
	 */
	@SuppressWarnings("deprecation")
	public void loadTimesheetFromLocalStorage( MonthlyReportPlace place, AsyncCallback<MonthlyTimeSheet> callback ) {
		
		AutoBean<MonthlyTimeSheet> ts = null;
		
		final String storageKey = getStorageKey(place);
		
		String payload = getLocalStorage().getItem( storageKey );
		
		if( payload==null ) {

			final java.util.List<String> activityNames = getDefaultActivityNames();
			
			ts = ef.makeTimeSheet();
			
			ts.as().setDate( new java.util.Date(place.getDate().getTime()) );
			ts.as().setActivityNames( activityNames );
				
			java.util.List<DaylyReport> dataList = new java.util.ArrayList<DaylyReport>(31);
				
			final java.util.Date v = new java.util.Date(place.getDate().getTime());

			final int currentMonth = v.getMonth();
			
			for( int date = 1; v.getMonth() == currentMonth; ++date ) {
				
				v.setDate(date);
				
				DaylyReport dr = ef.makeDaylyReport().as();
				
				dr.setDay( date );
				dr.setHours(0);
				
				dataList.add( dr );	
				
				java.util.List<ActivityReport> activityList = new java.util.ArrayList<ActivityReport>( activityNames.size() );
				
				for( String n : activityNames ) {
					
					AutoBean<ActivityReport> ar = ef.makeActivityReport();
					
					activityList.add( ar.as() );
					
				}
				
				dr.setActivityList(activityList);
				
			}
			
			ts.as().setDayList(dataList);
			
			getLocalStorage().setItem(storageKey, AutoBeanCodex.encode(ts).getPayload());
			
		}
		else {
			ts = AutoBeanCodex.decode(ef, MonthlyTimeSheet.class, payload) ;
		}

		callback.onSuccess( ts.as() );
	}
	
	/**
	 * http://sabina-soul.appspot.com/sabina/rest/service/scriptlet/exec/timesheet:worksheet-get?month=7
	 * @throws RequestException 
	 */
	@SuppressWarnings("deprecation")
	@Override
	public Request loadTimesheet(  final MonthlyReportPlace place, final AsyncCallback<MonthlyTimeSheet> callback) throws RequestException {

		String url = new StringBuilder()
		.append(getServiceUrl("rest/service/scriptlet/exec/timesheet:worksheet-get"))
		.append('?')
		.append("month").append('=')
		.append( place.getDate().getMonth()) 
		.toString();

		RequestBuilder result = new RequestBuilder( RequestBuilder.GET, url );
		result.setHeader("Content-Type",ContentType.TEXT_PLAIN.value());

		//result.setRequestData( data );

		result.setCallback( new RequestCallback() {
			
			@Override
			public void onResponseReceived(Request request, Response response) {
				
				if( response.getStatusCode() == Response.SC_OK ) {

					String payload = response.getText();

					getLocalStorage().setItem( getStorageKey(place), payload );
					
					AutoBean<MonthlyTimeSheet> result = AutoBeanCodex.decode(ef, MonthlyTimeSheet.class, payload);
					
					callback.onSuccess(result.as());
					
				}
				else {
					callback.onFailure( new Exception( response.getStatusText()) );
									
				}
				
			}
			
			@Override
			public void onError(Request request, Throwable exception) {
				
				callback.onFailure(exception);
			}
		});

		result.setUser( "admin");
		result.setPassword( "password");
		
		return result.send();
	}
	
	/**
	 * http://sabina-soul.appspot.com/sabina/rest/service/scriptlet/exec/timesheet:time-set?
	 * activity=TEST&day=15&activity=SABINA&hours=4&hours=1&hours=5&activity=FERIE&activity=ROL&hours=1
	 * 
	 * @param place
	 * @param callback
	 * @return
	 * @throws RequestException
	 */
	@SuppressWarnings("deprecation")
	@Override
	public Request saveDaylyActivities( final DaylyReportPlace place, final AsyncCallback<Void> callback) throws RequestException {
		
		StringBuilder url = new StringBuilder()
		.append(getServiceUrl("rest/service/scriptlet/exec/timesheet:time-set"))
		.append('?')
		.append("month=").append( place.getDate().getMonth())
		.append('&')
		.append("day=").append( place.getDay()+1 )
		.append('&')
		.append("year=").append( place.getDate().getYear() + 1900 )
		.append('&')
		;
		
		for( String a : place.getActivityNames() ) {
			url.append("activity=").append(a).append('&');
		}
		for( ActivityReport ae : place.getDaylyReport().getActivityList() ) {
			url.append("hours=").append(ae.getHours()).append('&');
		}
		
		RequestBuilder result = new RequestBuilder( RequestBuilder.GET, url.toString() );
		result.setHeader("Content-Type",ContentType.TEXT_PLAIN.value());

		//result.setRequestData( data );

		result.setCallback( new RequestCallback() {
			
			@Override
			public void onResponseReceived(Request request, Response response) {
				
				if( response.getStatusCode() == Response.SC_OK ) {

					callback.onSuccess(null);
					
				}
				else {
					callback.onFailure( new Exception( response.getStatusText()) );
									
				}
				
			}
			
			@Override
			public void onError(Request request, Throwable exception) {
				
				callback.onFailure(exception);
			}
		});

		result.setUser( "admin");
		result.setPassword( "password");
		
		return result.send();
	}

}
