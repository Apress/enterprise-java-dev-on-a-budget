package com.ejdoab.tcms.admin.abstracts;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import com.ejdoab.tcms.admin.Resources;
import com.ejdoab.tcms.services.dto.ConferenceAbstractDTO;

/**
 * @author Brian Sam-Bodden 
 */
public class AbstractTableLabelProvider
	implements ITableLabelProvider {

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object, int)
	 */
	public Image getColumnImage(Object element, int column) {			
		if (column == 0) {
		  return Resources.getImageRegistry().get("icons_abstract");
		}			
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object, int)
	 */
	public String getColumnText(Object element, int column) {
		String result = null;
		if (element instanceof ConferenceAbstractDTO) {
			ConferenceAbstractDTO dto = (ConferenceAbstractDTO)element;
			
			switch (column) {
				case 0 :
				    result = dto.getTitle();
					break;
				case 1 :
				    result = dto.getType();
					break;
				case 2 :
					result = dto.getLevel();
					break;
				case 3 :
					result = dto.getPresenter();
					break;				
			}			
		}
		
		return result;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.eclipse.jface.viewers.ILabelProviderListener)
	 */
	public void addListener(ILabelProviderListener arg0) {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
	 */
	public void dispose() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang.Object, java.lang.String)
	 */
	public boolean isLabelProperty(Object arg0, String arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org.eclipse.jface.viewers.ILabelProviderListener)
	 */
	public void removeListener(ILabelProviderListener arg0) {
		// TODO Auto-generated method stub

	}

}
