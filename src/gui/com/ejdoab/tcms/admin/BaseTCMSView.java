package com.ejdoab.tcms.admin;

import org.eclipse.swt.widgets.Composite;

/**
 * @author Brian Sam-Bodden
 */
public class BaseTCMSView extends Composite implements TCMSView {

	/**
	 * @param arg0
	 * @param arg1
	 */
	public BaseTCMSView(Composite parent, int style) {
		super(parent, style);
	}

	/* (non-Javadoc)
	 * @see com.ejdoab.tcms.admin.TCMSView#loadData(java.lang.Object[])
	 */
	public void loadData(Object[] items) {		
		// do nothing
	}

	/* (non-Javadoc)
	 * @see com.ejdoab.tcms.admin.TCMSView#getViewName()
	 */
	public String getViewName() {		
		return this.getClass().getName();
	}

}
