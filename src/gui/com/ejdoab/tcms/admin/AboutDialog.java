package com.ejdoab.tcms.admin;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;

import com.ejdoab.swt.util.SWTUtil;

/**
 * @author Brian Sam-Bodden
 */
public class AboutDialog extends TitleAreaDialog {

	TabFolder _tabs;
	Composite _toolPage;
	Composite _systemPage;
	StyledText _text;
	TableViewer _tableViewer;
	
	/**
	 * @param arg0
	 */
	protected AboutDialog(Shell shell) {
		super(shell);	
	}
	
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText("TCMS Admin");
	}	
	
	protected Control createContents(Composite parent) {
		Control contents = super.createContents(parent);
		
		setTitle("About TCMS Admin");
		setMessage("Information about the tool's version and the system's state");
		
		return contents;		
	}
	
	protected Control createDialogArea(Composite parent) {
		Composite outer = (Composite) super.createDialogArea(parent);
		Composite c = new Composite(outer, SWT.NONE);
		c.setLayoutData(new GridData(GridData.FILL_BOTH)); // API Failure, how does the user know that is it a grid?
		
		FormLayout formLayout = new FormLayout();
		formLayout.marginHeight = 10;
		formLayout.marginWidth = 10;		
		c.setLayout(formLayout);
		
		FormData formData = new FormData();
		formData.top = new FormAttachment(0,0);
		formData.bottom = new FormAttachment(100,0);
		formData.left = new FormAttachment(0,0);
		formData.right = new FormAttachment(100,0);	
			
		_tabs = new TabFolder(c, SWT.FLAT);
		_tabs.setLayoutData(formData);
		
		TabItem toolTab = new TabItem (_tabs, SWT.NULL);
		toolTab.setText("Tool");
		Composite toolPage = buildToolPage(_tabs);								
		toolTab.setControl(toolPage);
			
		TabItem systemTab = new TabItem(_tabs, SWT.NULL);
		systemTab.setText("System");
		Composite systemPage = buildSystemPage(_tabs);
		systemTab.setControl(systemPage);		
			
		return c;		
	}
	
	protected void createButtonsForButtonBar(Composite parent) {
		// create Close button
		createButton(
			parent,
			IDialogConstants.CLOSE_ID,
			IDialogConstants.CLOSE_LABEL,
			true);	
	}	
	
	protected void buttonPressed(int buttonId) {
		super.buttonPressed(buttonId);		
		if (IDialogConstants.CLOSE_ID == buttonId) {
			okPressed();
		}
	}	

	/**
	 * @param c
	 * @return
	 */
	private Composite buildToolPage(Composite c) {
		Composite page = new Composite(c, SWT.NONE);
		
		FormLayout formLayout = new FormLayout();
		formLayout.marginHeight = 20;
		formLayout.marginWidth = 20;		
		page.setLayout(formLayout);		
				
		_text =	new StyledText(page, SWT.MULTI | SWT.READ_ONLY);
		_text.setBackground(page.getBackground());
		_text.setCaret(null);
		_text.setFont(c.getFont());		
		_text.setCursor(null);
		
		FormData formData = new FormData();
		formData.top = new FormAttachment(0,0);
		formData.bottom = new FormAttachment(100,0);
		formData.left = new FormAttachment(0,0);
		formData.right = new FormAttachment(100,0);
		
		_text.setLayoutData(formData);			
		
		String LINE_SEP = _text.getLineDelimiter();
		String title = "TCMS Admin 1.0";
		StringBuffer sb = new StringBuffer();
		sb.append(title)
		  .append(LINE_SEP)
		  .append("Enterprise Java Development on a Budget")
		  .append(LINE_SEP)
		  .append("Technology Conference Management System")
		  .append(LINE_SEP)
		  .append("http://www.ejdoab.com")
		  .append(LINE_SEP)
		  .append("Licensed under The Apache Software License, Version 1.1")
		  .append(LINE_SEP)
		  .append("Copyright (c) 2003 Brian Sam-Bodden, Christopher M. Judd")
		  .append(LINE_SEP)
		  .append("All rights reserved.");
		_text.setText(sb.toString());
		
		StyleRange styleRange = new StyleRange();
		styleRange.start = 0;
		styleRange.length = title.length();
		styleRange.fontStyle = SWT.BOLD;			
		_text.setStyleRange(styleRange);	
		
		return page;
	}
	
	/**
	 * @param c
	 * @return
	 */
	private Composite buildSystemPage(Composite c) {
		Composite page = new Composite(c, SWT.NONE);
		
		FormLayout formLayout = new FormLayout();
		formLayout.marginHeight = 20;
		formLayout.marginWidth = 20;		
		page.setLayout(formLayout);		
		
		_tableViewer = new TableViewer(page, SWT.BORDER);		
		_tableViewer.setContentProvider(new SystemPropertiesTableContentProvider());
		_tableViewer.setLabelProvider(new SystemPropertiesTableLabelProvider());
		
		Table table = _tableViewer.getTable(); 
		table.setLinesVisible(true);
		table.setHeaderVisible(true);		
			
		SWTUtil.addTableColumn(_tableViewer, "Property", 210);				
		SWTUtil.addTableColumn(_tableViewer, "Value", 500);
			
		FormData formData = new FormData();
		formData.top = new FormAttachment(0,0);
		formData.bottom = new FormAttachment(100,0);
		formData.left = new FormAttachment(0,0);
		formData.right = new FormAttachment(100,0);
		formData.width = 320;
		formData.height = 200;
		
		_tableViewer.getTable().setLayoutData(formData);
		
		_tableViewer.setInput(System.getProperties());		
		
		return page;
	}
	

}
