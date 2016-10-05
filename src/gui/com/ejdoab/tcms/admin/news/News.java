package com.ejdoab.tcms.admin.news;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.ejdoab.tcms.admin.Resources;
import com.ejdoab.tcms.admin.remote.ConferenceServicesProxy;
import com.ejdoab.tcms.admin.remote.RemoteServerException;
import com.ejdoab.tcms.admin.remote.ServiceProxyFactory;
import com.ejdoab.tcms.services.dto.NewsItemDTO;
import com.ejdoab.tcms.services.dto.exceptions.DTOCreateException;

/** 
 * Enables the creation of a new abstract
 * Enables to edit and change the status of a submited abstract
 * 
 * @author Brian Sam-Bodden
 */
public class News extends ApplicationWindow {

	private Shell _shell;


	NewsPage page;

	/**
	 * 
	 */
	public News() {
		super(null);
		addMenuBar();
	}

	protected MenuManager createMenuManager() {
		MenuManager mm = new MenuManager("");
		MenuManager fileMenu = new MenuManager("&File");
		mm.add(fileMenu);
		fileMenu.add(loadDataAction);
		return mm;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		_shell = shell;		
		_shell.setText("TCMS - News");
	}
	
	protected void initializeBounds() {
		_shell.setSize(1024, 768);
	}	

	protected Control createContents(Composite parent) {		

		Composite c = new Composite(parent, SWT.NONE);
		c.setLayout(new FillLayout());
		page = new NewsPage(c, SWT.NONE); 
		
		return c;
	}

	public static NewsItemDTO[] news = null;


	/**
	 * @param args
	 */
	private static ConferenceServicesProxy service;

	public static void main(String[] args) {
		ServiceProxyFactory factory =
			new ServiceProxyFactory("localhost", "1099");
		try {
			service = factory.getConferencesServices();
			news = service.getNewsAsArray();
		} catch (DTOCreateException dce) {
			// TODO Auto-generated catch block
			dce.printStackTrace();
		} catch (RemoteServerException rse) {
			// TODO Auto-generated catch block
			rse.printStackTrace();
		}
		
		Display display = new Display();		
		Resources.setDisplay(display);
		
		News window = new News();
		NewsPage page = window.getPage();		
		
		window.setBlockOnOpen(true);
		window.open();
		Display.getCurrent().dispose();
	}

	//
	//	
	private IAction loadDataAction = new LoadDataAction();
	private class LoadDataAction extends Action {
		public LoadDataAction() {
			setToolTipText("Load Sample Data");
			setText("&Load");
		}

		public void run() {			
			page.setService(service);
			page.loadData(news);
		}
	}

	/**
	 * @return
	 */
	public NewsPage getPage() {
		return page;
	}

}
