package com.ejdoab.tcms.admin.news;

import org.eclipse.compare.CompareViewerPane;
import org.eclipse.compare.Splitter;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import com.ejdoab.swt.util.SWTUtil;
import com.ejdoab.tcms.admin.BaseTCMSView;
import com.ejdoab.tcms.admin.Resources;
import com.ejdoab.tcms.admin.remote.ConferenceServicesProxy;
import com.ejdoab.tcms.admin.remote.RemoteServerException;
import com.ejdoab.tcms.services.dto.NewsItemDTO;
import com.ejdoab.tcms.services.dto.exceptions.DTOUpdateException;

/**
 * 
 * @author Brian Sam-Bodden
 */
public class NewsPage extends BaseTCMSView {
	TreeViewer _treeViewer = null;

	Splitter _subForm;
	Splitter _mainForm;

	CompareViewerPane _detailsForm;
	Composite _detailsSubForm;

	CompareViewerPane _textForm;
	Composite _textSubForm;

	CompareViewerPane _newsTreeForm;
	Composite _newsTreeSubForm;

	StyledText _text;

	NewsItemDetailsWidget _details;
	
	private NewsItemDTO selected = null;
	private boolean _textModified = false;
	private NewsModel _model;	

	/**
	 * @param arg0
	 * @param arg1
	 */
	public NewsPage(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout());

		_mainForm = new Splitter(this, SWT.HORIZONTAL);
		createTreeViewForm(_mainForm);

