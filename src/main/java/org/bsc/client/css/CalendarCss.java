package org.bsc.client.css;

import com.google.gwt.resources.client.CssResource;

/**
 * 
 * @author bsorrentino
 *
 */
public interface CalendarCss extends CssResource {

	String holder();
	String month();
	String day();
	
	@ClassName("blue")
	String month_blue();

	@ClassName("red")
	String month_red();

	@ClassName("green")
	String month_green();

	@ClassName("yellow")
	String month_yellow();
}
