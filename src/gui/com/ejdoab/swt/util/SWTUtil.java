package com.ejdoab.swt.util;

import org.eclipse.compare.CompareViewerPane;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

/**
 * Miscellaneous static methods to create and manipulate SWT and JFace widgets
 * @author Brian Sam-Bodden
 */
public class SWTUtil {
	
	//
	// static initializations
	//
	static {
		initializeColors();
	}
	
	/**
	 * Uninstantiable class
	 */
	private SWTUtil() {}
	
	//
	// shell utilities
	//

	/**
	 * Centers a Shell on the given Display
	 * 
	 * @param shell
	 * @param display
	 */
	public static void centerShellOnDisplay(Shell shell, Display display) {
		Rectangle bounds = display.getBounds();
		Rectangle rect = shell.getBounds();
		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;
		shell.setLocation(x, y);
	}
	
	//
	// tableviewer utilities
	//

	/**
	 * Adds a column to a table viewer
	 * 
	 * @param tableViewer
	 * @param title
	 * @param width
	 * @return
	 */
	public static TableColumn addTableColumn(
		TableViewer tableViewer,
		String title,
		int width) {
		TableColumn column = addTableColumn(tableViewer, title);
		column.setWidth(width);
		return column;
	}
	
	public static TableColumn addTableColumn(
			TableViewer tableViewer,
			String title) {
			TableColumn column = new TableColumn(tableViewer.getTable(), SWT.LEFT);
			column.setText(title);
			return column;
	}	
	
	public static TableColumn addTableColumn(		
		final TableViewer tableViewer,
		String title,
		int width,
		final ViewerSorter sorter) {
		TableColumn column = addTableColumn(tableViewer, title, width);
		column.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				tableViewer.setSorter(sorter);
			}
		});
		return column;
	}
	
	
	//
	// label utilities
	//
	
	/**
	 * @param parent
	 * @param text
	 * @return
	 */
	public static Label createLabel(Composite parent, String text) {
		return createLabel(parent, text, SWT.NONE);
	}
	
	/**
	 * @param parent
	 * @param text
	 * @param style
	 * @return
	 */
	public static Label createLabel(Composite parent, String text, int style) {
		Label label = new Label(parent, style);
		if (text != null)
			label.setText(text);
		return label;
	}	
	
	/**
	 * @param parent
	 * @param text
	 * @param style
	 * @param bkcolor
	 * @return
	 */
	public static Label createLabel(Composite parent, String text, int style, int bkcolor) {
		Label label = createLabel(parent, text, style);
		label.setBackground(getColor(label, bkcolor));
		return label;
	}
	
	//
	// text utilities
	//
	
	/**
	 * @param parent
	 * @return
	 */
	public static Text createText(Composite parent) {
		return createText(parent, "", SWT.BORDER | SWT.SINGLE);
	}	

	/**
	 * @param parent
	 * @param value
	 * @return
	 */
	public static Text createText(Composite parent, String value) {
		return createText(parent, value, SWT.BORDER | SWT.SINGLE);
	}
	
	/**
	 * @param parent
	 * @param value
	 * @param style
	 * @return
	 */
	public static Text createText(Composite parent, String value, int style) {
		Text text = new Text(parent, style);
		text.setText(value);
		return text;
	}	
	
	/**
	 * @param parent
	 * @param value
	 * @param style
	 * @param bkcolor
	 * @return
	 */
	public static Text createText(Composite parent, String value, int style, int bkcolor) {
		Text text = createText(parent, value, style);
		text.setBackground(getColor(text, bkcolor));
		return text;
	}
	
	//
	// color utilities
	//
	
	/**
	 * @param color
	 * @return
	 */
	public static Color getColor(Control c, int color) {
		return c.getDisplay().getSystemColor(color);
	}	
	
	/**
	 * Returns a system color identified by a SWT constant.
	 * 
	 * @param color
	 * @return
	 */
	public static Color getSystemColor(int color) {
		Display display = Display.getDefault();
		return display.getSystemColor(color);
	}	
	

	//
	// font utilities
	//	
	
	/**
	 * @param font
	 * @return
	 */	
	public static Font getFont(String font) {	
	    return JFaceResources.getFontRegistry().get(font);
	}
	
	//
	// gradient colors and percentages
	//
	
	public static Color[] GRADIENT_COLORS;
	public static int[] GRADIENT_PERCENTAGES;
	
	private static void initializeColors() {	
		// Define active view gradient using same OS title gradient colors.
		Color low = getSystemColor(SWT.COLOR_TITLE_BACKGROUND);
		Color medium = getSystemColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT);
		Color high = getSystemColor(SWT.COLOR_WIDGET_BACKGROUND);
		GRADIENT_COLORS = new Color[] {low, medium, high};
		GRADIENT_PERCENTAGES = new int[] {50, 100};
	}
	
	//
	// CompareViewerPane utilities
	//
	
	/**
	 * FocusListener implementation that mimicks the OS gradient on focus behaviour
	 */
	public static class CLabelFocusListener implements FocusListener {
		private CLabel _clabel;
		
		public CLabelFocusListener(CompareViewerPane cwp) {
			_clabel = (CLabel) cwp.getTopLeft();			
		}
		
		public void focusGained(FocusEvent e) {
			_clabel.setForeground(SWTUtil.getSystemColor(SWT.COLOR_TITLE_FOREGROUND));
			_clabel.setBackground(GRADIENT_COLORS, GRADIENT_PERCENTAGES);
		}

		public void focusLost(FocusEvent e) {
			_clabel.setForeground(SWTUtil.getSystemColor(SWT.COLOR_INFO_FOREGROUND));
			_clabel.setBackground(null, null);
		}

	}	
	
	/**
	 * 
	 */
	public static class CLabelMouseAdapter extends MouseAdapter {
		private CLabel _clabel;
		private CompareViewerPane _cwp;
		
		public CLabelMouseAdapter(CompareViewerPane cwp) {
			_cwp = cwp;
		    _clabel = (CLabel) cwp.getTopLeft();
		    //TODO is this a good or bad practice
		    // assigning the listener in the constructor?
		    _clabel.addMouseListener(this);
	    }
	    
	    public void removeMouseListener() {
			_clabel.removeMouseListener(this);
	    }
	    
		public void mouseDown(MouseEvent event) {
			_cwp.getContent().setFocus();			
		}
	}
	
	
	public static void createHorizontalGridSeparator(Composite parent) {
		Layout layout = parent.getLayout();	
		if (layout instanceof GridLayout) {	
			GridLayout gridLayout = (GridLayout)layout;				
			Label line = new Label(parent, SWT.SEPARATOR|SWT.HORIZONTAL|SWT.BOLD);
			GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
			gridData.horizontalSpan = gridLayout.numColumns;
			line.setLayoutData(gridData);
		}
	}	
	
	/**
	 * This method blocks until the Shell is disposed subsequently
	 * disposing the parent Display
	 * 
	 * @param display
	 * @param shell
	 */
	public static void eventLoop(Display display, Shell shell) {
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}
	
	/**
	 * This method blocks until all Shells are disposed subsequently
	 * disposing the parent Display
	 * 
	 * @param display
	 * @param shell
	 */
	public static void eventLoop(Display display, Shell[] shells) {
		boolean shellsDisposed = true;
		while (!shellsDisposed) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
			for (int i = 0; i < shells.length; i++) {
				shellsDisposed = shellsDisposed && (shells[i].isDisposed());
			}			
		}
		display.dispose();
	}	
	
	public static void addStrings(String[] strings, List list) {
		for (int i = 0; i < strings.length; i++) {
			list.add(strings[i]);
		}
	}

	
}
