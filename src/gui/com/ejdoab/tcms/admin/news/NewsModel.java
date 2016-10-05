package com.ejdoab.tcms.admin.news;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.ejdoab.tcms.services.dto.NewsItemDTO;
import com.ejdoab.tcms.util.Arrays;
import com.ejdoab.tcms.util.Dates;

/**
 * @author Brian Sam-Bodden
 */
public class NewsModel {
	
	private List _newsItems;	
	private Map _model = new HashMap();	
	
	private NewsModelListener _listener; 

	/**
	 * 
	 */
	public NewsModel(Object[] newsItems) {
		super();
		_newsItems = new ArrayList(newsItems.length);
		CollectionUtils.addAll(_newsItems, newsItems);
		createModel();	
	}
	
	public void add(NewsItemDTO newsItem) {	
		Date date = newsItem.getDate();
		String key = Dates.format(date, "MM-dd-yyyy");
		List items = null;
		if (!_model.containsKey(key)) {
			items = new ArrayList();
			_model.put(key, items);						
		}				
				
		if (items == null) {
			items = (List)_model.get(key);	
		}
				
		// add assertion here for items != null
		items.add(newsItem);
		fireModelChanged();		
	}
	
	protected void fireModelChanged() {
		if (_listener != null) {
			_listener.modelChanged();
		}
	}
	
	public void remove(NewsItemDTO newsItem) {
		Date date = newsItem.getDate();
		String key = Dates.format(date, "MM-dd-yyyy");
		List items = (List)_model.get(key);
				
		if (items != null) {
			items.remove(newsItem);	
			fireModelChanged();	
		}
	}
	
	/**
	 * 
	 */
	private void createModel() {
		if (_newsItems != null) {
			// build a list of dates to be used as the key in the map				
			Iterator iter = _newsItems.iterator();		
			
			for (int i=0, n = _newsItems.size(); i < n; i++) {
				NewsItemDTO dto = (NewsItemDTO)iter.next();
				Date date = dto.getDate();
				String key = Dates.format(date, "MM-dd-yyyy");
				List items = null;
				if (!_model.containsKey(key)) {
					items = new ArrayList();
					_model.put(key, items);						
				}				
				
				if (items == null) {
					items = (List)_model.get(key);	
				}
				
				// add assertion here for items != null
				items.add(dto);				
			}
		}		
	}	
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
	 */
	public Object[] getChildren(Object object) {
		if (object instanceof String) {
			if (_model.containsKey(object)) {
				List children = (List)_model.get(object);
				return children.toArray();
			}
		}
		
		return Arrays.EMPTY_OBJECT_ARRAY;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
	 */
	public Object getParent(Object object) {
		if (object instanceof NewsItemDTO) {
			NewsItemDTO dto = (NewsItemDTO)object;
			return Dates.format(dto.getDate(), "MM-dd-yyyy"); 
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
	 */
	public boolean hasChildren(Object object) {
		if (object instanceof String) {
			if (_model.containsKey(object)) {
				List children = (List)_model.get(object);
				return !children.isEmpty();
			}
		}		

		return false;
	}
	
	public Object[] getDates() {
		return _model.keySet().toArray();
	}
	
	//
	// Listener methods
	//
	
	public void addChangeListener(NewsModelListener listener) {
		_listener = listener;
	}
	
	public void removeChangeListener(NewsModelListener listener) {
		if (_listener.equals(listener)) {
			_listener = null;
		}
	}
	
	/**
	 * 
	 */
	public void setModified() {
		fireModelChanged();		
	}

}
