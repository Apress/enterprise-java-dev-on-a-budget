package com.ejdoab.tcms.admin.news;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.io.FileUtils;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import com.ejdoab.swt.util.SWTUtil;
import com.ejdoab.tcms.util.Dates;

import com.ejdoab.tcms.admin.remote.ConferenceServicesProxy;
import com.ejdoab.tcms.admin.remote.RemoteServerException;
import com.ejdoab.tcms.services.dto.NewsItemDTO;
import com.ejdoab.tcms.services.dto.exceptions.DTOUpdateException;

/**
 * @author Brian Sam-Bodden 
 */
public class NewsItemWizard extends Wizard {

	// wizard page instances 
	private NewsItemBasicInfoPage basicInfoPage = new NewsItemBasicInfoPage();
	private NewsItemTextPage textPage = new NewsItemTextPage();
	
	private ConferenceServicesProxy _service;
	private NewsModel _model;	
	private String _dateString;
	private Date _date;

	public NewsItemWizard(NewsModel model, String date) {
		_model = model;
		_dateString = date;
		
		DateFormat format = Dates.createDateFormat(Dates.DATE_MMddyyyy);
		try {
			_date = format.parse(_dateString);
		} catch (ParseException e) {
			//TODO what do we do here?
		}		
		setWindowTitle("New News Article");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.IWizard#addPages()
	 */
	public void addPages() {
		addPage(basicInfoPage);
		addPage(textPage);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.IWizard#performFinish()
	 */
	public boolean performFinish() {
		String title = basicInfoPage.getArticleTitle();
		Date creationDate = basicInfoPage.getCreationDateAsDate();
		String creationDateAsString = basicInfoPage.getCreationDate();
		Date removalDate = basicInfoPage.getRemoveDateAsDate();
		String removalDateAsString = basicInfoPage.getRemoveDate();
		String body = textPage.getArticleBody();

		System.out.println(
			"Creating new article "
				+ title
				+ '\n'
				+ "created on "
				+ creationDateAsString
				+ '\n'
				+ "to be removed on "
				+ removalDateAsString
				+ '\n'
				+ " contents: \n"
				+ body);
				
		boolean submitted = false;
						
		NewsItemDTO dto = new NewsItemDTO(-1, _date, removalDate, creationDate, false, title, body);
		if (_service != null) {
			try {
				submitted = _service.submitNewsItem(dto);	
				if (submitted) {
					_model.add(dto);			
				}
			} catch (DTOUpdateException e) {
				submitted = false;
			} catch (RemoteServerException e) {
				submitted = false;
			}
		}
		else {
			//TODO log that there was no remote service available
		}
		

		return submitted;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.IWizard#canFinish()
	 */
	public boolean canFinish() {
		return basicInfoPage.canFlipToNextPage()
			&& (StringUtils.isNotEmpty(textPage.getArticleBody()));
	}

	/**
	 * Convenience utility method to display the Wizard
	 */
	public void showWizard() {
		WizardDialog dialog = new WizardDialog(getShell(), this);
		dialog.open();
	}
	
	/**
	 * @param proxy
	 */
	public void setService(ConferenceServicesProxy proxy) {
		_service = proxy;
	}	

	// ------------------------------------------------------------------------
	// Wizard Pages
	// ------------------------------------------------------------------------

	//
	// NewsItemBasicInfoPage Wizard Page
	//
	private class NewsItemBasicInfoPage
		extends WizardPage
		implements Listener {
		public NewsItemBasicInfoPage() {
			super("NewsItemBasicInfoPage");
			setTitle("News Article");
			setDescription(""); //TODO where does this show up?
			setMessage("Create a New News Article");
		}

		public void createControl(Composite parent) {
			Composite composite = new Composite(parent, SWT.NULL);
			GridLayout layout = new GridLayout();
			layout.numColumns = 2;
			composite.setLayout(layout);

			// title field
			SWTUtil.createLabel(composite, "Article's Title:", SWT.NULL);
			_title = SWTUtil.createText(composite, "", SWT.SINGLE | SWT.BORDER);
			{
				GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
				_title.setLayoutData(gridData);
			}

			// add a separator
			SWTUtil.createHorizontalGridSeparator(composite);

			// created on field
			SWTUtil.createLabel(composite, "Created on:", SWT.NULL);
			_creationDate =
				SWTUtil.createText(composite, "", SWT.SINGLE | SWT.BORDER);
			{
				GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
				_creationDate.setLayoutData(gridData);
			}

			// remove on field
			SWTUtil.createLabel(composite, "Remove on:", SWT.NULL);
			_removeDate =
				SWTUtil.createText(composite, "", SWT.SINGLE | SWT.BORDER);
			{
				GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
				_removeDate.setLayoutData(gridData);
			}

			setControl(composite);
			setDefaults();
			addListeners();
		}

		private Text _title;
		private Text _removeDate;
		private Text _creationDate;
		private Date _creationDateAsDate;
		private Date _removalDateAsDate;

		boolean _canFlipToNextPage = false;

		/* (non-Javadoc)
		 * @see org.eclipse.jface.wizard.IWizardPage#canFlipToNextPage()
		 */
		public boolean canFlipToNextPage() {
			boolean validateDates = false;
			boolean result = true;

			if (StringUtils.isEmpty(getArticleTitle())) {
				result = false;

			} else if ((getArticleTitle().length() > 32)) {
				result = false;
			}

			if (StringUtils.isEmpty(getCreationDate())) {
				result = false;
			} else if (
				!Dates.isInFormat(getCreationDate(), Dates.DATE_MMddyyyy)) {
				result = false;
			} else {
				validateDates = true;
			}

			if (StringUtils.isEmpty(getRemoveDate())) {
				result = false;
			} else if (
				!Dates.isInFormat(getRemoveDate(), Dates.DATE_MMddyyyy)) {
				result = false;
			} else {
				validateDates = validateDates && true;
			}

			if (validateDates) {
				DateFormat format = Dates.createDateFormat(Dates.DATE_MMddyyyy);
				try {
					_creationDateAsDate = format.parse(getCreationDate());
					_removalDateAsDate = format.parse(getRemoveDate());

					if (_creationDateAsDate.after(_removalDateAsDate)) {
						result = false;
					}
				} catch (ParseException e) {
					result = false;
				}
			}

			return result;
		}

		/**
		 * @return
		 */
		public String getCreationDate() {
			return _creationDate.getText();
		}

		public Date getCreationDateAsDate() {
			return _creationDateAsDate;
		}

		/**
		 * @return
		 */
		public String getRemoveDate() {
			return _removeDate.getText();
		}

		/**
		 * @return
		 */
		public Date getRemoveDateAsDate() {
			return _removalDateAsDate;
		}

		/**
		 * @return
		 */
		public String getArticleTitle() {
			return _title.getText();
		}

		/* 
		 * Untyped listener
		 * 
		 * (non-Javadoc)
		 * @see org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.Event)
		 */
		public void handleEvent(Event event) {
			// Guideline 4.5 - from http://www.eclipse.org/articles/Article-UI-Guidelines
			// Validate the wizard data in tab order.  Display a prompt when 
			// information is absent, and an error when information is invalid.
			setErrorMessage(null);
			setMessage("Create a New News Article");

			boolean validateDates = false;

			if (event.widget == _title) {
				if (StringUtils.isEmpty(getArticleTitle())) {
					setMessage(
						"Enter the new Article's Title",
						WizardPage.NONE);
				} else if (
					(getArticleTitle().length() > 32)
						&& (event.type == SWT.KeyUp)) {
					setMessage(
						"Article Title cannot be longer than 32 characters",
						WizardPage.ERROR);
				}
			} else if (event.widget == _creationDate) {
				if (StringUtils.isEmpty(getCreationDate())) {
					setMessage(
						"Enter/Modify the Article's Creation Date",
						WizardPage.NONE);
				} else if (
					!Dates.isInFormat(getCreationDate(), Dates.DATE_MMddyyyy)
						&& (event.type == SWT.KeyUp)) {
					setMessage(
						"Creation Date must be in MM-dd-yyyy format",
						WizardPage.ERROR);
				} else {
					validateDates = (event.type == SWT.KeyUp);
				}
			} else if (event.widget == _removeDate) {
				if (StringUtils.isEmpty(getRemoveDate())) {
					setMessage(
						"Enter the Article's Removal Date",
						WizardPage.NONE);
				} else if (
					!Dates.isInFormat(getRemoveDate(), Dates.DATE_MMddyyyy)
						&& (event.type == SWT.KeyUp)) {
					setMessage(
						"Removal Date must be in MM-dd-yyyy format",
						WizardPage.ERROR);
				} else {
					validateDates = (event.type == SWT.KeyUp);
				}
			}

			if (validateDates) {
				DateFormat format = Dates.createDateFormat(Dates.DATE_MMddyyyy);
				try {
					Date creationDate = format.parse(getCreationDate());
					Date removalDate = format.parse(getRemoveDate());

					if (creationDate.after(removalDate)) {
						setMessage(
							"Removal Date cannot be before Creation Date",
							WizardPage.ERROR);
					}
				} catch (ParseException e) {
					// add an assertion here this should never happen!
				}
			}

			// update the wizard buttons by invoking canFinish and canFlipToNextPage
			getWizard().getContainer().updateButtons();
		}

		/**
		 * 
		 */
		private void addListeners() {
			_title.addListener(SWT.KeyUp, this);
			_removeDate.addListener(SWT.KeyUp, this);
			_creationDate.addListener(SWT.KeyUp, this);
			_title.addListener(SWT.FocusIn, this);
			_removeDate.addListener(SWT.FocusIn, this);
			_creationDate.addListener(SWT.FocusIn, this);
		}

		/**
		 * 
		 */
		private void setDefaults() {
			_creationDate.setText(
				Dates.format(System.currentTimeMillis(), Dates.DATE_MMddyyyy));
		}

	}

	//
	// NewsItemTextPage Wizard Page
	//
	private class NewsItemTextPage extends WizardPage implements Listener {
		public NewsItemTextPage() {
			super("NewsItemTextPage");
			setTitle("News Article");
			setDescription(""); //TODO where does this show up?
			setMessage("Create a New News Article");
		}

		public void createControl(Composite parent) {
			Composite composite = new Composite(parent, SWT.NULL);
			GridLayout layout = new GridLayout();
			int columns = 3;
			layout.numColumns = columns;
			composite.setLayout(layout);

			// file field editor
			_fileFieldEditor =
				new FileFieldEditor("fileFieldEditor", "File Name:", composite);
			_fileFieldEditor.setEmptyStringAllowed(false);
			{
				GridData gridData = new GridData();
				gridData.widthHint = 300;
				_fileFieldEditor.getTextControl(composite).setLayoutData(
					gridData);
			}

			// get the text field out of the editor
			_fileName = _fileFieldEditor.getTextControl(composite);
			_fileName.setEditable(false);

			// add a separator
			SWTUtil.createHorizontalGridSeparator(composite);

			// article body field
			SWTUtil.createLabel(composite, "Article Body:", SWT.NULL);

			_text =
				new StyledText(
					composite,
					SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.WRAP);
			{
				GridData gridData = new GridData(GridData.FILL_BOTH);
				gridData.horizontalSpan = columns;
				_text.setLayoutData(gridData);
			}

			setControl(composite);
			addListeners();
		}

		private FileFieldEditor _fileFieldEditor;
		private StyledText _text;
		private Text _fileName;

		public String getArticleBody() {
			return StringUtils.trim(_text.getText());
		}

		private void addListeners() {
			_text.addListener(SWT.Modify, this);
			_text.addListener(SWT.FocusIn, this);
			_fileName.addListener(SWT.Modify, this);
		}

		/* (non-Javadoc)
		 * @see org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.Event)
		 */
		public void handleEvent(Event event) {
			if (event.widget == _text) {
				switch (event.type) {
					case SWT.Modify :
						if (StringUtils.isEmpty(getArticleBody())) {
							setMessage(
								"Enter the new Article's Body",
								WizardPage.NONE);
						}
						break;
					case SWT.FocusIn :
						setMessage(
							"Enter the new Article's Body",
							WizardPage.NONE);
						break;
				}
			} else if (event.widget == _fileName) {
				switch (event.type) {
					case SWT.Modify :
						System.out.println("File name text changed");
						try {
							String fileContents =
								FileUtils.fileRead(_fileName.getText());
							_text.setText(fileContents);
						} catch (IOException e) {
							setMessage(
								"File " + _fileName.getText() + " not found",
								WizardPage.ERROR);
						}
						break;
				}
			}
			// update the wizard buttons by invoking canFinish and canFlipToNextPage
			getWizard().getContainer().updateButtons();			
		}
	}

}
