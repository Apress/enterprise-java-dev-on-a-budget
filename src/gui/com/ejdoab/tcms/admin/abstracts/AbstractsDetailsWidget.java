package com.ejdoab.tcms.admin.abstracts;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.ejdoab.swt.util.SWTUtil;
import com.ejdoab.tcms.services.dto.ConferenceAbstractDTO;

/**
 * An example of a custom composite widget
 * 
 * @author Brian Sam-Bodden 
 */
public class AbstractsDetailsWidget extends ScrolledComposite {

	private Text _title;
	private CCombo _type;
	private CCombo _topic;
	private CCombo _level;
	private CCombo _status;
	private Button _presentations;
	private Text _presenter;

	private ConferenceAbstractDTO _model;

	private boolean _modified = false;

	/**
	 * 
	 */
	public AbstractsDetailsWidget(Composite parent) {
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
				"Abstract Information:",
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

		// type field
		GridData typeGridData = new GridData();
		typeGridData.widthHint = 200;

		SWTUtil.createLabel(grid, "Type", SWT.FLAT, SWT.COLOR_LIST_BACKGROUND);
		_type = new CCombo(grid, SWT.FLAT | SWT.BORDER);
		_type.setLayoutData(typeGridData);

		// topic field
		GridData topicGridData = new GridData();
		topicGridData.widthHint = 200;

		SWTUtil.createLabel(grid, "Topic", SWT.FLAT, SWT.COLOR_LIST_BACKGROUND);
		_topic = new CCombo(grid, SWT.FLAT | SWT.BORDER);

		_topic.setLayoutData(topicGridData);

		// level field
		GridData levelGridData = new GridData();
		levelGridData.widthHint = 200;

		SWTUtil.createLabel(grid, "Level", SWT.FLAT, SWT.COLOR_LIST_BACKGROUND);
		_level = new CCombo(grid, SWT.FLAT | SWT.BORDER);

		_level.setLayoutData(levelGridData);

		// status field
		GridData statusGridData = new GridData();
		statusGridData.widthHint = 200;

		SWTUtil.createLabel(
			grid,
			"Status",
			SWT.FLAT,
			SWT.COLOR_LIST_BACKGROUND);
		_status = new CCombo(grid, SWT.FLAT | SWT.BORDER);

		_status.setLayoutData(statusGridData);

		// presentations field
		SWTUtil.createLabel(
			grid,
			"has Presentations",
			SWT.FLAT,
			SWT.COLOR_LIST_BACKGROUND);
		_presentations = new Button(grid, SWT.CHECK | SWT.FLAT);
		_presentations.setBackground(
			SWTUtil.getColor(_presentations, SWT.COLOR_LIST_BACKGROUND));
		_presentations.setText("");
		_presentations.setSelection(true);
		_presentations.setEnabled(false);

		// presenter field
		GridData presenterGridData = new GridData();
		presenterGridData.widthHint = 215;

		SWTUtil.createLabel(
			grid,
			"Presenter",
			SWT.FLAT,
			SWT.COLOR_LIST_BACKGROUND);
		_presenter = SWTUtil.createText(grid, "");
		_presenter.setLayoutData(presenterGridData);

		// create and set internal listeners
		ModifyListener modifyListener = new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				onTextModified(e);
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
		_type.addModifyListener(modifyListener);
		_topic.addModifyListener(modifyListener);
		_level.addModifyListener(modifyListener);
		_status.addModifyListener(modifyListener);
		_presenter.addModifyListener(modifyListener);

		_title.addFocusListener(focusListener);
		_type.addFocusListener(focusListener);
		_topic.addFocusListener(focusListener);
		_level.addFocusListener(focusListener);
		_status.addFocusListener(focusListener);
		_presenter.addFocusListener(focusListener);

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

	public void applyChanges() {
		if (_modified && (!_loading)) {
			_model.setTitle(_title.getText());
			_model.setLevel(_level.getItem(_level.getSelectionIndex()));
			_model.setStatus(_status.getItem(_status.getSelectionIndex()));
			_model.setTopic(_topic.getItem(_topic.getSelectionIndex()));
			_model.setType(_type.getItem(_type.getSelectionIndex()));
		}		
	}
	
	public ConferenceAbstractDTO getModel() {
		return _model;
	}

	public void setModel(ConferenceAbstractDTO model) {
		_model = model;
		populateData();
	}

	boolean _loading = false;

	protected void populateData() {
		if (_model != null) {
			_loading = true;
			_title.setText(_model.getTitle());
			_presentations.setSelection(_model.hasPresentations());
			_presenter.setText(_model.getPresenter());
			_type.select(_type.indexOf(_model.getType()));
			_topic.select(_topic.indexOf(_model.getTopic()));
			_level.select(_level.indexOf(_model.getLevel()));
			_status.select(_status.indexOf(_model.getStatus()));
			_loading = false;
		}
	}

	/**
	 * @param strings
	 */
	public void setLevels(String[] strings) {
		_level.setItems(strings);
	}

	/**
	 * @param strings
	 */
	public void setStatus(String[] strings) {
		_status.setItems(strings);
	}

	/**
	 * @param strings
	 */
	public void setTopics(String[] strings) {
		_topic.setItems(strings);
	}

	/**
	 * @param strings
	 */
	public void setTypes(String[] strings) {
		_type.setItems(strings);
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
