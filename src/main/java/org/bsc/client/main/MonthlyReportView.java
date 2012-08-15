package org.bsc.client.main;

import java.util.Date;

import org.bsc.client.ui.MonthlyHeaderView;
import org.bsc.shared.EntityFactory;
import org.bsc.shared.DaylyReport;
import org.bsc.shared.MonthlyTimeSheet;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.place.shared.Place;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.googlecode.mgwt.ui.client.widget.CellList;
import com.googlecode.mgwt.ui.client.widget.LayoutPanel;
import com.googlecode.mgwt.ui.client.widget.celllist.Cell;
import com.googlecode.mgwt.ui.client.widget.celllist.CellSelectedEvent;

public class MonthlyReportView extends Composite implements HasValue<MonthlyTimeSheet> {

	public interface Presenter {
	
		void goTo( Place place );
		
	}
	
	public interface Resources extends ClientBundle {
		
	}
	
	@UiField Resources style;

	private static MonthViewUiBinder uiBinder = GWT
			.create(MonthViewUiBinder.class);

	interface MonthViewUiBinder extends UiBinder<Widget, MonthlyReportView> {
	}

	@UiField LayoutPanel layoutPanel;
	
	
	@UiField CellList<DaylyReport> monthCellList;

	final EntityFactory ef = GWT.create(EntityFactory.class);
	
	/**
	 * 
	 */
	public MonthlyReportView() {

		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiFactory CellList<DaylyReport> createMonthCellList() {
		final MonthlyHeaderView header = new MonthlyHeaderView();
		
		final Cell<DaylyReport> headerCell = new Cell<DaylyReport>() {

			@Override
			public void render(SafeHtmlBuilder safeHtmlBuilder, DaylyReport model) {
				
				header.setValue(model.getDay());
				
				safeHtmlBuilder.appendHtmlConstant( header.getElement().getInnerHTML() );
				
			}

			@Override
			public boolean canBeSelected(DaylyReport model) {
				return true;
			}
			
		};
	
 		return new CellList<DaylyReport>( headerCell/*, style.list()*/ );
	}
	
	public void renderValue() {
			
		this.monthCellList.render(value.getDayList());
	}
	
	private MonthlyTimeSheet value;
	private Presenter presenter;
	
	public Presenter getPresenter() {
		return presenter;
	}

	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<MonthlyTimeSheet> handler) {
		return null;
	}
	
	@Override
	public MonthlyTimeSheet getValue() {
		return value;
	}

	@Override
	public void setValue(MonthlyTimeSheet value) {
		this.value = value;
	}

	@Override
	public void setValue(MonthlyTimeSheet value, boolean fireEvents) {
		setValue( value );
		
	}

	@SuppressWarnings("deprecation")
	@UiHandler("monthCellList")
	void onMonthDaySelected(CellSelectedEvent event) {
		
		int index = event.getIndex();
		
		java.util.Date d = new java.util.Date(value.getDate().getTime());
		
		d.setDate(index);
		
		presenter.goTo( new DaylyReportPlace(d) );
		
	}
}
