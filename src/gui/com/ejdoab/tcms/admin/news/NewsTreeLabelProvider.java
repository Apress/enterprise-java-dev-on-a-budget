package com.ejdoab.tcms.admin.news;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.ejdoab.tcms.admin.Resources;
import com.ejdoab.tcms.services.dto.NewsItemDTO;

/**
 * Label provider for News Tree
 * 
 * @author Brian Sam-Bodden
 */
public class NewsTreeLabelProvider extends LabelProvider {


	public String getText(Object element) {
		if (element instanceof NewsItemDTO) {
			NewsItemDTO dto = (NewsItemDTO)element;
			return dto.getTitle();
		}
		return element.toString();		
	}
	
	public Image getImage(Object element) {
		if (element instanceof NewsItemDTO) {
			return Resources.getImageRegistry().get("icons_news_item");
		}
		return Resources.getImageRegistry().get("icons_folder_close");
	}

}
