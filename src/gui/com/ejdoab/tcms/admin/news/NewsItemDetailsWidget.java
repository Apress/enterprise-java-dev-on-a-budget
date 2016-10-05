package com.ejdoab.tcms.admin.news;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.ejdoab.swt.util.SWTUtil;
import com.ejdoab.tcms.services.dto.NewsItemDTO;
import com.ejdoab.tcms.util.Dates;

/**
 * An example of a custom composite widget
 * 
 * @author Brian Sam-Bodden 
 */
public class NewsItemDetailsWidget extends ScrolledComposite {

	private Text _title;
	private Text _removeDate;
	private Text _creationDate;
	private Button _published;

	private NewsItemDTO _model;

	private boolean _modified = false;

	/**
	 * 
	 */
	public NewsItemDetailsWidget(Composite parent) {
		super(parent, SWT.H_SCROLL | SWT.V_SCROLL);

		// take as much client area as possible
		setExpandHorizontal(true);
		setExpandVertical(true);

		// internal composite in which to layout the children widgets 
		// using a grid layout 
		Composite grid = new Composite(this, SWT.NONE);

		// set the background color		
		grid.setBackground(SWTUtil.getSystemColor(SWT.COLOR_LIST_BACKGROUND));

		// create and set the grid layout		
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.verticalSpacing = 4;
		layout.horizontalSpacing = 6;
		grid.setLayout(layout);

		// add a title label
		Label label =
			SWTUtil.createLabel(
				grid,
				"News Item Information:",
				SWT.WRAP,
				SWT.COLOR_LIST_BACKGROUND);
		label.setFont(SWTUtil.getFont(JFaceResources.BANNER_FONT));

		// separator
		Composite separator = new Composite(grid, SWT.NONE);
		separator.setBackground(
			SWTUtil.getColor(separator, SWT.COLOR_TITLE_BACKGROUND_GRADIENT));

		GridData separatorGridData = new GridData();
		separatorGridData.horizontalSpan = 2;
		separatorGridData.horizontalAlignment = GridData.FILL;
		separatorGridData.grabExcessHorizontalSpace = true;
		separatorGridData.verticalAlignment = GridData.BEGINNING;
		separatorGridData.heightHint = 2;
		separator.setLayoutData(separatorGridData);

		// title field
		GridData titleGridData = new GridData();
		titleGridData.widthHint = 215;

		SWTUtil.createLabel(grid, "Title", SWT.FLAT, SWT.COLOR_LIST_BACKGROUND);
		_title = SWTUtil.createText(grid, "", SWT.SINGLE | SWT.BORDER);
		_title.setLayoutData(titleGridData);

		// removal date field
		GridData typeGridData = new GridData();
		typeGridData.widthHint = 215;

		SWTUtil.createLabel(grid, "Removal Date", SWT.FLAT, SWT.COLOR_LIST_BACKGROUND);
		_removeDate = SWTUtil.createText(grid, "", SWT.SINGLE | SWT.BORDER);
		_removeDate.setLayoutData(typeGridData);

		// creation date field
		GridData topicGridData = new GridData();
		topicGridData.widthHint = 215;

		SWTUtil.createLabel(grid, "Creation Date:", SWT.FLAT, SWT.COLOR_LIST_BACKGROUND);
		_creationDate = SWTUtil.createText(grid, "", SWT.SINGLE | SWT.BORDER);
		_creationDate.setLayoutData(topicGridData);

		// is published field
		SWTUtil.createLabel(
			grid,
			"Has been Published?",
			SWT.FLAT,
			SWT.COLOR_LIST_BACKGROUND);
		_published = new Button(grid, SWT.CHECK | SWT.FLAT);
		_published.setBackground(
			SWTUtil.getColor(_published, SWT.COLOR_LIST_BACKGROUND));
		_published.setText("");

		// create and set internal listeners
		ModifyListener modifyListener = new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				onTextModified(e);
			}
		};
		
		SelectionListener selectionListener = new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				onPublished(e);				
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				onPublished(e);				
			}
		};		

		FocusListener focusListener = new FocusListener() {
			public void focusGained(FocusEvent e) {
				onFocusGained(e);
			}

			public void focusLost(FocusEvent e) {
				onFocusLost(e);
			}
		};

		MouseListener mouseListener = new MouseAdapter() {
			public void mouseDown(MouseEvent event) {
				notifyListeners(SWT.FocusIn, new Event());
				_title.setFocus();
			}
		};

		_title.addModifyListener(modifyListener);		
		_removeDate.addModifyListener(modifyListener);
		_creationDate.addModifyListener(modifyListener);
		_published.addSelectionListener(selectionListener);

		_title.addFocusListener(focusListener);
		_removeDate.addFocusListener(focusListener);
		_creationDate.addFocusListener(focusListener);
		
		label.addMouseListener(mouseListener);
		grid.addMouseListener(mouseListener);

		// to ensure that scrollbars show up when needed
		Point pt = grid.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		setMinWidth(pt.x);
		setMinHeight(pt.y);

		// add the grid to the widget
		setContent(grid);
	}

	/**
	 * Pass contained widgets focus lost event
	 * to the parent
	 * 
	 * @param event
	 */
	protected void onFocusLost(FocusEvent event) {
		notifyListeners(SWT.FocusOut, new Event());
	}

	/**
	 * Pass contained widgets focus gained event
	 * to the parent 
	 * 
	 * @param event
	 */
	protected void onFocusGained(FocusEvent event) {
		notifyListeners(SWT.FocusIn, new Event());
	}

	/**
	 * @param e
	 */
	protected void onTextModified(ModifyEvent e) {
		_modified = !_loading;
	}
	
	/**
	 * @param e
	 */
	protected void onPublished(SelectionEvent e) {
		_modified = !_loading;
	}	

	public NewsItemDTO getModel() {
		return _model;
	}
	
	public void applyChanges() {
		if (_modified && (!_loading)) {
			_model.setTitle(_title.getText());
			_model.setCreationDate(Dates.getDate(_creationDate.getText(), Dates.DATE_MMddyyyy));
			_model.setRemoveDate(Dates.getDate(_removeDate.getText(), Dates.DATE_MMddyyyy));
			_model.setPublished(_published.getSelection());
		}		
	}

	public void setModel(NewsItemDTO model) {
		_model = model;
		populateData();
	}

	boolean _loading = false;

	protected void populateData() {
		if (_model != null) {
			_loading = true;
			_title.setText(_model.getTitle());
			_published.setSelection(_model.isPublished());
			_published.setEnabled(!_model.isPublished());
			
			_creationDate.setText(Dates.format(_model.getCreationdate(), Dates.DATE_MMddyyyy));
			_removeDate.setText(Dates.format(_model.getRemovedate(), Dates.DATE_MMddyyyy));
			_loading = false;
		}
	}

	/**
	 * @return
	 */
	public boolean isModified() {
		return _modified;
	}

	/**
	 * @param b
	 */
	public void setModified(boolean b) {
		_modified = b;
	}

	/* Focus request are passed to the first editable field in 
	 * this composite widget
	 * 
	 * (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#setFocus()
	 */
	public boolean setFocus() {
		return _title.setFocus();
	}
}
