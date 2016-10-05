package com.ejdoab.tcms.admin.abstracts;

import java.util.ArrayList;
import java.util.List;

import com.ejdoab.tcms.services.dto.ConferenceAbstractDTO;
import org.apache.commons.collections.CollectionUtils;

/**
 * @author Brian Sam-Bodden
 *
 */
public class AbstractsModel {
	List _abstracts;
	AbstractsModelListener _listener;
	
	public AbstractsModel(Object[] abstracts) {			
		_abstracts = new ArrayList();
		CollectionUtils.addAll(_abstracts, abstracts);
	}
	
	public Object[] getElements() {
		return _abstracts.toArray();
	}
	
	public void addAbstract(ConferenceAbstractDTO dto) {
		_abstracts.add(dto);
		fireStructureChanged();
	}
	
	public void removeAbstract(ConferenceAbstractDTO dto) {
		_abstracts.remove(dto);
		fireStructureChanged();
	}
	
	public void setModified() {
		fireStructureChanged();
	}
	
	/**
	 * 
	 */
	private void fireStructureChanged() {
		if (_listener != null) {
			_listener.structureChanged();
		}
	}
	
	//
	// Listener methods
	//
	
	public void addListener(AbstractsModelListener listener) {
		_listener = listener;
	}
	
	public void removeListener(AbstractsModelListener listener) {
		if (_listener.equals(listener)) {
			_listener = null;
		}
	}	



}
