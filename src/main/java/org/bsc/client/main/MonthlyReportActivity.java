package org.bsc.client.main;

import org.bsc.client.ClientFactory;
import org.bsc.shared.MonthlyTimeSheet;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;
import com.googlecode.mgwt.ui.client.widget.base.PullArrowStandardHandler;
import com.googlecode.mgwt.ui.client.widget.base.PullArrowStandardHandler.PullActionHandler;

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
		
		initPullToRefresh(view);
		
		panel.setWidget(view);

		if( timeSheet == null ) {
			clientFactory.loadTimesheet(place, new AsyncCallback<MonthlyTimeSheet>() {
				
				@Override
				public void onSuccess(MonthlyTimeSheet result) {
					timeSheet = result;
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

	private void initPullToRefresh( MonthlyReportView display ) {
		display.getPullHeader().setHTML("pull down");

		display.getPullFooter().setHTML("pull up");

		PullArrowStandardHandler headerHandler = new PullArrowStandardHandler(display.getPullHeader(), display.getPullPanel());

		headerHandler.setErrorText("Error");
		headerHandler.setLoadingText("Loading");
		headerHandler.setNormalText("pull down");
		headerHandler.setPulledText("release to load");
		headerHandler.setPullActionHandler(new PullActionHandler() {

			@Override
			public void onPullAction(final AsyncCallback<Void> callback) {
				
				callback.onSuccess(null);
			}
		});

		display.setHeaderPullHandler(headerHandler);

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
