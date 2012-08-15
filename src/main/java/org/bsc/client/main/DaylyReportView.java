package org.bsc.client.main;

import java.util.Date;

import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.ui.client.MGWT;
import com.googlecode.mgwt.ui.client.widget.FormListEntry;
import com.googlecode.mgwt.ui.client.widget.HeaderButton;
import com.googlecode.mgwt.ui.client.widget.HeaderPanel;
import com.googlecode.mgwt.ui.client.widget.LayoutPanel;
import com.googlecode.mgwt.ui.client.widget.MIntegerBox;
import com.googlecode.mgwt.ui.client.widget.ScrollPanel;
import com.googlecode.mgwt.ui.client.widget.WidgetList;

public class DaylyReportView extends Composite implements HasValue<java.util.Date>  {

	public interface Presenter {

		/**
		 * Navigate to a new Place in the browser.
		 */
		void goTo(Place place);
	}

	final HeaderPanel headerPanel = new HeaderPanel();
	
	public DaylyReportView( java.util.List<String> activityNameList ) {
		LayoutPanel container = new LayoutPanel();

		HeaderButton backButton = new HeaderButton();
		backButton.setBackButton(true);
		backButton.setText("Timesheet");
		backButton.addTapHandler(new TapHandler() {
			
			@Override
			public void onTap(TapEvent event) {
				//History.back();
				presenter.goTo( new MonthlyReportPlace() );
				
			}
		});
		headerPanel.setLeftWidget(backButton);
		
		container.add(headerPanel);

		WidgetList widgetList = new WidgetList();
		widgetList.setRound(true);

		for( String activityName : activityNameList ) {
			widgetList.add(new FormListEntry(activityName, new MIntegerBox()));			 
		}


		ScrollPanel scrollPanel = new ScrollPanel();
		scrollPanel.setScrollingEnabledX(false);
		scrollPanel.setWidget(widgetList);
		// workaround for android formfields jumping around when using
		// -webkit-transform
		scrollPanel.setUsePos(MGWT.getOsDetection().isAndroid());

		container.add(scrollPanel);
		super.initWidget(container);
	}

	private Presenter presenter;

	public Presenter getPresenter() {
		return presenter;
	}

	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	java.util.Date value;
	
	@Override
	public Date getValue() {
		return value;
	}

	@Override
	public void setValue(Date value) {
		this.value = value;
		headerPanel.setCenter(  DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_MEDIUM).format(value));
		
	}

	@Override
	public void setValue(Date value, boolean fireEvents) {
		this.setValue(value);
	}
	
	@Override
	public HandlerRegistration addValueChangeHandler(
			ValueChangeHandler<Date> handler) {
		return null;
	}

	


}
