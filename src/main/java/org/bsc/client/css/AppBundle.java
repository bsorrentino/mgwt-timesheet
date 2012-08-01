package org.bsc.client.css;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;

public interface AppBundle extends ClientBundle {
	//This is a very nasty workaround because GWT CssResource does not support @media correctly!
	@Source("app.css")
	TextResource css();

	@Source("calendar.css")
	CalendarCss calendarIcon();
	
	public static final AppBundle INSTANCE = GWT.create(AppBundle.class);

}
