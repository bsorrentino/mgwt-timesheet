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

import org.bsc.client.calendar.CalendarView;
import org.bsc.client.main.DaylyReportView;
import org.bsc.client.main.MonthlyReportPlace;
import org.bsc.client.main.MonthlyReportView;
import org.bsc.client.main.TimesheetServiceFactory;
import org.bsc.client.ui.MonthlyHeaderView;
import org.bsc.shared.EntityFactory;
import org.bsc.shared.MonthlyTimeSheet;

import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.web.bindery.event.shared.EventBus;


public interface ClientFactory {

	public EventBus getEventBus();

	public PlaceController getPlaceController();
	
	public EntityFactory getEntityFactory();

	public com.google.gwt.storage.client.Storage getLocalStorage();
	
	public MonthlyReportView getMonthlyReportView();

	public MonthlyHeaderView getMonthlyHeaderView();
	
	public CalendarView getCalendarView();

	public DaylyReportView getDaylyReportView( );

	TimesheetServiceFactory getTimesheetServiceFactory();
	
	public void loadTimesheet( MonthlyReportPlace place, AsyncCallback<MonthlyTimeSheet> callback );
	
}
 
