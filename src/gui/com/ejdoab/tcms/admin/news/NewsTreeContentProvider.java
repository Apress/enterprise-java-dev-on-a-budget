package com.ejdoab.tcms.admin.news;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;

import com.ejdoab.tcms.util.Arrays;

/**
 * @author Brian Sam-Bodden
 */
public class NewsTreeContentProvider implements ITreeContentProvider, NewsModelListener {
	
	private NewsModel _model = null; 
	private TreeViewer _viewer = null;

	/**
	 * 
	 */
	public NewsTreeContentProvider() {
	}

	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
	 */
	public Object[] getChildren(Object object) {
		if (_model != null) {
			return _model.getChildren(object);
		}
		
		return Arrays.EMPTY_OBJECT_ARRAY;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
	 */
	public Object getParent(Object object) {
		if (_model != null) {
			return _model.getParent(object);
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
	 */
	public boolean hasChildren(Object object) {
		if (_model != null) {
			return _model.hasChildren(object);
		}

		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
	 */
	public Object[] getElements(Object object) {
		if (object instanceof NewsModel) {
			NewsModel model = (NewsModel)object;
			_model = model;
			return _model.getDates();
		}
		return Arrays.EMPTY_OBJECT_ARRAY;		
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
	 */
	public void dispose() {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		_viewer = (TreeViewer) viewer;	
		
		if (oldInput != null) {
			NewsModel oldModel = (NewsModel)oldInput;		
			oldModel.removeChangeListener(this);
		}
		
		if (newInput != null) {
			NewsModel newModel = (NewsModel)newInput;
			newModel.addChangeListener(this);
		}
	}

	/* (non-Javadoc)
	 * @see com.ejdoab.tcms.admin.news.NewsModelListener#modelChanged()
	 */
	public void modelChanged() {
		_viewer.refresh();	
	}

}
