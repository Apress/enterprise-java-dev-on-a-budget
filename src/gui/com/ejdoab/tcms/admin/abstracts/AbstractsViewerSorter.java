package com.ejdoab.tcms.admin.abstracts;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

import com.ejdoab.tcms.services.dto.ConferenceAbstractDTO;

/**
 * Used by the table viewer to reorder the elements provided by its 
 * content provider. 

 * @author Brian Sam-Bodden 
 */
public class AbstractsViewerSorter extends ViewerSorter {

	public final static int SORT_CRITERIA_TITLE	= 0;
	public final static int SORT_CRITERIA_TYPE = 1; 				
	public final static int SORT_CRITERIA_LEVEL	= 2;
	public final static int SORT_CRITERIA_PRESENTER	= 3;

	private int _criteria;

	public AbstractsViewerSorter(int criteria) {
		super();
		_criteria = criteria;
	}

	public int compare(Viewer viewer, Object o1, Object o2) {

		ConferenceAbstractDTO dto1 = (ConferenceAbstractDTO) o1;
		ConferenceAbstractDTO dto2 = (ConferenceAbstractDTO) o2;

		switch (_criteria) {
			case SORT_CRITERIA_TITLE :
				return collator.compare(dto1.getTitle(), dto2.getTitle());
			case SORT_CRITERIA_TYPE :
				return collator.compare(dto1.getType(), dto2.getType());
			case SORT_CRITERIA_LEVEL :
				return collator.compare(dto1.getLevel(), dto2.getLevel());
			case SORT_CRITERIA_PRESENTER :
				return collator.compare(dto1.getPresenter(), dto2.getPresenter());				
			default:
				return 0;
		}
	}

	/**
	 * Returns the sort criteria of this this sorter.
	 *
	 * @return the sort criterion
	 */
	public int getCriteria() {
		return _criteria;
	}

}
