package org.bsc.client.main;

import java.util.logging.Logger;

import org.bsc.client.event.TimeUpdateEvent;
import org.bsc.shared.ActivityReport;
import org.bsc.shared.DaylyReport;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.googlecode.mgwt.dom.client.event.tap.HasTapHandlers;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.ui.client.MGWT;
import com.googlecode.mgwt.ui.client.widget.FormListEntry;
import com.googlecode.mgwt.ui.client.widget.HeaderButton;
import com.googlecode.mgwt.ui.client.widget.HeaderPanel;
import com.googlecode.mgwt.ui.client.widget.LayoutPanel;
import com.googlecode.mgwt.ui.client.widget.MListBox;
import com.googlecode.mgwt.ui.client.widget.ScrollPanel;
import com.googlecode.mgwt.ui.client.widget.WidgetList;

public class DaylyReportView extends Composite implements HasValue<DaylyReportPlace>  {

	final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(getClass().getName());
	
	public interface Presenter {

		/**
		 * Navigate to a new Place in the browser.
		 */
		void goTo(Place place);
		
		void fireTimeUpdateEvent( DaylyReport report );
		
		ActivityReport makeActivityReport();
		
	}

	final HeaderPanel headerPanel = new HeaderPanel();
	final WidgetList widgetList = new WidgetList();
	final HeaderButton saveButton = new HeaderButton();
	
	public DaylyReportView( java.util.List<String> activityNameList ) {
		
		saveButton.setText("Save");
		
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
		headerPanel.setRightWidget(saveButton);
		
		container.add(headerPanel);

		widgetList.setRound(true);

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

	HasTapHandlers getSaveHandler() {
	
		return saveButton;
	}
	
	DaylyReportPlace value;
	
	@Override
	public DaylyReportPlace getValue() {
		return value;
	}

	@Override
	public void setValue(DaylyReportPlace value) {
		this.value = value;
		
		headerPanel.setCenter(  DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_MEDIUM).format(value.getDate()));

		int index = 0;
		widgetList.clear();
		
		DaylyReport report = value.getDaylyReport();
		
		for( String activityName : value.getActivityNames() ) {
			//MIntegerBox box = new MIntegerBox();
			MListBox box = createInputBox(index);
			
			if( report!=null ) {
				ActivityReport aa = report.getActivityList().get(index);
				
				if( aa != null ) {
								
					int h = aa.getHours();
					
					//box.setValue(  h );
					box.setItemSelected(h, true);
				}
				else {
					
					report.getActivityList().add( getPresenter().makeActivityReport() );	
				}
				++index;
			}
			
			widgetList.add(new FormListEntry(activityName, box));			 
		}

		
	}

	@Override
	public void setValue(DaylyReportPlace value, boolean fireEvents) {
		this.setValue(value);
	}
	
	@Override
	public HandlerRegistration addValueChangeHandler(
			ValueChangeHandler<DaylyReportPlace> handler) {
		return null;
	}


	MListBox createInputBox( final int ordinal ) {
		final MListBox mListBox = new MListBox();
		for( int i=0; i <10; ++i ) {

			mListBox.addChangeHandler( new ChangeHandler() {
				
				@Override
				public void onChange(ChangeEvent event) {
					int index = mListBox.getSelectedIndex();
					
					if( index >= 0 ) {
					
						getValue().getDaylyReport().getActivityList().get(ordinal).setHours( index );
					
						getPresenter().fireTimeUpdateEvent( getValue().getDaylyReport() );
					}
				}
			});
			mListBox.addItem(String.valueOf(i));
		}
		return mListBox;
	}

}
