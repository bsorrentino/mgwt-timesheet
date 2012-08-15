package org.bsc.client.main;

import org.bsc.client.ClientFactory;
import org.bsc.shared.MonthlyTimeSheet;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;

public class MonthlyReportActivity extends MGWTAbstractActivity implements MonthlyReportView.Presenter {

	final ClientFactory clientFactory;
	final MonthlyReportPlace place;
	MonthlyTimeSheet timeSheet;
	
	public MonthlyReportActivity(ClientFactory clientFactory, MonthlyReportPlace place) {
		super();
		this.clientFactory = clientFactory;
		this.place = place;
		
		
	}


	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {

		final MonthlyReportView view = clientFactory.getMonthlyReportView();
		
		view.setPresenter(this);
		
		panel.setWidget(view);

		if( timeSheet == null ) {
			clientFactory.loadTimesheet(place, new AsyncCallback<MonthlyTimeSheet>() {
				
				@Override
				public void onSuccess(MonthlyTimeSheet result) {
					timeSheet = result;
					view.setValue(result);
					view.renderValue();
					
				}
				
				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					
				}
			});
		}

			
	}


	@Override
	public void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);
	}


}
