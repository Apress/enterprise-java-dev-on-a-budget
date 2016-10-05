/* ========================================================================= *
 *                                                                           *
 *                   Enterprise Java Development on a Budget                 *
 *                   Technology Conference Management System                 *
 *                              www.ejdoab.com                               *
 *            Licensed under The Apache Software License, Version 1.1        *
 *                                                                           *
 *            Copyright (c) 2003 Brian Sam-Bodden, Christopher M. Judd       *
 *                          All rights reserved.                             *
 *                                                                           *
 * ========================================================================= */

package com.ejdoab.tcms.ws;

import com.ejdoab.tcms.services.*;
import com.ejdoab.tcms.services.dto.*;
import com.ejdoab.tcms.services.dto.page.*;

import java.rmi.*;

import java.util.*;


/**
 * Web service for getting news.
 * @author cjudd
 */
public class News {
    /**
     * Gets all current news items
     *
     * @return An array of news items
     *
     * @throws RemoteException Any possible exception.
     */
    public NewsItemDTO[] getNews() throws RemoteException {
        try {
            Calendar currentCalendar = Calendar.getInstance();
            Calendar newsCalendar = Calendar.getInstance();
            List currentNews = new ArrayList();
            ConferenceServicesLocalHome cslh = ConferenceServicesUtil.getLocalHome();
            ConferenceServicesLocal csl = cslh.create();
            Page page = csl.getNews(0, csl.getNewsCount());

            while (page.hasNext()) {
                NewsItemDTO item = (NewsItemDTO) page.next();

                newsCalendar.setTime(item.getRemovedate());

                if (item.isPublished() && currentCalendar.before(newsCalendar)) {
                    currentNews.add(item);
                }
            }

            NewsItemDTO[] items = new NewsItemDTO[currentNews.size()];
            int i = 0;

            for (Iterator iter = currentNews.iterator(); iter.hasNext(); i++) {
                items[i] = (NewsItemDTO) iter.next();
            }

            return items;
        } catch (Exception ex) {
            throw new RemoteException(ex.getMessage(), ex);
        }
    }
}
