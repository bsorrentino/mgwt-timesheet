package org.bsc.shared;

import java.util.Date;
import java.util.List;

public class DaylyReportImpl implements DaylyReport {

	final DaylyReport delegate;
	final java.util.Date date;
	
	@SuppressWarnings("deprecation")
	public DaylyReportImpl(DaylyReport delegate, Date date) {
		if( delegate == null ) throw new IllegalArgumentException("delegate is null!");
		if( date == null ) throw new IllegalArgumentException("date is null!");
		
		this.delegate = delegate;
		this.date = new java.util.Date( date.getTime() );
		this.date.setDate(delegate.getDay());
	}	
	
	public java.util.Date getDate() {
		return date;
	}

	public int getHours() {
		return delegate.getHours();
	}

	public void setHours(int hours) {
		delegate.setHours(hours);
	}

	public int getDay() {
		return delegate.getDay();
	}

	public void setDay(int day) {
		delegate.setDay(day);
	}

	public List<ActivityReport> getActivityList() {
		return delegate.getActivityList();
	}

	public void setActivityList(List<ActivityReport> activityList) {
		delegate.setActivityList(activityList);
	}

}
