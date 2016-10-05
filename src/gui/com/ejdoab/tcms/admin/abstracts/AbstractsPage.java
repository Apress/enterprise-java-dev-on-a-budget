package com.ejdoab.tcms.admin.abstracts;

import org.eclipse.compare.CompareViewerPane;
import org.eclipse.compare.Splitter;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import com.ejdoab.swt.util.SWTUtil;
import com.ejdoab.tcms.admin.BaseTCMSView;
import com.ejdoab.tcms.admin.Resources;
import com.ejdoab.tcms.admin.remote.ConferenceServicesProxy;
import com.ejdoab.tcms.admin.remote.RemoteServerException;
import com.ejdoab.tcms.services.dto.ConferenceAbstractDTO;
import com.ejdoab.tcms.services.dto.exceptions.DTOUpdateException;

/**
 * 
 * @author Brian Sam-Bodden
 */
public class AbstractsPage extends BaseTCMSView {
	TableViewer _tableViewer = null;

	Splitter _subForm;
	Splitter _mainForm;

	CompareViewerPane _detailsForm;
	Composite _detailsSubForm;

	CompareViewerPane _textForm;
	Composite _textSubForm;

	CompareViewerPane _abstractsTableForm;
	Composite _abstractsTableSubForm;

	StyledText _text;

	AbstractsDetailsWidget _details;
	
	AbstractsModel _model;
	
	private ConferenceAbstractDTO _selected = null;
	private boolean _textModified = false;	

	/**
	 * @param arg0
	 * @param arg1
	 */
	public AbstractsPage(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout());
		
		_mainForm = new Splitter(this, SWT.HORIZONTAL);
		createTableViewForm(_mainForm);

