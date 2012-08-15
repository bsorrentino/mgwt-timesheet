package org.bsc.shared;

public interface MonthlyTimeSheet {

	public java.util.List<String> getActivityNames();
	public void setActivityNames( java.util.List<String> names );
	
	
	public java.util.Date getDate() ;

	public void setDate(java.util.Date date) ;

	public java.util.List<DaylyReport> getDayList() ;

	public void setDayList(java.util.List<DaylyReport> dayList) ;
	
}
