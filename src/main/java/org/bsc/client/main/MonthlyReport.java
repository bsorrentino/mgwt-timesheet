package org.bsc.client.main;

public class MonthlyReport {

	private int hours;
	private String day;
	
	
	public MonthlyReport() {
		this( 0, "");
	}

	public MonthlyReport(int hours, String day) {
		super();
		this.hours = hours;
		this.day = day;
	}
	
	public int getHours() {
		return hours;
	}
	public void setHours(int hours) {
		this.hours = hours;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	
}
