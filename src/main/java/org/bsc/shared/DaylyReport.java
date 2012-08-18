package org.bsc.shared;

public interface DaylyReport {

	
	public int getHours() ;
	public void setHours(int hours) ;
	public int getDay() ;
	public void setDay(int day) ;

	public java.util.List<ActivityReport> getActivityList() ;

	public void setActivityList(java.util.List<ActivityReport> activityList) ;
	
}
