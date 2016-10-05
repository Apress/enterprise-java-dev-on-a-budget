package com.ejdoab.tcms.admin;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.part.PageBook;

import com.ejdoab.swt.util.SWTUtil;
import com.ejdoab.tcms.admin.abstracts.AbstractsPage;
import com.ejdoab.tcms.admin.news.NewsPage;
import com.ejdoab.tcms.admin.remote.ConferenceServicesProxy;
import com.ejdoab.tcms.admin.remote.RemoteServerException;
import com.ejdoab.tcms.admin.remote.ServiceProxyFactory;
import com.ejdoab.tcms.services.dto.exceptions.DTOCreateException;

/**
 * Main Application Window of the TCMSAdmin Application
 * 
 * @author Brian Sam-Bodden
 */
public class TCMSAdmin extends ApplicationWindow {
	private boolean showShortcutBar = true;
	private boolean showStatusLine = true;
	private boolean showToolBar = true;

	private ToolBarManager shortcutBar;

	// static fields for inner classes.
	static final int VGAP = 0;
	static final int CLIENT_INSET = 3;
	static final int BAR_SIZE = 23;

	private Label separator1;
	private Label separator2;
	private Label separator3;
	
	private Shell _shell;
	
	private PageBook _pages;
	private AbstractsPage _abstracts;
	private NewsPage _news;
	
	private TCMSView _activeView;	
	
	private AboutAction aboutAction = new AboutAction();
	private ShowAbstractsPageAction conferencesAction = new ShowAbstractsPageAction();
	private ShowNewsPageAction newsAction = new ShowNewsPageAction();
	private RefreshDataAction refreshAction = new RefreshDataAction();
	private ShowPreferencesAction preferencesAction = new ShowPreferencesAction();
	private ExitAction exitAction = new ExitAction(this);
	
		
	private static String REMOTE_HOST = "localhost";
	private static String REMOTE_PORT = "1099";
	
	private ServiceProxyFactory _serviceFactory;
	private ConferenceServicesProxy _conferenceService;
	
	private PreferenceStore _preferenceStore;

	/**
	 * @param shell
	 */
	public TCMSAdmin(Shell shell) {
		super(null);
		_shell = shell;

		setDefaultImage(Resources.getImageRegistry().get("icons_application"));

		addMenuBar();
		addToolBar(SWT.FLAT | SWT.WRAP);
		addStatusLine();
		addShortcutBar(SWT.FLAT | SWT.WRAP | SWT.VERTICAL);

		setBlockOnOpen(true);
	}

	/**
	 * Sets the ApplicationWindows's content layout.
	 * This layout supports a fixed size Toolbar area, a separator line,
	 * the variable size content area,
	 * and a fixed size status line.
	 */
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setLayout(getLayout());
		shell.setText("TCMS Admin");

		separator1 = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		separator2 = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		separator3 = new Label(shell, SWT.SEPARATOR | SWT.VERTICAL);

		createShortcutBar(shell);

		shortcutBar.add(conferencesAction);
		shortcutBar.add(newsAction);
		shortcutBar.update(false);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.window.Window#createContents(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createContents(Composite parent) {
		Composite c = new Composite(parent, SWT.NONE);
		c.setLayout(new FillLayout());	
				
		_pages = new PageBook(c, SWT.NONE);
		
		_abstracts = new AbstractsPage(_pages, SWT.NONE);
		_news = new NewsPage(_pages, SWT.NONE);
			
