package org.bsc.shared;

import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;

public interface EntityFactory extends AutoBeanFactory {

	AutoBean<MonthlyTimeSheet> makeTimeSheet();
	
	AutoBean<DaylyReport> makeDaylyReport();

	AutoBean<ActivityReport> makeActivityReport();

	
}
