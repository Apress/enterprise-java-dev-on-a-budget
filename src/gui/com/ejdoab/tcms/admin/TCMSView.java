package com.ejdoab.tcms.admin;

/**
 * @author Brian Sam-Bodden 
 */
public interface TCMSView {
	void loadData(Object[] items);
	String getViewName();
}
