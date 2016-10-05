package com.ejdoab.tcms.admin.abstracts;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;

import com.ejdoab.tcms.util.Arrays;

/**
 * @author Brian Sam-Bodden 
 */
public class AbstractsTableContentProvider
	implements IStructuredContentProvider, AbstractsModelListener {
		
	AbstractsModel _model;
	TableViewer _viewer;

	public AbstractsTableContentProvider() {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
	 */
	public Object[] getElements(Object element) {
		if (_model != null) {
			return _model.getElements();
		}
		
		return Arrays.EMPTY_OBJECT_ARRAY;
	}

	public void dispose() {
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		_viewer = (TableViewer) viewer;

		if (oldInput != null) {
			AbstractsModel oldModel = (AbstractsModel) oldInput;
			oldModel.removeListener(this);
			_model = null;
		}

		if (newInput != null) {
			AbstractsModel newModel = (AbstractsModel) newInput;
			newModel.addListener(this);
			_model = newModel;
		}
	}

	/* (non-Javadoc)
	 * @see com.ejdoab.tcms.admin.abstracts.AbstractsModelListener#structureChanged()
	 */
	public void structureChanged() {
		_viewer.refresh();
	}
	
	

}
