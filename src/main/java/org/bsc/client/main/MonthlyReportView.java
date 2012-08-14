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
	
	
	@UiField GroupingCellList<java.util.Date, MonthlyReport> monthCellList;

	/**
	 * 
	 */
	public MonthlyReportView() {

		initWidget(uiBinder.createAndBindUi(this));
	}

	HeaderList<java.util.Date,MonthlyReport> createHeaderList() {
		
		monthCellList = createMonthCellList();
		
		return new HeaderList<java.util.Date,MonthlyReport>( monthCellList );
	}
	
	@UiFactory GroupingCellList<java.util.Date,MonthlyReport> createMonthCellList() {
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
	
		final Cell<MonthlyReport> activityCell = new Cell<MonthlyReport>() {

			@Override
			public void render(SafeHtmlBuilder safeHtmlBuilder, MonthlyReport model) {

				safeHtmlBuilder.appendHtmlConstant("<h2>TEST</h2>");
			}

			@Override
			public boolean canBeSelected(MonthlyReport model) {
				return true;
			}
			
		};
		
 		return new GroupingCellList<java.util.Date,MonthlyReport>( activityCell, headerCell/*, style.list()*/ );
	}
	
	@SuppressWarnings("deprecation")
	public void renderValue() {
		
		java.util.List<CellGroup<java.util.Date, MonthlyReport>> dataList = new java.util.ArrayList<CellGroup<java.util.Date, MonthlyReport>>(31);
		
		java.util.List<MonthlyReport> activityList = new java.util.ArrayList<MonthlyReport>(10);
		activityList.add( new MonthlyReport() );
		activityList.add( new MonthlyReport() );
		
		
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
					new StandardCellGroup<java.util.Date,MonthlyReport>( String.valueOf(v.getTime()), new java.util.Date(v.getTime()), activityList ) 
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
