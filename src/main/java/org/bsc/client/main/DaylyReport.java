package org.bsc.client.main;

public class DaylyReport {
	private int hours;
	private String comment;
	
	
	public DaylyReport() {
		this( 0, "");
	}

	public DaylyReport(int hours, String comment) {
		super();
		this.hours = hours;
		this.comment = comment;
	}
	
	public int getHours() {
		return hours;
	}
	public void setHours(int hours) {
		this.hours = hours;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}

}
