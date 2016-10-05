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

package com.ejdoab.tcms.services.dto;

import com.ejdoab.tcms.entities.ConferenceNewsLocal;
import com.ejdoab.tcms.entities.ConferenceNewsLocalHome;
import com.ejdoab.tcms.entities.ConferenceNewsUtil;
import com.ejdoab.tcms.services.dto.exceptions.DTOCreateException;
import com.ejdoab.tcms.services.dto.exceptions.DTOUpdateException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import javax.naming.NamingException;


/**
 * @author Brian Sam-Bodden
 */
public class NewsItemDTOFactory implements DTOFactory {
    private static Log log = LogFactory.getLog(NewsItemDTOFactory.class);

    /* (non-Javadoc)
     * @see com.ejdoab.tcms.services.dto.DTOFactory#getDTO(java.lang.Object)
     */
    public DTO getDTO(Object obj) throws DTOCreateException {
        if (obj instanceof ConferenceNewsLocal) {
            try {
                ConferenceNewsLocal newsItem = (ConferenceNewsLocal) obj;

                return buildScheduleDTO(newsItem);
            } catch (Exception e) {
                e.printStackTrace();
                throw new DTOCreateException(e);
            }
        } else {
            throw new DTOCreateException(
                "invalid source object for DTO creation");
        }
    }

    /**
     * @param newsItem
     * @return
     */
    private DTO buildScheduleDTO(ConferenceNewsLocal newsItem) {
        int newsItemId = newsItem.getId().intValue();
        Date date = newsItem.getDate();
        Date removedate = newsItem.getRemoveDate();
        Date creationdate = newsItem.getCreationDate();
        boolean published = newsItem.getPublished();
        String body = newsItem.getBody();
        String title = newsItem.getTitle();

        NewsItemDTO dto = new NewsItemDTO(newsItemId, date, removedate,
                creationdate, published, title, body);

        return dto;
    }

    /* (non-Javadoc)
     * @see com.ejdoab.tcms.services.dto.DTOFactory#setDTO(com.ejdoab.tcms.services.dto.DTO)
     */
    public boolean setDTO(DTO dto) throws DTOUpdateException {
        if (dto instanceof NewsItemDTO) {
            NewsItemDTO newsItem = (NewsItemDTO) dto;

            return saveNewsItem(newsItem);
        } else {
            throw new DTOUpdateException(
                "Attempting to update with an invalid DTO class");
        }
    }

    /**
     * @param newsItem
     * @return
     */
    private boolean saveNewsItem(NewsItemDTO dto) throws DTOUpdateException {
        ConferenceNewsLocal news = null;
        ConferenceNewsLocalHome cnlh = null;

        try {
            cnlh = ConferenceNewsUtil.getLocalHome();
        } catch (NamingException ne) {
            throw new DTOUpdateException("Exception getting ConferenceNewsLocalHome",
                ne);
        }

        Integer id = new Integer(dto.getNewsItemId());

        Date date = dto.getDate();
        Date removeDate = dto.getRemovedate();
        Date creationDate = dto.getCreationdate();
        boolean published = dto.isPublished();
        String body = dto.getBody();
        String title = dto.getTitle();

        if (id.intValue() == -1) {
            try {
                news = cnlh.create(date, removeDate, creationDate, published,
                        title, body);
            } catch (CreateException ce) {
                throw new DTOUpdateException("Could not create news item", ce);
            }
        } else {
            try {
                news = cnlh.findByPrimaryKey(id);
                news.setDate(date);
                news.setRemoveDate(removeDate);
                news.setCreationDate(creationDate);
                news.setTitle(title);
                news.setBody(body);
                news.setPublished(published);
            } catch (FinderException fe) {
                throw new DTOUpdateException("There is no news item with id=" +
                    id, fe);
            }
        }

        return news != null;
    }

    /* (non-Javadoc)
     * @see com.ejdoab.tcms.services.dto.DTOFactory#removeDTO(com.ejdoab.tcms.services.dto.DTO)
     */
    public boolean removeDTO(DTO dto) throws DTOUpdateException {
        if (dto instanceof NewsItemDTO) {
            NewsItemDTO newsItem = (NewsItemDTO) dto;
            removeNewsItem(newsItem);

            return true;
        } else {
            throw new DTOUpdateException(
                "Attempting to delete with an invalid DTO class");
        }
    }

    /**
     * @param dto
     * @throws DTOUpdateException
     */
    public void removeNewsItem(NewsItemDTO dto) throws DTOUpdateException {
        ConferenceNewsLocal news = null;
        ConferenceNewsLocalHome cnlh = null;

        try {
            cnlh = ConferenceNewsUtil.getLocalHome();
        } catch (NamingException ne) {
            throw new DTOUpdateException("Exception getting ConferenceNewsLocalHome",
                ne);
        }

        Integer id = new Integer(dto.getNewsItemId());

        if (id.intValue() != -1) {
            try {
                cnlh.remove(id);
            } catch (EJBException ejbe) {
                throw new DTOUpdateException("Exception removing dto", ejbe);
            } catch (RemoveException re) {
                throw new DTOUpdateException("Exception removing dto", re);
            }
        }
    }
}
