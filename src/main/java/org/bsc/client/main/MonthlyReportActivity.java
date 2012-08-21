package org.bsc.client.main;

import org.bsc.client.ClientFactory;
import org.bsc.client.event.TimeUpdateEvent;
import org.bsc.shared.MonthlyTimeSheet;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;
import com.googlecode.mgwt.ui.client.widget.base.PullArrowStandardHandler;
import com.googlecode.mgwt.ui.client.widget.base.PullArrowStandardHandler.PullActionHandler;

public class MonthlyReportActivity extends MGWTAbstractActivity implements MonthlyReportView.Presenter {

	final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(getClass().getName());
	
	final ClientFactory clientFactory;
	final MonthlyReportPlace place;
	
	public MonthlyReportActivity(ClientFactory clientFactory, MonthlyReportPlace place) {
		super();
		this.clientFactory = clientFactory;
		this.place = place;
		
		
	}


	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {

		final MonthlyReportView view = clientFactory.getMonthlyReportView();
		
		view.setPresenter(this);
		
		initPullDown(view);
		
		panel.setWidget(view);

		MonthlyTimeSheet timeSheet = view.getValue();

		if( timeSheet == null ) {
			clientFactory.loadTimesheet(place, new AsyncCallback<MonthlyTimeSheet>() {
				
				@Override
				public void onSuccess(MonthlyTimeSheet result) {
					view.setValue(result);
					view.refresh();
					
				}
				
				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					
				}
			});
		}

			
	}

	Request currentRequest;
	
	private void initPullDown( final MonthlyReportView display ) {

		display.getPullHeader().setHTML("pull down");
		
		PullArrowStandardHandler headerHandler = 
				new PullArrowStandardHandler(display.getPullHeader(), display.getPullPanel()) {{

				setErrorText("Error");
				setLoadingText("Loading");
				setNormalText("pull down");
				setPulledText("release to load");
				setPullActionHandler(new PullActionHandler() {
		
					@Override
					public void onPullAction(final AsyncCallback<Void> callback) {
						try {
							currentRequest = clientFactory.getTimesheetServiceFactory().loadTimesheet(place, new AsyncCallback<MonthlyTimeSheet>() {

								@Override
								public void onFailure(Throwable caught) {									
									callback.onFailure(caught);
								}

								@Override
								public void onSuccess(MonthlyTimeSheet result) {
									
									display.setValue(result);
									display.refresh();
									callback.onSuccess(null);
								}
							
							});
						} catch (RequestException e) {
							callback.onFailure(e);
						}
						
					}
				});
		}};
		
		display.setHeaderPullHandler(headerHandler);
		
	}
	
	private void initPullUp( MonthlyReportView display ) {

		display.getPullFooter().setHTML("pull up");


		PullArrowStandardHandler footerHandler = new PullArrowStandardHandler(display.getPullFooter(), display.getPullPanel());

		footerHandler.setErrorText("Error");
		footerHandler.setLoadingText("Loading");
		footerHandler.setNormalText("pull up");
		footerHandler.setPulledText("release to load");
		footerHandler.setPullActionHandler(new PullActionHandler() {

			@Override
			public void onPullAction(final AsyncCallback<Void> callback) {
				callback.onSuccess(null);
			}
		});

		display.setFooterPullHandler(footerHandler);

	}

	@Override
	public void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);
	}


}
