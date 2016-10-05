package com.ejdoab.tcms.admin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.ejdoab.tcms.util.NameValue;

/**
 * @author Brian Sam-Bodden 
 */
public class SystemPropertiesTableContentProvider
	implements IStructuredContentProvider {
		
	private List properties = new ArrayList();
		
	public SystemPropertiesTableContentProvider() {
	
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
	 */
	public Object[] getElements(Object element) {
		if (element instanceof Properties) {
			loadProperties((Properties)element);
		}
		return properties.toArray();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		if (oldInput != null) {
			properties.clear();
		}

		if (newInput != null) {
			Properties newModel = (Properties) newInput;
			loadProperties(newModel);
		}		
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
	 */
	public void dispose() {		
	}
	
	private void loadProperties(Properties props) {		
		Iterator i = props.keySet().iterator();

		for (int index = 0, n = props.size(); index < n; index++) {
			String name = (String) i.next();
			String value = props.getProperty(name);
			NameValue nv = new NameValue(name, value);
			System.out.println(nv);
			properties.add(nv);			
		}		
	}


}
