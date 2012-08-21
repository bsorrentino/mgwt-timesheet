package org.bsc.client.ui;

import org.bsc.shared.DaylyReportImpl;

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

public class MonthlyHeaderView extends Composite implements HasValue<DaylyReportImpl> {

	private static CalendarViewUiBinder uiBinder = GWT.create(CalendarViewUiBinder.class);
	private DaylyReportImpl value;
	@UiField ParagraphElement lblDayOrdinal;
	@UiField ParagraphElement lblMonthName;
	@UiField ParagraphElement lblDayName;
	@UiField ParagraphElement lblHours;

	DateTimeFormat monthFormat = DateTimeFormat.getFormat("MMM");
	DateTimeFormat dayFormat = DateTimeFormat.getFormat("EEEE");
	
	String format = monthFormat.format( new java.util.Date() );

	interface CalendarViewUiBinder extends UiBinder<Widget, MonthlyHeaderView> {
	}

	public MonthlyHeaderView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<DaylyReportImpl> handler) {
		return null;
	}

	@Override
	public DaylyReportImpl getValue() {
		return value;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void setValue(DaylyReportImpl value) {
		if( value == null ) throw new IllegalArgumentException("value is null!");
		
		final java.util.Date day = value.getDate();
		
		int month = day.getMonth();

		lblDayOrdinal.setInnerText(String.valueOf(day.getDate()));
		lblMonthName.setInnerText( monthFormat.format(day));
		
		int day_of_week = day.getDay();
		
		if( day_of_week == 0 /* sunday*/ || day_of_week == 6 /*saturday*/	) {
			lblDayName.getStyle().setColor("red");
		}
		else {
			lblDayName.getStyle().setColor("black");			
		}
		lblDayName.setInnerText( dayFormat.format(day));
		
		lblHours.setInnerText( String.valueOf( value.getHours() ));
	}

	@Override
	public void setValue(DaylyReportImpl value, boolean fireEvents) {
		this.setValue(value);
		
	}




}
