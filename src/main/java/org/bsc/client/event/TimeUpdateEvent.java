package org.bsc.client.event;

import org.bsc.shared.DaylyReport;

import com.google.web.bindery.event.shared.Event;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class TimeUpdateEvent extends Event<TimeUpdateEvent.Handler> {

	public static interface Handler {
		
		void onTimeUpdate( TimeUpdateEvent event );
	}
	
	private static final Type<TimeUpdateEvent.Handler> TYPE = new Type<TimeUpdateEvent.Handler>();

	
	private final DaylyReport report;
	
	protected TimeUpdateEvent(DaylyReport report) {
		this.report = report;
	}

	@Override
	public com.google.web.bindery.event.shared.Event.Type<Handler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(Handler handler) {
		handler.onTimeUpdate(this);
		
	}

	public DaylyReport getReport() {
		return report;
	}

	
	public static void fire(EventBus eventBus, DaylyReport report) {
		eventBus.fireEvent(new TimeUpdateEvent(report));
	}

	public static HandlerRegistration register(EventBus eventBus, Handler handler) {
		return eventBus.addHandler(TYPE, handler);
	}
	
}
