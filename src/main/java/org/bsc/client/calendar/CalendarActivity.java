package org.bsc.client.calendar;

import org.bsc.client.ClientFactory;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;

public class CalendarActivity extends MGWTAbstractActivity {

	final ClientFactory factory;
	
	public CalendarActivity( ClientFactory factory ) {
		this.factory = factory;
	}

	@Override
	public String mayStop() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCancel() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub

	}

	@Override
	public void start(AcceptsOneWidget panel, com.google.web.bindery.event.shared.EventBus eventBus) {

		CalendarView view = factory.getCalendarView();
		
		view.setValue( new java.util.Date() );
		
		panel.setWidget(view);
		
		
	}

}
