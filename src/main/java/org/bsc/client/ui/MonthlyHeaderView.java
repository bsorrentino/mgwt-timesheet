package org.bsc.client.ui;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.ParagraphElement;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;

public class MonthlyHeaderView extends Composite implements HasValue<java.util.Date> {

	private static CalendarViewUiBinder uiBinder = GWT.create(CalendarViewUiBinder.class);
	private java.util.Date value;
	@UiField ParagraphElement lblDayOrdinal;
	@UiField ParagraphElement lblMonthName;
	@UiField ParagraphElement lblDayName;

	DateTimeFormat monthFormat = DateTimeFormat.getFormat("MMM");
	DateTimeFormat dayFormat = DateTimeFormat.getFormat("EEEE");
	
	String format = monthFormat.format( new java.util.Date() );

	interface CalendarViewUiBinder extends UiBinder<Widget, MonthlyHeaderView> {
	}

	public MonthlyHeaderView() {
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
		if( value == null ) throw new IllegalArgumentException("value is null!");
		
		lblDayOrdinal.setInnerText(String.valueOf(value.getDate()));
		lblMonthName.setInnerText( monthFormat.format(value));
		
		int day_of_week = value.getDay();
		
		if( day_of_week == 0 /* sunday*/ || day_of_week == 6 /*saturday*/	) {
			lblDayName.getStyle().setColor("red");
		}
		else {
			lblDayName.getStyle().setColor("black");			
		}
		lblDayName.setInnerText( dayFormat.format(value));
		
	}

	@Override
	public void setValue(Date value, boolean fireEvents) {
		this.setValue(value);
		
	}




}
