#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.calendar;

import ${package}.ClientFactory;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
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
	public void start(AcceptsOneWidget panel, EventBus eventBus) {

		CalendarView view = factory.getCalendarView();
		
		view.setValue( new java.util.Date() );
		
		panel.setWidget(view);
		
		
	}

}
