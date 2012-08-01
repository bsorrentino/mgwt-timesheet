package org.bsc.client.calendar;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class CalendarView extends Composite implements HasValue<java.util.Date> {

	private static CalendarViewUiBinder uiBinder = GWT.create(CalendarViewUiBinder.class);
	private java.util.Date value;
	@UiField Label lblDayOrdinal;
	@UiField Label lblMonthName;

	DateTimeFormat dtf = DateTimeFormat.getFormat("MMMM");
	
	String format = dtf.format( new java.util.Date() );

	interface CalendarViewUiBinder extends UiBinder<Widget, CalendarView> {
	}

	public CalendarView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Date> handler) {
		return null;
	}

	@Override
	public Date getValue() {
		return value;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void setValue(Date value) {
		
		lblDayOrdinal.setText( String.valueOf(value.getDate()) );
		lblMonthName.setText( dtf.format(value));
		
	}

	@Override
	public void setValue(Date value, boolean fireEvents) {
		
		
	}




}