		_subForm = new Splitter(_mainForm, SWT.VERTICAL);
		createTextEditorForm(_subForm);
		createDetailsForm(_subForm);
	}

	/**
	 * @param sashForm
	 * 
	 */
	private void createTreeViewForm(SashForm sashForm) {
		_newsTreeForm = new CompareViewerPane(sashForm, SWT.BORDER);
		_newsTreeForm.setText("News");
		_newsTreeForm.setImage(Resources.getImageRegistry().get("icons_news"));

		SWTUtil.CLabelFocusListener gradientHandler =
			new SWTUtil.CLabelFocusListener(_newsTreeForm);
		SWTUtil.CLabelMouseAdapter cma =
			new SWTUtil.CLabelMouseAdapter(_newsTreeForm);

		_newsTreeSubForm = new Composite(_newsTreeForm, SWT.NONE);
		_newsTreeSubForm.setLayout(new FillLayout());

		_treeViewer = new TreeViewer(_newsTreeSubForm);
		
		Tree tree = _treeViewer.getTree();		

		_treeViewer.setContentProvider(new NewsTreeContentProvider());
		_treeViewer.setLabelProvider(new NewsTreeLabelProvider());

		_newsTreeForm.setContent(_newsTreeSubForm);

		tree.addFocusListener(gradientHandler);

		_treeViewer
			.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection =
					(IStructuredSelection) event.getSelection();

				Object o = selection.getFirstElement();

				if (o instanceof NewsItemDTO) {
					if (_details.isModified() || _textModified) {
						// prompt to save modifications
						checkToSave(selected);
					}
					selected = (NewsItemDTO) o;
					_text.removeModifyListener(_textModifiedListener);
					_text.setText(selected.getBody());
					_text.addModifyListener(_textModifiedListener);
					_details.setModel(selected);
				}
			}
		});
		
		//
		// listener examples
		//

		// this is equivalent to the listener above but on the SWT widget instead of the JFace viewer
		tree.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event arg0) {
				System.out.println("SWT.Selection Event!");
			}
		});

		tree.addListener(SWT.MouseDoubleClick, new Listener() {
			public void handleEvent(Event arg0) {
				System.out.println("SWT.MouseDoubleClick Event!");
			}
		});

		tree.addListener(SWT.KeyDown, new Listener() {
			public void handleEvent(Event arg0) {
				System.out.println("SWT.KeyDown Event!");
			}
		});

		tree.addListener(SWT.Expand, new Listener() {
			public void handleEvent(Event e) {
				TreeItem item = (TreeItem) e.item;
				item.setImage(Resources.getImageRegistry().get("icons_folder_open"));
				System.out.println("SWT.Expand Event!");
			}
		});

		tree.addListener(SWT.Collapse, new Listener() {
			public void handleEvent(Event e) {
				TreeItem item = (TreeItem) e.item;
				item.setImage(Resources.getImageRegistry().get("icons_folder_close"));
				System.out.println("SWT.Collapse Event!");
			}
		});

		// create the context menu for the tree
		tree.setMenu(createTreePopupMenu(tree));

	}



	private class TextModifiedListener implements ModifyListener {

		public void modifyText(ModifyEvent event) {
			_textModified = true;
			selected.setBody(_text.getText());
		}
	}

	private TextModifiedListener _textModifiedListener =
		new TextModifiedListener();

	/**
	 * @param sashForm
	 */
	private void createTextEditorForm(SashForm sashForm) {
		_textForm = new CompareViewerPane(sashForm, SWT.BORDER);
		_textForm.setText("News Article Text");
		_textForm.setImage(Resources.getImageRegistry().get("icons_news_item_edit"));
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

		_details = new NewsItemDetailsWidget(_detailsSubForm);
		_details.addFocusListener(gradientHandler);
		_detailsForm.setContent(_detailsSubForm);
	}

	public static Object[] news = null;

	/**
	 * @param args
	 */
	private static ConferenceServicesProxy _service;

	private void checkToSave(NewsItemDTO selected) {
		MessageDialog dialog =
			new MessageDialog(
				this.getShell(),
				"News Item Modified",
				null,
				"News Item ["
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
					
					if (_service.submitNewsItem(selected)) {
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

	public void loadData(Object[] items) {
		_model = new NewsModel(items);
		_treeViewer.setInput(_model);
	}

	/**
	 * @param proxy
	 */
	public void setService(ConferenceServicesProxy proxy) {
		_service = proxy;
	}

	///
	/// popup
	///

	public Menu createTreePopupMenu(final Tree tree) {
		final Menu menu = new Menu(tree.getShell(), SWT.POP_UP);

		final MenuItem addNewsArticleMenuItem = new MenuItem(menu, SWT.PUSH);
		addNewsArticleMenuItem.setText("Add News Item");
		addNewsArticleMenuItem.setImage(Resources.getImageRegistry().get("icons_plus"));
		addNewsArticleMenuItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				TreeItem item = tree.getSelection()[0];
				if (item != null) {
					NewsItemWizard wizard = new NewsItemWizard(_model, item.getText());
					wizard.setService(_service);
					wizard.showWizard();
				}
			}
		});
		
		final MenuItem deleteNewsArticleMenuItem = new MenuItem(menu, SWT.PUSH);
		deleteNewsArticleMenuItem.setText("Delete Article");
		deleteNewsArticleMenuItem.setImage(Resources.getImageRegistry().get("icons_minus"));
		deleteNewsArticleMenuItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				TreeItem item = tree.getSelection()[0];
				
				IStructuredSelection selection = (IStructuredSelection) _treeViewer.getSelection();
				Object o = selection.getFirstElement();
				if (o instanceof NewsItemDTO) {
					NewsItemDTO dto = (NewsItemDTO)o;
					// confirmation dialog
					
					// remove from the server
					if (_service != null) {
						try {							
							if (_service.removeNewsItem(dto)) {	
								_model.remove(dto);	
							}
						} catch (DTOUpdateException e) {
							// do nothing
						} catch (RemoteServerException e) {
							// do nothing
						}
					}
				}
			}
		});		
		
		menu.addListener(SWT.Show, new Listener() {
			public void handleEvent(Event e) {
				TreeItem [] treeItems = tree.getSelection ();
				
				if (treeItems == null) {
					e.doit = false;
					return;
				}	
				
				for (int i=0; i<treeItems.length; i++) {
					addNewsArticleMenuItem.setEnabled(treeItems[i].getParentItem() == null);
					deleteNewsArticleMenuItem.setEnabled(treeItems[i].getParentItem() != null);
				}
			}
		});

		return menu;
		
	}
	
}
