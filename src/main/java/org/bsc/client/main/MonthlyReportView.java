package org.bsc.client.main;

import java.util.Date;

import org.bsc.client.ui.MonthlyHeaderView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.ui.client.widget.GroupingCellList;
import com.googlecode.mgwt.ui.client.widget.GroupingCellList.CellGroup;
import com.googlecode.mgwt.ui.client.widget.GroupingCellList.StandardCellGroup;
import com.googlecode.mgwt.ui.client.widget.HeaderList;
import com.googlecode.mgwt.ui.client.widget.LayoutPanel;
import com.googlecode.mgwt.ui.client.widget.celllist.Cell;

public class MonthlyReportView extends Composite implements HasValue<java.util.Date> {

	public interface Resources extends ClientBundle {
		
	}
	
	@UiField Resources style;

	private static MonthViewUiBinder uiBinder = GWT
			.create(MonthViewUiBinder.class);

	interface MonthViewUiBinder extends UiBinder<Widget, MonthlyReportView> {
	}

	@UiField LayoutPanel layoutPanel;
	
	
	@UiField GroupingCellList<java.util.Date, DaylyActivity> monthCellList;

	/**
	 * 
	 */
	public MonthlyReportView() {

		initWidget(uiBinder.createAndBindUi(this));
	}

	HeaderList<java.util.Date,DaylyActivity> createHeaderList() {
		
		monthCellList = createMonthCellList();
		
		return new HeaderList<java.util.Date,DaylyActivity>( monthCellList );
	}
	
	@UiFactory GroupingCellList<java.util.Date,DaylyActivity> createMonthCellList() {
		final MonthlyHeaderView header = new MonthlyHeaderView();
		
		final Cell<java.util.Date> headerCell = new Cell<java.util.Date>() {

			@Override
			public void render(SafeHtmlBuilder safeHtmlBuilder, Date model) {
				
				header.setValue(model);
				
				safeHtmlBuilder.appendHtmlConstant( header.getElement().getInnerHTML() );
				
			}

			@Override
			public boolean canBeSelected(Date model) {
				return false;
			}
			
		};
	
		final Cell<DaylyActivity> activityCell = new Cell<DaylyActivity>() {

			@Override
			public void render(SafeHtmlBuilder safeHtmlBuilder, DaylyActivity model) {

				safeHtmlBuilder.appendHtmlConstant("<h2>TEST</h2>");
			}

			@Override
			public boolean canBeSelected(DaylyActivity model) {
				return true;
			}
			
		};
		
 		return new GroupingCellList<java.util.Date,DaylyActivity>( activityCell, headerCell/*, style.list()*/ );
	}
	
	public void renderValue() {
		
		java.util.List<CellGroup<java.util.Date, DaylyActivity>> dataList = new java.util.ArrayList<CellGroup<java.util.Date, DaylyActivity>>(31);
		
		java.util.List<DaylyActivity> activityList = new java.util.ArrayList<DaylyActivity>(10);
		activityList.add( new DaylyActivity() );
		activityList.add( new DaylyActivity() );
		
		
		java.util.Date v = getValue();
		
		final int currentMonth = v.getMonth();
		
		// yyyy-MM-dd'T'HH:mm:ss.SSSZZZ
		final DateTimeFormat dtf = DateTimeFormat.getFormat("MMMM dd");

		v.setDate(1);
		
		for( int date = 1; v.getMonth() == currentMonth; ++date ) {
			
			String format = dtf.format( v );
			
			System.out.println(format);
			
			v.setDate( date );
			
			dataList.add( 
					new StandardCellGroup<java.util.Date,DaylyActivity>( String.valueOf(v.getTime()), new java.util.Date(v.getTime()), activityList ) 
					);
		}
		
		this.monthCellList.renderGroup(dataList);
	}
	
	private java.util.Date value;
	
	@Override
	public HandlerRegistration addValueChangeHandler(
			ValueChangeHandler<Date> handler) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Date getValue() {
		return value;
	}

	@Override
	public void setValue(Date value) {
		this.value = value;
	}

	@Override
	public void setValue(Date value, boolean fireEvents) {
		setValue( value );
		
	}

}
