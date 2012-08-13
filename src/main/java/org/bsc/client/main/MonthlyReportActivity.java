package org.bsc.client.main;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;
import org.bsc.client.ClientFactory;

public class MonthlyReportActivity extends MGWTAbstractActivity {

	final ClientFactory clientFactory;
	
	
	public MonthlyReportActivity(ClientFactory clientFactory) {
		super();
		this.clientFactory = clientFactory;
	}


	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {

		MonthlyReportView view = clientFactory.getMonthlyReportView();
		
		panel.setWidget(view);
		
		view.setValue( new java.util.Date() );
		
		view.renderValue();
		
	}


}
