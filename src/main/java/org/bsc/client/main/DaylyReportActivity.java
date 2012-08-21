package org.bsc.client.main;

import org.bsc.client.ClientFactory;
import org.bsc.client.event.TimeUpdateEvent;
import org.bsc.shared.ActivityReport;
import org.bsc.shared.DaylyReport;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;

public class DaylyReportActivity extends MGWTAbstractActivity implements DaylyReportView.Presenter {


	@Override
	public void onStop() {
		super.onStop();
	}



	@Override
	public String mayStop() {
		return ( currentRequest!=null ) ? "A request is still in progress!" : null;
	}



	@Override
	public void onCancel() {
		if( currentRequest!=null) {
			currentRequest.cancel();
			currentRequest = null;
		}
		super.onCancel();
	}


	final ClientFactory clientFactory;
	final DaylyReportPlace place;
	
	
	public DaylyReportActivity(ClientFactory clientFactory, DaylyReportPlace place ) {
		super();
		this.clientFactory = clientFactory;
		this.place = place;
	}


	Request currentRequest;

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		
		DaylyReportView view = clientFactory.getDaylyReportView();
		
		view.setPresenter(this);
		view.setValue( place );
		
		panel.setWidget(view);
		
		super.addHandlerRegistration( view.getSaveHandler().addTapHandler(new TapHandler() {
			
			@Override
			public void onTap(TapEvent event) {
			
				try {
					currentRequest = clientFactory.getTimesheetServiceFactory().saveDaylyActivities(place, new AsyncCallback<Void>() {
						
						@Override
						public void onSuccess(Void result) {
							currentRequest = null;
							Window.alert("saving completed!" );
							
						}
						
						@Override
						public void onFailure(Throwable caught) {
							Window.alert("error saving activities\n" + caught.getMessage());
							currentRequest = null;
						}
					});
				} catch (RequestException e) {
					Window.alert("communication error saving activities\n" + e.getMessage());
					currentRequest = null;
				}
			}
		}));
	}



	@Override
	public void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);
	}

 	@Override
	public void fireTimeUpdateEvent(DaylyReport report) {
		
 		TimeUpdateEvent.fire( clientFactory.getEventBus(), report);
 		
	}



	@Override
	public ActivityReport makeActivityReport() {
		return clientFactory.getEntityFactory().makeActivityReport().as();
	}


	
	
}
