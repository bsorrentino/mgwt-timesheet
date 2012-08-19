package org.bsc.client.main;

import java.util.List;

import org.bsc.client.pulltorefresh.PullToRefreshDisplay;
import org.bsc.client.ui.MonthlyHeaderView;
import org.bsc.shared.DaylyReport;
import org.bsc.shared.DaylyReportImpl;
import org.bsc.shared.EntityFactory;
import org.bsc.shared.MonthlyTimeSheet;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
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
import com.googlecode.mgwt.ui.client.widget.CellList;
import com.googlecode.mgwt.ui.client.widget.LayoutPanel;
import com.googlecode.mgwt.ui.client.widget.base.HasRefresh;
import com.googlecode.mgwt.ui.client.widget.base.PullArrowFooter;
import com.googlecode.mgwt.ui.client.widget.base.PullArrowHeader;
import com.googlecode.mgwt.ui.client.widget.base.PullArrowWidget;
import com.googlecode.mgwt.ui.client.widget.base.PullPanel;
import com.googlecode.mgwt.ui.client.widget.base.PullPanel.Pullhandler;
import com.googlecode.mgwt.ui.client.widget.celllist.Cell;
import com.googlecode.mgwt.ui.client.widget.celllist.CellSelectedEvent;

public class MonthlyReportView extends Composite implements PullToRefreshDisplay<DaylyReport>, HasValue<MonthlyTimeSheet> {

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
	@UiField PullPanel pullPanel;

	private PullArrowHeader pullArrowHeader = new PullArrowHeader();;
	private PullArrowFooter pullArrowFooter = new PullArrowFooter();

	final EntityFactory ef = GWT.create(EntityFactory.class);
	
	/**
	 * 
	 */
	public MonthlyReportView() {

		initWidget(uiBinder.createAndBindUi(this));
		
		pullPanel.setHeader(pullArrowHeader);
		pullPanel.setHeader(pullArrowFooter);
	}

	@UiFactory CellList<DaylyReport> createMonthCellList() {
		final MonthlyHeaderView header = new MonthlyHeaderView();
		
		final Cell<DaylyReport> headerCell = new Cell<DaylyReport>() {

			@Override
			public void render(SafeHtmlBuilder safeHtmlBuilder, DaylyReport model) {
				
				header.setValue( new DaylyReportImpl(model, getValue().getDate()) );
				
				safeHtmlBuilder.appendHtmlConstant( header.getElement().getInnerHTML() );
				
			}

			@Override
			public boolean canBeSelected(DaylyReport model) {
				return true;
			}
			
		};
	
 		return new CellList<DaylyReport>( headerCell/*, style.list()*/ );
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
		
		d.setDate(++index);
		
		presenter.goTo( new DaylyReportPlace(d) );
		
	}

	@Override
	public void render(List<DaylyReport> topics) {
		this.monthCellList.render(topics);
		
	}

	@Override
	public void setHeaderPullHandler(Pullhandler pullHandler) {
		pullPanel.setHeaderPullhandler(pullHandler);
	}

	@Override
	public void setFooterPullHandler(Pullhandler pullHandler) {
		pullPanel.setFooterPullHandler(pullHandler);
	}

	@Override
	public PullArrowWidget getPullHeader() {
		return pullArrowHeader;
	}

	@Override
	public PullArrowWidget getPullFooter() {
		return pullArrowFooter;
	}

	@Override
	public void refresh() {
		render(value.getDayList());		
	}

	@Override
	public HasRefresh getPullPanel() {
		return pullPanel;
	}
	
	
	
}
