package org.bsc.client.main;

import java.util.List;

import org.bsc.client.event.TimeUpdateEvent;
import org.bsc.client.pulltorefresh.PullToRefreshDisplay;
import org.bsc.client.ui.MonthlyHeaderView;
import org.bsc.shared.ActivityReport;
import org.bsc.shared.DaylyReport;
import org.bsc.shared.DaylyReportImpl;
import org.bsc.shared.MonthlyTimeSheet;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.ParagraphElement;
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
	final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(getClass().getName());

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
	@UiField(provided=true) PullPanel pullPanel;

	private PullArrowHeader pullArrowHeader = new PullArrowHeader();;
	private PullArrowFooter pullArrowFooter = new PullArrowFooter();

	/**
	 * 
	 */
	public MonthlyReportView() {

		pullPanel = new PullPanel();
		pullPanel.setHeader(pullArrowHeader);
		pullPanel.setFooter(pullArrowFooter);

		initWidget(uiBinder.createAndBindUi(this));
		
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
	
	public void updateDaylyReport( DaylyReport report ) {
	    Node node = monthCellList.getElement().getChild( report.getDay()-1 );
	    Element li = Element.as(node);

	    ParagraphElement p = li.getChild(4).cast();
	    
	    int totHours = 0;
	    
	    for( ActivityReport a : report.getActivityList() ) totHours += a.getHours();
	    p.setInnerText(String.valueOf(totHours));
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

	@UiHandler("monthCellList")
	void onMonthDaySelected(CellSelectedEvent event) {
		
		int index = event.getIndex();
		
		
		presenter.goTo( new DaylyReportPlace(value,index) );
		
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
		pullPanel.refresh();
		render(value.getDayList());		
	}

	@Override
	public HasRefresh getPullPanel() {
		return pullPanel;
	}
	
	
	
}