		return c;
	}	
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.window.Window#initializeBounds()
	 */
	protected void initializeBounds() {
		_shell.setSize(1024, 768);
	}	

	/* (non-Javadoc)
	 * @see org.eclipse.jface.window.ApplicationWindow#createMenuManager()
	 */
	protected MenuManager createMenuManager() {
		MenuManager menuManager = new MenuManager();
		menuManager.add(buildFileMenu());
		menuManager.add(buildViewMenu());
		menuManager.add(buildHelpMenu());
		return menuManager;
	}

	/**
	 * @return
	 */
	private MenuManager buildFileMenu() {
		MenuManager fileMenu = new MenuManager("&File");
		fileMenu.add(preferencesAction);
		fileMenu.add(refreshAction);		
		fileMenu.add(exitAction);
		return fileMenu;
	}

	/**
	 * @return
	 */
	private MenuManager buildViewMenu() {
		MenuManager viewMenu = new MenuManager("&View");
		viewMenu.add(conferencesAction);
		viewMenu.add(newsAction);
		return viewMenu;
	}
	
	/**
	 * @return
	 */
	private MenuManager buildHelpMenu() {
		MenuManager helpMenu = new MenuManager("&Help");
		helpMenu.add(aboutAction);
		return helpMenu;			
	}

	/**
	 * Create the shortcut toolbar control
	 */
	private void createShortcutBar(Shell shell) {
		// Create control.
		if (shortcutBar == null)
			return;
		shortcutBar.createControl(shell);

		ToolBar tb = shortcutBar.getControl();
	}

	/**
	 * Sets whether the shortcut bar should be visible.
	 * 
	 * @param show <code>true</code> to show it, <code>false</code> to hide it
	 */
	public void setShowShortcutBar(boolean show) {
		showShortcutBar = show;
	}

	/**
	 * Sets whether the status line should be visible.
	 * 
	 * @param show <code>true</code> to show it, <code>false</code> to hide it
	 */
	public void setShowStatusLine(boolean show) {
		showStatusLine = show;
	}

	/**
	 * Sets whether the tool bar should be visible.
	 * 
	 * @param show <code>true</code> to show it, <code>false</code> to hide it
	 */
	public void setShowToolBar(boolean show) {
		showToolBar = show;
	}

	/**
	 * Configures this window to have a shortcut bar.
	 * Does nothing if it already has one.
	 * This method must be called before this window's shell is created.
	 */
	protected void addShortcutBar(int style) {
		if ((getShell() == null) && (shortcutBar == null)) {
			shortcutBar = new ToolBarManager(style);
		}
	}

	/**
	 * Returns the shortcut bar.
	 */
	public ToolBarManager getShortcutBar() {
		return shortcutBar;
	}

	/**
	 * Returns the separator2 control.
	 */
	protected Label getSeperator1() {
		return separator1;
	}

	/**
	 * Returns the separator2 control.
	 */
	protected Label getSeparator2() {
		return separator2;
	}

	/**
	 * Returns the separator3 control.
	 */
	protected Label getSeparator3() {
		return separator3;
	}

	/**
	 * Returns whether the tool bar should be shown.
	 * 
	 * @return <code>true</code> to show it, <code>false</code> to hide it
	 */
	public boolean getShowToolBar() {
		return showToolBar;
	}

	/**
	 * Returns whether the status bar should be shown.
	 * 
	 * @return <code>true</code> to show it, <code>false</code> to hide it
	 */
	public boolean getShowStatusLine() {
		return showStatusLine;
	}

	/**
	 * Returns whether the shortcut bar should be shown.
	 * 
	 * @return <code>true</code> to show it, <code>false</code> to hide it
	 */
	public boolean getShowShortcutBar() {
		return showShortcutBar;
	}

	/**
	 * Get the workbench client area.
	 */
	protected Composite getClientComposite() {
		return (Composite) getContents();
	}

	/**
	 * Returns the layout for the shell.
	 * 
	 * @return the layout for the shell
	 */
	protected Layout getLayout() {
		return new TCMSWindowLayout();
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.window.ApplicationWindow#createToolBarManager(int)
	 */
	protected ToolBarManager createToolBarManager(int style) {
		ToolBarManager toolBarManager = new ToolBarManager(style);
		toolBarManager.add(preferencesAction);
		toolBarManager.add(refreshAction);
		
		return toolBarManager;
	}
	
	//
	// actions
	// 	
	
	private class AboutAction extends Action {

		public AboutAction() {
			setToolTipText("About this Application");
			setText("&About");
			setImageDescriptor(Resources.getImageRegistry().getDescriptor("icons_about"));		
		}

		public void run() {
			AboutDialog dialog = new AboutDialog(getShell());
			dialog.open();
		}
	}

	private class ShowAbstractsPageAction extends Action {

		public ShowAbstractsPageAction() {
			setToolTipText("Switch to the Abstract's Page");
			setText("&Abstracts@Alt+A");
			setImageDescriptor(Resources.getImageRegistry().getDescriptor("icons_abstracts"));
		}

		public void run() {
			_pages.showPage(_abstracts);
			_activeView = (TCMSView)_abstracts;
			setStatus("Abstracts Page Loaded");
		}
	}

	private class ShowNewsPageAction extends Action {

		public ShowNewsPageAction() {
			setToolTipText("Switch to the News Page");
			setText("&News@Alt+N");
			setImageDescriptor(Resources.getImageRegistry().getDescriptor("icons_news"));
		}

		public void run() {
			_pages.showPage(_news);
			_activeView = (TCMSView)_news;
			setStatus("News Page Loaded");
		}
	}	
	
	private class ShowPreferencesAction extends Action {
		public ShowPreferencesAction() {
			setToolTipText("Brings up the Application Preferences Dialog");
			setText("&Preferences");
			setImageDescriptor(Resources.getImageRegistry().getDescriptor("icons_preferences"));
		}

		public void run() {
			PreferenceStore store = getPreferenceStore();
		
			try {
				store.load();
			} catch (IOException ex) {
				;
			}
			PreferenceManager manager = new PreferenceManager();

			PreferencePage serverPage = new ServerPreferencesPage();
			PreferenceNode serverNode = new PreferenceNode("serverNode");
			serverNode.setPage(serverPage);

			manager.addToRoot(serverNode);

			PreferenceDialog dialog = new PreferenceDialog(getShell(), manager);
			dialog.setPreferenceStore(store);

			dialog.open();
		}
	}	

	private class RefreshDataAction extends Action {

		public RefreshDataAction() {
			setToolTipText("Refresh the Active Page Data from the Server");
			setText("&Refresh@Alt+R");
			setImageDescriptor(Resources.getImageRegistry().getDescriptor("icons_refresh"));
		}

		public void run() {
			ProgressMonitorDialog dialog =
				new ProgressMonitorDialog(getShell());				
			try {
				dialog.setCancelable(false);
				dialog.run(true, true, new LoadData());
			} catch (InvocationTargetException ex) {
				ex.printStackTrace();
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}						
		}
	}	
	
	private class ExitAction extends Action {
		ApplicationWindow _window;

		public ExitAction(ApplicationWindow window) {
			_window = window;
			setText("E&xit@Ctrl+X");
			setToolTipText("Exit TCMS Admin");			
		}

		public void run() {
			_window.close();
		}	
	}	
	
	//
	// Progress
	//
	private class LoadData implements IRunnableWithProgress {

		/* (non-Javadoc)
		 * @see org.eclipse.jface.operation.IRunnableWithProgress#run(org.eclipse.core.runtime.IProgressMonitor)
		 */
		public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
			monitor.beginTask("Contacting Server", IProgressMonitor.UNKNOWN);
			if (_activeView != null) {
				try {
					Object[] data = null;
					String[] topics = null;
					String[] levels = null;
					String[] types = null;
					String[] status = null;
										
					if (_serviceFactory == null) {
						PreferenceStore store = getPreferenceStore();						
						String host = store.getString("host");
						String port = store.getString("port");
						_serviceFactory = new ServiceProxyFactory(host, port);
						monitor.subTask("Obtained Service Proxy Factory");						
					}

					if (_serviceFactory != null) {					
						_conferenceService = _serviceFactory.getConferencesServices();
						monitor.subTask("Obtained Conference Service Proxy");

						if (_activeView.equals(_abstracts)) {	
							_abstracts.setService(_conferenceService);											
							data = _conferenceService.getAbstractAsArray();
							monitor.subTask("Retrieved Abstracts List");
							topics = _conferenceService.getValidTopics();
							monitor.subTask("Retrieved Topics");
							levels = _conferenceService.getValidLevels();
							monitor.subTask("Retrieved Levels");
							types = _conferenceService.getValidTypes();
							monitor.subTask("Retrieved Types");
							status =
								_conferenceService.getValidAbstractStatus();
							monitor.subTask("Retrieve Status");
							
							_shell.getDisplay().syncExec(new AbstractsViewUpdater(_abstracts, data, topics, levels, types, status));
						} else if (_activeView.equals(_news)) {
							_news.setService(_conferenceService);
							data = _conferenceService.getNewsAsArray();
							monitor.subTask("Retrieved News Articles");
							_shell.getDisplay().syncExec(new NewsViewUpdater(_news, data));
						}
					}					
					monitor.subTask("Data Loaded");
					monitor.done();
				} catch (RemoteServerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (DTOCreateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
		}
		
		class AbstractsViewUpdater implements Runnable {
			Object[] _data;
			String[] _topics;
			String[] _levels;
			String[] _types;
			String[] _status;	
			AbstractsPage _abstracts;		
			
			public AbstractsViewUpdater(
				AbstractsPage abstracts,
				Object[] data,
				String[] topics,
				String[] levels,
				String[] types,
				String[] status) {
					_abstracts = abstracts;
					_data = data;
					_topics = topics;
					_levels = levels;
					_types = types;
					_status = status;		
			}
			
			/* (non-Javadoc)
			 * @see java.lang.Runnable#run()
			 */
			public void run() {
				_abstracts.setTopics(_topics);
				_abstracts.setLevels(_levels);
				_abstracts.setTypes(_types);
				_abstracts.setStatus(_status);
				_abstracts.loadData(_data);				
			}				
		}	
		
		class NewsViewUpdater implements Runnable {
			Object[] _data;
			NewsPage _news;
			
			/**
			 * 
			 */
			public NewsViewUpdater(NewsPage news, Object[] data) {
				_news = news;
				_data = data;
			}

			/* (non-Javadoc)
			 * @see java.lang.Runnable#run()
			 */
			public void run() {
				_news.loadData(_data);
			}	
		}
	}	
	
	/**
	 * Preferences
	 */
	private static class ServerPreferencesPage extends FieldEditorPreferencePage {
		public ServerPreferencesPage() {
			super("Server", FieldEditorPreferencePage.GRID);
		}

		protected void createFieldEditors() {
			addField(
				new StringFieldEditor(
					"host",
					"&Host:",
					getFieldEditorParent()));
			addField(
				new IntegerFieldEditor(
					"port",
					"&Port:",
					getFieldEditorParent()));
		}
		
	}

	/**
	 * The layout for the TCMS Admin window shell.
	 */
	class TCMSWindowLayout extends Layout {

		protected Point computeSize(
			Composite composite,
			int wHint,
			int hHint,
			boolean flushCache) {
			if (wHint != SWT.DEFAULT && hHint != SWT.DEFAULT)
				return new Point(wHint, hHint);

			Point result = new Point(0, 0);
			Control[] ws = composite.getChildren();
			for (int i = 0; i < ws.length; i++) {
				Control w = ws[i];
				boolean skip = false;
				if (w == getToolBarControl()) {
					skip = true;
					result.y += BAR_SIZE;
				} else if (
					getShortcutBar() != null
						&& w == getShortcutBar().getControl()) {
					skip = true;
				}
				if (!skip) {
					Point e = w.computeSize(wHint, hHint, flushCache);
					result.x = Math.max(result.x, e.x);
					result.y += e.y + VGAP;
				}
			}

			result.x += BAR_SIZE; // For shortcut bar.
			if (wHint != SWT.DEFAULT)
				result.x = wHint;
			if (hHint != SWT.DEFAULT)
				result.y = hHint;
			return result;
		}

		protected void layout(Composite composite, boolean flushCache) {
			Rectangle clientArea = composite.getClientArea();

			//Null on carbon
			if (getSeperator1() != null) {
				//Layout top seperator
				Point sep1Size =
					getSeperator1().computeSize(
						SWT.DEFAULT,
						SWT.DEFAULT,
						flushCache);
				getSeperator1().setBounds(
					clientArea.x,
					clientArea.y,
					clientArea.width,
					sep1Size.y);
				clientArea.y += sep1Size.y;
				clientArea.height -= sep1Size.y;
			}

			int toolBarWidth = clientArea.width;

			//Layout the toolbar	
			Control toolBar = getToolBarControl();
			if (toolBar != null) {
				if (getShowToolBar()) {
					int height = BAR_SIZE;

					if (toolBarChildrenExist()) {
						Point toolBarSize =
							toolBar.computeSize(
								clientArea.width,
								SWT.DEFAULT,
								flushCache);
						height = toolBarSize.y;
					}
					toolBar.setBounds(
						clientArea.x,
						clientArea.y,
						toolBarWidth,
						height);
					clientArea.y += height;
					clientArea.height -= height;
				} else
					getToolBarControl().setBounds(0, 0, 0, 0);
			}

			//Layout side seperator
			Control sep2 = getSeparator2();
			if (sep2 != null) {
				if (getShowToolBar()) {
					Point sep2Size =
						sep2.computeSize(SWT.DEFAULT, SWT.DEFAULT, flushCache);
					sep2.setBounds(
						clientArea.x,
						clientArea.y,
						clientArea.width,
						sep2Size.y);
					clientArea.y += sep2Size.y;
					clientArea.height -= sep2Size.y;
				} else
					sep2.setBounds(0, 0, 0, 0);
			}

			if (getStatusLineManager() != null) {
				Control statusLine = getStatusLineManager().getControl();
				if (statusLine != null) {
					if (getShowStatusLine()) {

						int width = 0;
						if (getShortcutBar() != null && getShowShortcutBar()) {
							Widget shortcutBar = getShortcutBar().getControl();
							if (shortcutBar != null
								&& shortcutBar instanceof ToolBar) {
								ToolBar bar = (ToolBar) shortcutBar;
								if (bar.getItemCount() > 0) {
									ToolItem item = bar.getItem(0);
									width = item.getWidth();
									Rectangle trim =
										bar.computeTrim(0, 0, width, width);
									width = trim.width;
								}
							}
						}

						Point statusLineSize =
							statusLine.computeSize(
								SWT.DEFAULT,
								SWT.DEFAULT,
								flushCache);
						statusLine.setBounds(
							clientArea.x + width,
							clientArea.y + clientArea.height - statusLineSize.y,
							clientArea.width - width,
							statusLineSize.y);
						clientArea.height -= statusLineSize.y + VGAP;
					} else
						getStatusLineManager().getControl().setBounds(
							0,
							0,
							0,
							0);
				}
			}

			if (getShortcutBar() != null) {
				Control shortCutBar = getShortcutBar().getControl();
				if (shortCutBar != null) {
					if (getShowShortcutBar()) {

						int width = BAR_SIZE;
						if (shortCutBar instanceof ToolBar) {
							ToolBar bar = (ToolBar) shortCutBar;
							if (bar.getItemCount() > 0) {
								ToolItem item = bar.getItem(0);
								width = item.getWidth();
								Rectangle trim =
									bar.computeTrim(0, 0, width, width);
								width = trim.width;
							}
						}
						shortCutBar.setBounds(
							clientArea.x,
							clientArea.y,
							width,
							clientArea.height);
						clientArea.x += width + VGAP;
						clientArea.width -= width + VGAP;
					} else
						getShortcutBar().getControl().setBounds(0, 0, 0, 0);
				}
			}

			Control sep3 = getSeparator3();

			if (sep3 != null) {
				if (getShowShortcutBar()) {
					Point sep3Size =
						sep3.computeSize(SWT.DEFAULT, SWT.DEFAULT, flushCache);
					sep3.setBounds(
						clientArea.x,
						clientArea.y,
						sep3Size.x,
						clientArea.height);
					clientArea.x += sep3Size.x;
				} else
					sep3.setBounds(0, 0, 0, 0);
			}

			if (getClientComposite() != null)
				getClientComposite().setBounds(
					clientArea.x + CLIENT_INSET,
					clientArea.y + CLIENT_INSET + VGAP,
					clientArea.width - (2 * CLIENT_INSET),
					clientArea.height - VGAP - (2 * CLIENT_INSET));

		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final Display display = new Display();
		Resources.setDisplay(display);
		
		// splash shell
		final int loops = 15;

		final Shell splashShell = new Shell(SWT.ON_TOP);
		final Splash splash = new Splash(splashShell, SWT.NONE);
		splash.configureProgressBar(loops, 1);
		splashShell.pack();
		SWTUtil.centerShellOnDisplay(splashShell, display);

		splashShell.open();

		display.asyncExec(new Runnable() {
			public void run() {
				for (int i = 0; i < loops; i++) {
					splash.increment();
					try {
						Thread.sleep(500);
					} catch (Throwable e) {
					}
				}
				splashShell.close();
				splashShell.dispose();
			}
		});

		
		Shell shell = new Shell(display);
		ApplicationWindow window = new TCMSAdmin(shell);		
		window.open();					
	}	 

	/**
	 * @return
	 */
	public PreferenceStore getPreferenceStore() {
		if (_preferenceStore == null) {		
			_preferenceStore = new PreferenceStore("tcms-gui.properties");			
			_preferenceStore.setDefault("host", REMOTE_HOST);
			_preferenceStore.setDefault("port", Integer.parseInt(REMOTE_PORT));
		}		
		return _preferenceStore;
	}

}
