/*
 * Copyright 2010 Daniel Kurka
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.bsc.client;

import java.util.Arrays;

import org.bsc.client.calendar.CalendarView;
import org.bsc.client.main.DaylyReportView;
import org.bsc.client.main.MonthlyReportPlace;
import org.bsc.client.main.MonthlyReportView;
import org.bsc.client.ui.MonthlyHeaderView;
import org.bsc.shared.ActivityReport;
import org.bsc.shared.DaylyReport;
import org.bsc.shared.EntityFactory;
import org.bsc.shared.MonthlyTimeSheet;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;

/**
 * @author Daniel Kurka
 * 
 */
public class ClientFactoryImpl implements ClientFactory {

	private EventBus eventBus;
	private PlaceController placeController;
	
	public ClientFactoryImpl() {
		eventBus = new SimpleEventBus();

		placeController = new PlaceController(eventBus);

	}

	@Override
	public EventBus getEventBus() {
		return eventBus;
	}

	@Override
	public PlaceController getPlaceController() {
		return placeController;
	}

	private EntityFactory ef = GWT.create(EntityFactory.class);
	
	@Override
	public EntityFactory getEntityFactory() {
		return ef;
	}

	private com.google.gwt.storage.client.Storage ls = 
			com.google.gwt.storage.client.Storage.getLocalStorageIfSupported();


	@Override
	public com.google.gwt.storage.client.Storage getLocalStorage() {
		return ls;
	}

	
	@SuppressWarnings("deprecation")
	@Override
	public void loadTimesheet( MonthlyReportPlace place, AsyncCallback<MonthlyTimeSheet> callback ) {
		
		AutoBean<MonthlyTimeSheet> ts = null;
		
		MonthlyReportPlace.Tokenizer t = new MonthlyReportPlace.Tokenizer();

		final EntityFactory ef = getEntityFactory();
		
		final String storageKey = MonthlyTimeSheet.class.getName() + ":" + t.getToken(place);
		
		String payload = getLocalStorage().getItem( storageKey );
		
		if( payload==null ) {

			final java.util.List<String> activityNames = getDefaultActivityNames();
			final java.util.Date v = place.getDate();
			
			ts = ef.makeTimeSheet();
			
			ts.as().setDate( v );
			ts.as().setActivityNames( activityNames );
				
			java.util.List<DaylyReport> dataList = new java.util.ArrayList<DaylyReport>(31);
				
			final int currentMonth = v.getMonth();
			
			// yyyy-MM-dd'T'HH:mm:ss.SSSZZZ
			final DateTimeFormat dtf = DateTimeFormat.getFormat("MMMM dd");

			v.setDate(1);
			
			for( int date = 1; v.getMonth() == currentMonth; ++date ) {
				
				String format = dtf.format( v );
				
				v.setDate( date );
				
				DaylyReport dr = ef.makeDaylyReport().as();
				
				dr.setDay(new java.util.Date(v.getTime()));
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

		
		if( drv == null ) {
			drv = new DaylyReportView( ts.as().getActivityNames() );
			
		}
		callback.onSuccess( ts.as() );
	}

	public java.util.List<String> getDefaultActivityNames() {
		
		return Arrays.asList("FERIE","ROL", "VARIE");
	}

	private final CalendarView calendarView = new CalendarView();

	public CalendarView getCalendarView() {
		return calendarView;
	}

	final MonthlyReportView mrv = new MonthlyReportView();
	
	@Override
	public MonthlyReportView getMonthlyReportView() {
		return mrv;
	}

	MonthlyHeaderView mhv = new MonthlyHeaderView();
	
	@Override
	public MonthlyHeaderView getMonthlyHeaderView() {
		return mhv;
	}

	DaylyReportView drv ;
	
	@Override
	public DaylyReportView getDaylyReportView() {
		return drv;
	}

}