		_subForm = new Splitter(_mainForm, SWT.VERTICAL);
		createTextEditorForm(_subForm);
		createDetailsForm(_subForm);	
	}

	/**
	 * @param sashForm
	 */
	private void createTableViewForm(SashForm sashForm) {
		_abstractsTableForm = new CompareViewerPane(sashForm, SWT.BORDER);
		_abstractsTableForm.setText("Abstracts");
		_abstractsTableForm.setImage(Resources.getImageRegistry().get("icons_abstracts"));

		SWTUtil.CLabelFocusListener gradientHandler =
			new SWTUtil.CLabelFocusListener(_abstractsTableForm);
		SWTUtil.CLabelMouseAdapter cma =
			new SWTUtil.CLabelMouseAdapter(_abstractsTableForm);

		_abstractsTableSubForm = new Composite(_abstractsTableForm, SWT.NONE);
		_abstractsTableSubForm.setLayout(new FillLayout());

		_tableViewer = new TableViewer(_abstractsTableSubForm, SWT.BORDER);
		_tableViewer.setContentProvider(
			new AbstractsTableContentProvider());
		_tableViewer.setLabelProvider(
			new AbstractTableLabelProvider());

		SWTUtil.addTableColumn(
			_tableViewer,
			"Title",
			140,
			new AbstractsViewerSorter(
				AbstractsViewerSorter.SORT_CRITERIA_TITLE));
				
		SWTUtil.addTableColumn(
			_tableViewer,
			"Type",
			60,
			new AbstractsViewerSorter(
				AbstractsViewerSorter.SORT_CRITERIA_TYPE));
				
		SWTUtil.addTableColumn(
			_tableViewer,
			"Level",
			60,
			new AbstractsViewerSorter(
				AbstractsViewerSorter.SORT_CRITERIA_LEVEL));
				
		SWTUtil.addTableColumn(
			_tableViewer,
			"Presenter",
			140,
			new AbstractsViewerSorter(
				AbstractsViewerSorter.SORT_CRITERIA_PRESENTER));

		_tableViewer.getTable().setHeaderVisible(true);

		_tableViewer
			.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {
				if (_details.isModified() || _textModified) {
					// prompt to save modifications
					checkToSave(_selected);
				}
				IStructuredSelection selection =
					(IStructuredSelection) event.getSelection();
				_selected = (ConferenceAbstractDTO) selection.getFirstElement();
				_text.removeModifyListener(_textModifiedListener);
				if (_selected != null) {
					_text.setText(_selected.getBody());
					_text.addModifyListener(_textModifiedListener);
					_details.setModel(_selected);
				}
			}
		});

		_tableViewer.getTable().addFocusListener(gradientHandler);

		_abstractsTableForm.setContent(_abstractsTableSubForm);
	}


	
	private class TextModifiedListener implements ModifyListener {

		public void modifyText(ModifyEvent event) {
			_textModified = true;
			_selected.setBody(_text.getText());
		}
	}
	
	private TextModifiedListener _textModifiedListener = new TextModifiedListener();

	/**
	 * @param sashForm
	 */
	private void createTextEditorForm(SashForm sashForm) {
		_textForm = new CompareViewerPane(sashForm, SWT.BORDER);
		_textForm.setText("Abstract's Text");
		_textForm.setImage(Resources.getImageRegistry().get("icons_abstract_edit"));
		SWTUtil.CLabelFocusListener gradientHandler =
			new SWTUtil.CLabelFocusListener(_textForm);
		SWTUtil.CLabelMouseAdapter cma =
			new SWTUtil.CLabelMouseAdapter(_textForm);
		_textSubForm = new Composite(_textForm, SWT.NONE);
		_textSubForm.setLayout(new FillLayout());

		_text =
			new StyledText(
				_textSubForm,
				SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.WRAP);
		_text.addFocusListener(gradientHandler);
		_textForm.setContent(_textSubForm);				
	}

	/**
	 * @param sashForm
	 */
	private void createDetailsForm(SashForm sashForm) {
		_detailsForm = new CompareViewerPane(sashForm, SWT.BORDER);
		_detailsForm.setText("Details");
		_detailsForm.setImage(Resources.getImageRegistry().get("icons_details"));
		SWTUtil.CLabelFocusListener gradientHandler =
			new SWTUtil.CLabelFocusListener(_detailsForm);
		SWTUtil.CLabelMouseAdapter cma =
			new SWTUtil.CLabelMouseAdapter(_detailsForm);

		_detailsSubForm = new Composite(_detailsForm, SWT.NONE);
		_detailsSubForm.setLayout(new FillLayout());

		_details = new AbstractsDetailsWidget(_detailsSubForm);	
		_details.addFocusListener(gradientHandler);
		_detailsForm.setContent(_detailsSubForm);
	}

	public static Object[] abstracts = null;
	public String[] topics = null;
	public String[] levels = null;
	public String[] types = null;
	public String[] status = null;

	/**
	 * @param args
	 */
	private ConferenceServicesProxy _service;

	private void checkToSave(ConferenceAbstractDTO selected) {
		MessageDialog dialog =
			new MessageDialog(
				this.getShell(),
				"Abstract Modified",
				null,
				"Abstract Information for ["
					+ selected.getTitle()
					+ "] has been modified. Commit changes to the server?",
				MessageDialog.QUESTION,
				new String[] {
					"&Commit",
					"&Ignore Changes",
					IDialogConstants.CANCEL_LABEL },
				0);

		dialog.open();
		int rc = dialog.getReturnCode();
		switch (rc) {
			case 0 : // save the changes
				try {
					System.out.println("Commit selected");
					if (_details.isModified()) {
						_details.applyChanges();					
					}
										
					if (_service.submitAbstract(selected)) {
						System.out.println("commit succeded");
						_details.setModified(false);
						_textModified = false;
						_model.setModified();
					}						
					
				} catch (DTOUpdateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (RemoteServerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 1 : // don't save the changes
				_details.setModified(false);
				_textModified = false;
			case 2 : // cancel
				break;
		}
	}
	
	/**
	 * @param strings
	 */
	public void setLevels(String[] strings) {		
		_details.setLevels(strings);		
	}

	/**
	 * @param strings
	 */
	public void setStatus(String[] strings) {
		_details.setStatus(strings);			
	}

	/**
	 * @param strings
	 */
	public void setTopics(String[] strings) {
		_details.setTopics(strings);
	}

	/**
	 * @param strings
	 */
	public void setTypes(String[] strings) {
		_details.setTypes(strings);
	}	
	
	public void loadData(Object[] data) {	
		if (_tableViewer != null) {
			_model = new AbstractsModel(data);
			_tableViewer.setInput(_model);
		}
	}

	/**
	 * @param proxy
	 */
	public void setService(ConferenceServicesProxy proxy) {
		_service = proxy;
	}

}
