package com.ejdoab.tcms.admin;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;

/**
 * @author Brian Sam-Bodden
 */
public class Splash extends Composite {

	ProgressBar _bar;
	Label _label;
	
	int _increment;
	
	/**
	 * @param arg0
	 * @param arg1
	 */
	public Splash(Composite parent, int style) {
		super(parent, style);
		
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		
		setLayout(layout);
	
		_label = new Label(this, SWT.NONE);
		_label.setImage(Resources.getImageRegistry().get("image_splash"));
		
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;

		_bar = new ProgressBar(this, SWT.NONE);
		_bar.setLayoutData(gridData);
		
		this.pack();		
	}
	
	public void configureProgressBar(int maximum, int increment) {
		_increment = increment;
		_bar.setMaximum(maximum);
	}
	
	public void increment() {
		_bar.setSelection(_bar.getSelection() + _increment);
	}

}
