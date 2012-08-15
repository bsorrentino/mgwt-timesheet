package org.bsc.client.main;

import org.bsc.client.ClientFactory;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;

public class DaylyReportActivity extends MGWTAbstractActivity implements DaylyReportView.Presenter {

	final ClientFactory clientFactory;
	final DaylyReportPlace place;
	
	
	public DaylyReportActivity(ClientFactory clientFactory, DaylyReportPlace place ) {
		super();
		this.clientFactory = clientFactory;
		this.place = place;
	}



	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		
		DaylyReportView view = clientFactory.getDaylyReportView();
		
		view.setPresenter(this);
		view.setValue( place.getDate() );
		
		panel.setWidget(view);
	}



	@Override
	public void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);
	}

}
