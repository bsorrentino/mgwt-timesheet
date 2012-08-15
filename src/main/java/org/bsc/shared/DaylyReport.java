package org.bsc.shared;

public interface DaylyReport {

	
	public int getHours() ;
	public void setHours(int hours) ;
	public java.util.Date getDay() ;
	public void setDay(java.util.Date day) ;

	public java.util.List<ActivityReport> getActivityList() ;

	public void setActivityList(java.util.List<ActivityReport> activityList) ;
	
}
